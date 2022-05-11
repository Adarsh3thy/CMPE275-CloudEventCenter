import * as React from "react";
import { Grid, Typography } from "@mui/material";

export default function EventRegistration({ ...props }) {
  return (
    <div>
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
            This is a sample description of an event created on the Cloud Event
            Center. This event is a great way to connect with industry
            professionals and brainstorm/have ideation sessions to solve some of
            the critical problems in the software industry right now.
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
          <Typography sx={{ fontWeight: "bold", background: "yellow" }}>
            NOTE: The amount will be charged to your default bank account upon
            confirmation. Proceed with the registration?
          </Typography>
        </Grid>
      </Grid>
    </div>
  );
}
