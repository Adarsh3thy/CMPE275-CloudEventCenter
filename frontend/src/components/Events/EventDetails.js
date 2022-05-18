import React, { useEffect, useState } from "react";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import { Grid, Link, Typography, Button } from "@mui/material";
import EventRegistration from "./EventRegistration";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import {
  approveParticipant,
  rejectParticipant,
} from "../../controllers/events";

export default function EventDetails({
  open,
  handleClose,
  eventDetails,
  handleEventRegistration,
  isOrganizer,
  getEventDetailsFunc = () => {},
}) {
  const [isSignUpModal, setIsSignUpModal] = useState(false);

  const handleParticipant = (e, action, ids) => {
    e.preventDefault();
    if (action === "approve") {
      approveParticipant(ids.eventId, ids.participantId)
        .then(() => {
          getEventDetailsFunc(ids.eventId);
        })
        .catch((err) => console.log(err));
    } else if (action === "reject") {
      rejectParticipant(ids.eventId, ids.participantId)
        .then(() => {
          getEventDetailsFunc(ids.eventId);
        })
        .catch((err) => console.log(err));
    } else {
      return;
    }
  };

  useEffect(() => {
    return;
  }, [eventDetails]);

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
                {!isOrganizer ? (
                  <Grid item>
                    <Typography>
                      <p>
                        Have any questions?{" "}
                        <Link
                          sx={{ cursor: "pointer" }}
                          href={"/signup-forum?eventId=" + eventDetails.id}
                        >
                          Check out our sign up forum here
                        </Link>
                      </p>
                    </Typography>
                  </Grid>
                ) : (
                  <Grid item>
                    <Typography>Participants: </Typography>
                    {eventDetails.participants &&
                      eventDetails.participants.map((item, index) => (
                        <List sx={{ margin: 0, padding: 0 }}>
                          <ListItem sx={{ margin: 0, padding: 0 }}>
                            <ListItemText
                              primary={
                                index +
                                1 +
                                ")" +
                                item.participant.screenName +
                                "(" +
                                item.status +
                                ")"
                              }
                            />
                            {item.status === "Pending" ? (
                              <Button
                                sx={{ border: "15px" }}
                                variant="contained"
                                color="success"
                                size={"small"}
                                onClick={(e) =>
                                  handleParticipant(e, "approve", item.id)
                                }
                              >
                                Approve
                              </Button>
                            ) : null}
                            {item.status === "Pending" ? (
                              <Button
                                sx={{ border: "15px" }}
                                variant="outlined"
                                color="error"
                                size={"small"}
                                onClick={(e) =>
                                  handleParticipant(e, "reject", item.id)
                                }
                              >
                                Reject
                              </Button>
                            ) : null}
                          </ListItem>
                        </List>
                      ))}
                  </Grid>
                )}
              </Grid>
            )}
          </DialogContent>
          {!isOrganizer ? (
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
          ) : null}
        </Dialog>
      ) : null}
    </div>
  );
}
