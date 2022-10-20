package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import jnr.ffi.annotations.In;

public class TransactionInformation extends AppCompatActivity {
    private String bcAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_transaction_information);
        ImageButton backButton= (ImageButton) findViewById(R.id.backOptions4);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Get the data from previous activity
        Intent intent= getIntent();
        String nTransaction = intent.getStringExtra("nTransaction");
        String transactionName= intent.getStringExtra("Tname");
        String contract= intent.getStringExtra("Contract");
        bcAddress= intent.getStringExtra("BCAddress");
        TextView transaction= (TextView) findViewById(R.id.transaction_number);
        transaction.setText(transactionName);

        //Gets and sets the information
        setList(nTransaction,contract);

    }

    /**
     * Gets the information of the transaction with web3j
     * @param address String of the transaction address
     * @return List of the transaction information
     */
    private List<String> getInformation(String address){
        Web3j web3j = Web3j.build( new HttpService(bcAddress) );
        DefaultGasProvider gasProvider = new DefaultGasProvider();
        try {
            Transaction transaction = web3j.ethGetTransactionByHash(address).sendAsync().get().getTransaction().get();
            //List
            List <String> information= new ArrayList<>();
            information.add("Block:\n"+transaction.getBlockHash());
            information.add("From: \n"+transaction.getFrom());
            switch (transaction.getInput()){
                case "0x6fac0fe9":
                    information.add("Action:\nGrantConsent");
                    break;
                case "0xff4c4d54":
                    information.add("Action:\nRevokeConsent");
                    break;
                default:
                    information.add("Action:\n"+transaction.getInput());
                            break;
            }

            return information;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Sets the information into a list
     * @param nTransaction String of the transaction hash
     * @param contract String of the contract address
     */
    private void setList(String nTransaction, String contract){
        List<String> info = getInformation(nTransaction);
        ListView listView = findViewById(R.id.informationList);
        info.add(0,"Transaction Hash:\n"+nTransaction);
        info.add(1,"Contract address:\n"+contract);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.row2, info);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}