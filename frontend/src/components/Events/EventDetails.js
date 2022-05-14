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
  eventDetails,
  handleClose,
  handleEventRegistration,
}) {
  const [isSignUpModal, setIsSignUpModal] = useState(false);

  return (
    <div>
      {eventDetails ? (
        <Dialog open={open} onClose={handleClose}>
          <DialogTitle>
            {isSignUpModal ? "Event Sign up" : "Event Details"}
          </DialogTitle>
          <DialogContent>
            {isSignUpModal ? (
              <EventRegistration eventDetails={eventDetails} />
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
                  <p>{eventDetails.description}</p>
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
                    <p>{eventDetails.minParticipants}</p>
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
                    <p>{eventDetails.maxParticipants}</p>
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
                    <p>${eventDetails.fee}</p>
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
                    <p>{eventDetails.participants.length}</p>
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
      ) : null}
    </div>
  );
}
