import axios from "axios";
import { jwtDecode } from "jwt-decode";

const fetchUserDetails = async (userType) => {
    const authToken = localStorage.getItem("authToken");
    const user = jwtDecode(authToken);

    const API_URL = process.env.API_URL;

    const apiUrl = `${API_URL}/api/v1/${userType}/${user.appUserId}`;
      try {
        const headers = {
          Authorization: `Bearer ${authToken}`,
          "Content-Type": "application/json",
        };

        const response = await axios.get(apiUrl, { headers: headers });

        return response.data;
        
      } catch (error) {
        console.log(error);
        return [{id: 1 , name: "Error"}];
      }
};

export default fetchUserDetails;
