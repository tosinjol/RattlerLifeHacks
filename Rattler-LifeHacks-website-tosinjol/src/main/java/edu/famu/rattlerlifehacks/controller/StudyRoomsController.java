package edu.famu.rattlerlifehacks.controller;

import edu.famu.rattlerlifehacks.model.Events;
import edu.famu.rattlerlifehacks.model.StudyRooms;
import edu.famu.rattlerlifehacks.model.User;
import edu.famu.rattlerlifehacks.service.StudyRoomsService;
import edu.famu.rattlerlifehacks.service.UserService;
import edu.famu.rattlerlifehacks.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/studyrooms")
public class StudyRoomsController {
    private StudyRoomsService service;

    public StudyRoomsController(StudyRoomsService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<StudyRooms>>> getAllStudyRooms() {
        try {
            List<StudyRooms> studyRooms = service.getAllStudyRooms();

            if (studyRooms != null && !studyRooms.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Study Room List", studyRooms, null));
            } else {
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "No study rooms found", null, null));
            }

        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach Firebase", null, e));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<StudyRooms>> createSR(@RequestBody StudyRooms studyRooms) {
        try {
            StudyRooms createdSR = service.createSR(studyRooms);

            if (createdSR != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Study Room created successfully", createdSR, null));
            } else {
                return ResponseEntity.status(400).body(new ApiResponse<>(false, "Failed to create study room", null, null));
            }

        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));

        }
    }
}
