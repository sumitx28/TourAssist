import React from 'react';
import { Link } from 'react-router-dom';

const AgentDashboard = () => {
  return (
    <div className="min-h-screen bg-gray-100">
      <div className="container mx-auto px-4">
        <h1 className="text-3xl font-semibold text-gray-800 my-6">Dashboard</h1>
        <div className="bg-white shadow-md rounded-lg overflow-hidden">
          <nav className="flex flex-col sm:flex-row">
            <Link className="text-gray-600 hover:bg-gray-200 hover:text-gray-800 px-6 py-4 transition duration-300 ease-in-out" to="/search">Search Packages</Link>
            <Link className="text-gray-600 hover:bg-gray-200 hover:text-gray-800 px-6 py-4 transition duration-300 ease-in-out" to="/profile">My Profile</Link>
            {/* Add other relevant links */}
          </nav>
        </div>
        {/* Dashboard content */}
        <div className="mt-8">
          {/* Add widgets or content here */}
          <div className="bg-white p-6 rounded-lg shadow-lg">
            <h2 className="text-2xl font-semibold mb-4">Welcome back, [User Name]!</h2>
            {/* Add additional content and widgets */}
          </div>
        </div>
      </div>
    </div>
  );
};

export default AgentDashboard;
