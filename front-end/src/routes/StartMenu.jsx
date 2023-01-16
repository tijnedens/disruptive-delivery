import { NavLink } from 'react-router-dom';
import './StartMenu.css'

export default function StartMenu(){
  return (
    <div id="start-menu">
      <NavLink id="tracking" className="option" to="/tracking">
        <span className="material-symbols-outlined icon">
          local_shipping
        </span>
        <span className="sub-text">Tracking</span>
      </NavLink>
      <NavLink id="login" className="option" to="/login">
        <span className="material-symbols-outlined icon">
          person
        </span>
        <span className="sub-text">Log-in</span>
      </NavLink>
    </div>
  )
}