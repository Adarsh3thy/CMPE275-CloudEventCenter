import React, { useState, useEffect } from "react";
import Clock from "react-clock";
import "react-clock/dist/Clock.css";

const VirtualClock = ({ ...props }) => {
  const [value, setValue] = useState(new Date());

  useEffect(() => {
    const interval = setInterval(() => setValue(new Date()), 1000);
    return () => {
      clearInterval(interval);
    };
  }, []);

  return (
    <div style={{ marginBottom: "15px" }}>
      <Clock value={value} />
    </div>
  );
};

export default VirtualClock;
