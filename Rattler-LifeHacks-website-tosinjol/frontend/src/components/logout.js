import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Logout({ setUser }) {
    const navigate = useNavigate();

    useEffect(() => {
        // Clear session storage or any other user data
        sessionStorage.clear();

        // Set user state to null (log out)
        setUser(null);

        // Redirect to login page after 3 seconds
        const timer = setTimeout(() => {
            navigate("/"); // Redirect to the login page
        }, 3000);

        // Cleanup the timer if the component unmounts
        return () => clearTimeout(timer);
    }, [navigate, setUser]);

    return (
        <div style={{ textAlign: "center", marginTop: "50px" }}>
            <h1>You have been logged out</h1>
            <p>Redirecting to the login page...</p>
        </div>
    );
}

export default Logout; // Add this line
