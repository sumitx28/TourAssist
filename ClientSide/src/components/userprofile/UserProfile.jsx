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

  const authToken = localStorage.getItem("authToken");
  const user = jwtDecode(authToken);
  const navigate = useNavigate();

  const [data, setdata] = useState({
    id: 0,
    firstName: "",
    lastName: "",
    mobile: "",
    dateOfBirth: "",
    country: "",
    companyName: "",
    employeeCount: "",
    verificationId: "",
  });

  const [isEditMode, setIsEditMode] = useState(false);
  const [isMobileEditMode, setIsMobileEditMode] = useState(false);

  const API_URL = process.env.API_URL;

  const formatDate = (dateString) => {
    const options = { day: "2-digit", month: "2-digit", year: "numeric" };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };

  useEffect(() => {
    const authToken = localStorage.getItem("authToken");
    const user = jwtDecode(authToken);

    let apiEndpoint = `${API_URL}/api/v1/customer/${user.appUserId}`;

    if (user.role == "AGENT") {
      apiEndpoint = `${API_URL}/api/v1/agent/${user.appUserId}`;
    }

    const headers = {
      Authorization: `Bearer ${authToken}`,
    };

    axios
      .get(apiEndpoint, { headers })
      .then((response) => {
        setdata(response.data);
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
    window.location.reload(-1);
    setIsMobileEditMode(false);
  };

  const handleSaveEmail = () => {
    updateUserDetails();
    navigate("/agent-login");
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
      alert(`Profile updated successfully! Email: ${data.email}`);
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
          {user.role == "CUSTOMER" && (
            <ListItem>
              <ListItemText
                primary={
                  <Typography variant="body1" color="textSecondary" paragraph>
                    <strong>Name:</strong>{" "}
                    {`${data.firstName} ${data.lastName}`}
                  </Typography>
                }
              />
            </ListItem>
          )}
          {user.role == "AGENT" && (
            <ListItem>
              <ListItemText
                primary={
                  <Typography variant="body1" color="textSecondary" paragraph>
                    <strong>Company:</strong> {`${data.companyName}`}
                  </Typography>
                }
              />
            </ListItem>
          )}
          <ListItem>
            {user.role == "CUSTOMER" && isMobileEditMode ? (
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
                    <strong>Mobile:</strong> {data.mobile}
                  </Typography>
                }
              />
            )}
            {user.role == "CUSTOMER" && isMobileEditMode ? (
              <CheckIcon
                color="primary"
                onClick={handleSaveMobile}
                sx={{ cursor: "pointer", marginLeft: 1 }}
              />
            ) : (
              user.role == "CUSTOMER" && (
                <EditIcon
                  color="primary"
                  onClick={handleMobileToggleEditMode}
                  sx={{ cursor: "pointer", marginLeft: 1 }}
                />
              )
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
          {user.role == "AGENT" && (
            <ListItem>
              <ListItemText
                primary={
                  <Typography variant="body1" color="textSecondary" paragraph>
                    <strong>Verification ID:</strong> {`${data.verificationId}`}
                  </Typography>
                }
              />
            </ListItem>
          )}
          {user.role == "CUSTOMER" && (
            <ListItem>
              <ListItemText
                primary={
                  <Typography variant="body1" color="textSecondary" paragraph>
                    <strong>Date of Birth:</strong>{" "}
                    {formatDate(data.dateOfBirth)}
                  </Typography>
                }
              />
            </ListItem>
          )}
          {user.role == "CUSTOMER" && (
            <ListItem>
              <ListItemText
                primary={
                  <Typography variant="body1" color="textSecondary" paragraph>
                    <strong>Country:</strong> {data.country}
                  </Typography>
                }
              />
            </ListItem>
          )}
        </List>
      </CardContent>
    </Card>
  );
}
