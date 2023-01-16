import { useNavigate, useParams } from 'react-router-dom';
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
  return (
    <div>{params.trackingId}</div>
  )
}