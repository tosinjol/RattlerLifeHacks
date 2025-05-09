package edu.famu.rattlerlifehacks.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//needs a rest and ab; user is a ref

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RattlerCard{

    @DocumentId

    String balance;
    String cardNum;
    String userId;

}
