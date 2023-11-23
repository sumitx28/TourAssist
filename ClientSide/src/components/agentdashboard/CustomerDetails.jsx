import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import {
  Card,
  CardContent,
  Typography,
  List,
  ListItem,
  ListItemText,
} from "@mui/material";
import fetchUserDetails from "../../utility/requestUserDetails";

const CustomerDetails = () => {
  const [customerDetails, setCustomerDetails] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const API_URL = process.env.API_URL;
  const authToken = localStorage.getItem("authToken");

  useEffect(() => {
    if (!authToken) {
      setError("Authentication token is missing.");
      return;
    }
    let user;
    try {
      user = jwtDecode(authToken);
    } catch (e) {
      setError("Authentication token is invalid.");
      return;
    }

    const fetchCustomerDetails = async () => {
      setLoading(true);
      try {
        const userDetails = await fetchUserDetails("agent");

        const response = await axios.get(
          `${API_URL}/api/v1/booking/customer-details/${userDetails.id}`,
          {
            headers: {
              Authorization: `Bearer ${authToken}`,
            },
          }
        );
        setCustomerDetails(response.data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCustomerDetails();
  }, [authToken]);

  if (loading) return <Typography>Loading...</Typography>;
  if (error) return <Typography>Error: {error}</Typography>;

  const hasCustomerDetails =
    customerDetails &&
    Array.isArray(customerDetails) &&
    customerDetails.length > 0;

  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap justify-center">
        {hasCustomerDetails ? (
          customerDetails.map((detail, index) => (
            <Card key={index} sx={{ maxWidth: 345, m: 2 }}>
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  Customer ID: {detail.customer.id}
                </Typography>
                <List dense={true}>
                  <ListItem>
                    <ListItemText
                      primary="Name"
                      secondary={`${detail.customer.firstName} ${detail.customer.lastName}`}
                    />
                  </ListItem>
                  <ListItem>
                    <ListItemText
                      primary="Mobile"
                      secondary={detail.customer.mobile}
                    />
                  </ListItem>
                  <ListItem>
                    <ListItemText
                      primary="Date of Birth"
                      secondary={new Date(
                        detail.customer.dateOfBirth
                      ).toLocaleDateString()}
                    />
                  </ListItem>
                  <ListItem>
                    <ListItemText
                      primary="Country"
                      secondary={detail.customer.country}
                    />
                  </ListItem>
                </List>
              </CardContent>
            </Card>
          ))
        ) : (
          <Typography>No customer details found.</Typography>
        )}
      </div>
    </div>
  );
};

export default CustomerDetails;
