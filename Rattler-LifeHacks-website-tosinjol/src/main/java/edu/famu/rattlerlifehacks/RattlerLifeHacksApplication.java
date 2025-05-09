package edu.famu.rattlerlifehacks;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class RattlerLifeHacksApplication {

    public static void main(String[] args) throws IOException {

        ClassLoader loader = RattlerLifeHacksApplication.class.getClassLoader();

        File file = new File(loader.getResource("serviceKey.json").getFile());

        FileInputStream serviceAccount  = new FileInputStream(file.getAbsolutePath());
        // two lines above open the service key file
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        // lines above are opening firebase credentials and making sure its open
        if(FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(options);
        }



        SpringApplication.run(RattlerLifeHacksApplication.class, args);
    }

}
