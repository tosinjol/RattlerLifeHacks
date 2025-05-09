import React, { useEffect, useState } from "react";
import axios from "axios";
import Navbar from "./navbar"; // Optional: Include Navbar for navigation.

const Events = () => {
    const [events, setEvents] = useState([]);
    const [newEvent, setNewEvent] = useState({ eventId: "", title: "", date: "", location: "" });
    const [updateEvent, setUpdateEvent] = useState({ eventId: "", title: "", date: "", location: "" });

    // Fetch events from the backend
    useEffect(() => {
        axios
            .get("/api/events/") // Ensure the correct endpoint matches the backend
            .then((response) => {
                if (response.data.success) {
                    const formattedEvents = response.data.data.map((event) => ({
                        ...event,
                        date: event.date.seconds
                            ? new Date(event.date.seconds * 1000) // Convert Firestore timestamp to JS Date
                            : new Date(event.date),
                    }));
                    setEvents(formattedEvents || []);
                } else {
                    console.error("Error fetching events:", response.data.message);
                }
            })
            .catch((error) => console.error("Error fetching events:", error));
    }, []);

    // Create a new event
    const createEventHandler = async () => {
        const { eventId, title, date, location } = newEvent;

        if (!eventId.trim() || !title.trim() || !date.trim() || !location.trim()) {
            alert("All fields (Event ID, Title, Date, and Location) are required to create an event.");
            return;
        }

        try {
            const formattedEvent = {
                ...newEvent,
                date: new Date(date).toISOString(),
            };

            const response = await axios.post("/api/events/create", formattedEvent);
            if (response.data.success) {
                alert("Event created successfully!");
                setEvents([...events, { ...response.data.data, date: new Date(date) }]);
                setNewEvent({ eventId: "", title: "", date: "", location: "" }); // Reset the input fields
            } else {
                alert(response.data.message || "Failed to create event.");
            }
        } catch (error) {
            console.error("Error creating event:", error);
            alert("Error creating event. Please try again.");
        }
    };

    // Update an event
    const updateEventHandler = async () => {
        const { eventId, title, date, location } = updateEvent;

        if (!eventId.trim() || !title.trim() || !date.trim() || !location.trim()) {
            alert("All fields (Event ID, Title, Date, and Location) are required to update an event.");
            return;
        }

        try {
            const formattedDate = new Date(date).toISOString();

            const response = await axios.put(`/api/events/update/${eventId}`, null, {
                params: { title, date: formattedDate },
            });

            if (response.data.success) {
                alert("Event updated successfully!");

                setEvents((prevEvents) =>
                    prevEvents.map((event) =>
                        event.eventId === eventId
                            ? { ...event, title, date: new Date(date), location }
                            : event
                    )
                );

                setUpdateEvent({ eventId: "", title: "", date: "", location: "" });
            } else {
                alert(response.data.message || "Failed to update event.");
            }
        } catch (error) {
            console.error("Error updating event:", error);
            alert("Error updating event. Please try again.");
        }
    };

    // Delete an event
    const deleteEventHandler = async (eventId) => {
        try {
            const response = await axios.delete(`/api/events/delete/${eventId}`);
            if (response.data.success) {
                alert("Event deleted successfully!");
                setEvents(events.filter((event) => event.eventId !== eventId));
            } else {
                alert(response.data.message || "Failed to delete event.");
            }
        } catch (error) {
            console.error("Error deleting event:", error);
            alert("Error deleting event. Please try again.");
        }
    };

    return (
        <div className="eventsbody">
            <div className="events-container">
                <Navbar /> {/* Optional: Include a navbar */}
                <h1 className="banner">Upcoming Events</h1>

                <div className="events-list">
                    {events.length > 0 ? (
                        <ul>
                            {events.map((event) => (
                                <li key={event.eventId}>
                                    <strong>{event.title}</strong> - {" "}
                                    {event.date instanceof Date
                                        ? event.date.toLocaleString()
                                        : "Invalid Date"} {" "}
                                    ({event.location}) {" "}
                                    <button
                                        onClick={() => setUpdateEvent(event)}
                                        style={{ marginLeft: "10px" }}
                                    >
                                        Update
                                    </button>
                                    <button
                                        onClick={() => deleteEventHandler(event.eventId)}
                                        style={{ marginLeft: "10px", color: "red" }}
                                    >
                                        Delete
                                    </button>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No upcoming events available.</p>
                    )}

                    {/* Event Creation Form */}
                    <div className="event-creation">
                        <h2>Create New Event</h2>
                        <input
                            type="text"
                            placeholder="Event ID"
                            value={newEvent.eventId}
                            onChange={(e) => setNewEvent({ ...newEvent, eventId: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Event Title"
                            value={newEvent.title}
                            onChange={(e) => setNewEvent({ ...newEvent, title: e.target.value })}
                        />
                        <input
                            type="datetime-local"
                            value={newEvent.date}
                            onChange={(e) => setNewEvent({ ...newEvent, date: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Location"
                            value={newEvent.location}
                            onChange={(e) => setNewEvent({ ...newEvent, location: e.target.value })}
                        />
                        <button onClick={createEventHandler}>Create Event</button>
                    </div>

                    {/* Event Update Form */}
                    {updateEvent.eventId && (
                        <div className="event-update">
                            <h2>Update Event</h2>
                            <input
                                type="text"
                                placeholder="Event ID"
                                value={updateEvent.eventId}
                                readOnly
                            />
                            <input
                                type="text"
                                placeholder="Event Title"
                                value={updateEvent.title}
                                onChange={(e) => setUpdateEvent({ ...updateEvent, title: e.target.value })}
                            />
                            <input
                                type="datetime-local"
                                value={updateEvent.date}
                                onChange={(e) => setUpdateEvent({ ...updateEvent, date: e.target.value })}
                            />
                            <input
                                type="text"
                                placeholder="Location"
                                value={updateEvent.location}
                                onChange={(e) => setUpdateEvent({ ...updateEvent, location: e.target.value })}
                            />
                            <button onClick={updateEventHandler}>Update Event</button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Events;
