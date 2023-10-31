import axios from "axios";
import API_URL from "../../config/config";

const fetchData = async (path) => {
    const authToken = localStorage.getItem("authToken");
    const apiUrl = `${API_URL}${path}`;
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

export default fetchData;
