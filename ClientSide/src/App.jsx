import AgentLogin from "./components/AgentLogin";
import Dashboard from "./components/dashboard/Dashboard";
import AgentDash from "./components/dashboard/Dashboard";
import ForgotPassword from "./components/ForgotPassword";
import Home from "./components/Home";
import Login from "./components/Login";
import ResetPassword from "./components/ResetPassword";
import SignUp from "./components/Signup";
import AuthGuard from "./guards/AuthGuard";
import CreatePackage from "./components/agent/CreatePackage";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import PackageDetail from "./components/travel-package/PackageDetail";
import Search from "./components/search/Search";
import PastBookings from "./components/agentdashboard/PastBookings";
import UpcomingBookings from "./components/agentdashboard/UpcomingBookings";
import CustomerDetails from "./components/agentdashboard/CustomerDetails";
import AmountEarned from "./components/agentdashboard/AmountEarned";
import UserProfile from "./components/userprofile/UserProfile";

function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route exact path="/" element={<Home />}></Route>
          <Route exact path="/login" element={<Login />}></Route>
          <Route exact path="/signup" element={<SignUp />}></Route>
          <Route path="/agent-login" element={<AgentLogin />} />
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
          <Route
            path="/search"
            element={
              <AuthGuard>
                <AgentDash title="Search" Component={<Search />} />
              </AuthGuard>
            }
          />
          <Route
            path="/userprofile"
            element={
              <AuthGuard>
                <AgentDash title="User Profile" Component={<UserProfile />} />
              </AuthGuard>
            }
          />
          <Route
            path="/dashboard"
            element={
              <AuthGuard>
                <Dashboard />
              </AuthGuard>
            }
          />
          <Route
            path="/pastbookings"
            element={
              <AuthGuard>
                <AgentDash title="Past Bookings" Component={<PastBookings />} />
              </AuthGuard>
            }
          />
          <Route
            path="/upcomingbookings"
            element={
              <AuthGuard>
                <AgentDash
                  title="Upcoming Bookings"
                  Component={<UpcomingBookings />}
                />
              </AuthGuard>
            }
          />
          <Route
            path="/customerdetails"
            element={
              <AuthGuard>
                <AgentDash
                  title="Customer Details"
                  Component={<CustomerDetails />}
                />
              </AuthGuard>
            }
          />
          <Route
            path="/amountearned"
            element={
              <AuthGuard>
                <AgentDash title="Amount Earned" Component={<AmountEarned />} />
              </AuthGuard>
            }
          />
          <Route
            path="/package/:id"
            element={
              <AuthGuard>
                <AgentDash title="Tour Assist" Component={<PackageDetail />} />
              </AuthGuard>
            }
          />
        </Routes>
      </div>
    </Router>
  );
}
export default App;
