package edu.famu.rattlerlifehacks.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AOrg {
@DocumentId
    String  orgId;
    String contactNumber;
    String description;
    String email;
    String eventId;
    String name;

}
