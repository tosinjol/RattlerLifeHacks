import React, { useEffect, useState } from "react";
import axios from "axios";
import { GoogleMap, useJsApiLoader } from "@react-google-maps/api";
import "./WaitTimes.css";

const containerStyle = {
    width: "100%",
    height: "200px",
};

// Hardcoded mapping of location IDs to coordinates
const locationCoordinates = {
    "Lee Hall": { latitude: 30.4270879, longitude: -84.2851049 },
    "Library": { latitude: 30.421120, longitude: -84.284120 },
    "Student Union": { latitude: 30.424200, longitude: -84.285300 },
    "Financial Aid Office": { latitude: 30.423800, longitude: -84.284500 },
    "Admission's Office": { latitude: 30.423800, longitude: -84.284500 },
    "Registrar's Office": { latitude: 30.423800, longitude: -84.284500 },
    "Department of Computer & Information Sciences": { latitude: 30.422500, longitude: -84.283200 },
    "Counseling Services": { latitude: 30.423800, longitude: -84.284500 },
    "Athletic Academic Advising": { latitude: 30.425500, longitude: -84.286700 },
    "Athletic Financial Aid Office": { latitude: 30.425500, longitude: -84.286700 },
    "Chick-fil-A": { latitude: 30.424200, longitude: -84.285300 },
    "Wingstop": { latitude: 30.424200, longitude: -84.285300 },
    "Pizza Express": { latitude: 30.424200, longitude: -84.285300 },
    "Tropical Smoothie": { latitude: 30.424200, longitude: -84.285300 },
    "Rattler's Nest": { latitude: 30.424200, longitude: -84.285300 },
    "President's Dining Room": { latitude: 30.424200, longitude: -84.285000 },
};

const WaitTimes = () => {
    const [waitTimes, setWaitTimes] = useState([]);
    const [error, setError] = useState("");
    const [flippedCards, setFlippedCards] = useState({}); // Track flipped state of each card
    const { isLoaded } = useJsApiLoader({
        googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY, // Ensure your .env file has this key.
    });

    useEffect(() => {
        axios
            .get("http://localhost:8080/api/waittimes/") // Correct endpoint
            .then((response) => {
                if (response.data.success) {
                    setWaitTimes(response.data.data || []); // Update state with fetched data
                } else {
                    setError(response.data.message || "Failed to fetch wait times");
                }
            })
            .catch((err) => {
                console.error("Error fetching wait times:", err);
                setError("Error fetching wait times. Please try again later.");
            });
    }, []);

    const toggleCard = (key) => {
        setFlippedCards((prev) => ({
            ...prev,
            [key]: !prev[key], // Toggle the state of the clicked card
        }));
    };

    const getCoordinatesForLocation = (locationId) => {
        return locationCoordinates[locationId] || { latitude: 0, longitude: 0 }; // Default to (0,0) if not found
    };

    return (
        <div className="waittimes">
            <h1 style={{ textAlign: "center", color: "#1b5633" }}>Wait Times</h1>
            {error && <p className="error-message" style={{ color: "red", textAlign: "center" }}>{error}</p>}
            {waitTimes.length > 0 ? (
                <div className="wait-times-container">
                    {waitTimes.map((wt, index) => (
                        <div
                            key={index}
                            className={`wait-time-card ${flippedCards[index] ? "flipped" : ""}`}
                            onClick={() => toggleCard(index)}
                        >
                            {/* Front of the card */}
                            <div className="card-front">
                                <h3 style={{ color: "#1b5633" }}>{wt.locationId}</h3>
                                <p><strong>Type:</strong> {wt.locationType}</p>
                                <p><strong>Wait Time:</strong> {wt.currentWaitTime} mins</p>
                            </div>

                            {/* Back of the card (Google Map) */}
                            <div className="card-back">
                                {isLoaded ? (
                                    <GoogleMap
                                        mapContainerStyle={{ width: "100%", height: "100%" }}
                                        center={{
                                            lat: getCoordinatesForLocation(wt.locationId).latitude,
                                            lng: getCoordinatesForLocation(wt.locationId).longitude,
                                        }}
                                        zoom={15}
                                    ></GoogleMap>
                                ) : (
                                    <p>Loading map...</p>
                                )}
                            </div>
                        </div>
                    ))}
                </div>
            ) : (
                <p style={{ textAlign: "center" }}>No wait times available.</p>
            )}
        </div>
    );
};

export default WaitTimes;
