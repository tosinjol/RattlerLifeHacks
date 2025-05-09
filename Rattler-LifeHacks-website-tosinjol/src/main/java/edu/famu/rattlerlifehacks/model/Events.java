package edu.famu.rattlerlifehacks.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
//needs a rest and ab;  orgid is a ref

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Events {
    @DocumentId
    private String id;
    String description;
    String eventId;
    String location;
    String title;
    Timestamp date;
    public void setDate(String date) throws ParseException {
        this.date = Timestamp.fromProto(Timestamps.parse(date));
    }

}














