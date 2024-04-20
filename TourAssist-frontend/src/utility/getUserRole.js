import { jwtDecode} from "jwt-decode";

export default () => { 
    const token = localStorage.getItem("authToken");
    const userDetails = jwtDecode(token);
    return userDetails.role;
};