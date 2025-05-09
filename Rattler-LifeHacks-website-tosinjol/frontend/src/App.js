import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { LoadScript } from "@react-google-maps/api"; // Import LoadScript for Google Maps API

import Login from "./components/login"; // Correct path for login.js
import Create from "./components/create"; // Correct path for create.js
import Profile from "./components/profile"; // Correct path for profile.js
import WaitTimes from "./components/wait"; // Correct path for wait.js
import StudyRooms from "./components/study"; // Correct path for study.js
import Events from "./components/events"; // Correct path for events.js
import Navbar from "./components/navbar"; // Correct path for navbar.js
import Logout from "./components/logout";
import Homepage from "./components/homepage"; // Correct path for homepage.js

const App = () => {
    const [user, setUser] = useState(null); // State to track logged-in user

    return (
        <LoadScript googleMapsApiKey={process.env.REACT_APP_GOOGLE_MAPS_API_KEY}>
            <Router>
                {user && <Navbar />} {/* Show Navbar only when logged in */}
                <Routes>
                    {/* Public Routes */}
                    <Route
                        path="/"
                        element={user ? <Navigate to="/home" /> : <Login setUser={setUser} />}
                    />
                    <Route path="/create" element={<Create />} />

                    {/* Protected Routes */}
                    <Route
                        path="/home"
                        element={user ? <Homepage /> : <Navigate to="/" />}
                    />
                    <Route
                        path="/profile"
                        element={user ? <Profile user={user} setUser={setUser} /> : <Navigate to="/" />}
                    />
                    <Route
                        path="/wait-times"
                        element={user ? <WaitTimes /> : <Navigate to="/" />}
                    />
                    <Route
                        path="/study-rooms"
                        element={user ? <StudyRooms /> : <Navigate to="/" />}
                    />
                    <Route
                        path="/events"
                        element={user ? <Events /> : <Navigate to="/" />}
                    />
                    <Route
                        path="/logout"
                        element={user ? <Logout setUser={setUser} /> : <Navigate to="/" />}
                    />
                </Routes>
            </Router>
        </LoadScript>
    );
};

export default App;
