import * as React from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from "@mui/material";
import { useState, useEffect } from "react";
import {
  getSystemReport,
  getParticipantReport,
  getOrganizerReport,
} from "../../controllers/reports";
import { AuthConsumer } from "../contexts/Auth/AuthContext";

const Reports = ({ user }) => {
  const [systemReport, setSystemReport] = useState(null);
  const [participantReport, setParticipantReport] = useState(null);
  const [organizerReport, setOrganizerReport] = useState(null);

  useEffect(() => {
    if (user) {
      getSystemReport()
        .then((res) => {
          setSystemReport(res.data);
          return getParticipantReport(user.id);
        })
        .then((res) => {
          setParticipantReport(res.data);
          return getOrganizerReport(user.id);
        })
        .then((res) => {
          setOrganizerReport(res.data);
        })
        .catch((err) => console.log(err));
    }
  }, [user]);

  return (
    <>
      <h1>System Report</h1>
      {systemReport ? (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 450 }} aria-label="simple table">
            <TableBody>
              {Object.keys(systemReport).map((key) => (
                <TableRow
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    {key}
                  </TableCell>
                  <TableCell align="right">{systemReport[key]}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : null}
      <br />
      <br />
      <h1>Participant Report</h1>
      {participantReport ? (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 450 }} aria-label="simple table">
            <TableBody>
              {Object.keys(participantReport).map((key) => (
                <TableRow
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    {key}
                  </TableCell>
                  <TableCell align="right">{participantReport[key]}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : null}
      <br />
      <br />
      <h1>Organizer Report</h1>
      {organizerReport ? (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 450 }} aria-label="simple table">
            <TableBody>
              {Object.keys(organizerReport).map((key) => (
                <TableRow
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    {key}
                  </TableCell>
                  <TableCell align="right">{organizerReport[key]}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : null}
    </>
  );
};

export default (props) => (
  <AuthConsumer>
    {({ user }) => <Reports user={user} {...props} />}
  </AuthConsumer>
);
