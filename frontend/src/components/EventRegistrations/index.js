import { sentenceCase } from "change-case";
import React, { useEffect, useState } from "react";
import {
  Card,
  Table,
  Stack,
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
import SearchNotFound from "../../mui_components/SearchNotFound";
import {
  UserListHead,
  UserListToolbar,
} from "../../sections/@dashboard/events";
import { AuthConsumer } from "../contexts/Auth/AuthContext";
import { getEventRegistrationsByParticipant } from "../../controllers/events";
import MuiAlert from "@mui/material/Alert";

const TABLE_HEAD = [
  { id: "name", label: "Title", alignRight: false },
  { id: "company", label: "Time", alignRight: false },
  { id: "role", label: "Location", alignRight: false },
  { id: "status", label: "Current Status", alignRight: false },
  { id: "" },
];

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

const EventRegistrations = ({ user }) => {
  const [page, setPage] = useState(0);
  const [selected, setSelected] = useState([]);
  const [filterName, setFilterName] = useState("");
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [allEvents, setAllEvents] = useState(null);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const getEventsFunc = (userId) => {
    getEventRegistrationsByParticipant(userId)
      .then((res) => {
        setAllEvents(res.data);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    if (user) getEventsFunc(user.id);
  }, [user]);

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
              Registrations
            </Typography>
          </Stack>

          {user ? (
            <>
              <Card>
                <UserListToolbar
                  numSelected={selected.length}
                  filterName={filterName}
                  isRegistration={true}
                />

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
                                // onClick={(e) => eventDetailsHandler(e, item.id)}
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
                                      {item.event.title}
                                    </Typography>
                                  </Stack>
                                </TableCell>
                                <TableCell align="left">
                                  {item.event.startTime} - {item.event.endTime}
                                </TableCell>
                                <TableCell align="left">
                                  {item.event.address.street},{" "}
                                  {item.event.address.number},{" "}
                                  {item.event.address.city},{" "}
                                  {item.event.address.state},{" "}
                                  {item.event.address.zip}
                                </TableCell>
                                <TableCell align="left">
                                  <Label
                                    variant="ghost"
                                    color={
                                      (item.event.status === "banned" &&
                                        "error") ||
                                      "success"
                                    }
                                  >
                                    {sentenceCase(item.event.status)}
                                  </Label>
                                </TableCell>
                              </TableRow>
                            </>
                          ))}
                      </TableBody>

                      {allEvents && allEvents.length === 0 && (
                        <TableCell colSpan={8} sx={{ py: 5 }}>
                          <SearchNotFound searchQuery={filterName} />
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
            </>
          ) : null}
        </Container>
      </Page>
    </>
  );
};

export default (props) => (
  <AuthConsumer>
    {({ user }) => <EventRegistrations user={user} {...props} />}
  </AuthConsumer>
);
