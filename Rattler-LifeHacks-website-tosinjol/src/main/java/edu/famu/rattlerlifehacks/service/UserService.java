package edu.famu.rattlerlifehacks.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import edu.famu.rattlerlifehacks.model.Events;
import edu.famu.rattlerlifehacks.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    private static Firestore firestore;

    private static final String USER_COLLECTION = "User";

    public UserService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public User getUserById(String userId) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference usersRef = firestore.collection(USER_COLLECTION).document(userId);
        DocumentSnapshot userSnap = usersRef.get().get();
        if (!userSnap.exists()) {
            return null; // or throw a custom exception if desired
        }
        return documentToUser(userSnap);
    }

    private User documentToUser(DocumentSnapshot document) throws ParseException {
        User user = null;

        if (document.exists()) {
            user = document.toObject(User.class);
        }

        return user;

    }


    public List<User> getAllUsers() throws ExecutionException, InterruptedException {
        CollectionReference usersCollection = firestore.collection(USER_COLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = usersCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        // doing the entire query for Users in firebase, program will not wait for the response from this query

        List<User> users = documents.isEmpty() ? null : new ArrayList<>();
        // variable for the results above, below: if nothing, null, if not, allocated name

        documents.forEach(document -> {
            User user = null;
            try {
                user = documentToUser(document);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            users.add(user);
        });
        return users;
    }

    public boolean deleteUserbyId(String userId) throws ExecutionException, InterruptedException {
        // Query to locate the document based on the actual `userId` field
        Query userQuery = firestore.collection(USER_COLLECTION)
                .whereEqualTo("userId", userId)
                .limit(1); // Assuming `userId` is unique

        // Get the query result
        ApiFuture<QuerySnapshot> querySnapshot = userQuery.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        // If no document is found, return false
        if (documents.isEmpty()) {
            return false; // User not found
        }

        // Get the reference to the document to delete
        DocumentReference userRef = documents.get(0).getReference();

        // Delete the user document
        ApiFuture<WriteResult> writeResult = userRef.delete();
        writeResult.get(); // Wait for the delete operation to complete

        return true; // User deleted successfully
    }





    public User createUser(User users) throws ExecutionException, InterruptedException {
        if (users.getUserId() == null || users.getUserId().trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        // Validate Email
        if (users.getEmail() == null || users.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        // Assign default profile picture URL if not provided
        if (users.getProfilePictureUrl() == null || users.getProfilePictureUrl().isEmpty()) {
            users.setProfilePictureUrl("https://via.placeholder.com/150"); // Default profile picture
        }

        // Reference to the user's document in the collection
        DocumentReference userRef = firestore.collection(USER_COLLECTION).document(users.getUserId());

        // Check if the user already exists
        DocumentSnapshot existingUserSnap = userRef.get().get();
        if (existingUserSnap.exists()) {
            throw new IllegalArgumentException("User ID already exists. Please choose a different User ID.");
        }

        // Save the user data to Firestore
        ApiFuture<WriteResult> writeResult = userRef.set(users);
        writeResult.get(); // Wait for the write operation to complete

        return users; // Return the saved user object
    }



    public User loginUser(String email, String password) throws ExecutionException, InterruptedException {
        DocumentReference usersRef = firestore.collection(USER_COLLECTION).document(email);
        DocumentSnapshot userSnap = usersRef.get().get();

        if (userSnap.exists()) {
            User existingUser = userSnap.toObject(User.class);

            // Check if the password matches
            if (existingUser != null && existingUser.getPassword().equals(password)) {
                return existingUser;
            }
        }
        return null; // Return null if the user doesn't exist or password doesn't match
    }

    public User getUserByFieldId(String userId) throws ExecutionException, InterruptedException, ParseException {
        System.out.println("Fetching user with field userId: " + userId);
        CollectionReference usersCollection = firestore.collection(USER_COLLECTION);

        // Query the collection where the userId field matches
        Query query = usersCollection.whereEqualTo("userId", userId);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        // Get the list of matching documents
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        // If a matching document is found, map it to the User object
        if (!documents.isEmpty()) {
            System.out.println("User found: " + documents.get(0).getData());
            return documentToUser(documents.get(0));
        }

        System.out.println("User not found for userId: " + userId);
        return null;
    }



    public User validateUser(String userId, String password) throws ExecutionException, InterruptedException, ParseException {
        User user = getUserByFieldId(userId);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


    public boolean updateUserId(String currentUserId, String newUserId) throws ExecutionException, InterruptedException {
        // Locate the current user document based on the actual userId field
        Query userQuery = firestore.collection(USER_COLLECTION)
                .whereEqualTo("userId", currentUserId)
                .limit(1);

        // Get the user's document snapshot
        ApiFuture<QuerySnapshot> querySnapshot = userQuery.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        if (documents.isEmpty()) {
            throw new IllegalArgumentException("User not found with userId: " + currentUserId);
        }

        DocumentSnapshot currentUserSnap = documents.get(0);
        DocumentReference currentUserRef = currentUserSnap.getReference();

        // Check if the new userId already exists
        Query newUserQuery = firestore.collection(USER_COLLECTION)
                .whereEqualTo("userId", newUserId)
                .limit(1);

        ApiFuture<QuerySnapshot> newUserQuerySnapshot = newUserQuery.get();
        if (!newUserQuerySnapshot.get().isEmpty()) {
            throw new IllegalArgumentException("UserId already exists. Please choose a different UserId.");
        }

        // Update the userId field in the located document
        ApiFuture<WriteResult> updateFuture = currentUserRef.update("userId", newUserId);
        updateFuture.get(); // Wait for the update to complete

        return true; // Successfully updated the userId
    }

    public boolean updateUserField(String userId, Map<String, Object> updates) throws ExecutionException, InterruptedException {
        // Locate the user document based on the userId field
        Query userQuery = firestore.collection(USER_COLLECTION)
                .whereEqualTo("userId", userId)
                .limit(1);

        // Get the user's document snapshot
        ApiFuture<QuerySnapshot> querySnapshot = userQuery.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        if (documents.isEmpty()) {
            throw new IllegalArgumentException("User not found with userId: " + userId);
        }

        DocumentSnapshot userSnap = documents.get(0);
        DocumentReference userRef = userSnap.getReference();

        // Update the specified fields
        ApiFuture<WriteResult> updateFuture = userRef.update(updates);
        updateFuture.get(); // Wait for the update to complete

        return true; // Successfully updated the user field(s)
    }



    public String uploadProfilePicture(MultipartFile file, String userId) throws Exception {
        // Define the file name
        String fileName = "profile_pictures/" + userId + "-" + System.currentTimeMillis() + ".jpg";

        // Reference to the Firebase Storage bucket
        Bucket bucket = StorageClient.getInstance().bucket();

        // Upload the file to Firebase Storage
        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

        // Get the public download URL
        String downloadUrl = String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), blob.getName());

        // Update the Firestore user document with the profile picture URL
        DocumentReference userRef = firestore.collection(USER_COLLECTION).document(userId);
        userRef.update("profilePictureUrl", downloadUrl).get(); // Wait for the update to complete

        return downloadUrl;
    }



}