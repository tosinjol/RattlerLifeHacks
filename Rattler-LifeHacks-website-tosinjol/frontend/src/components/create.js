import React, { useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

const Create = () => {
    const [username, setUsername] = useState(""); // State for username
    const [email, setEmail] = useState(""); // State for email
    const [password, setPassword] = useState(""); // State for password
    const [errorMessage, setErrorMessage] = useState(""); // State for error messages
    const [successMessage, setSuccessMessage] = useState(""); // State for success messages
    const navigate = useNavigate(); // For redirecting to login after successful account creation

    const createUserHandler = async (e) => {
        e.preventDefault(); // Prevent default form submission
        setErrorMessage(""); // Clear previous error messages
        setSuccessMessage(""); // Clear previous success messages

        // Check if all fields are filled
        if (!username || !email || !password) {
            setErrorMessage("All fields are required.");
            return;
        }

        // Prepare the user data
        const userData = {
            userId: username,
            email,
            password,
            profilePictureUrl: "https://via.placeholder.com/150", // Default profile picture
        };

        try {
            const response = await axios.post("http://localhost:8080/api/user/create", userData);

            if (response.data.success) {
                setSuccessMessage("Account created successfully! Redirecting to login...");
                setTimeout(() => {
                    navigate("/"); // Redirect to login page after success
                }, 2000); // Redirect after 2 seconds
            } else {
                setErrorMessage(response.data.message || "Failed to create account. Please try again.");
            }
        } catch (error) {
            console.error("Error creating account:", error);
            setErrorMessage("Error creating account. Please try again.");
        }
    };

    return (
        <div className="create-container">
            <h1>Create New Account</h1>

            {/* Success or error messages */}
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            {successMessage && <p className="success-message">{successMessage}</p>}

            {/* Form to create a new user */}
            <form onSubmit={createUserHandler}>
                <div>
                    <label htmlFor="username">Username</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        placeholder="Enter your username"
                    />
                </div>
                <div>
                    <label htmlFor="email">Email</label>
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="Enter your email"
                    />
                </div>
                <div>
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="Enter your password"
                    />
                </div>
                <button type="submit">Create Account</button>
            </form>
        </div>
    );
};

export default Create;