package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class ModifyData extends AppCompatActivity {
    private boolean auth;
    private String bcAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_data);
        auth=false;
        //Get the data from previous activity
        Intent intent= getIntent();
        String nContract = intent.getStringExtra("nSmartContract");
        String contractName= intent.getStringExtra("StringContract");
        TextView contractText= (TextView) findViewById(R.id.contractNum);
        contractText.setText(contractName);
        bcAddress= intent.getStringExtra("BCAddress");

        ImageButton backButton= (ImageButton) findViewById(R.id.backOptions6);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Set lastData
        modifyData(nContract,setBiometric());

    }

    /**
     * Sets the biometric for extra authentication
     * @return BiometricPrompt
     */
    private BiometricPrompt setBiometric(){
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt= new BiometricPrompt(this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                auth=true;

            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(ModifyData.this,"Failed to authenticate!",Toast.LENGTH_SHORT).show();
            }
        });
        return biometricPrompt;
    }

    /**
     * Gets the data from the contract
     * Modifies the data from the contract if authenticated and requested
     * @param nContract String address of contract
     * @param biometricPrompt BiometricPrompt for authentication
     */
    private void modifyData(String nContract,BiometricPrompt biometricPrompt){
        try {
            //Prepare for loading data
            Web3j web3j = Web3j.build(new HttpService(bcAddress));

            DefaultGasProvider gasProvider = new DefaultGasProvider();

            Intent intent= getIntent();
            String key = intent.getStringExtra("Key");
            Credentials credentials= Credentials.create(key);


            CollectionConsent contract = CollectionConsent.load(nContract, web3j,credentials , gasProvider);
            //Gets the last data
            //CollectionConsent contract = CollectionConsent.load(nContract, web3j, transactionManager, gasProvider);
            TextView data= (TextView) findViewById(R.id.lastData);
            String lastData= contract.getData().sendAsync().get().toString();
            data.setText(lastData);

            //Sets the new data
            Button setData= (Button) findViewById(R.id.modifyData);
            setData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText text= (EditText) findViewById(R.id.editData) ;
                    String newValue= text.getText().toString();

                    if(!newValue.matches("")){
                        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG).setTitle("To Modify")
                                .setDescription("Use your fingerprint to modifyData ").setNegativeButtonText("Cancel").build();
                        biometricPrompt.authenticate(promptInfo);
                        if(auth){

                            try {
                                System.out.println("newvalue: "+newValue);
                                contract.modifyData(BigInteger.valueOf(Long.parseLong(newValue))).sendAsync().get();
                                Intent intent = getIntent();
                                web3j.shutdown();
                                finish();
                                startActivity(intent);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}