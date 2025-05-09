package edu.famu.rattlerlifehacks.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaitTimes {

    @DocumentId
            private String id;
    String locationId;
    String locationType;
    Integer currentWaitTime;
    Timestamp lastUpdated;

    public void lastUpdated(String lastUpdated) throws ParseException {
        this.lastUpdated = Timestamp.fromProto(Timestamps.parse(lastUpdated));
    }

}

