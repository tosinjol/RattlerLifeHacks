package edu.famu.rattlerlifehacks.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor

public class Organizations extends AOrg{


    private String userId; // Reference to userId

    public Organizations(String orgId, String contactNumber, String description, String email, String eventId, String name, String userId) {
        super(orgId, contactNumber, description, email, eventId, name);
        this.userId = userId;
    }

    // Getter and Setter for userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
