import React, { useState, useEffect } from "react";
import { Grid, TextField, Typography, Button } from "@mui/material";
import { getQuestionAnswers } from "../../../controllers/signupForum";
import { useLocation } from "react-router-dom";

const SignupForumComments = () => {
  const [answers, setAnswers] = useState(null);

  const search = useLocation().search;

  useEffect(() => {
    const questionId = new URLSearchParams(search).get("questionId");
    getQuestionAnswers(questionId)
      .then((res) => {
        setAnswers(res.data);
      })
      .catch((err) => console.log(err));
  }, []);

  console.log("answers: ", answers);
  return answers && answers.length > 0 ? (
    <Grid container direction="column">
      <Grid item>
        <Typography sx={{ fontSize: "24px", fontWeight: "bold" }}>
          <i>{answers[0].question.text}</i>
          <p
            style={{
              textAlign: "left",
              color: "gray",
              margin: 0,
              fontSize: "18px",
            }}
          >
            asked by {answers[0].user.screenName}
          </p>
        </Typography>
      </Grid>
      <Grid item sx={{ marginTop: "25px" }}>
        <TextField
          variant="filled"
          multiline
          fullWidth
          rows="3"
          placeholder="Add a comment..."
        />
      </Grid>
      <Grid
        item
        container
        direction="row"
        justifyContent={"right"}
        sx={{ marginTop: "5px" }}
        spacing={2}
      >
        <Grid item>
          <Button
            variant="outlined"
            sx={{ textTransform: "none" }}
            size="large"
          >
            Comment
          </Button>
        </Grid>
      </Grid>
      <Grid item>
        <Typography>
          <h1>All Comments</h1>
        </Typography>
      </Grid>
      {answers.map((item) => (
        <Grid
          item
          container
          direction="column"
          sx={{ marginBottom: "25px", marginLeft: "10px" }}
        >
          <Grid item justifyContent="left" xs zeroMinWidth>
            <h4
              style={{
                margin: 0,
                textAlign: "left",
                fontWeight: "bold",
                fontSize: "18px",
                marginBottom: "10px",
              }}
            >
              {item.user.screenName}
            </h4>
          </Grid>
          <Grid item style={{ textAlign: "left", marginBottom: "5px" }}>
            {item.text}
          </Grid>
          <Grid item style={{ textAlign: "left", color: "gray" }}>
            commented on {item.createdAt}
          </Grid>
        </Grid>
      ))}
    </Grid>
  ) : null;
};

export default SignupForumComments;
