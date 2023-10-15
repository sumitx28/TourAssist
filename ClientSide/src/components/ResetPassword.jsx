import { useState } from "react";
import { Link, useParams } from "react-router-dom";
import axios from "axios";
import API_URL from "../../config/config";

function ResetPassword() {
  const { token } = useParams();
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [currentStatus, setCurrentStatus] = useState({
    status: "pass_reset",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        `${API_URL}/api/v1/auth/reset`,
        {
          token,
          password,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 200) {
        setCurrentStatus({
          status: "success",
        });
      }
    } catch (err) {
      setCurrentStatus({
        status: "failed",
      });
    }
  };

  const successHeader = () => {
    return (
      <div
        className="bg-green-100 border-t border-b border-green-500 text-green-700 mx-auto mt-10 w-full max-w-sm text-center"
        role="alert"
      >
        <p className="font-bold">Password Reset Successful!</p>
        <p className="text-sm">
          <Link to="/login">Login Here</Link>
        </p>
      </div>
    );
  };

  const failedHeader = () => {
    return (
      <div
        className="bg-red-100 border border-red-400 text-red-700 mx-auto mt-10 w-full max-w-sm rounded relative flex items-center justify-center"
        role="alert"
      >
        <strong className="font-bold">Reset Failed! </strong>
        <span className="block sm:inline">Please try again.</span>
      </div>
    );
  };

  const Banner = () => {
    return (
      <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <img
            className="mx-auto h-10 w-auto"
            src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
            alt="Your Company"
          />
          <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
            Reset Password
          </h2>
        </div>

        {currentStatus.status === "success" ? successHeader() : failedHeader()}
      </div>
    );
  };

  const resetForm = () => {
    return (
      <div className="flex min-h-screen">
        <div className="w-2/5 bg-gradient-to-b from-red-500 to-blue-500 text-white flex flex-col justify-center items-center">
          <div className="text-center">
            <p className="text-xl lg:text-2xl font-bold mb-4 lg:mb-8">
              Tour Assist: Your one-stop travel solution
            </p>
          </div>
        </div>

        <div className="w-3/5 flex items-center justify-center">
          <div className="sm:mx-auto sm:w-full sm:max-w-sm">
            <h2 className="mt-6 text-center text-xl lg:text-2xl font-bold leading-6 tracking-tight text-gray-900">
              Reset Password
            </h2>

            <form
              className="mt-6 space-y-4 sm:space-y-6"
              action="#"
              method="POST"
            >
              <div>
                <div className="flex items-center justify-between">
                  <label
                    htmlFor="new_password"
                    className="block text-sm font-medium leading-5 text-gray-900"
                  >
                    Enter New Password
                  </label>
                </div>
                <div className="mt-2">
                  <input
                    name="new_password"
                    type="password"
                    onChange={(e) => setPassword(e.target.value)}
                    autoComplete="current-password"
                    required
                    className="block w-full rounded-md border py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-5"
                  />
                </div>
              </div>

              <div>
                <div className="flex items-center justify-between">
                  <label
                    htmlFor="confirm_new_password"
                    className="block text-sm font-medium leading-5 text-gray-900"
                  >
                    Confirm Password
                  </label>
                </div>
                <div className="mt-2">
                  <input
                    name="confirm_new_password"
                    type="password"
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    autoComplete="current-password"
                    required
                    className="block w-full rounded-md border py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-5"
                  />
                </div>
              </div>

              <div>
                <button
                  type="button"
                  className="flex w-full justify-center rounded-md bg-indigo-600 px-4 py-2 text-sm font-semibold leading-5 text-white shadow-sm hover:bg-indigo-500 focus:ring-2 focus:ring-inset focus:ring-indigo-600"
                  onClick={handleSubmit}
                >
                  Submit
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    );
  };

  return (
    <div>
      {currentStatus.status === "pass_reset" ? resetForm() : <Banner />}
    </div>
  );
}

export default ResetPassword;
