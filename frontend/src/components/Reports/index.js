import VictoryCharts from "../../utils/VictoryCharts";
import { Grid } from "@mui/material";

const Reports = () => {
  return (
    <Grid container direction="column">
      <Grid item container direction="row">
        <Grid item style={{ width: 500, height: 500 }}>
          <h3 style={{ fontWeight: "bold", textDecoration: "underline" }}>
            System Report
          </h3>
          <VictoryCharts />
        </Grid>
        <Grid item style={{ width: 500, height: 500 }}>
          <h3 style={{ fontWeight: "bold", textDecoration: "underline" }}>
            Participant Report
          </h3>
          <VictoryCharts />
        </Grid>
      </Grid>
    </Grid>
  );
};

export default Reports;
