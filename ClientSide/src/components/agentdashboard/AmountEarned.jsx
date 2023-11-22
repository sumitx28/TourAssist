import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';

const LastYearRevenue = () => {
  const [revenue, setRevenue] = useState(null);
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

  useEffect(() => {
    const fetchRevenue = async () => {
      setLoading(true);
      try {
        const response = await axios.get('http://localhost:8080/api/v1/revenue/last_year/1', {
          headers: {
            'Authorization': `Bearer ${authToken}`
          }
        });
        setRevenue(response.data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchRevenue();
  }, []);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;
  if (revenue === null) return <p>No revenue data available.</p>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap justify-center">
        <div className="w-full sm:w-1/2 md:w-3/4 p-2">
          <div className="bg-white shadow rounded-lg p-4 text-center">
            <h3 className="text-lg font-semibold mb-4">Last Year's Revenue</h3>
            <p className="text-2xl">{`$${revenue.toFixed(2)}`}</p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LastYearRevenue;
