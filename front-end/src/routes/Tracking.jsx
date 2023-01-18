import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import './Tracking.css'

function findOrder(e, nav) {
  nav(`/tracking/${e.target[0].value}`);
  e.preventDefault();
  return false;
}

export default function Tracking() {
  const navigate = useNavigate();
  return (
    <>
    <div id="tracking-menu">
      <span>Track your order</span>
      <form id="numberInput" noValidate onSubmit={(e) => findOrder(e, navigate)}>
        <input type="text" placeholder="Tracking Number" />
        <button>
          <span className="material-symbols-outlined icon">
            search
          </span>
        </button>
        <input type="submit" hidden tabIndex="-1"/>
      </form>
    </div>
    <TrackingResult/>
    </>
  );
}

function TrackingResult() {
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