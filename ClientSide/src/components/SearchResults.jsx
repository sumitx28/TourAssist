import React, { useState } from 'react';
import axios from 'axios'; // Make sure to install axios with 'npm install axios'
import API_URL from "../../config/config";
const SearchResults = () => {
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  // Search parameters
  const [sourceCity, setSourceCity] = useState('');
  const [destinationCity, setDestinationCity] = useState('');

  const [rating, setRating] = useState('');
  const [sort, setSort] = useState('');
  const [priceRange, setPriceRange] = useState([100, 500]);
  const [ratingRange, setRatingRange] = useState([1, 5]);

  // API token and headers (Replace 'YOUR_API_TOKEN' with your actual API token)
  const apiToken = API_URL;
  const headers = {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${apiToken}`,
  };  

  // Function to fetch travel packages based on filters and sorting
  const fetchTravelPackages = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await axios.post('http://localhost:8080/api/v1/search/travel-packages', {
        headers,
        params: {
          sourceCity,
          destinationCity,
          sortBy: sort,
          filterBy: `${priceRange && `price:${priceRange}`}${rating && `,packageRating:${rating}`}`
        }
      });

      setResults(response.data); // Assuming the API returns the array of results in the response body
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  // Handlers for input changes
  const handleSourceCityChange = (e) => setSourceCity(e.target.value);
  const handleDestinationCityChange = (e) => setDestinationCity(e.target.value);
  const handlePriceRangeChange = (e) => setPriceRange(e.target.value);
  const handleRatingChange = (e) => setRating(e.target.value);

  // Handlers for sorting
  const handleSortPrice = () => {
    setSort('priceSort:ASC'); // or 'priceSort:DESC' for descending
    fetchTravelPackages();
  };

  const handleSortRating = () => {
    setSort('packageRating:ASC'); // or 'packageRating:DESC' for descending
    fetchTravelPackages();
  };

  // Render search inputs, filters, sorting buttons, and results
  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap -mx-2">
        
        {/* Filter Pane */}
        <div className="w-full md:w-1/4 px-2 mb-4">
          <div className="bg-white p-4 shadow rounded">
            <h2 className="font-bold text-lg mb-4">Filters</h2>
            
            {/* Filter by Price Range */}
            <label className="block mb-2 text-sm font-medium text-gray-900" htmlFor="price-range">
              Price Range
            </label>
            <input
              id="price-range"
              type="text"
              placeholder="e.g. 0#1700"
              className="mb-4 p-2 border rounded w-full"
              value={priceRange}
              onChange={handlePriceRangeChange}
            />
            
            {/* Filter by Rating */}
            <label className="block mb-2 text-sm font-medium text-gray-900" htmlFor="rating">
              Rating
            </label>
            <input
              id="rating"
              type="text"
              placeholder="e.g. 4.5"
              className="mb-4 p-2 border rounded w-full"
              value={rating}
              onChange={handleRatingChange}
            />
          </div>
        </div>

        {/* Search Results and Sorting */}
        <div className="w-full md:w-3/4 px-2">
          {/* Sorting and Search Bar */}
          <div className="flex justify-between items-center bg-white p-4 shadow rounded mb-4">
            <input
              type="text"
              placeholder="Search..."
              className="p-2 border rounded w-full mr-4"
              // value and onChange would be added here
            />
            <button
              className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600"
              onClick={handleSortPrice}
            >
              Sort by Price
            </button>
            <button
              className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 ml-2"
              onClick={handleSortRating}
            >
              Sort by Rating
            </button>
          </div>

          {/* Results */}
          <div className="bg-white p-4 shadow rounded">
            <h2 className="font-bold text-lg mb-4">Search Results</h2>
            {loading ? (
              <div className="flex justify-center items-center h-64">
                Loading...
              </div>
            ) : (
              <div className="space-y-4">
                {results.map((result, index) => (
                  <div key={index} className="border-b last:border-b-0 py-4">
                    <h5 className="text-lg font-bold">{result.title}</h5>
                    <p className="text-gray-600">{result.description}</p>
                  </div>
                ))}
              </div>
            )}
            {error && <p className="text-red-500">{error}</p>}
          </div>
        </div>
      </div>
    </div>
  );
};

export default SearchResults;