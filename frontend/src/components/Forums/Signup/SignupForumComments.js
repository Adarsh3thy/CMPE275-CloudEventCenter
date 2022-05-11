import React from "react";
import { Grid, TextField, Typography, Button } from "@mui/material";

const SignupForumComments = () => {
  return (
    <Grid container direction="column">
      <Grid item>
        <Typography sx={{ fontSize: "24px", fontWeight: "bold" }}>
          <i>
            How do i attach images while commenting on a particular forum? Also,
            mastercard isn't getting processed as my default payment method,
            need assistance with this. Any kind of help would be appreciated.
            TIA
          </i>
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
      {[1, 2, 3, 4, 5, 6, 7].map((item, index) => (
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
              Anay Naik
            </h4>
          </Grid>
          <Grid item style={{ textAlign: "left", marginBottom: "5px" }}>
            How do i attach images while commenting on a particular forum? Also,
            mastercard isn't getting processed as my default payment method,
            need assistance with this. Any kind of help would be appreciated.
            TIA
          </Grid>
          <Grid item style={{ textAlign: "left", color: "gray" }}>
            commented 1 minute ago
          </Grid>
        </Grid>
      ))}
    </Grid>
  );
};

export default SignupForumComments;
