package edu.famu.rattlerlifehacks.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @DocumentId
    String objectId;
    String email;
    String userId;
    String password;
    String profilePictureUrl;
}
