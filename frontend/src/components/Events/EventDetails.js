import React, { useState } from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import { Grid, Link, Typography } from "@mui/material";
import EventRegistration from "./EventRegistration";

export default function EventDetails({
  open,
  handleClose,
  handleEventRegistration,
}) {
  /*
    1) Description
    2) Fee
    3) Min/max sign-ups
    4) current number of sign-ups
  */
  const [isSignUpModal, setIsSignUpModal] = useState(false);

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>
          {isSignUpModal ? "Event Sign up" : "Event Details"}
        </DialogTitle>
        <DialogContent>
          {isSignUpModal ? (
            <EventRegistration />
          ) : (
            <Grid container direction="column">
              <Grid item>
                <Typography
                  sx={{
                    textDecoration: "underline",
                    fontWeight: "bold",
                  }}
                >
                  Description:
                </Typography>
                <p>
                  This is a sample description of an event created on the Cloud
                  Event Center. This event is a great way to connect with
                  industry professionals and brainstorm/have ideation sessions
                  to solve some of the critical problems in the software
                  industry right now.
                </p>
              </Grid>
              <Grid item container direction="row" md={12}>
                <Grid item md={6}>
                  <Typography
                    sx={{
                      textDecoration: "underline",
                      fontWeight: "bold",
                    }}
                  >
                    Min participants:
                  </Typography>
                  <p>25</p>
                </Grid>
                <Grid item md={6}>
                  <Typography
                    sx={{
                      textDecoration: "underline",
                      fontWeight: "bold",
                    }}
                  >
                    Max participants:
                  </Typography>
                  <p>100</p>
                </Grid>
              </Grid>
              <Grid item container direction="row" md={12}>
                <Grid item md={6}>
                  <Typography
                    sx={{
                      textDecoration: "underline",
                      fontWeight: "bold",
                    }}
                  >
                    Fee:
                  </Typography>
                  <p>$200.00</p>
                </Grid>
                <Grid item md={6}>
                  <Typography
                    sx={{
                      textDecoration: "underline",
                      fontWeight: "bold",
                    }}
                  >
                    Current no. of sign-ups:
                  </Typography>
                  <p>37</p>
                </Grid>
              </Grid>
              <Grid item>
                <Typography>
                  <p>
                    Have any questions?{" "}
                    <Link sx={{ cursor: "pointer" }} href="/signup-forum">
                      Check out our sign up forum here
                    </Link>
                  </p>
                </Typography>
              </Grid>
            </Grid>
          )}
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            sx={{ textTransform: "none" }}
            onClick={
              isSignUpModal ? () => setIsSignUpModal(false) : handleClose
            }
          >
            {isSignUpModal ? "Back" : "Close"}
          </Button>
          <Button
            variant="contained"
            sx={{ textTransform: "none" }}
            onClick={
              isSignUpModal
                ? (e) => handleEventRegistration(e)
                : () => setIsSignUpModal(true)
            }
          >
            {isSignUpModal ? "Confirm" : "Sign up for the event"}
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
