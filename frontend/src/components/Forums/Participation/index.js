import React from "react";
import { Avatar, Grid, Paper } from "@mui/material";

const imgLink =
  "https://images.pexels.com/photos/1681010/pexels-photo-1681010.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260";

const ParticipationForum = () => {
  return (
    <div style={{ padding: 14 }} className="App">
      <h1
        style={{
          marginBottom: 50,
        }}
      >
        Participant Forum
      </h1>
      {[1, 2, 3, 4].map((item, index) => (
        <Paper
          style={{
            padding: "40px 20px",
            marginTop: 25,
            cursor: "pointer",
            background: index === 0 ? "#E5E4E2" : null,
          }}
          onClick={() => {
            window.location.href = "/participation-forum-comments";
          }}
        >
          <Grid container wrap="nowrap" spacing={2}>
            <Grid item>
              <Avatar alt="Remy Sharp" src={imgLink} />
            </Grid>
            <Grid justifyContent="left" item xs zeroMinWidth>
              <h4
                style={{
                  margin: 0,
                  textAlign: "left",
                }}
              >
                Anay Naik {index === 0 ? "(Organizer)" : ""}
              </h4>
              <p style={{ textAlign: "left" }}>
                How do i attach images while commenting on a particular forum?
                Also, mastercard isn't getting processed as my default payment
                method, need assistance with this. Any kind of help would be
                appreciated. TIA
              </p>
              <p style={{ textAlign: "left", color: "gray" }}>
                posted 1 minute ago
              </p>
            </Grid>
          </Grid>
        </Paper>
      ))}
    </div>
  );
};

export default ParticipationForum;
