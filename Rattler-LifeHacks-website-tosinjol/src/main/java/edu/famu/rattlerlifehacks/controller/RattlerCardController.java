package edu.famu.rattlerlifehacks.controller;

import edu.famu.rattlerlifehacks.model.RattlerCard;
import edu.famu.rattlerlifehacks.model.User;
import edu.famu.rattlerlifehacks.service.RattlerCardService;
import edu.famu.rattlerlifehacks.service.UserService;
import edu.famu.rattlerlifehacks.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;
@RestController
public class RattlerCardController {

    private RattlerCardService service;
    public RattlerCardController(RattlerCardService service) {
        this.service = service;
    }

    @GetMapping("rattlercard/{userId}")
    public ResponseEntity<ApiResponse<RattlerCard>> getbyId(@PathVariable(name = "userId") String userId) {
        try {
            RattlerCard rc = service.getbyId(userId);

            if (rc != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "User found", rc, null));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(false, "User not found", null, null));
            }

        } catch (ExecutionException | ParseException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));

        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach Firebase", null, e));
        }
    }
}
