import { filter } from "lodash";
import { sentenceCase } from "change-case";
import { useEffect, useState } from "react";
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
import { getEvents, getEventDetails } from "../../controllers/events";

const TABLE_HEAD = [
  { id: "name", label: "Title", alignRight: false },
  { id: "company", label: "Time", alignRight: false },
  { id: "role", label: "Location", alignRight: false },
  { id: "isVerified", label: "Organizer", alignRight: false },
  { id: "status", label: "Current Status", alignRight: false },
  { id: "" },
];

function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

const Events = ({ user }) => {
  const [page, setPage] = useState(0);
  const [selected, setSelected] = useState([]);
  const [filterName, setFilterName] = useState("");
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [open, setOpen] = useState(false);
  const [openEventDetails, setOpenEventDetails] = useState(false);
  const [allEvents, setAllEvents] = useState(null);
  const [eventDetails, setEventDetails] = useState(null);

  const handleClose = () => {
    setOpen(false);
  };

  const handleEventDetailsClose = () => {
    setOpenEventDetails(false);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleEventRegistration = (e) => {
    e.preventDefault();
    setOpenEventDetails(false);
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

  useEffect(() => {
    getEvents(1)
      .then((res) => {
        setAllEvents(res.data.content);
      })
      .catch((err) => console.log(err));
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
              onClick={(e) => setOpen(true)}
            >
              New Event
            </Button>
          </Stack>

          {allEvents && allEvents.length > 0 ? (
            <Card>
              <UserListToolbar
                numSelected={selected.length}
                filterName={filterName}
              />

              <Scrollbar>
                <TableContainer sx={{ minWidth: 800 }}>
                  <Table>
                    <UserListHead headLabel={TABLE_HEAD} />
                    <TableBody>
                      {allEvents.map((item) => (
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
                              {item.startTime} - {item.endTime}
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
                      <TableBody>
                        <TableRow>
                          <TableCell align="center" colSpan={6} sx={{ py: 3 }}>
                            <SearchNotFound searchQuery={filterName} />
                          </TableCell>
                        </TableRow>
                      </TableBody>
                    )}
                  </Table>
                </TableContainer>
              </Scrollbar>

              <TablePagination
                rowsPerPageOptions={[5, 10, 25]}
                component="div"
                count={allEvents.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
              />
            </Card>
          ) : null}
        </Container>

        <CreateEvent open={open} handleClose={handleClose} />

        <EventDetails
          open={openEventDetails}
          eventDetails={eventDetails}
          handleClose={handleEventDetailsClose}
          handleEventRegistration={handleEventRegistration}
        />
      </Page>
    </>
  );
};

export default (props) => (
  <AuthConsumer>{({ user }) => <Events user={user} {...props} />}</AuthConsumer>
);
