import { Redirect, Route } from "react-router-dom";
import { AuthConsumer } from "../components/contexts/Auth/AuthContext";

const PrivateRoute = ({ redirectPath = "/login", children, user }) => {
  const isLoggedIn = user != null;

  const errorMessage = !isLoggedIn ? "You need to be logged in" : null;

  const shouldRedirect = isLoggedIn;

  return (
    <Route
      render={({ location }) =>
        shouldRedirect ? (
          children
        ) : (
          <Redirect
            to={{
              pathname: redirectPath,
              state: { from: location, errorMessage: errorMessage },
            }}
          />
        )
      }
    />
  );
};

export default (props) => (
  <AuthConsumer>
    {({ user }) => <PrivateRoute user={user} {...props} />}
  </AuthConsumer>
);
