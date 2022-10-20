package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ContractInformation extends AppCompatActivity {
    private String bcAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_contract_information);

        //sets the backButton
        ImageButton backButton= (ImageButton) findViewById(R.id.backOptions5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // //Get the data from previous activity
        Intent intent= getIntent();
        String nContract = intent.getStringExtra("nSmartContract");
        String contractName= intent.getStringExtra("StringContract");
        bcAddress= intent.getStringExtra("BCAddress");
        TextView transaction= (TextView) findViewById(R.id.contractN);
        transaction.setText(contractName);

        //Gets and sets the information
        setList(nContract);

    }

    /**
     * Gets the information of the contract with web3j
     * @param address String of the contract address
     * @return List with the contract information
     */
    private List<String> getInformation(String address){
        DefaultGasProvider gasProvider = new DefaultGasProvider();
        CollectionConsent contract = null;
        //Part of the code for grant & revoke consent  and check consent
        try {
            Web3j web3j = Web3j.build(new HttpService(bcAddress));
            Intent intent= getIntent();
            String key = intent.getStringExtra("Key");
            Credentials credentials= Credentials.create(key);

            contract = CollectionConsent.load(address, web3j, credentials, gasProvider);

            List<String> contractInformation = new ArrayList<>();
            contractInformation.add("Valid:\n" + contract.verify().sendAsync().get());
            contractInformation.add("Data:\n" + contract.getData().sendAsync().get());
            web3j.shutdown();

            return contractInformation;

        }catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sets the information into a list
     * @param nContract String address of the SmartContract
     */
    private void setList(String nContract){
        List<String> info = getInformation(nContract);
        ListView listView = findViewById(R.id.informationCList);
        info.add(0,"Contract address:\n"+nContract);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.row2, info);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}