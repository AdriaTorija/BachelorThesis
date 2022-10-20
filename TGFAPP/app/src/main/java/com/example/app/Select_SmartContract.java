package com.example.app;


import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Select_SmartContract extends AppCompatActivity {

    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_smart_contract);

        ImageButton backButton= (ImageButton) findViewById(R.id.backMenu);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ListView listView = findViewById(R.id.listAddresses);
        ProgressBar progressBar = findViewById(R.id.progressBarMenu);
        progressBar.setVisibility(View.VISIBLE);

        //Gets the data from firebase
        readData(new FireStoreCallback() {
            @Override
            public void onCallback(List<String> list) {
                showList(list);
                setList(list);
            }

            @Override
            public void onCallback2(String keyS) {
                key=keyS;
            }
        });

    }

    /**
     * Connects to firebase and gets the SmartContracts of the User
     * @param fireStoreCallback
     */
    private void readData(FireStoreCallback fireStoreCallback) {
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Task<DocumentSnapshot> documentSnapshotTask = mFirestore.collection("Users").document(user.getUid()).collection("Data").document("SmartContracts").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> aux = new ArrayList<>();
                aux.addAll((Collection<? extends String>) task.getResult().get("SmartContracts"));
                fireStoreCallback.onCallback(aux);
            } else {
                Log.d(TAG, "Error getting documents");
            }
        });
        Task<DocumentSnapshot> documentSnapshotTask2 = mFirestore.collection("Users").document(user.getUid()).collection("Data").document("Keys").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String Key;

                fireStoreCallback.onCallback2((String) task.getResult().get("Private"));
            } else {
                Log.d(TAG, "Error getting documents");
            }
        });
    }

    private interface FireStoreCallback {
        void onCallback(List<String> list);
        void onCallback2(String key);
    }



    /**
     * Sets the list of addresses into the component list
     * @param list
     */
    private void setList(List<String> list) {
        ListView listView = findViewById(R.id.listAddresses);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarMenu);
        progressBar.setVisibility(View.INVISIBLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Select_SmartContract.this, SelectOption.class);

                intent.putExtra("StringContract",adapterView.getItemAtPosition(i).toString());
                intent.putExtra("nSmartContract", list.get(i));
                intent.putExtra("Key",key);
                startActivity(intent);

            }
        });
    }

    /**
     * Return to previous activity
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void showList(List<String> list){
        ListView listView = findViewById(R.id.listAddresses);
        List<String> contracts= new ArrayList<>();
        for (int i=0; i<list.size();i++){
            contracts.add("Contract_"+i);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.row, contracts);
        listView.setAdapter(arrayAdapter);
    }

}
