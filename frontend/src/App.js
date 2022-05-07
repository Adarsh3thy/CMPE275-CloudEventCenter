import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import UpdateUser from "./components/UpdateUser";
import Events from "./components/Events";
import Forums from "./components/Forums";
import DashboardLayout from "./components/Dashboard";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <Route exact path="/login" component={Login} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/events" component={DashboardLayout} />
          <Route exact path="/forums" component={Forums} />
          <Route exact path="/update-user" component={UpdateUser} />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
