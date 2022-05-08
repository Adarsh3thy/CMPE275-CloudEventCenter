import React, { useState } from "react";
import Button from "@mui/material/Button";
import {
  Grid,
  TextField,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  MenuItem,
} from "@mui/material";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";

export default function CreateEvent({ open, handleClose }) {
  /*
    1) Title: brief text to name the event.
    2) Description: text to describe the event.
    3) StartTime: date and time for the event to start. Must be in the future. 
    4) EndTime: Must be after the start time.
    5) Deadline: date and time that participants must sign up before. A deadline must be no later than the start time. 
      This is also the time the sign-up forum closes for new postings and entering read-only mode.
    6) Address: Street and number (optional), City, State, Zip Code. We are assuming US addresses only. You can provide 
      default values for the latter three to simplify the registration process.
    7) MinParticipants: (inclusive) the minimum number of participants that must sign up before the deadline, or the 
      event will be canceled.  
    8) MaxParticipants: (inclusive) capacity of the event; if reached, no  new sign-ups are accepted.
    9) Fee: amount in USD. An event can either be free or paid - only an event created by an organization can require 
      a fee.  
    10) AdmissionPolicy: first-come-first-served, or approval-required. For the former, the approval is automatic - 
        registrations are confirmed right away. For the latter, registrations are not confirmed until approved by the 
        organizer.  
  */

  const [startTime, setStartTime] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());
  const [deadline, setDeadline] = useState(new Date());

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Create Event</DialogTitle>
        <DialogContent>
          <Grid
            container
            direction="column"
            spacing={2}
            sx={{ marginTop: "5px" }}
          >
            <Grid item>
              <TextField
                required
                label="Title"
                fullWidth
                autoComplete="given-name"
                variant="outlined"
                autoFocus
              />
            </Grid>
            <Grid item>
              <TextField
                required
                label="Description"
                fullWidth
                autoComplete="given-name"
                variant="outlined"
              />
            </Grid>
            <Grid item container direction="row" md={12} spacing={2}>
              <Grid item md={4}>
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                  <DateTimePicker
                    renderInput={(props) => <TextField {...props} />}
                    label="Start time"
                    value={startTime}
                    onChange={(newValue) => {
                      setStartTime(newValue);
                    }}
                  />
                </LocalizationProvider>
              </Grid>
              <Grid item md={4}>
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                  <DateTimePicker
                    renderInput={(props) => <TextField {...props} />}
                    label="End time"
                    value={endTime}
                    onChange={(newValue) => {
                      setEndTime(newValue);
                    }}
                  />
                </LocalizationProvider>
              </Grid>
              <Grid item md={4}>
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                  <DateTimePicker
                    renderInput={(props) => <TextField {...props} />}
                    label="Deadline"
                    value={deadline}
                    onChange={(newValue) => {
                      setDeadline(newValue);
                    }}
                  />
                </LocalizationProvider>
              </Grid>
            </Grid>
            <Grid item container direction="row" spacing={2}>
              <Grid item md={6}>
                <TextField
                  required
                  label="Address Line 1"
                  fullWidth
                  autoComplete="given-name"
                  variant="outlined"
                />
              </Grid>
              <Grid item md={6}>
                <TextField
                  label="Address Line 2"
                  fullWidth
                  autoComplete="family-name"
                  variant="outlined"
                />
              </Grid>
            </Grid>
            <Grid item container direction="row" spacing={2}>
              <Grid item md={4}>
                <TextField
                  required
                  label="City"
                  fullWidth
                  autoComplete="given-name"
                  variant="outlined"
                  defaultValue={"San Jose"}
                />
              </Grid>
              <Grid item md={4}>
                <TextField
                  required
                  label="State"
                  fullWidth
                  autoComplete="family-name"
                  variant="outlined"
                  defaultValue={"California"}
                />
              </Grid>
              <Grid item md={4}>
                <TextField
                  required
                  label="Zip Code"
                  fullWidth
                  autoComplete="family-name"
                  variant="outlined"
                  defaultValue={"95126"}
                />
              </Grid>
            </Grid>
            <Grid item container direction="row" md={12} spacing={2}>
              <Grid item md={6}>
                <TextField
                  required
                  label="Min participants"
                  fullWidth
                  autoComplete="family-name"
                  variant="outlined"
                />
              </Grid>
              <Grid item md={6}>
                <TextField
                  required
                  label="Max participants"
                  fullWidth
                  autoComplete="family-name"
                  variant="outlined"
                />
              </Grid>
            </Grid>
            <Grid item container direction="row" md={12} spacing={2}>
              <Grid item md={6}>
                <TextField
                  required
                  label="Fee"
                  fullWidth
                  autoComplete="family-name"
                  variant="outlined"
                />
              </Grid>
              <Grid item md={6}>
                <TextField fullWidth select label="Admission policy">
                  <MenuItem value="fcfs">First come first served</MenuItem>
                  <MenuItem value="ar">Approval required</MenuItem>
                </TextField>
              </Grid>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            sx={{ textTransform: "none" }}
            onClick={handleClose}
          >
            Close
          </Button>
          <Button
            variant="contained"
            sx={{ textTransform: "none" }}
            onClick={handleClose}
          >
            Create
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
