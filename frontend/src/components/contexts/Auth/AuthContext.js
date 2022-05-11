import React, { useState } from "react";

const AuthContext = React.createContext();

const AuthProvider = (props) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(null);
  const [isAuth, setIsAuth] = useState(false);

  const processLogin = (loginResponse) => {
    return new Promise((resolve, reject) => {
      if (loginResponse) {
        setIsAuth(true);
        setToken(loginResponse.token);
        setUser(loginResponse);
        resolve("update-user");
      }
    });
  };

  const processLogout = () => {
    setIsAuth(false);
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user: user,
        token: token,
        processLogin: processLogin,
        processLogout: processLogout,
        isAuth: isAuth,
      }}
    >
      {props.children}
    </AuthContext.Provider>
  );
};

const AuthConsumer = AuthContext.Consumer;

export { AuthProvider, AuthConsumer };
