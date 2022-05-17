import { sentenceCase } from "change-case";
import React, { useEffect, useState } from "react";
import {
  Card,
  Table,
  Stack,
  Button,
  TableRow,
  TableBody,
  TableCell,
  Container,
  Typography,
  TableContainer,
  TablePagination,
} from "@mui/material";
import Page from "../../mui_components/Page";
import Label from "../../mui_components/Label";
import Scrollbar from "../../mui_components/Scrollbar";
import Iconify from "../../mui_components/Iconify";
import SearchNotFound from "../../mui_components/SearchNotFound";
import {
  UserListHead,
  UserListToolbar,
} from "../../sections/@dashboard/events";
import CreateEvent from "./CreateEvent";
import EventDetails from "./EventDetails";
import { AuthConsumer } from "../contexts/Auth/AuthContext";
import {
  getEvents,
  getEventDetails,
  registerEvent,
} from "../../controllers/events";
import Snackbar from "@mui/material/Snackbar";
import MuiAlert from "@mui/material/Alert";

const TABLE_HEAD = [
  { id: "name", label: "Title", alignRight: false },
  { id: "company", label: "Time", alignRight: false },
  { id: "role", label: "Location", alignRight: false },
  { id: "isVerified", label: "Organizer", alignRight: false },
  { id: "status", label: "Current Status", alignRight: false },
  { id: "" },
];

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

const Events = ({ user }) => {
  const [page, setPage] = useState(1);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [openCreateEvent, setOpenCreateEvent] = useState(false);
  const [openEventDetails, setOpenEventDetails] = useState(false);
  const [allEvents, setAllEvents] = useState(null);
  const [eventDetails, setEventDetails] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);
  const [successMessageReg, setSuccessMessageReg] = useState(null);
  const [openSnack, setOpenSnack] = useState(false);
  const [openRegSnack, setOpenRegSnack] = useState(false);

  const handleClose = () => {
    setOpenCreateEvent(false);
    setSuccessMessage("Successfully created event");
    setOpenSnack(true);
    getEventsFunc();
  };

  const handleEventDetailsClose = () => {
    setOpenEventDetails(false);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value));
    setPage(1);
  };

  const handleEventRegistration = (e) => {
    e.preventDefault();
    registerEvent(eventDetails.id, user.id)
      .then((res) => {
        if (res.status === 200) {
          setOpenEventDetails(false);
          setOpenRegSnack(true);
          setSuccessMessageReg("Registered for the event!");
        }
      })
      .catch((err) => console.log(err));
  };

  const eventDetailsHandler = (e, eventId) => {
    e.preventDefault();
    getEventDetails(eventId)
      .then((res) => {
        setEventDetails(res.data);
        setOpenEventDetails(true);
      })
      .catch((err) => console.log(err));
  };

  const getEventsFunc = () => {
    getEvents(page)
      .then((res) => {
        setAllEvents(res.data.content);
      })
      .catch((err) => console.log(err));
  };

  const formatDate = (res) => {
    return res.split("T")[0];
  };

  useEffect(() => {
    getEventsFunc();
  }, []);

  return (
    <>
      <Page title="User">
        <Container>
          <Stack
            direction="row"
            alignItems="center"
            justifyContent="space-between"
            mb={5}
          >
            <Typography variant="h4" gutterBottom>
              Events
            </Typography>
            <Button
              variant="contained"
              startIcon={<Iconify icon="eva:plus-fill" />}
              onClick={(e) => setOpenCreateEvent(true)}
            >
              New Event
            </Button>
          </Stack>

          {user ? (
            <>
              <Card>
                <UserListToolbar />

                <Scrollbar>
                  <TableContainer sx={{ minWidth: 800 }}>
                    <Table>
                      <UserListHead headLabel={TABLE_HEAD} />
                      <TableBody>
                        {allEvents &&
                          allEvents.length > 0 &&
                          allEvents.map((item) => (
                            <>
                              <TableRow
                                hover
                                key={item.id}
                                tabIndex={-1}
                                role="checkbox"
                                onClick={(e) => eventDetailsHandler(e, item.id)}
                                sx={{ cursor: "pointer" }}
                              >
                                <TableCell padding="checkbox" />
                                <TableCell
                                  component="th"
                                  scope="row"
                                  padding="none"
                                >
                                  <Stack
                                    direction="row"
                                    alignItems="center"
                                    spacing={2}
                                  >
                                    <Typography variant="subtitle2" noWrap>
                                      {item.title}
                                    </Typography>
                                  </Stack>
                                </TableCell>
                                <TableCell align="left">
                                  {formatDate(item.startTime)} -{" "}
                                  {formatDate(item.endTime)}
                                </TableCell>
                                <TableCell align="left">
                                  {item.address.street}, {item.address.number},{" "}
                                  {item.address.city}, {item.address.state},{" "}
                                  {item.address.zip}
                                </TableCell>
                                <TableCell align="left">
                                  {item.organizer.screenName}
                                </TableCell>
                                <TableCell align="left">
                                  <Label
                                    variant="ghost"
                                    color={
                                      (item.status === "banned" && "error") ||
                                      "success"
                                    }
                                  >
                                    {sentenceCase(item.status)}
                                  </Label>
                                </TableCell>
                              </TableRow>
                            </>
                          ))}
                      </TableBody>

                      {allEvents && allEvents.length === 0 && (
                        <TableCell colSpan={8} sx={{ py: 5 }}>
                          <SearchNotFound />
                        </TableCell>
                      )}
                    </Table>
                  </TableContainer>
                </Scrollbar>

                <TablePagination
                  rowsPerPageOptions={[5, 10, 25]}
                  component="div"
                  count={allEvents && allEvents.length}
                  rowsPerPage={rowsPerPage}
                  page={page}
                  onPageChange={handleChangePage}
                  onRowsPerPageChange={handleChangeRowsPerPage}
                />
              </Card>

              <CreateEvent
                open={openCreateEvent}
                handleClose={handleClose}
                userId={user.id}
              />

              <EventDetails
                open={openEventDetails}
                eventDetails={eventDetails}
                handleClose={handleEventDetailsClose}
                handleEventRegistration={handleEventRegistration}
              />

              <Snackbar
                open={openSnack}
                autoHideDuration={6000}
                onClose={handleClose}
              >
                <Alert severity="success" sx={{ width: "100%" }}>
                  {successMessageReg}
                </Alert>
              </Snackbar>

              <Snackbar
                open={openRegSnack}
                autoHideDuration={6000}
                onClose={handleClose}
              >
                <Alert severity="success" sx={{ width: "100%" }}>
                  {successMessage}
                </Alert>
              </Snackbar>
            </>
          ) : null}
        </Container>
      </Page>
    </>
  );
};

export default (props) => (
  <AuthConsumer>{({ user }) => <Events user={user} {...props} />}</AuthConsumer>
);
