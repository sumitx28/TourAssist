import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import axios from "axios";
import API_URL from "../../config/config";

function AgentLogin() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const naviagte = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        `${API_URL}/api/v1/auth/authenticate`,
        {
          email,
          password,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 200) {
        const { access_token } = response.data;

        localStorage.setItem("authToken", access_token);

        naviagte("/agent-dashboard");
      } else {
        alert("Login failed");
      }
    } catch (error) {
      alert("Login failed");
      console.error("An error occurred:", error);
    }
  };

  return (
    <>
      <div className="flex min-h-screen flex-col sm:flex-row">
        <div className="w-full sm:w-3/5 bg-gradient-to-b from-red-500 to-blue-500 text-white flex items-center justify-center p-6 sm:p-12">
          <div className="text-center">
            <p className="text-xl sm:text-2xl font-bold mb-4 sm:mb-8">
              Tour Assist: Your one-stop travel solution
            </p>
          </div>
        </div>

        <div className="w-full sm:w-2/5 flex items-center justify-center p-6 sm:px-8 sm:py-12">
          <div className="sm:mx-auto sm:w-full sm:max-w-sm">
            <h2 className="mt-6 text-center text-xl sm:text-2xl font-bold leading-6 tracking-tight text-gray-900">
              Agent Login
            </h2>

            <form
              className="mt-6 space-y-4 sm:space-y-6"
              action="#"
              onSubmit={handleLogin}
            >
              <div>
                <label
                  htmlFor="email"
                  className="block text-sm font-medium leading-5 text-gray-900"
                >
                  Email address
                </label>
                <div className="mt-1">
                  <input
                    id="email"
                    name="email"
                    type="email"
                    onChange={(e) => setEmail(e.target.value)}
                    autoComplete="email"
                    required
                    className="block w-full rounded-md border py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-5"
                  />
                </div>
              </div>

              <div>
                <div className="flex items-center justify-between">
                  <label
                    htmlFor="password"
                    className="block text-sm font-medium leading-5 text-gray-900"
                  >
                    Password
                  </label>
                  <div className="text-sm">
                    <Link
                      to="/forgot-password"
                      className="font-semibold text-indigo-600 hover:text-indigo-500"
                    >
                      Forgot password?
                    </Link>
                  </div>
                </div>
                <div className="mt-1">
                  <input
                    id="password"
                    name="password"
                    type="password"
                    autoComplete="current-password"
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    className="block w-full rounded-md border py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-5"
                  />
                </div>
              </div>

              <div>
                <button
                  type="submit"
                  className="flex w-full justify-center rounded-md bg-indigo-600 px-4 py-2 text-sm font-semibold leading-5 text-white shadow-sm hover:bg-indigo-500 focus:ring-2 focus:ring-inset focus:ring-indigo-600"
                >
                  Login
                </button>
              </div>
            </form>

            <p className="mt-4 text-center text-sm text-gray-500">
              Are you a Customer?{" "}
              <Link
                to="/login"
                className="font-semibold text-indigo-600 hover:text-indigo-500"
              >
                Login here
              </Link>
            </p>
          </div>
        </div>
      </div>
    </>
  );
}

export default AgentLogin;
