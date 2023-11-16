import React from 'react';
import {
  Box,
  Container,
  Grid,
  Paper,
  Typography,
  TextField,
  Button,
  Avatar,
} from '@mui/material';
import { styled, createTheme, ThemeProvider } from '@mui/material/styles';

const defaultTheme = createTheme();

const Item = styled(Paper)(({ theme }) => ({
  ...theme.typography.body2,
  padding: theme.spacing(2),
  margin: theme.spacing(1),
  color: theme.palette.text.secondary,
}));

export default function UserProfile() {
  // Example user data, replace the placeholder data with actual data
  const userData = {
    firstName: 'Jane',
    lastName: 'Doe',
    email: 'jane.doe@example.com',
    password: '********', // Password should not be displayed in plain text for security reasons
    mobileNumber: '123-456-7890',
    dateOfBirth: '1990-01-01', // Format the date accordingly
    country: 'Canada',
    avatar: '/path/to/avatar.jpg', // Replace with path to user's avatar image
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Box sx={{ flexGrow: 1 }}>
          <Grid container spacing={2} justifyContent="center">
            {/* Avatar & Name */}
            <Grid item xs={12} sm={6} md={4}>
              <Item>
                <Avatar
                  alt={`${userData.firstName} ${userData.lastName}`}
                  src={userData.avatar}
                  sx={{ width: 128, height: 128, margin: 'auto' }}
                />
                <Typography variant="h5" component="h2" sx={{ mt: 2 }}>
                  {`${userData.firstName} ${userData.lastName}`}
                </Typography>
              </Item>
            </Grid>

            {/* User Details */}
            <Grid item xs={12} sm={6} md={8}>
              <Item>
                <Typography variant="h6" component="h2" gutterBottom>
                  Personal Information
                </Typography>
                <form noValidate autoComplete="off">
                  <TextField
                    fullWidth
                    label="First Name"
                    margin="normal"
                    value={userData.firstName}
                    InputProps={{
                      readOnly: true,
                    }}
                  />
                  <TextField
                    fullWidth
                    label="Last Name"
                    margin="normal"
                    value={userData.lastName}
                    InputProps={{
                      readOnly: true,
                    }}
                  />
                  <TextField
                    fullWidth
                    label="Email Address"
                    margin="normal"
                    value={userData.email}
                    InputProps={{
                      readOnly: true,
                    }}
                  />
                  <TextField
                    fullWidth
                    label="Password"
                    margin="normal"
                    value={userData.password}
                    type="password"
                    InputProps={{
                      readOnly: true,
                    }}
                  />
                  <TextField
                    fullWidth
                    label="Mobile Number"
                    margin="normal"
                    value={userData.mobileNumber}
                    InputProps={{
                      readOnly: true,
                    }}
                  />
                  <TextField
                    fullWidth
                    label="Date of Birth"
                    margin="normal"
                    value={userData.dateOfBirth}
                    InputProps={{
                      readOnly: true,
                    }}
                  />
                  <TextField
                    fullWidth
                    label="Country"
                    margin="normal"
                    value={userData.country}
                    InputProps={{
                      readOnly: true,
                    }}
                  />
                  <Box mt={3}>
                    <Button variant="contained" color="primary">
                      Edit Profile
                    </Button>
                  </Box>
                </form>
              </Item>
            </Grid>
          </Grid>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
