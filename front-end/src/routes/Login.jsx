import { useNavigate, useParams } from 'react-router-dom';
import './Login.css'

function loginUser(e, navigate) {
  e.preventDefault();
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