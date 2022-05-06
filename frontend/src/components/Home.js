import React, { useEffect } from "react";

const Home = () => {
  useEffect(() => {
    window.location.href = "/login";
  }, []);

  return <div>Code goes here</div>;
};

export default Home;
