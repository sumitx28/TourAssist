import React, { useState } from 'react';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';

export default function UserProfile() {
  const [editFields, setEditFields] = useState({
    email: '****@dal.ca',
    mobileNumber: '***-***-****'
  });

  const [editMode, setEditMode] = useState(false);

  const handleInputChange = (e) => {
    setEditFields({ ...editFields, [e.target.name]: e.target.value });
  };

  const updateUserProfile = async (fieldToUpdate) => {
    const authToken = localStorage.getItem("authToken");
    const user = jwtDecode(authToken);
    const apiEndpoint = 'http://localhost:8080/api/v1/user-profile/update-profile';

    const headers = {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${authToken}`
    };

    const data = {
      appUserId: user.appUserId,
      [fieldToUpdate]: editFields[fieldToUpdate]
    };

    try {
      await axios.post(apiEndpoint, data, { headers });
      alert('Profile updated successfully!');
      setEditMode(false);
    } catch (error) {
      console.error('Error updating profile:', error);
      alert('Failed to update profile.');
    }
  };

  const handleSaveEmail = () => {
    updateUserProfile('email');
  };

  const handleSaveMobileNumber = () => {
    updateUserProfile('mobile');
  };

  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap justify-center">
        <div className="w-full sm:w-1/2 md:w-3/4 p-2">
          <div className="bg-white shadow rounded-lg p-4">
            <h3 className="text-lg font-semibold mb-4">Personal Information</h3>
            <div className="space-y-3">
              {editMode ? (
                <>
                  <input
                    type="text"
                    name="email"
                    value={editFields.email}
                    onChange={handleInputChange}
                    className="border border-gray-300 rounded-md py-2 px-4 w-full"
                  />
                  <input
                    type="text"
                    name="mobileNumber"
                    value={editFields.mobileNumber}
                    onChange={handleInputChange}
                    className="border border-gray-300 rounded-md py-2 px-4 w-full"
                  />
                  <button onClick={handleSaveEmail} className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">
                    Save Email
                  </button>
                  <button onClick={handleSaveMobileNumber} className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">
                    Save Mobile Number
                  </button>
                </>
              ) : (
                <>
                  <p className="text-gray-600">{`Email: ${editFields.email}`}</p>
                  <p className="text-gray-600">{`Mobile Number: ${editFields.mobileNumber}`}</p>
                  <button onClick={() => setEditMode(true)} className="mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">
                    Edit Profile
                  </button>
                </>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
