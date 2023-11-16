import React from 'react';

export default function UserProfile() {
  // Example user data, replace the placeholder data with actual data
  const userData = {
    firstName: 'Jane',
    lastName: 'Doe',
    email: 'jane.doe@example.com',
    mobileNumber: '123-456-7890',
    dateOfBirth: '1990-01-01', // Format the date accordingly
    country: 'Wonderland',
    avatar: 'path_to_avatar_image', // Replace with path to user's avatar image
  };

  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap justify-center">
        {/* Avatar & Name */}
        <div className="w-full sm:w-1/2 md:w-1/4 p-2">
          <div className="bg-white shadow rounded-lg p-4 text-center">
            <img
              className="w-32 h-32 rounded-full mx-auto"
              src={userData.avatar}
              alt={`${userData.firstName} ${userData.lastName}`}
            />
            <h2 className="text-xl font-semibold mt-2">{`${userData.firstName} ${userData.lastName}`}</h2>
          </div>
        </div>

        {/* User Details */}
        <div className="w-full sm:w-1/2 md:w-3/4 p-2">
          <div className="bg-white shadow rounded-lg p-4">
            <h3 className="text-lg font-semibold mb-4">Personal Information</h3>
            <div className="space-y-3">
              <p className="text-gray-600">{`Email: ${userData.email}`}</p>
              <p className="text-gray-600">{`Mobile Number: ${userData.mobileNumber}`}</p>
              <p className="text-gray-600">{`Date of Birth: ${userData.dateOfBirth}`}</p>
              <p className="text-gray-600">{`Country: ${userData.country}`}</p>
              <button className="mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-300">
                Edit Profile
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
