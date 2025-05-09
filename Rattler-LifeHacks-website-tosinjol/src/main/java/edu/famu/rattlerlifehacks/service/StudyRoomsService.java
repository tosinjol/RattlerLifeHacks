package edu.famu.rattlerlifehacks.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.rattlerlifehacks.model.Events;
import edu.famu.rattlerlifehacks.model.StudyRooms;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class StudyRoomsService {

    private static Firestore firestore;

    private static final String STUDYROOMS_COLLECTION = "StudyRooms";

    public StudyRoomsService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public List<StudyRooms> getAllStudyRooms() throws ExecutionException, InterruptedException {
        CollectionReference eventsCollection = firestore.collection(STUDYROOMS_COLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = eventsCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<StudyRooms> studyRooms = documents.isEmpty() ? null : new ArrayList<>();

        documents.forEach(document -> {
            StudyRooms studyRoom = null;
            try {
                studyRoom = documentToStudyRooms(document);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            studyRooms.add(studyRoom);
        });

        return studyRooms;
    }

    private StudyRooms documentToStudyRooms(DocumentSnapshot document) throws ParseException {
        StudyRooms studyRooms = null;

        if (document.exists()) {
            studyRooms = document.toObject(StudyRooms.class);
        }

        return studyRooms;
    }

    public StudyRooms createSR(StudyRooms studyRooms) throws ExecutionException, InterruptedException {
        DocumentReference studyRoomRef = firestore.collection(STUDYROOMS_COLLECTION).document();
        studyRooms.setRoomId(studyRoomRef.getId());

        ApiFuture<WriteResult> writeResult = studyRoomRef.set(studyRooms);
        writeResult.get(); // Wait for the write operation to complete
        return studyRooms;
    }

 //   public static StudyRooms updateAvail(String availablilityStatus) throws ExecutionException, InterruptedException {
     //   DocumentReference studyRoomRef = firestore.collection(STUDYROOMS_COLLECTION).document();
     //   Map<String, Object> updates = new HashMap<>();
    //    updates.put("availablilityStatus", availablilityStatus);



     //   ApiFuture<WriteResult> writeResult = studyRoomRef.update(updates);
     //   writeResult.get();  // Ensures the update completes before returning

     //   StudyRooms updatedSR = new StudyRooms();
      //  updatedSR.setAvailablilityStatus(availablilityStatus);

     //   return updatedSR;

   // }
}
