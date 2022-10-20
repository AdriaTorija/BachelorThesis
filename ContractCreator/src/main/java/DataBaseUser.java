import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.web3j.crypto.Credentials;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

public class DataBaseUser {
    //For testing install ganache get, the private key of the first user, and create a user in the db of Firebase. 
    //Set the account Service Key of Firebase and its url associated.
    private static Firestore dbFireStore;
    private static String fileKeyPath = "src/resources/serviceAccountKey.json";
    private static String firebaseUrl= "";
    private static String user="";
    private static final String privateKey= "";
    private static final String RSA = "RSA";
    DataBaseUser() {

            ClassLoader classLoader = Main.class.getClassLoader();
            File file = new File(fileKeyPath);

            FileInputStream serviceAccount= null;
            try {
                serviceAccount = new FileInputStream(file.getAbsolutePath());

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl(firebaseUrl)
                        .build();
                dbFireStore= FirestoreClient.getFirestore(FirebaseApp.initializeApp(options));

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    private static long getNumContract() {
        long nSC = 0;
        DocumentReference docRef = dbFireStore.collection("Users").document(user).collection("Data").document("SmartContracts");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot doc = null;
        try {
            doc = future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (doc.exists()) {
            Object x = (doc.getData().get("nSmartContracts"));
            if (x != null) {
                nSC = (long) x;
            } else {
                nSC = 0;
            }
        } else {
            System.out.println("No such document");
        }
        return nSC;
    }

    private static void updateNumContract() {
        long nContracts = getNumContract() + 1;
        store( "SmartContracts", "nSmartContracts", nContracts);
    }


    private static void store(String document,String field, long data){
        ApiFuture<WriteResult> future = dbFireStore.collection("Users").document(user).collection("Data").document(document).update(field,data);
        WriteResult result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    private static void store(String document,String field, String data){
        ApiFuture<WriteResult> future = dbFireStore.collection("Users").document(user).collection("Data").document(document).update(field,data);
        WriteResult result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    public Credentials getCredential(){
        return Credentials.create(privateKey);
    }
    public void setKeys(){
        store("Keys","Private",privateKey);
    }


    public void storeSC(String address) throws Exception {


        ApiFuture<WriteResult> future = dbFireStore.collection("Users").document(user).collection("Data").document("SmartContracts").update("SmartContracts", FieldValue.arrayUnion(address));
        WriteResult result = null;
        try {
            result = future.get();
            updateNumContract();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Write result: " + result);
    }



}

