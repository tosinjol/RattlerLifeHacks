package edu.famu.rattlerlifehacks.service;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.rattlerlifehacks.model.Events;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;


@Service
public class EventsService {
    private static Firestore firestore;
    private static final String EVENTS_COLLECTION = "Events";
    public EventsService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    // Method to create a new event
    public Events createEvent(Events event) throws ExecutionException, InterruptedException {
        // Validate required fields
        if (event.getTitle() == null || event.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Event title is required.");
        }
        if (event.getLocation() == null || event.getLocation().isEmpty()) {
            throw new IllegalArgumentException("Event location is required.");
        }
        if (event.getDate() == null) {
            throw new IllegalArgumentException("Event date is required.");
        }

        // Ensure the date is a Firestore-compatible Timestamp
        if (!(event.getDate() instanceof Timestamp)) {
            throw new IllegalArgumentException("Event date must be a Firestore Timestamp.");
        }

        // Use the provided eventId or generate one if not present
        DocumentReference eventRef = firestore.collection(EVENTS_COLLECTION).document(event.getEventId() != null ? event.getEventId() : firestore.collection(EVENTS_COLLECTION).document().getId());
        event.setEventId(eventRef.getId());

        // Save the event to Firestore
        ApiFuture<WriteResult> writeResult = eventRef.set(event);
        writeResult.get(); // Wait for the write operation to complete

        return event; // Return the saved event
    }









    public List<Events> getAllEvents() throws ExecutionException, InterruptedException {
        CollectionReference eventsCollection = firestore.collection(EVENTS_COLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = eventsCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Events> events = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            try {
                Events event = documentToEvent(document); // Use the helper method
                if (event != null) {
                    events.add(event); // Add event only if it's not null
                }
            } catch (Exception e) {
                System.err.println("Error parsing event document: " + document.getId());
                e.printStackTrace(); // Print full stack trace for debugging
            }
        }

        System.out.println("Total events fetched: " + events.size()); // Debugging info
        return events; // Return events or an empty list if no documents found
    }





    private Events documentToEvent(DocumentSnapshot document) {
        if (!document.exists()) {
            return null;
        }

        try {
            Events event = new Events();
            event.setEventId(document.getId());
            event.setTitle(document.getString("title"));
            event.setLocation(document.getString("location"));
            event.setDescription(document.getString("description"));

            Timestamp timestamp = document.getTimestamp("date");
            if (timestamp != null) {
                event.setDate(timestamp.toString()); // Use the exact Firestore Timestamp string

            }

            return event;
        } catch (Exception e) {
            System.err.println("Error converting document to Event object: " + document.getId());
            e.printStackTrace();
            return null; // Return null if conversion fails
        }
    }


    public  Events updateEventTime(String eventId, String title, String date) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference eventRef = firestore.collection(EVENTS_COLLECTION).document(eventId);
        Map<String, Object> updates = new HashMap<>();
        updates.put("date", date);
        updates.put("title", title);



        ApiFuture<WriteResult> writeResult = eventRef.update(updates);
        writeResult.get();  // Ensures the update completes before returning

        Events updatedEvent = new Events();
        updatedEvent.setLocation(title);
        updatedEvent.setDate(date);
        updatedEvent.setId(eventId);
        return updatedEvent;

    }

    public boolean deleteEventbyId(String eventId) throws ExecutionException, InterruptedException {
        // Query the collection where the eventId field matches the input eventId
        CollectionReference eventsCollection = firestore.collection(EVENTS_COLLECTION);
        Query query = eventsCollection.whereEqualTo("eventId", eventId).limit(1);
        ApiFuture<QuerySnapshot> querySnapshotFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotFuture.get();

        if (querySnapshot.isEmpty()) {
            return false; // Event not found
        }

        // Get the first matching document (assuming eventId is unique)
        DocumentSnapshot eventSnap = querySnapshot.getDocuments().get(0);
        DocumentReference eventRef = eventSnap.getReference();

        // Delete the document
        ApiFuture<WriteResult> deleteFuture = eventRef.delete();
        deleteFuture.get(); // Wait for the delete operation to complete

        return true; // Event deleted successfully
    }
}