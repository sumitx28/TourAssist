import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';

const CustomerDetails = () => {
  const [customerDetails, setCustomerDetails] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const authToken = localStorage.getItem("authToken");
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

  const hasCustomerDetails = customerDetails && Array.isArray(customerDetails) && customerDetails.length > 0;

  useEffect(() => {
    const fetchCustomerDetails = async () => {
      setLoading(true);
      try {
        const response = await axios.get('http://localhost:8080/api/v1/booking/customer-details/1', {
          headers: {
            'Authorization': `Bearer ${authToken}`
          }
        });
        setCustomerDetails(response.data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCustomerDetails();
  }, []);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap justify-center">
        <div className="w-full sm:w-1/2 md:w-3/4 p-2">
          <div className="bg-white shadow rounded-lg p-4">
            <h3 className="text-lg font-semibold mb-4">Customer Details</h3>
            <div className="space-y-3">
              {hasCustomerDetails ? (
                <ul>
                  {customerDetails.map((detail, index) => (
                    <li key={index}>
                      <p>Customer ID: {detail.customer.id}</p>
                      <p>Name: {detail.customer.firstName} {detail.customer.lastName}</p>
                      <p>Mobile: {detail.customer.mobile}</p>
                      <p>Date of Birth: {new Date(detail.customer.dateOfBirth).toLocaleDateString()}</p>
                      <p>Country: {detail.customer.country}</p>
                    </li>
                  ))}
                </ul>
              ) : (
                <p>No customer details found.</p>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CustomerDetails;
