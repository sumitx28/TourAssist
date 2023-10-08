import { Navigate } from "react-router-dom";

function isUserAuthenticated() {
  const token = localStorage.getItem("authToken");
  return !!token;
}

export default function ProtectedRoute({ children }) {
  if (isUserAuthenticated()) {
    return children;
  } else {
    return <Navigate to="/login" />;
  }
}
