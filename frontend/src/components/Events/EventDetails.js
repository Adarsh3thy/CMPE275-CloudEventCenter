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
import {
  getAverageOrganizerRatings,
  getAverageParticipantRatings,
} from "../../controllers/reviews";

export default function EventDetails({
  open,
  handleClose,
  eventDetails,
  handleEventRegistration,
  isOrganizer,
  getEventDetailsFunc = () => {},
}) {
  const [isSignUpModal, setIsSignUpModal] = useState(false);
  const [organizerRating, setOrganizerRating] = useState(null);
  const [participantRatingObj, setParticipantRatingObj] = useState({});

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
    if (eventDetails) {
      getAverageOrganizerRatings(eventDetails.organizer.id)
        .then((res) => setOrganizerRating(res.data))
        .catch((err) => console.log(err));
    }
  }, [eventDetails]);

  useEffect(() => {
    eventDetails &&
      eventDetails.participants &&
      eventDetails.participants.map((item) => {
        getAverageParticipantRatings(item.participant.id)
          .then((res) => {
            let updatedValue = {};
            updatedValue = {
              [item.participant.id]: res.data,
            };
            setParticipantRatingObj((participantRatingObj) => ({
              ...participantRatingObj,
              ...updatedValue,
            }));
          })
          .catch((err) => console.log(err));
      });
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
                      fontWeight: "bold",
                      marginBottom: "15px",
                      background: "#E5E4E2",
                    }}
                  >
                    Organizer Rating: {organizerRating}/5{" "}
                    <a
                      style={{
                        cursor: "pointer",
                        color: "blue",
                      }}
                      href={
                        "/organizer-reviews?organizerId=" +
                        eventDetails.organizer.id
                      }
                    >
                      <i>(See all reviews)</i>
                    </a>
                  </Typography>
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
                    {eventDetails.participants.length === 0 ? null : (
                      <Typography>Participants: </Typography>
                    )}
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
                                ")" +
                                " Rating: " +
                                participantRatingObj[item.participant.id] +
                                "/5"
                              }
                            />
                            <a
                              style={{
                                cursor: "pointer",
                                color: "blue",
                              }}
                              href={
                                "/participant-reviews?participantId=" +
                                item.participant.id
                              }
                            >
                              <i>(See all reviews)</i>
                            </a>
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
