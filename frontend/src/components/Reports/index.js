import { Grid } from "@mui/material";
import { useState, useEffect } from "react";
import {
  getSystemReport,
  getParticipantReport,
  getOrganizerReport,
} from "../../controllers/reports";

const Reports = () => {
  useEffect(() => {
    getSystemReport()
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
  }, []);

  return (
    <Grid container direction="column">
      <Grid item container direction="row">
        <Grid item style={{ width: 500, height: 500 }}>
          <h3 style={{ fontWeight: "bold", textDecoration: "underline" }}>
            System Report
          </h3>
        </Grid>
        <Grid item style={{ width: 500, height: 500 }}>
          <h3 style={{ fontWeight: "bold", textDecoration: "underline" }}>
            Participant Report
          </h3>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default Reports;
