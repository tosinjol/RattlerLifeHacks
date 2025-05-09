package edu.famu.rattlerlifehacks.controller;

import edu.famu.rattlerlifehacks.model.Events;
import edu.famu.rattlerlifehacks.model.User;
import edu.famu.rattlerlifehacks.service.UserService;
import edu.famu.rattlerlifehacks.util.ApiResponse;
import jakarta.ws.rs.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // Get user by userId
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable(name = "userId") String userId) {
        try {
            User user = service.getUserById(userId);

            if (user != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "User found", user, null));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(false, "User not found", null, null));
            }

        } catch (ExecutionException | ParseException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));

        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach Firebase", null, e));
        }
    }

    // Get all users
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        try {
            List<User> users = service.getAllUsers();

            if (users != null && !users.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Users List", users, null));
            } else {
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "No users found", null, null));
            }

        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach Firebase", null, e));
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<User>> deleteUserbyId(@PathVariable String userId) {
        try {
            // Call the service method to delete the user
            boolean deleted = service.deleteUserbyId(userId);

            if (deleted) {
                return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null, null));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(false, "User not found", null, null));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(false, e.getMessage(), null, null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        }
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User users) {
        try {
            // Assign default profile picture URL if not provided
            if (users.getProfilePictureUrl() == null || users.getProfilePictureUrl().isEmpty()) {
                users.setProfilePictureUrl("https://via.placeholder.com/150"); // Default profile picture
            }

            // Call the service to create the user
            User createdUser = service.createUser(users);

            if (createdUser != null) {
                // Respond with success if the user was created
                return ResponseEntity.ok(new ApiResponse<>(true, "User created successfully", createdUser, null));
            } else {
                // Respond with failure if the user creation failed
                return ResponseEntity.status(400).body(new ApiResponse<>(false, "Failed to create user", null, null));
            }
        } catch (IllegalArgumentException e) {
            // Handle specific validation errors (e.g., missing email, duplicate userId)
            return ResponseEntity.status(400).body(new ApiResponse<>(false, e.getMessage(), null, null));
        } catch (ExecutionException | InterruptedException e) {
            // Handle errors related to Firestore operations
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        }
    }



    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> validateUser(@RequestBody User user) {
        try {

            User validatedUser = service.validateUser(user.getUserId(), user.getPassword());

            if (validatedUser != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", validatedUser, null));
            } else {
                return ResponseEntity.status(401).body(new ApiResponse<>(false, "Invalid username or password", null, null));
            }
        } catch (Exception e) {
            System.err.println("Error during user validation: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        }
    }


    @PutMapping("/update/{currentUserId}")
    public ResponseEntity<ApiResponse<String>> updateUserId(
            @PathVariable String currentUserId,
            @RequestParam String newUserId) {
        try {
            // Call the service method to update the userId
            boolean updated = service.updateUserId(currentUserId, newUserId);

            if (updated) {
                // Return success response if the update is successful
                return ResponseEntity.ok(new ApiResponse<>(true, "UserId updated successfully", null, null));
            } else {
                // Return failure response if the update was not successful
                return ResponseEntity.status(400).body(new ApiResponse<>(false, "Failed to update userId", null, null));
            }
        } catch (IllegalArgumentException e) {
            // Handle validation or input-related errors
            return ResponseEntity.status(400).body(new ApiResponse<>(false, e.getMessage(), null, null));
        } catch (Exception e) {
            // Handle unexpected server errors
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        }
    }

    @PutMapping("/updateProfilePicture/{userId}")
    public ResponseEntity<ApiResponse<String>> updateProfilePicture(
            @PathVariable String userId,
            @RequestParam String profilePictureUrl) {
        try {
            Map<String, Object> updates = new HashMap<>();
            updates.put("profilePictureUrl", profilePictureUrl);

            boolean updated = service.updateUserField(userId, updates);

            if (updated) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Profile picture updated successfully", null, null));
            } else {
                return ResponseEntity.status(400).body(new ApiResponse<>(false, "Failed to update profile picture", null, null));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(false, e.getMessage(), null, null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        }
    }


}