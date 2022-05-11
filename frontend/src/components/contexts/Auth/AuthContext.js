import React, { useState } from "react";

const AuthContext = React.createContext();

const AuthProvider = (props) => {
  const [user, setUser] = useState("user", null);
  const [isAuth, setIsAuth] = useState("isAuth", false);

  const processLogin = (loginResponse) => {
    return new Promise((resolve, reject) => {
      if (loginResponse) {
        setIsAuth(true);
        setUser(loginResponse);
        resolve("update-user");
      }
    });
  };

  const processLogout = () => {
    setIsAuth(false);
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user: user,
        processLogin: processLogin,
        processLogout: processLogout,
        history: window.history,
        isAuth: isAuth,
      }}
    >
      {props.children}
    </AuthContext.Provider>
  );
};

const AuthConsumer = AuthContext.Consumer;

export { AuthProvider, AuthConsumer };
