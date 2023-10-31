import React, { useState } from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
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
import API_URL from "../../config/config";
import TravelLogo from "./commons/TravelLogo";
import { Paper } from "@mui/material";
import Copyright from "./commons/Copyright";

const defaultTheme = createTheme();

export default function SignUp() {
  const [data, setData] = useState({
    userType: "customer",
    country: "Canada",
    dob: null,
  });

  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);

    try {
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
        alert("Signup Successful!");
        navigate("/login");
      } else {
        alert("Sign-up failed");
      }
    } catch (error) {
      console.error("An error occurred:", error);
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <div style={{ overflow: "hidden", height: "100vh", width: "100vw" }}>
        <Grid container component="main" sx={{ height: "100vh" }}>
          <CssBaseline />
          <TravelLogo />
          <Grid
            item
            xs={12}
            sm={8}
            md={5}
            component={Paper}
            elevation={6}
            square
          >
            <Box
              sx={{
                my: 8,
                mx: 4,
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                overflow: "hidden",
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
                  sx={{ mt: 3, mb: 2 }}
                >
                  Sign Up
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
        </Grid>
      </div>
    </ThemeProvider>
  );
}
