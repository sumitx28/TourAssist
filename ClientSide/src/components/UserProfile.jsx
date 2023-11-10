import React from 'react';
import {
  Box,
  Container,
  Grid,
  Paper,
  Typography,
  List,
  ListItem,
  ListItemText,
  Divider,
  Avatar,
} from '@mui/material';
import { styled, createTheme, ThemeProvider } from '@mui/material/styles';

const defaultTheme = createTheme();

const Item = styled(Paper)(({ theme }) => ({
  ...theme.typography.body2,
  padding: theme.spacing(2),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));

export default function UserProfile() {
  // Example user data
  const userData = {
    name: 'Jane Doe',
    email: 'jane.doe@example.com',
    bio: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus.',
    avatar: '/path/to/avatar.jpg', // Replace with path to user's avatar image
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Grid container spacing={3}>
          {/* Profile Section */}
          <Grid item xs={12} md={4}>
            <Item>
              <Avatar
                alt={userData.name}
                src={userData.avatar}
                sx={{ width: 128, height: 128, margin: 'auto' }}
              />
              <Typography variant="h6" component="h2" sx={{ mt: 2 }}>
                {userData.name}
              </Typography>
              <Typography color="text.secondary">{userData.email}</Typography>
            </Item>
          </Grid>

          {/* Details Section */}
          <Grid item xs={12} md={8}>
            <Item>
              <Typography variant="h6" component="h2">
                Bio
              </Typography>
              <Typography sx={{ mt: 1 }}>{userData.bio}</Typography>
            </Item>
          </Grid>

          {/* Other information can be added here in a similar pattern */}
        </Grid>
      </Container>
    </ThemeProvider>
  );
}
