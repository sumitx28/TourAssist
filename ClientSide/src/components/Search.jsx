import React, { useState } from 'react';
import axios from 'axios';

const Search = () => {
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  // API token and headers
  const authToken = localStorage.getItem("authToken");
  const headers = {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${authToken}`,
  };

   // Search parameters
   const [sourceCity, setSourceCity] = useState('');
   const [destinationCity, setDestinationCity] = useState('');
   const [packageStartDate, setPackageStartDate] = useState('');
   const [packageEndDate, setPackageEndDate] = useState('');
   const [numberOfGuest, setNumberOfGuest] = useState(1);

   const fetchTravelPackages = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await axios({
        method: 'get',
        url: 'http://localhost:8080/api/v1/search/travel-packages',
        headers: headers,
        params: {
          sortBy: 'packageName', // Sorting by package name
          // Add other parameters here if needed
        },
        data: {
          sourceCity,
          destinationCity,
          packageStartDate,
          packageEndDate,
          numberOfGuest,
        }
      });
      
      setResults(response.data);
    } catch (e) {
      setError(`Error: ${e.response ? e.response.data.message : e.message}`);
    } finally {
      setLoading(false);
    }
  };
  const handleSourceCityChange = (e) => setSourceCity(e.target.value);
  const handleDestinationCityChange = (e) => setDestinationCity(e.target.value);
  const handlePackageStartDateChange = (e) => setPackageStartDate(e.target.value);
  const handlePackageEndDateChange = (e) => setPackageEndDate(e.target.value);
  const handleNumberOfGuestChange = (e) => setNumberOfGuest(e.target.value);

  return (
    <div>
      <input type="text" value={sourceCity} onChange={handleSourceCityChange} placeholder="Source City" />
      <input type="text" value={destinationCity} onChange={handleDestinationCityChange} placeholder="Destination City" />
      <input type="date" value={packageStartDate} onChange={handlePackageStartDateChange} />
      <input type="date" value={packageEndDate} onChange={handlePackageEndDateChange} />
      <input type="number" value={numberOfGuest} onChange={handleNumberOfGuestChange} placeholder="Number of Guests" min="1" />
      <button onClick={fetchTravelPackages}>Search Packages</button>
      {loading && <p>Loading...</p>}
      {error && <p>{error}</p>}
      {results.length > 0 && (
        <ul>
          {results.map((packageItem, index) => (
            <li key={index}>{packageItem.packageName}</li> // Replace with actual property names
          ))}
        </ul>
      )}
    </div>
  );
};

export default Search;
