import React, { useState, useEffect } from "react";
import { Avatar, Grid, Paper } from "@mui/material";
import { getQuestionsByEvent } from "../../../controllers/signupForum";
import { getEventDetails } from "../../../controllers/events";
import { useLocation } from "react-router-dom";

const imgLink =
  "https://images.pexels.com/photos/1681010/pexels-photo-1681010.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260";

const SignupForum = () => {
  const [questions, setQuestions] = useState(null);
  const [eventDetails, setEventDetails] = useState(null);

  const search = useLocation().search;

  useEffect(() => {
    const eventId = new URLSearchParams(search).get("eventId");
    getEventDetails(eventId)
      .then((res) => {
        setEventDetails(res.data);
        return getQuestionsByEvent(eventId);
      })
      .then((res) => {
        setQuestions(res.data);
      })
      .catch((err) => console.log(err));
  }, []);

  return eventDetails && questions ? (
    <div style={{ padding: 14 }} className="App">
      <h1
        style={{
          marginBottom: 50,
        }}
      >
        Sign Up Forum ({eventDetails.title})
      </h1>
      {questions.map((item) => (
        <Paper
          style={{
            padding: "40px 20px",
            marginTop: 25,
            cursor: "pointer",
            background:
              item.user.id === eventDetails.organizer.id ? "#E5E4E2" : null,
          }}
          onClick={() => {
            window.location.href =
              "/signup-forum-comments?questionId=" + item.id;
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
                {item.user.screenName}{" "}
                {item.user.id === eventDetails.organizer.id
                  ? "(Organizer)"
                  : ""}
              </h4>
              <p style={{ textAlign: "left" }}>{item.text}</p>
              <p style={{ textAlign: "left", color: "gray" }}>
                posted on {item.createdAt}
              </p>
            </Grid>
          </Grid>
        </Paper>
      ))}
    </div>
  ) : null;
};

export default SignupForum;
