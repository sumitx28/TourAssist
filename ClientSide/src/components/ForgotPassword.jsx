import { useState } from "react";
import axios from "axios";
import API_URL from "../../config/config";

const ForgotPassword = () => {
  const [email, setEmail] = useState("");

  const handleReset = async (e) => {
    e.preventDefault();

    if (!email) {
      alert("Please enter a valid email");
      return;
    }

    try {
      const response = await axios.post(
        `${API_URL}/api/v1/auth/request`,
        {
          email,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 200) {
        alert("Reset password email sent successfully!");
      }
    } catch (err) {
      console.log(err);
      alert("Please try again!");
    }
  };

  return (
    <>
      <div className="flex min-h-screen flex-col sm:flex-row">
        <div className="w-full sm:w-3/5 bg-gradient-to-b from-red-500 to-blue-500 text-white flex items-center justify-center p-6 sm:p-12">
          <div className="text-center">
            <p className="text-xl sm:text-2xl font-bold mb-4 sm:mb-8">
              Forgot Password?
            </p>
          </div>
        </div>

        <div className="w-full sm:w-2/5 flex items-center justify-center p-6 sm:px-8 sm:py-12">
          <div className="sm:mx-auto sm:w-full sm:max-w-sm">
            <div>
              <label
                htmlFor="email"
                className="block text-sm font-medium leading-5 text-gray-900"
              >
                Email address
              </label>
              <div className="mt-2">
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

            <div className="mt-4">
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-indigo-600 px-4 py-2 text-sm font-semibold leading-5 text-white shadow-sm hover:bg-indigo-500 focus:ring-2 focus:ring-inset focus:ring-indigo-600"
                onClick={handleReset}
              >
                Reset
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default ForgotPassword;
