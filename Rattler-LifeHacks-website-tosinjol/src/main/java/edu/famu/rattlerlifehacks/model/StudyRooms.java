package edu.famu.rattlerlifehacks.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRooms {
@DocumentId
        private String id;
    String availablilityStatus;
    Timestamp reservationTime;
    String reservedBy;
    String roomId;
}
