package com.example.app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class ChangeConsent extends AppCompatActivity {

    private boolean auth;
    private String bcAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent_manager);

        ImageButton backButton= (ImageButton) findViewById(R.id.backOptions2);
        Intent intent = getIntent();
        String nSC = intent.getStringExtra("nSmartContract");
        String contractName = intent.getStringExtra("StringContract");
        bcAddress= intent.getStringExtra("BCAddress");
        String key = intent.getStringExtra("Key");
        Credentials credentials= Credentials.create(key);
        TextView nSmartContract= (TextView) findViewById(R.id.SmartContract2);
        nSmartContract.setText(contractName);
        Web3j web3j= Web3j.build(new HttpService(bcAddress));
        auth=false;


        DefaultGasProvider gasProvider = new DefaultGasProvider();
        getConsent(web3j,gasProvider,nSC,credentials);
        setGrantConsent(web3j,gasProvider,nSC,credentials);
        setRevokeConsent(web3j,gasProvider,nSC,credentials);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                web3j.shutdown();
            }
        });

    }

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
                Toast.makeText(ChangeConsent.this,"Failed to authenticate!",Toast.LENGTH_SHORT).show();
            }
        });
        return biometricPrompt;
    }

    /**
     * Gets the consent to show in app
     * @param web3j Web3j
     * @param gasProvider DefaultGasprovider
     * @param nSC String address of SmartContract
     * @param credentials TransactionManager User in the blockchain
     */
    private void getConsent(Web3j web3j, DefaultGasProvider gasProvider, String nSC, Credentials credentials){
        try{

            CollectionConsent contract = CollectionConsent.load(nSC, web3j, credentials, gasProvider);
            Boolean aux = contract.verify().sendAsync().get();
            TextView validView= (TextView) findViewById(R.id.ValidText);
            if(aux) validView.setText("The SmartContract is:\n\tvalid");
            else validView.setText("The SmartContract is:\n\tnot valid");

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the functionality of grantConsentButton
     * @param web3j Web3j
     * @param gasProvider DefaultGasprovider
     * @param nSC String address of SmartContract
     * @param credentials TransactionManager User in the blockchain
     */
    private void setGrantConsent(Web3j web3j,DefaultGasProvider gasProvider,String nSC, Credentials credentials){
        Button grantConsent= (Button) findViewById(R.id.GrantConsent);
        BiometricPrompt biometricPrompt= setBiometric();

        grantConsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG).setTitle("To grant")
                            .setDescription("Use your fingerprint to GrantConsent ").setNegativeButtonText("Cancel").build();
                    biometricPrompt.authenticate(promptInfo);
                    if (auth){
                        CollectionConsent contract = CollectionConsent.load(nSC, web3j, credentials, gasProvider);
                        contract.grantConsent().sendAsync().get();
                        Intent intent = getIntent();
                        web3j.shutdown();
                        finish();
                        startActivity(intent);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Sets the functionality of revokeConsentButton
     * @param web3j Web3j
     * @param gasProvider DefaultGasprovider
     * @param nSC String address of SmartContract
     * @param credentials TransactionManager User in the blockchain
     */
    private void setRevokeConsent(Web3j web3j,DefaultGasProvider gasProvider,String nSC, Credentials credentials){
        Button revokeConsent= (Button) findViewById(R.id.RevokeConsent);
        BiometricPrompt biometricPrompt= setBiometric();

        //RevokeConsent
        revokeConsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG).setTitle("To Revoke")
                            .setDescription("Use your fingerprint to RevokeConsent ").setNegativeButtonText("Cancel").build();
                    biometricPrompt.authenticate(promptInfo);
                    if (auth){
                        CollectionConsent contract = CollectionConsent.load(nSC, web3j, credentials, gasProvider);
                        contract.revokeConsent().sendAsync().get();
                        Intent intent = getIntent();
                        web3j.shutdown();
                        finish();
                        startActivity(intent);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}