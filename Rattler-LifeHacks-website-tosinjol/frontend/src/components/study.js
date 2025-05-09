import React, { useEffect, useState } from "react";
import axios from "axios";
import Navbar from "./navbar"; // Navbar component for navigation

const StudyRooms = () => {
    const [studyRooms, setStudyRooms] = useState([]);

    useEffect(() => {
        axios
            .get("http://localhost:8080/api/studyrooms/") // Corrected HTTP endpoint
            .then((response) => {
                if (response.data.success) {
                    setStudyRooms(response.data.data); // Update state with study room data
                } else {
                    console.error("Error fetching study rooms:", response.data.message);
                }
            })
            .catch((error) => console.error("Error fetching study rooms:", error));
    }, []);

    const formatReservationTime = (timestamp) => {
        if (!timestamp) return "No reservation time";
        // Handle Firestore Timestamp or ISO Date formats
        const date = timestamp.seconds
            ? new Date(timestamp.seconds * 1000)
            : new Date(timestamp);
        return date instanceof Date && !isNaN(date)
            ? date.toLocaleString() // Convert to readable date and time
            : "Invalid Date";
    };

    return (
        <div className="studyrooms">
            <div className="study-rooms-container">
                <Navbar />
                <h1>Study Rooms</h1>
                {studyRooms.length > 0 ? (
                    studyRooms.map((room, idx) => {
                        var id = Math.random() * 100 + idx;
                        return (
                            <div key={id} className="study-room-card">


                                <p>
                                    <strong>Availability:</strong>
                                    <span
                                        className={
                                            room.availablilityStatus === "Available"
                                                ? "available"
                                                : "occupied"
                                        }
                                    >
                                        {room.availablilityStatus}
                                    </span>
                                </p>
                                <p>
                                    <strong>Reservation Time:</strong> {formatReservationTime(room.reservationTime)}
                                </p>
                                <p>
                                    <strong>Reserved By:</strong> {room.reservedBy || "Not Reserved"}
                                </p>
                            </div>
                        );
                    })
                ) : (
                    <p>No study rooms available at the moment.</p>
                )}
            </div>
        </div>
    );
};

export default StudyRooms;
