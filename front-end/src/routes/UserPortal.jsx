import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import './UserPortal.css'
import { useEffect, useState } from 'react';

export default function UserPortal() {
  const navigate = useNavigate();
  let params = useParams();
  return (
    <>
    <div id="user-portal">
      <span>{params.username}</span>
      {/* <form id="numberInput" noValidate onSubmit={(e) => loginUser(e, navigate)}>
        <input type="text" placeholder="Username" required/>
        <input type="password" placeholder="Password" required/>
        <button>
          Log-in
        </button>
        <input type="submit" hidden tabIndex="-1"/>
      </form> */}
    </div>
    <LoginResult/>
    </>
  );
}

function LoginResult() {
  let params = useParams();
  const [userObject, setUser] = useState(null);
  const [orderArray, setOrderArray] = useState(null);

  async function getData() {
    let orderResponse = await axios.get(`http://localhost:8080/orders`);
    let userResponse = await axios.get(`http://localhost:8080/user/${params.username}`);
    setOrderArray(orderResponse.data);
    
    setUser(userResponse);
  }
  
  useEffect(() => {
    if ((!userObject || !orderArray) && params.username) {
      getData();
    }
  });

  async function updateStatus(id, value) {
    await axios.put(`http://localhost:8080/order/${id}?newStatus=${value}`);
  }

  return (
    <div>{userObject && Object.keys(userObject.data).length > 0 && (
      <>
      <span>Details:</span>
      <ul>
        <li key={userObject.data.name}>Name: {userObject.data.name}</li>
        <li key={userObject.data.surname}>Surname: {userObject.data.surname}</li>
      </ul>
      <span>Deliveries:</span>
      <ul>
          {orderArray.map(order => (

            <li key={order.ord_num}>
              Id: {order.ord_num} <br/>Pick up at: {order.sender_address} <br/>Deliver at: {order.receiver_address} 
              <br/>
              Status: 
              <select id="statusChoose" name="status" defaultValue={order.status}>
                <option value="delivered" onClick={(e) => {updateStatus(order.ord_num, e.target.value)}}>Delivered</option>
                <option value="warehouse" onClick={(e) => {updateStatus(order.ord_num, e.target.value)}}>Warehouse</option>
                <option value="on_route" onClick={(e) => {updateStatus(order.ord_num, e.target.value)}}>On Route</option>
              </select>
            </li>

          ))}

        </ul>
      </>
      
    )}
    </div>
  )
}