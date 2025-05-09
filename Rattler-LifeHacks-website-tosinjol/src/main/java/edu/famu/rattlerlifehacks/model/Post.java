package edu.famu.rattlerlifehacks.model;

import com.google.cloud.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//needs a set and get date

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    String caption;
    Timestamp createdAt;
    String image;
    String locationId;
    String objectId;
    Number registerCount;
    Timestamp updatedAt;
}
