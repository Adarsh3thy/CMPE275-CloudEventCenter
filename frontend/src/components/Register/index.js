import React, { useState } from "react";
import Image52 from "../../assets/google-logo.png";
import UndrawImage from "../../assets/isometric.png";
import arrowUp from "../../assets/arrow-up.svg";
import { logo } from "../../utils/constants";
import { TextField, Typography, Button, Grid, MenuItem } from "@mui/material";
import Snackbar from "@mui/material/Snackbar";
import MuiAlert from "@mui/material/Alert";
import { registerUser } from "../../controllers/authentication";

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

const Register = ({ ...props }) => {
  const [fullName, setFullName] = useState(null);
  const [role, setRole] = useState(null);
  const [email, setEmail] = useState(null);
  const [password, setPassword] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);
  const [isSubmitted, setIsSubmitted] = useState(false);
  // Hook for the MUI snackbar alert
  const [open, setOpen] = useState(false);

  const handleClose = (e) => {
    e.preventDefault();
    setOpen(false);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setIsSubmitted(true);
    let data = {};
    data.fullName = fullName;
    data.email = email;
    data.password = password;
    data.screenName = fullName;
    data.role = new Array(role);
    registerUser(data)
      .then((res) => {
        setIsSubmitted(false);
        setSuccessMessage(res.message + " Redirecting...");
        setTimeout(() => {
          window.location.href = "/login";
        }, 3000);
      })
      .catch((err) => {
        console.log(err);
        setIsSubmitted(false);
        setErrorMessage("Something went wrong");
        setOpen(true);
      });
  };

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
      <Grid item md={1.5} lg={1.5} xl={1.5} />
      <Grid
        item
        container
        direction="column"
        xs={12}
        sm={12}
        md={4}
        lg={4}
        xl={4}
      >
        <Grid item>
          <img
            src={logo}
            style={{
              width: "239.58px",
              height: "112.93px",
              cursor: "pointer",
              marginTop: "84.27px",
            }}
          />
        </Grid>
        <Grid item>
          <span
            style={{
              fontFamily: "Work Sans",
              fontStyle: "normal",
              fontWeight: "normal",
              fontSize: "18px",
              color: "#000000",
            }}
          >
            Organizing events has never been that easy.
          </span>
        </Grid>
        <Snackbar
          open={errorMessage}
          autoHideDuration={6000}
          onClose={handleClose}
        >
          <Alert severity="error" sx={{ width: "100%" }}>
            {errorMessage}
          </Alert>
        </Snackbar>
        <Grid item>
          <Typography
            style={{
              height: "45px",
              fontFamily: "Work Sans",
              fontStyle: "normal",
              fontWeight: "bold",
              fontSize: "30px",
              color: "#000000",
              marginTop: "25px",
            }}
          >
            Get started with CEC!
          </Typography>
        </Grid>
        <form onSubmit={handleSubmit}>
          <Grid item>
            <TextField
              required
              type="text"
              autoComplete="fullName"
              placeholder="Full Name"
              style={{
                width: "395px",
                height: "67px",
                boxSizing: "border-box",
                borderRadius: "5px",
                marginTop: "30px",
              }}
              error={open ? true : false}
              onChange={(e) => setFullName(e.target.value)}
            />
          </Grid>
          <Grid item>
            <TextField
              required
              type="email"
              autoComplete="email"
              placeholder="Email Address"
              style={{
                width: "395px",
                height: "67px",
                boxSizing: "border-box",
                borderRadius: "5px",
              }}
              error={open ? true : false}
              onChange={(e) => setEmail(e.target.value)}
            />
          </Grid>
          <Grid item>
            <TextField
              required
              type="password"
              autoComplete="current-password"
              placeholder="Password"
              style={{
                width: "395px",
                height: "67px",
                boxSizing: "border-box",
                borderRadius: "5px",
                marginTop: "5px",
              }}
              error={open ? true : false}
              onChange={(e) => setPassword(e.target.value)}
            />
          </Grid>
          <Grid item>
            <TextField
              required
              select
              label="Account type"
              style={{
                width: "395px",
                height: "67px",
                boxSizing: "border-box",
                borderRadius: "5px",
              }}
              error={open ? true : false}
              onChange={(e) => setRole(e.target.value)}
            >
              <MenuItem value={"participant"}>Participant</MenuItem>
              <MenuItem value={"organizer"}>Organizer</MenuItem>
            </TextField>
          </Grid>
          <Grid item>
            <Button
              type="submit"
              style={{
                width: "395px",
                height: "68px",
                cursor: "pointer",
                background: "#8A2BE2",
                borderRadius: "5px",
                color: "#ffffff",
                textTransform: "none",
                fontFamily: "Work Sans",
                fontStyle: "normal",
                fontWeight: "bold",
                fontSize: "18px",
                marginTop: "12px",
              }}
              disabled={isSubmitted ? true : false}
            >
              <img
                src={arrowUp}
                style={{
                  position: "static",
                  width: "24px",
                  height: "24px",
                  flex: "none",
                  order: 0,
                  flexGrow: 0,
                  margin: "0px 8px",
                }}
              />
              {isSubmitted ? "Please wait.." : "Sign Up"}
            </Button>
          </Grid>
        </form>
        <Grid item>
          <div
            style={{
              height: "28px",
              fontFamily: "Work Sans",
              fontStyle: "normal",
              fontWeight: "normal",
              fontSize: "18px",
              lineHeight: "27px",
              color: "#000000",
              opacity: 0.5,
              marginTop: "25px",
            }}
          >
            Or Sign Up with:
          </div>
        </Grid>
        <Grid item container direction="row">
          <Grid
            item
            style={{
              marginTop: "15px",
              marginRight: "15px",
            }}
          >
            <div
              style={{
                width: "100px",
                height: "68px",
                cursor: "pointer",
                background: "#E6E6FA",
                border: "1px solid rgba(33, 131, 223, 0.52)",
                boxSizing: "border-box",
                borderRadius: "5px",
                textAlign: "center",
              }}
              // onClick={handleGoogleRegister}
            >
              <img
                src={Image52}
                style={{
                  width: "48px",
                  height: "48px",
                  cursor: "pointer",
                  marginTop: "10px",
                }}
              />
            </div>
          </Grid>
        </Grid>
        <Grid
          item
          container
          direction="row"
          style={{
            marginTop: "25px",
            fontFamily: "Work Sans",
            fontStyle: "normal",
            fontWeight: "normal",
            fontSize: "18px",
          }}
        >
          <Grid
            item
            style={{
              color: "#000000",
              opacity: 0.5,
            }}
          >
            <div>Already have an account?</div>
          </Grid>
          <Grid item>
            <a
              style={{
                cursor: "pointer",
                color: "#8A2BE2",
                fontWeight: "bold",
                marginLeft: "10px",
              }}
              onClick={() => {
                window.location.href = "/login";
              }}
            >
              Sign in
            </a>
          </Grid>
        </Grid>
      </Grid>
      <Grid
        item
        sx={{
          display: {
            xs: "none",
            sm: "none",
            md: "block",
            lg: "block",
            xl: "block",
          },
        }}
        md={6.5}
        lg={6.5}
        xl={6.5}
      >
        <img
          src={UndrawImage}
          style={{
            marginTop: "30px",
          }}
        />
      </Grid>
      <Snackbar
        open={successMessage}
        autoHideDuration={6000}
        onClose={handleClose}
      >
        <Alert severity="success" sx={{ width: "100%" }}>
          {successMessage}
        </Alert>
      </Snackbar>
    </Grid>
  );
};

export default Register;
