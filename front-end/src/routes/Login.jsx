import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import './Login.css'
import { useEffect, useState } from 'react';

async function loginUser(e, nav) {
  e.preventDefault();
  let response = await axios.get(`http://localhost:8080/user/login?username=${e.target[0].value}&password=${e.target[1].value}&pos=driver`);
  console.log(response);
  if (response && response.data) {
    nav(`/portal/employee/${e.target[0].value}`);
  }
  return false;
}

export default function Login() {
  const navigate = useNavigate();
  return (
    <>
    <div id="login-menu">
      <span>User Log-in:</span>
      <form id="numberInput" noValidate onSubmit={(e) => loginUser(e, navigate)}>
        <input type="text" placeholder="Username" required/>
        <input type="password" placeholder="Password" required/>
        <button>
          Log-in
        </button>
        <input type="submit" hidden tabIndex="-1"/>
      </form>
    </div>
    <LoginResult/>
    </>
  );
}

function LoginResult() {
  let params = useParams();
  const [orderObject, setOrder] = useState(null);

  async function getOrder() {
    let response = await axios.get(`http://localhost:8080/order/${params.trackingId}`);
    setOrder(response);
  }
  
  useEffect(() => {
    if (!orderObject && params.trackingId) {
      getOrder();
    }
  });

  return (
    <div>{orderObject && Object.keys(orderObject.data).length > 0 && (
      <ul>
        <li>Tracking number: {orderObject.data.ord_num}</li>
        <li>Status: {orderObject.data.status}</li>
        <li>Dropoff Location: {orderObject.data.dropoff_loc}</li>
        <li>Delivery Date: {orderObject.data.exp_Date}</li>
        <li>Delivery Time: {orderObject.data.exp_Time}</li>
      </ul>
    )}
    </div>
  )
}