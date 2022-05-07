import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import UpdateUser from "./components/UpdateUser";
import EventRegistrations from "./components/EventRegistrations";
import Events from "./components/Events";
import DashboardLayout from "./components/Dashboard";

function App() {
  const DashboardRoute = ({ exact, path, component: Component }) => (
    <Route
      exact={exact}
      path={path}
      render={(props) => (
        <div>
          <DashboardLayout children={<Component {...props} />} />
        </div>
      )}
    />
  );

  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <Route exact path="/login" component={Login} />
          <Route exact path="/register" component={Register} />
          <DashboardRoute exact path="/events" component={Events} />
          <DashboardRoute exact path="/update-user" component={UpdateUser} />
          <DashboardRoute
            exact
            path="/registrations"
            component={EventRegistrations}
          />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
