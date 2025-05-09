import React, { useState, useEffect, useRef } from "react";
import { Link } from "react-router-dom";

function Navbar({ onToggle }) {
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const [clicked, setClicked] = useState(false);
    const navbarRef = useRef(null);
    const buttonRef = useRef(null);
    const dropdownRef = useRef(null);

    const handleButtonClick = () => {
        setDropdownOpen((prev) => !prev);
        setClicked((prev) => !prev);
        if (onToggle) {
            onToggle(!dropdownOpen);
        }
    };

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (
                navbarRef.current &&
                !navbarRef.current.contains(event.target) &&
                (!dropdownRef.current || !dropdownRef.current.contains(event.target))
            ) {
                setDropdownOpen(false);
                setClicked(false);
            }
        };

        document.addEventListener("mousedown", handleClickOutside);

        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, []);

    return (
        <>
            {/* The Navbar */}
            <nav
                ref={navbarRef}
                className="dropdown-nav"
                style={{
                    position: "fixed",
                    top: 0,
                    left: 0,
                    width: "100%",
                    backgroundColor: "#1b5633",
                    zIndex: 1000,
                    display: "flex",
                    justifyContent: "center",
                    padding: "5px 0",
                    boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
                    transition: "top 0.3s ease", // Added smooth transition
                }}
            >
                <button
                    ref={buttonRef}
                    className="dropdown-toggle"
                    onClick={handleButtonClick}
                    style={{
                        backgroundColor: "#1b5633",
                        border: "1px solid #1b5633",
                        color: "white",
                        fontSize: "16px",
                        padding: "5px 10px",
                        borderRadius: "5px",
                        cursor: "pointer",
                    }}
                >
                    Menu
                </button>
                {dropdownOpen && (
                    <ul
                        ref={dropdownRef}
                        className="dropdown-menu"
                        onMouseEnter={() => setDropdownOpen(true)}
                        onMouseLeave={() => {
                            if (!clicked) setDropdownOpen(false);
                        }}
                        style={{
                            position: "absolute",
                            top: "100%",
                            left: "50%",
                            transform: "translateX(-50%)",
                            backgroundColor: "#1b5633",
                            listStyleType: "none",
                            padding: "10px 0",
                            margin: 0,
                            borderRadius: "5px",
                            boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
                            textAlign: "center",
                        }}
                    >
                        <li style={{ marginBottom: "10px" }}>
                            <Link to="/home" style={{ color: "white", textDecoration: "none" }}>
                                Home
                            </Link>
                        </li>
                        <li style={{ marginBottom: "10px" }}>
                            <Link to="/profile" style={{ color: "white", textDecoration: "none" }}>
                                Profile
                            </Link>
                        </li>
                        <li style={{ marginBottom: "10px" }}>
                            <Link to="/wait-times" style={{ color: "white", textDecoration: "none" }}>
                                Wait Times
                            </Link>
                        </li>
                        <li style={{ marginBottom: "10px" }}>
                            <Link to="/study-rooms" style={{ color: "white", textDecoration: "none" }}>
                                Study Rooms
                            </Link>
                        </li>
                        <li style={{ marginBottom: "10px" }}>
                            <Link to="/events" style={{ color: "white", textDecoration: "none" }}>
                                Events
                            </Link>
                        </li>
                        <li>
                            <Link
                                to="/logout"
                                style={{
                                    backgroundColor: "red",
                                    color: "white",
                                    border: "none",
                                    padding: "5px 10px",
                                    borderRadius: "5px",
                                    cursor: "pointer",
                                    textDecoration: "none",
                                    display: "inline-block",
                                }}
                            >
                                Logout
                            </Link>
                        </li>
                    </ul>
                )}
            </nav>

            {/* Page content that moves down when the navbar is active */}
            <div
                style={{
                    marginTop: dropdownOpen ? "300px" : "0px", // This moves the content down when dropdown is open
                    transition: "margin-top 0.2s ease", // Smooth transition for content
                }}
            >
                {/* Place the main content here */}
            </div>
        </>
    );
}

export default Navbar;