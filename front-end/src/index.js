import './index.css';
import reportWebVitals from './reportWebVitals';
import React, { createRef } from 'react';
import ReactDOM from 'react-dom/client';
import StartMenu from './routes/StartMenu';
import Tracking from './routes/Tracking';
import Login from './routes/Login';
import UserPortal from './routes/UserPortal';
import { createBrowserRouter, RouterProvider, useLocation, useOutlet} from 'react-router-dom';
import {
  CSSTransition,
  SwitchTransition
} from "react-transition-group";

const routes = [
  { path: '/', name: 'Home', element: <StartMenu />, nodeRef: createRef() },
  { path: '/tracking/', name: 'Tracking', element: <Tracking />, nodeRef: createRef() },
  { path: '/tracking/:trackingId', name: 'Tracking Order', element: <Tracking />, nodeRef: createRef() },
  { path: '/login/', name: 'Log-in', element: <Login />, nodeRef: createRef() },
  { path: '/portal/employee/:username', name: 'User UI', element: <UserPortal />, nodeRef: createRef() }
]

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    children: routes.map((route) => ({
      index: route.path === '/',
      path: route.path === '/' ? undefined : route.path,
      element: route.element,
    }))
  }
]);

export default function Root() {
  const location = useLocation();
  const currentOutlet = useOutlet();
  const { nodeRef } =
    routes.find((route) => route.path === location.pathname) ?? {}
  return (
    <div className="container center-content">
      <div id="main-menu">
        <span>Disruptive Delivery</span>
        <SwitchTransition>
          <CSSTransition
            key={location.pathname}
            nodeRef={nodeRef}
            timeout={300}
            classNames="page"
            unmountOnExit
          >
            {(state) => (
              <div ref={nodeRef} className="page">
                {currentOutlet}
              </div>
            )}
        </CSSTransition>
      </SwitchTransition>
      </div>
    </div>
  );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<RouterProvider router={router} />
);



// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
