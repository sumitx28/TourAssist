import React, { useState } from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Snackbar from "@mui/material/Snackbar";
import MuiAlert from "@mui/material/Alert";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import countries from "../data/countries.json";
import { Link as RouterLink, useNavigate } from "react-router-dom";
import axios from "axios";
import TravelLogo from "./commons/TravelLogo";
import { Paper } from "@mui/material";
import Copyright from "./commons/Copyright";

const defaultTheme = createTheme();

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

export default function SignUp() {
  const [data, setData] = useState({
    userType: "customer",
    country: "Canada",
    dob: null,
  });

  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [loginStatus, setLoginStatus] = useState(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarSeverity, setSnackbarSeverity] = useState("success");
  const API_URL = process.env.API_URL;

  const handleSnackbarClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }

    setSnackbarOpen(false);
  };

  const showSnackbar = (message, severity) => {
    setSnackbarMessage(message);
    setSnackbarSeverity(severity);
    setSnackbarOpen(true);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);

    const requiredFields = ["email", "password", "mobile-number"];
    if (data.userType === "customer") {
      requiredFields.push("firstName", "lastName", "country");
    } else if (data.userType === "agent") {
      requiredFields.push(
        "company-name",
        "number-of-employees",
        "verification-id"
      );
    }

    const isEmptyField = requiredFields.some(
      (field) => formData.get(field) === ""
    );

    if (isEmptyField) {
      showSnackbar("Fill all required fields", "error");
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.get("email"))) {
      showSnackbar("Invalid email format", "error");
      return;
    }

    const password = formData.get("password");
    if (password.length < 8) {
      showSnackbar("Password must be at least 8 characters long", "error");
      return;
    }

    const mobileNumberRegex = /^[0-9]{10}$/;
    if (!mobileNumberRegex.test(formData.get("mobile-number"))) {
      showSnackbar("Invalid mobile number format", "error");
      return;
    }

    try {
      setLoading(true);

      const userData = {
        firstName:
          data.userType === "customer" ? formData.get("firstName") : null,
        lastName:
          data.userType === "customer" ? formData.get("lastName") : null,
        email: formData.get("email"),
        password: formData.get("password"),
        companyName: formData.get("company-name"),
        mobile: formData.get("mobile-number"),
        dateOfBirth: data.dob,
        country: data.country,
        employeeCount:
          data.userType === "agent"
            ? formData.get("number-of-employees")
            : null,
        verificationId:
          data.userType === "agent" ? formData.get("verification-id") : null,
      };

      const response = await axios.post(
        `${API_URL}/api/v1/auth/register/${data.userType}`,
        userData,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      console.log(response);
      console.log(userData);

      if (response.status === 200) {
        showSnackbar("Signup Successful.", "success");
        setTimeout(() => {
          navigate("/login");
        }, 2000);
      } else {
        showSnackbar("Invalid Details. Try Again!!", "error");
        setTimeout(() => {
          setLoginStatus(null);
          setLoading(false);
        }, 2000);
      }
    } catch (error) {
      showSnackbar("Backend Down. Try Again later!!", "error");
      setTimeout(() => {
        setLoginStatus(null);
        setLoading(false);
      }, 2000);
      console.error("An error occurred:", error);
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <div style={{ display: "flex", height: "100vh", width: "100vw" }}>
        <div style={{ flex: "0 0 60%", overflow: "hidden" }}>
          <TravelLogo />
        </div>
        <div
          style={{
            display: "flex",
            flex: "1",
            overflowY: "auto",
            width: "100%",
          }}
        >
          <Grid
            item
            xs={12}
            sm={8}
            md={5}
            sx={{ display: "flex", flexDirection: "column", flexGrow: 1 }}
          >
            <Box
              sx={{
                my: 8,
                mx: 4,
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
                <LockOutlinedIcon />
              </Avatar>
              <Typography component="h1" variant="h5">
                Sign up
              </Typography>
              <Box
                component="form"
                noValidate
                onSubmit={handleSubmit}
                sx={{ mt: 1 }}
              >
                <Select
                  labelId="user-type-label"
                  id="user-type"
                  value={data.userType}
                  onChange={(event) =>
                    setData({ ...data, userType: event.target.value })
                  }
                  fullWidth
                  sx={{ mt: 2 }}
                >
                  <MenuItem value="customer">Customer</MenuItem>
                  <MenuItem value="agent">Agent</MenuItem>
                </Select>
                <Grid container spacing={2} sx={{ mt: 1 }}>
                  {data.userType === "customer" && (
                    <Grid item xs={12} sm={6}>
                      <TextField
                        autoComplete="given-name"
                        name="firstName"
                        required
                        fullWidth
                        id="firstName"
                        label="First Name"
                        autoFocus
                      />
                    </Grid>
                  )}
                  {data.userType === "customer" && (
                    <Grid item xs={12} sm={6}>
                      <TextField
                        required
                        fullWidth
                        id="lastName"
                        label="Last Name"
                        name="lastName"
                        autoComplete="family-name"
                      />
                    </Grid>
                  )}
                  <Grid item xs={12} sx={{ mt: 1 }}>
                    <TextField
                      required
                      fullWidth
                      id="email"
                      label="Email Address"
                      name="email"
                      autoComplete="email"
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      name="password"
                      label="Password"
                      type="password"
                      id="password"
                      autoComplete="new-password"
                    />
                  </Grid>
                  {data.userType === "agent" && (
                    <Grid item xs={12}>
                      <TextField
                        required
                        fullWidth
                        id="company-name"
                        label="Company Name"
                        name="company-name"
                        autoComplete="company-name"
                      />
                    </Grid>
                  )}
                  {data.userType === "agent" && (
                    <Grid item xs={12}>
                      <TextField
                        required
                        fullWidth
                        id="number-of-employees"
                        label="Employee Count"
                        type="number"
                        name="number-of-employees"
                      />
                    </Grid>
                  )}
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      name="mobile-number"
                      label="Mobile Number"
                      id="mobile-number"
                      autoComplete="mobile-number"
                    />
                  </Grid>
                  {data.userType === "agent" && (
                    <Grid item xs={12}>
                      <TextField
                        required
                        fullWidth
                        id="verification-id"
                        label="Company Registeration ID"
                        type="text"
                        name="verification-id"
                      />
                    </Grid>
                  )}
                  {data.userType === "customer" && (
                    <Grid item xs={12}>
                      <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <DatePicker
                          label="Date of Birth"
                          value={data.dob}
                          onChange={(date) =>
                            setData({
                              ...data,
                              dob: new Date(date).toISOString(),
                            })
                          }
                          disableFuture={true}
                          sx={{ width: "100%" }}
                        />
                      </LocalizationProvider>
                    </Grid>
                  )}
                  {data.userType === "customer" && (
                    <Grid item xs={12}>
                      <Select
                        id="country"
                        value={data.country}
                        onChange={(event) =>
                          setData({ ...data, country: event.target.value })
                        }
                        fullWidth
                        required
                        sx={{ mt: 2 }}
                      >
                        {countries.countries.map((country) => (
                          <MenuItem key={country.code} value={country.name}>
                            {country.name}
                          </MenuItem>
                        ))}
                      </Select>
                    </Grid>
                  )}
                </Grid>
                <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  sx={{
                    mt: 3,
                    mb: 2,
                    backgroundColor: loading ? "gray" : undefined,
                  }}
                  disabled={loading}
                >
                  {loading
                    ? "Signing up..."
                    : loginStatus === "success"
                    ? "Success!"
                    : "Sign Up"}
                </Button>
                <Grid container justifyContent="flex-end">
                  <Grid item>
                    <Link variant="body2">
                      <RouterLink to={"/login"}>
                        Already have an account? Sign in
                      </RouterLink>
                    </Link>
                  </Grid>
                </Grid>
                <Copyright sx={{ mt: 5 }} />
              </Box>
            </Box>
          </Grid>
        </div>
      </div>
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={handleSnackbarClose}
      >
        <Alert onClose={handleSnackbarClose} severity={snackbarSeverity}>
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </ThemeProvider>
  );
}
