package edu.famu.rattlerlifehacks.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import edu.famu.rattlerlifehacks.model.Events;
import edu.famu.rattlerlifehacks.model.User;
import edu.famu.rattlerlifehacks.service.EventsService;
import edu.famu.rattlerlifehacks.service.UserService;
import edu.famu.rattlerlifehacks.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;



@RestController
@RequestMapping("/api/events")
public class     EventsController {

    private EventsService service;

    public EventsController(EventsService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Events>> createEvent(@RequestBody Events event) {
        try {
            Events createdEvent = service.createEvent(event);

            return ResponseEntity.ok(new ApiResponse<>(true, "Event created successfully", createdEvent, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(false, e.getMessage(), null, null));
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        }
    }







    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Events>>> getAllEvents() {
        try {
            List<Events> events = service.getAllEvents();

            if (!events.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Events retrieved successfully", events, null));
            } else {
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "No events found", new ArrayList<>(), null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        }
    }



    @PutMapping("/update/{eventId}")
    public ResponseEntity<ApiResponse<Events>> updateEventTime(
            @PathVariable String eventId,
            @RequestParam String title,
            @RequestParam String date) {
        try {
            Events e = service.updateEventTime(eventId, title, date);

            return ResponseEntity.ok(new ApiResponse<>(true, "Event time & name updated successfully", e, null));
        } catch (ExecutionException | InterruptedException | ParseException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        }
    }


    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<ApiResponse<Void>> deleteEventbyId(@PathVariable String eventId) {
        try {
            boolean deleted = service.deleteEventbyId(eventId);

            if (deleted) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Event deleted successfully", null, null));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(false, "Event not found", null, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        }
    }

}
