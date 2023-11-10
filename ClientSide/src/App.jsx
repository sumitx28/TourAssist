import AgentDashboard from "./components/agent/AgentDashboard";
import AgentLogin from "./components/AgentLogin";
import Dashboard from "./components/Dashboard";
import AgentDash from "./components/dashboard/Dashboard";
import ForgotPassword from "./components/ForgotPassword";
import Home from "./components/Home";
import Login from "./components/Login";
import ResetPassword from "./components/ResetPassword";
import SignUp from "./components/Signup";
import AuthGuard from "./guards/AuthGuard";
import CreatePackage from "./components/agent/CreatePackage";
import UserProfile from "./components/UserProfile"

import { Route, BrowserRouter as Router, Routes } from "react-router-dom";

function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route exact path="/" element={<Home />}></Route>
          <Route exact path="/login" element={<Login />}></Route>
          <Route exact path="/signup" element={<SignUp />}></Route>
          <Route path="/agent-login" element={<AgentLogin />} />
          <Route
            path="/agent-dashboard"
            element={
              <AuthGuard>
                <AgentDashboard />
              </AuthGuard>
            }
          />
          <Route path="/forgot-password" element={<ForgotPassword />} />
          <Route path="/reset-password/:token" element={<ResetPassword />} />
          <Route
            path="/create-package"
            element={
              <AuthGuard>
                <AgentDash
                  title="New Travel Package"
                  Component={<CreatePackage />}
                />
              </AuthGuard>
            }
          />
{/*           <Route */}
{/*             path="/user-profile" */}
{/*             element={ */}
{/*                 <AuthGuard> */}
{/*                     <UserProfile /> */}
{/*                 </AuthGuard> */}
{/*             } */}
{/*           /> */}
          <Route path="/user-profile" element={<UserProfile />} />
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
