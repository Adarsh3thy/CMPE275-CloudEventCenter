import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import { AuthProvider } from "./components/contexts/Auth/AuthContext";
// Components (Layouts/Pages)
import Login from "./components/Login";
import Register from "./components/Register";
import UpdateUser from "./components/UpdateUser";
import EventRegistrations from "./components/EventRegistrations";
import Events from "./components/Events";
import SignupForum from "./components/Forums/Signup";
import ParticipationForum from "./components/Forums/Participation";
import ParticipationForumComments from "./components/Forums/Participation/ParticipationForumComments";
import SignupForumComments from "./components/Forums/Signup/SignupForumComments";
import DashboardLayout from "./components/Dashboard";
import PrivateRoute from "./utils/PrivateRoute";

function App() {
  const DashboardRoute = ({ exact, path, component: Component }) => (
    <Route
      exact={exact}
      path={path}
      render={(props) => (
        <div>
          <PrivateRoute>
            <DashboardLayout children={<Component {...props} />} />
          </PrivateRoute>
        </div>
      )}
    />
  );

  return (
    <div className="App">
      <AuthProvider>
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
            <DashboardRoute
              exact
              path="/signup-forum"
              component={SignupForum}
            />
            <DashboardRoute
              exact
              path="/participation-forum"
              component={ParticipationForum}
            />
            <DashboardRoute
              exact
              path="/signup-forum-comments"
              component={SignupForumComments}
            />
            <DashboardRoute
              exact
              path="/participation-forum-comments"
              component={ParticipationForumComments}
            />
          </Switch>
        </BrowserRouter>
      </AuthProvider>
    </div>
  );
}

export default App;
