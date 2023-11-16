//   return (
//     <div>
//       <form onSubmit={handleSearch}>
//         <input
//           type="text"
//           value={query}
//           onChange={(e) => setQuery(e.target.value)}
//           placeholder="Search for tour packages..."
//         />
//         <button type="submit">Search</button>
//       </form>
//       <div>
//         {results.map((result, index) => (
//           <div key={index}>
//             <h2>{result.name}</h2>
//             <p>{result.description}</p>
//             {/* Add other result details here */}
//           </div>
//         ))}
//       </div>
//     </div>
//   );
// };

import React, { useState } from 'react';

const SearchBar = () => {
    const [query, setQuery] = useState('');
    const [results, setResults] = useState([]);

  const handleSearch = async (event) => {
    event.preventDefault();
    try {
      const response = await fetch(`/api/search?query=${encodeURIComponent(query)}`);
      const data = await response.json();
      setResults(data);
    } catch (error) {
      console.error('Error fetching search results:', error);
    }
  };

  return (
    <div className="max-w-lg mx-auto">
      <form onSubmit={handleSearch} className="flex items-center">
        <div className="relative w-full">
          <input
            type="search"
            className="w-full bg-white border border-gray-300 rounded-md py-2 px-4 leading-tight focus:outline-none focus:border-blue-500"
            placeholder="Search for tour packages..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
          />
          <button
            type="submit"
            className="absolute right-0 top-0 mt-2 mr-4"
          >
            <svg
              className="h-4 w-4 fill-current text-gray-600"
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
            >
              <path d="M10,2A8,8,0,1,0,18,10,8,8,0,0,0,10,2Zm0,14a6,6,0,1,1,6-6A6,6,0,0,1,10,16Z"/>
              <path d="M21,20.88l-4.29-4.3a1,1,0,0,0-1.42,1.42l4.3,4.29a1,1,0,0,0,1.41-1.42Z"/>
            </svg>
          </button>
        </div>
      </form>
    </div>
  );
};

export default SearchBar;
