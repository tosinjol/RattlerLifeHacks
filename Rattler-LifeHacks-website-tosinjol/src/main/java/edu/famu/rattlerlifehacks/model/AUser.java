package edu.famu.rattlerlifehacks.model;

import com.google.cloud.firestore.annotation.DocumentId;

public class AUser {
    @DocumentId
    String objectId;
    String email;
    String userId;
    String password;
    String profilePictureUrl;
}
