import React, { useState } from "react";
import axios from "axios";

const Search = () => {
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [sourceCity, setSourceCity] = useState("");
  const [destinationCity, setDestinationCity] = useState("");
  const [packageStartDate, setPackageStartDate] = useState("");
  const [packageEndDate, setPackageEndDate] = useState("");
  const [numberOfGuest, setNumberOfGuest] = useState("");
  const [priceRange, setPriceRange] = useState("");
  const [isCustomizable, setIsCustomizable] = useState("");
  const [packageName, setPackageName] = useState("");
  const [packageRating, setPackageRating] = useState("");
  const [sortBy, setSortBy] = useState("");

  const authToken = localStorage.getItem("authToken");
  const headers = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${authToken}`,
  };

  const fetchTravelPackages = async () => {
    setLoading(true);
    setError("");

    const dataPayload = {
      sourceCity,
      destinationCity,
      packageStartDate,
      packageEndDate,
      numberOfGuest,
    };

    const queryParams = new URLSearchParams();
    if (sortBy) queryParams.append("sortBy", sortBy);
    if (priceRange) queryParams.append("filterBy", `price:${priceRange}`);
    if (isCustomizable)
      queryParams.append("filterBy", `isCustomizable:${isCustomizable}`);
    if (packageName)
      queryParams.append("filterBy", `packageName:${packageName}`);
    if (packageRating)
      queryParams.append("filterBy", `packageRating:${packageRating}`);

    const url = `http://localhost:8080/api/v1/search/travel-packages?${queryParams.toString()}`;

    try {
      const response = await axios.post(url, dataPayload, { headers });
      if (response.data && response.data.travelPackages) {
        setResults(response.data.travelPackages);
      } else {
        setError(
          "The response from the API does not have the expected structure."
        );
      }
    } catch (e) {
      setError(`Error: ${e.response ? e.response.data.message : e.message}`);
    } finally {
      setLoading(false);
    }
  };

  const handleSourceCityChange = (e) => setSourceCity(e.target.value);
  const handleDestinationCityChange = (e) => setDestinationCity(e.target.value);
  const handlePackageStartDateChange = (e) =>
    setPackageStartDate(new Date(e.target.value).toISOString());
  const handlePackageEndDateChange = (e) =>
    setPackageEndDate(new Date(e.target.value).toISOString());
  const handleNumberOfGuestChange = (e) => setNumberOfGuest(e.target.value);
  const handlePriceRangeChange = (e) => setPriceRange(e.target.value);
  const handleIsCustomizableChange = (e) => setIsCustomizable(e.target.value);
  const handlePackageNameChange = (e) => setPackageName(e.target.value);
  const handlePackageRatingChange = (e) => setPackageRating(e.target.value);
  const handleSortByChange = (e) => setSortBy(e.target.value);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-4">
      <div className="w-full max-w-2xl bg-white rounded-lg shadow-md p-6">
        <div className="grid grid-cols-1 gap-4 md:grid-cols-2">
          <input
            type="text"
            value={sourceCity}
            onChange={handleSourceCityChange}
            placeholder="Source City"
            className="border p-2 rounded-md"
          />
          <input
            type="text"
            value={destinationCity}
            onChange={handleDestinationCityChange}
            placeholder="Destination City"
            className="border p-2 rounded-md"
          />
          <input
            type="date"
            value={packageStartDate}
            onChange={handlePackageStartDateChange}
            className="border p-2 rounded-md"
          />
          <input
            type="date"
            value={packageEndDate}
            onChange={handlePackageEndDateChange}
            className="border p-2 rounded-md"
          />
          <input
            type="number"
            value={numberOfGuest}
            onChange={handleNumberOfGuestChange}
            placeholder="Number of Guests"
            min="1"
            className="border p-2 rounded-md"
          />
          <input
            type="text"
            value={priceRange}
            onChange={handlePriceRangeChange}
            placeholder="Price Range (e.g., 0#1700)"
            className="border p-2 rounded-md"
          />
          <input
            type="text"
            value={packageRating}
            onChange={handlePackageRatingChange}
            placeholder="Minimum Package Rating (e.g., 4.5)"
            className="border p-2 rounded-md"
          />
        </div>

        <div className="flex justify-between items-center mt-4">
          <select
            value={sortBy}
            onChange={handleSortByChange}
            className="border p-2 rounded-md"
          >
            <option value="">Sort By</option>
            <option value="priceSort:ASC">Price - Low to High</option>
            <option value="priceSort:DESC">Price - High to Low</option>
            <option value="packageRating:ASC">Rating - Low to High</option>
            <option value="packageRating:DESC">Rating - High to Low</option>
          </select>

          <button
            onClick={fetchTravelPackages}
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
          >
            Search Packages
          </button>
        </div>

        {/* Results section */}
        {loading && <p>Loading...</p>}
        {error && <p className="text-red-500">{error}</p>}
        <div className="mt-4">
          {console.log(results)}
          {results.length > 0 &&
            results.map((packageItem, index) => (
              <div
                key={index}
                className="bg-gray-50 p-4 rounded-lg shadow-sm mb-4"
              >
                <h2 className="text-xl font-semibold">
                  {packageItem.packageName}
                </h2>
                <p className="text-gray-700">
                  Price: ${packageItem.totalPackagePrice}
                </p>
                <p className="text-gray-700">
                  Start Date:{" "}
                  {new Date(packageItem.tripStartDate).toLocaleDateString()}
                </p>
                <p className="text-gray-700">
                  End Date:{" "}
                  {new Date(packageItem.tripEndDate).toLocaleDateString()}
                </p>
                <p className="text-gray-700">
                  Is Customizable:{" "}
                  {packageItem.isPackageCustomizable ? "Yes" : "No"}
                </p>
                <p className="text-gray-700">
                  Agency: {packageItem.agentDetails.companyName}
                </p>
              </div>
            ))}
        </div>
      </div>
    </div>
  );
};

export default Search;
