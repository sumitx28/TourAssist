import Home from "./components/Home";
import Login from "./components/Login";
import Dashboard from "./components/Dashboard";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AuthGuard from "./guards/AuthGuard";
import SignUp from "./components/Signup";

function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route exact path="/" element={<Home />}></Route>
          <Route exact path="/login" element={<Login />}></Route>
          <Route exact path="/signup" element={<SignUp />}></Route>
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
