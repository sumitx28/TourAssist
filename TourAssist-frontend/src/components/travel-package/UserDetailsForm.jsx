import React, { useState } from "react";
import {
  DialogTitle,
  DialogContent,
  Grid,
  TextField,
  IconButton,
  Typography,
  Button,
  DialogActions,
  CircularProgress,
} from "@mui/material";
import RemoveCircleIcon from "@mui/icons-material/RemoveCircle";

const UserDetailsForm = ({
  userDetailsArray,
  handleUserDetailsChange,
  handleAddTraveler,
  handleRemoveTraveler,
  handleConfirmBooking,
}) => {
  const [isConfirmingBooking, setConfirmingBooking] = useState(false);

  const handleConfirmButtonClick = async () => {
    // Add any additional logic you need before confirming booking
    setConfirmingBooking(true);

    // Simulate asynchronous action (e.g., API call)
    await new Promise((resolve) => setTimeout(resolve, 1000));

    // Call handleConfirmBooking function
    handleConfirmBooking();

    // Reset the confirming booking state after the action is completed
    setConfirmingBooking(false);
  };

  return (
    <div>
      <DialogTitle style={{ backgroundColor: "#3f51b5", color: "#fff" }}>
        Enter Traveler Details
      </DialogTitle>
      <DialogContent>
        {userDetailsArray.map((user, index) => (
          <Grid
            container
            spacing={2}
            key={index}
            alignItems="center"
            style={{ marginTop: index > 0 ? 15 : 0 }}
          >
            <Grid item xs={12} md={2}>
              <Typography variant="subtitle2">Traveler {index + 1}</Typography>
            </Grid>
            <Grid item xs={12} md={3}>
              <TextField
                label="First Name"
                value={user.firstName}
                onChange={(e) =>
                  handleUserDetailsChange(index, "firstName", e.target.value)
                }
                fullWidth
              />
            </Grid>
            <Grid item xs={12} md={3}>
              <TextField
                label="Last Name"
                value={user.lastName}
                onChange={(e) =>
                  handleUserDetailsChange(index, "lastName", e.target.value)
                }
                fullWidth
              />
            </Grid>
            <Grid item xs={12} md={3}>
              <TextField
                type="date"
                label="Date of Birth"
                placeholder="DD/MM/YYYY"
                value={user.dateOfBirth}
                onChange={(e) =>
                  handleUserDetailsChange(index, "dateOfBirth", e.target.value)
                }
                fullWidth
                InputLabelProps={{ shrink: true }}
              />
            </Grid>
            <Grid item xs={12} md={1}>
              <IconButton
                onClick={() => handleRemoveTraveler(index)}
                color="secondary"
              >
                <RemoveCircleIcon />
              </IconButton>
            </Grid>
          </Grid>
        ))}
      </DialogContent>
      <DialogActions>
        <Button
          onClick={handleAddTraveler}
          color="primary"
          variant="outlined"
          style={{ marginRight: 10 }}
        >
          Add Traveler
        </Button>
        <Button
          onClick={handleConfirmButtonClick}
          color="primary"
          variant="contained"
          disabled={isConfirmingBooking}
        >
          {isConfirmingBooking ? (
            <CircularProgress size={24} color="inherit" />
          ) : (
            "Confirm Booking"
          )}
        </Button>
      </DialogActions>
    </div>
  );
};

export default UserDetailsForm;
