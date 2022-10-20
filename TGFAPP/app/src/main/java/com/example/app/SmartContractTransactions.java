package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class SmartContractTransactions extends AppCompatActivity {
    private String bcAddress;
    private String contractName;
    private String nSC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_contract_transactions);
        Intent intent = getIntent();
        nSC = intent.getStringExtra("nSmartContract");
        contractName = intent.getStringExtra("StringContract");
        bcAddress= intent.getStringExtra("BCAddress");
        TextView nSmartContract= (TextView) findViewById(R.id.SmartContract3);
        nSmartContract.setText(contractName);
        ImageButton backButton= (ImageButton) findViewById(R.id.backOptions3);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent0 = new Intent(SmartContractTransactions.this, SelectOption.class);
                intent0.putExtra("nSmartContract",nSC);
                intent0.putExtra("StringContract",contractName);
                startActivity(intent0);
                finish();

            }
        });

        setList(getHistory(nSC),nSC);




    }

    /**
     * Gets the history of a SmartContract
     * @param nSC String address of SmartContract
     * @return List of transactions of a SmartContract
     */
    private List<Object> getHistory(String nSC){
        Web3j web3j = Web3j.build( new HttpService(bcAddress) );
        DefaultGasProvider gasProvider = new DefaultGasProvider();
        BigInteger maxB = null;

        //Gets the lastBlock
        try {
            maxB = web3j.ethBlockNumber().sendAsync().get().getBlockNumber();

        int max=maxB.intValue();
        List<EthBlock> blocks= new ArrayList<>();

        for(int i=0;i<max+1;i++){
            blocks.add(web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(i)), true).sendAsync().get());
        }
        List<Object> contractTransactions= new ArrayList<>();
        for (EthBlock x : blocks){
            List<EthBlock.TransactionResult> transactions = new ArrayList<>();
            transactions = x.getBlock().getTransactions();

            transactions.forEach(t->{
                EthBlock.TransactionObject transactionObject= (EthBlock.TransactionObject) t.get();
                try {
                    Optional<TransactionReceipt> receipt= web3j.ethGetTransactionReceipt(transactionObject.getHash()).sendAsync().get().getTransactionReceipt();
                    if(receipt.get().getContractAddress()!= null){
                        if(receipt.get().getContractAddress().equals(nSC)){
                            contractTransactions.add(receipt.get().getTransactionHash());
                        }
                    }
                    else {
                        if (receipt.get().getTo().equals(nSC)) {
                            contractTransactions.add(receipt.get().getTransactionHash());
                        }
                    }
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return contractTransactions;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sets the list of transactions of a SmartContract
     * @param history List of transactions
     */
    private void setList(List<Object> history, String nSC){
        ListView listView = findViewById(R.id.listAddresses);
        List<String> transactions= new ArrayList<>();
        for (int i=0; i<history.size();i++){
            transactions.add("Transaction_"+i);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.row, transactions);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent0 = new Intent(SmartContractTransactions.this, TransactionInformation.class);
                intent0.putExtra("nTransaction",history.get(position).toString());
                intent0.putExtra("Tname",transactions.get(position));
                intent0.putExtra("Contract",nSC);
                intent0.putExtra("BCAddress",bcAddress);
                startActivity(intent0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent0 = new Intent(SmartContractTransactions.this, SelectOption.class);
        intent0.putExtra("nSmartContract",nSC);
        intent0.putExtra("StringContract",contractName);
        startActivity(intent0);
        finish();
    }
}