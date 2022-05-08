import React, { useState } from "react";
import {
  TextField,
  Typography,
  Button,
  Grid,
  Radio,
  RadioGroup,
  FormControlLabel,
  FormLabel,
} from "@mui/material";

/*
1) Email: a valid email address. Must be unique and cannot be changed. 
Beside identification within the service, it is used for verification, notification, and communication purposes too. 

2) Account Type: Person or Organization. 

3) Full Name: Required for all users. For simplicity, we can use one string to capture first, middle, and last names. 

4) Screen Name: also string type, and must be unique among all screen names. An org’s screen name must equal to 
its full name, or start with the full name with an optional suffix to achieve uniqueness. 

5) Gender: optional, and applies to persons only.

6) Description: optional text to describe the user.

7) Address: Street and number (optional), City, State, Zip Code. We are assuming US addresses only. 
You can provide default values for the latter three to simplify the registration process.
*/

const UpdateUser = ({ ...props }) => {
  const [firstName, setFirstName] = useState(null);
  const [middleName, setMiddleName] = useState(null);
  const [lastName, setLastName] = useState(null);
  const [screenName, setScreenName] = useState(null);
  const [gender, setGender] = useState(null);
  const [email, setEmail] = useState(null);
  const [accountType, setAccountType] = useState(null);
  const [description, setDescription] = useState(null);
  const [address1, setAddress1] = useState(null);
  const [address2, setAddress2] = useState(null);
  const [city, setCity] = useState(null);
  const [state, setState] = useState(null);
  const [zipcode, setZipcode] = useState(null);

  // TODO: handlers

  return (
    <Grid
      container
      direction="row"
      style={{
        width: "100%",
        height: "100%",
        background: "#ffffff",
      }}
    >
      <Grid
        item
        container
        direction="column"
        xs={12}
        sm={12}
        md={12}
        lg={12}
        xl={12}
      >
        <Grid item>
          <Typography
            style={{
              height: "45px",
              fontFamily: "Work Sans",
              fontStyle: "normal",
              fontWeight: "bold",
              fontSize: "30px",
              color: "#000000",
            }}
          >
            Update your user information.
          </Typography>
        </Grid>
        <form
        // onSubmit={handleSubmit}
        >
          <Grid
            container
            direction="column"
            spacing={2}
            style={{ marginTop: "25px" }}
          >
            <Grid item container direction="row" spacing={2}>
              <Grid item md={4}>
                <TextField
                  required
                  label="First Name"
                  fullWidth
                  autoComplete="given-name"
                  variant="outlined"
                  autoFocus
                  onChange={(e) => setFirstName(e.target.value)}
                />
              </Grid>
              <Grid item md={4}>
                <TextField
                  required
                  label="Middle Name"
                  fullWidth
                  autoComplete="family-name"
                  variant="outlined"
                  onChange={(e) => setMiddleName(e.target.value)}
                />
              </Grid>
              <Grid item md={4}>
                <TextField
                  required
                  label="Last Name"
                  fullWidth
                  autoComplete="family-name"
                  variant="outlined"
                  onChange={(e) => setLastName(e.target.value)}
                />
              </Grid>
            </Grid>
            <Grid item container direction="row" spacing={2}>
              <Grid item md={6}>
                <TextField
                  required
                  label="Email"
                  fullWidth
                  autoComplete="given-name"
                  variant="outlined"
                  onChange={(e) => setLastName(e.target.value)}
                />
              </Grid>
              <Grid item md={6} container direction="row">
                <Grid item md={3} sx={{ marginTop: "15px" }}>
                  <FormLabel
                    id="demo-row-radio-buttons-group-label"
                    sx={{ fontWeight: "bold" }}
                  >
                    Account Type:
                  </FormLabel>
                </Grid>
                <Grid item md={9} sx={{ marginTop: "5px" }}>
                  <RadioGroup
                    row
                    aria-labelledby="demo-row-radio-buttons-group-label"
                    name="row-radio-buttons-group"
                    onChange={(e) => setAccountType(e.target.value)}
                  >
                    <FormControlLabel
                      value="participant"
                      control={<Radio />}
                      label="Participant"
                    />
                    <FormControlLabel
                      value="organizer"
                      control={<Radio />}
                      label="Organizer"
                    />
                  </RadioGroup>
                </Grid>
              </Grid>
            </Grid>
            <Grid item container direction="row" spacing={2}>
              <Grid item md={6}>
                <TextField
                  required
                  label="Screen Name"
                  fullWidth
                  autoComplete="given-name"
                  variant="outlined"
                  onChange={(e) => setScreenName(e.target.value)}
                />
              </Grid>
              <Grid item md={6} container direction="row">
                <Grid item md={2} sx={{ marginTop: "15px" }}>
                  <FormLabel
                    id="demo-row-radio-buttons-group-label"
                    sx={{ fontWeight: "bold" }}
                  >
                    Gender:
                  </FormLabel>
                </Grid>
                <Grid item md={10} sx={{ marginTop: "5px" }}>
                  <RadioGroup
                    row
                    aria-labelledby="demo-row-radio-buttons-group-label"
                    name="row-radio-buttons-group"
                    onChange={(e) => setGender(e.target.value)}
                  >
                    <FormControlLabel
                      value="male"
                      control={<Radio />}
                      label="Male"
                    />
                    <FormControlLabel
                      value="female"
                      control={<Radio />}
                      label="Female"
                    />
                  </RadioGroup>
                </Grid>
              </Grid>
            </Grid>
            <Grid item>
              <TextField
                required
                label="Description"
                fullWidth
                multiline
                rows="3"
                autoComplete="family-name"
                variant="outlined"
                onChange={(e) => setDescription(e.target.value)}
              />
            </Grid>
            <Grid item container direction="row" spacing={2}>
              <Grid item md={6}>
                <TextField
                  required
                  label="Address Line 1"
                  fullWidth
                  autoComplete="given-name"
                  variant="outlined"
                  onChange={(e) => setAddress1(e.target.value)}
                />
              </Grid>
              <Grid item md={6}>
                <TextField
                  label="Address Line 2"
                  fullWidth
                  autoComplete="family-name"
                  variant="outlined"
                  onChange={(e) => setAddress2(e.target.value)}
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
                  onChange={(e) => setCity(e.target.value)}
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
                  onChange={(e) => setState(e.target.value)}
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
                  onChange={(e) => setZipcode(e.target.value)}
                />
              </Grid>
            </Grid>
            <Grid item>
              <Button
                type="submit"
                fullWidth
                style={{
                  width: "100%",
                  height: "55px",
                  cursor: "pointer",
                  background: "#1F51FF",
                  borderRadius: "5px",
                  color: "#ffffff",
                  textTransform: "none",
                  fontFamily: "Work Sans",
                  fontStyle: "normal",
                  fontWeight: "bold",
                  fontSize: "18px",
                }}
              >
                Save
              </Button>
            </Grid>
          </Grid>
        </form>
      </Grid>
    </Grid>
  );
};

export default UpdateUser;
