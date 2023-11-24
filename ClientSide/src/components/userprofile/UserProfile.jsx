import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import TextField from "@mui/material/TextField";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import CheckIcon from "@mui/icons-material/Check";
import EditIcon from "@mui/icons-material/Edit";
import { useNavigate } from "react-router-dom";

export default function UserProfile() {
  const [editFields, setEditFields] = useState({
    email: "",
    mobileNumber: "",
  });

  const [customerData, setCustomerData] = useState({
    id: 0,
    firstName: "",
    lastName: "",
    mobile: "",
    dateOfBirth: "",
    country: "",
  });
  const [isEditMode, setIsEditMode] = useState(false);
  const [isMobileEditMode, setIsMobileEditMode] = useState(false);
  const navigate = useNavigate();

  const API_URL = process.env.API_URL;

  const formatDate = (dateString) => {
    const options = { day: "2-digit", month: "2-digit", year: "numeric" };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };

  useEffect(() => {
    const authToken = localStorage.getItem("authToken");
    const user = jwtDecode(authToken);

    const apiEndpoint = `${API_URL}/api/v1/customer/${user.appUserId}`;

    const headers = {
      Authorization: `Bearer ${authToken}`,
    };

    axios
      .get(apiEndpoint, { headers })
      .then((response) => {
        setCustomerData(response.data);
        setEditFields({ email: user.sub, mobileNumber: response.data.mobile });
      })
      .catch((error) => {
        console.error("Error fetching customer data:", error);
      });

    setEditFields({ ...editFields, email: user.sub });
  }, []);

  const handleToggleEditMode = () => {
    setIsEditMode(!isEditMode);
  };

  const handleMobileToggleEditMode = () => {
    setIsMobileEditMode(!isMobileEditMode);
  };

  const handleInputChange = (e) => {
    setEditFields({ ...editFields, [e.target.name]: e.target.value });
  };

  const handleSaveMobile = () => {
    updateUserDetails();
    window.location.reload(false);
    setIsMobileEditMode(false);
  };

  const handleSaveEmail = () => {
    updateUserDetails();
    window.location.reload(false);
    setIsEditMode(false);
  };

  const updateUserDetails = async () => {
    const authToken = localStorage.getItem("authToken");
    const user = jwtDecode(authToken);
    const apiEndpoint = `${API_URL}/api/v1/user-profile/update-profile`;

    const headers = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${authToken}`,
    };

    const data = {
      appUserId: user.appUserId,
      mobile: editFields.mobileNumber,
      email: editFields.email,
    };

    try {
      await axios.post(apiEndpoint, data, { headers });
      alert("Profile updated successfully!");
    } catch (error) {
      console.error("Error updating profile:", error);
    }
  };

  return (
    <Card
      elevation={0}
      sx={{ textAlign: "center", backgroundColor: "transparent" }}
    >
      <CardContent>
        <Typography variant="h5" gutterBottom sx={{ marginBottom: 3 }}>
          Personal Information
        </Typography>
        <List sx={{ marginBottom: 2 }}>
          <ListItem>
            <ListItemText
              primary={
                <Typography variant="body1" color="textSecondary" paragraph>
                  <strong>Name:</strong>{" "}
                  {`${customerData.firstName} ${customerData.lastName}`}
                </Typography>
              }
            />
          </ListItem>
          <ListItem>
            {isMobileEditMode ? (
              <TextField
                label="Mobile"
                name="mobileNumber"
                value={editFields.mobileNumber}
                onChange={handleInputChange}
                variant="outlined"
                fullWidth
                margin="normal"
                sx={{ marginBottom: 2 }}
              />
            ) : (
              <ListItemText
                primary={
                  <Typography variant="body1" color="textSecondary" paragraph>
                    <strong>Mobile:</strong> {customerData.mobile}
                  </Typography>
                }
              />
            )}
            {isMobileEditMode ? (
              <CheckIcon
                color="primary"
                onClick={handleSaveMobile}
                sx={{ cursor: "pointer", marginLeft: 1 }}
              />
            ) : (
              <EditIcon
                color="primary"
                onClick={handleMobileToggleEditMode}
                sx={{ cursor: "pointer", marginLeft: 1 }}
              />
            )}
          </ListItem>
          <ListItem>
            <ListItemText
              primary={
                isEditMode ? (
                  <TextField
                    label="Email"
                    name="email"
                    value={editFields.email}
                    onChange={handleInputChange}
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    sx={{ marginBottom: 2 }}
                  />
                ) : (
                  <Typography variant="body1" color="textSecondary" paragraph>
                    <strong>Email:</strong> {editFields.email}
                  </Typography>
                )
              }
            />
            {isEditMode ? (
              <CheckIcon
                color="primary"
                onClick={handleSaveEmail}
                sx={{ cursor: "pointer", marginLeft: 1 }}
              />
            ) : (
              <EditIcon
                color="primary"
                onClick={handleToggleEditMode}
                sx={{ cursor: "pointer", marginLeft: 1 }}
              />
            )}
          </ListItem>
          <ListItem>
            <ListItemText
              primary={
                <Typography variant="body1" color="textSecondary" paragraph>
                  <strong>Date of Birth:</strong>{" "}
                  {formatDate(customerData.dateOfBirth)}
                </Typography>
              }
            />
          </ListItem>
          <ListItem>
            <ListItemText
              primary={
                <Typography variant="body1" color="textSecondary" paragraph>
                  <strong>Country:</strong> {customerData.country}
                </Typography>
              }
            />
          </ListItem>
        </List>
      </CardContent>
    </Card>
  );
}
