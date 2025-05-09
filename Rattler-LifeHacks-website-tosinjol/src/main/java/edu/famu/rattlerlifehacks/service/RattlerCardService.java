package edu.famu.rattlerlifehacks.service;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.rattlerlifehacks.model.RattlerCard;
import edu.famu.rattlerlifehacks.model.User;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Service
public class RattlerCardService {

    private static Firestore firestore;
    private static final String RATTLERCARD_COLLECTION = "RattlerCard";

    public RattlerCardService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public RattlerCard getbyId(String userId) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference rcRef = firestore.collection(RATTLERCARD_COLLECTION).document(userId);
        DocumentSnapshot rcSnap = rcRef.get().get();
        if (!rcSnap.exists()) {
            return null; // or throw a custom exception if desired
        }
        return documentToRC(rcSnap);
    }


    private RattlerCard documentToRC(DocumentSnapshot document) throws ParseException {
        RattlerCard rc = null;

        if(document.exists()){
            rc = document.toObject(RattlerCard.class);
        }

        return rc;

    }
}
