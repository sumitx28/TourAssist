import { jwtDecode } from "jwt-decode";
import { Navigate, useNavigate } from "react-router-dom";

function isUserAuthenticated() {
  const token = localStorage.getItem("authToken");
  return !!token;
}

export default function ProtectedRoute({ children, ROLE }) {
  const token = localStorage.getItem("authToken");
  const user = jwtDecode(token);
  const navigate = useNavigate();

  if (isUserAuthenticated() && (ROLE == "OPEN" || user.role == ROLE)) {
    return children;
  } else {
    return <Navigate to="/login" />;
  }
}
