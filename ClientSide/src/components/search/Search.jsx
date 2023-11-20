import React, { useState } from 'react';
import axios from 'axios';
import Search2 from "./Search2";

const Search = () => {
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const authToken = localStorage.getItem("authToken");
  const headers = {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${authToken}`,
  };

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
          sortBy: 'packageName',
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
        <div>
          {results.travelPackages.map((packageItem, index) => (
            <div key={index} style={{ margin: '20px', padding: '20px', border: '1px solid #ddd' }}>
              <h2>{packageItem.packageName}</h2>
              <p>Price: ${packageItem.totalPackagePrice}</p>
              <p>Start Date: {new Date(packageItem.tripStartDate).toLocaleDateString()}</p>
              <p>End Date: {new Date(packageItem.tripEndDate).toLocaleDateString()}</p>
              <p>Is Customizable: {packageItem.isPackageCustomizable ? 'Yes' : 'No'}</p>
              <p>Agency: {packageItem.agentDetails.companyName}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Search;
