import Navbar from "./commons/NavBar";
import { jwtDecode } from "jwt-decode";

const AgentDashboard = () => {
  return (
    <>
      <Navbar />
      <h1>Hello Agent</h1>
    </>
  );
};

export default AgentDashboard;
