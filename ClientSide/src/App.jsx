import Home from "./components/Home";
import Login from "./components/Login";
import Dashboard from "./components/Dashboard";
import AuthGuard from "./guards/AuthGuard";
import AgentLogin from "./components/AgentLogin";
import AgentDashboard from "./components/AgentDashboard";
import ForgotPassword from "./components/ForgotPassword";
import ResetPassword from "./components/ResetPassword";

import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route exact path="/" element={<Home />}></Route>
          <Route exact path="/login" element={<Login />}></Route>
          <Route path="/agent-login" element={<AgentLogin />} />
          <Route path="/agent-dashboard" element={<AgentDashboard />} />
          <Route path="/forgot-password" element={<ForgotPassword />} />
          <Route path="/reset-password/:token" element={<ResetPassword />} />
          <Route
            path="/dashboard"
            element={
              <AuthGuard>
                <Dashboard />
              </AuthGuard>
            }
          />
        </Routes>
      </div>
    </Router>
  );
}
export default App;
