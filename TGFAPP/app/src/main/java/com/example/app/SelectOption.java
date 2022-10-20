package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SelectOption extends AppCompatActivity {
    //Insert your blockchain address for testing purposes.
    private String blockchainAddress="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);
        ImageButton backButton= (ImageButton) findViewById(R.id.backOptions);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Get the data from previous activity
        Intent intent = getIntent();
        String nSC = intent.getStringExtra("nSmartContract");
        String contractName= intent.getStringExtra("StringContract");
        String key = intent.getStringExtra("Key");

        setMenuList(nSC,contractName,key);



    }
    /**
     * Sets the options of the menu
     * @return list with the options
     */
    private List<String> createOptions(){
        List<String> options= new ArrayList<String>();
        options.add("Get information");
        options.add("Modify Data");
        options.add("Manage Consent");
        options.add("Check History of SmartContract");
        options.add("Select another SmartContract");
        return options;
    }

    /**
     * Sets the list of options in the component list
     * Sets the listeners of the list
     * @param nSC String address of the SC
     * @param contractName Number of SC selected
     */
    private void setMenuList(String nSC, String contractName,String key){
        List<String> options = createOptions();
        ListView listView = findViewById(R.id.Smart_Options);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.row, options);
        listView.setAdapter(arrayAdapter);

        TextView textView = (TextView) findViewById(R.id.textViewOptions);
        textView.setText(contractName);
        ProgressBar progressBar= (ProgressBar) findViewById(R.id.progressBarOption);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                progressBar.setVisibility(View.VISIBLE);
                switch (i){
                    case 0:
                        Intent intent0 = new Intent(SelectOption.this, ContractInformation.class);
                        intent0.putExtra("nSmartContract",nSC);
                        intent0.putExtra("StringContract",contractName);
                        intent0.putExtra("BCAddress",blockchainAddress);
                        intent0.putExtra("Key",key);
                        progressBar.setVisibility(View.INVISIBLE);

                        startActivity(intent0);
                        break;

                    case 1:
                        Intent intent1 = new Intent(SelectOption.this, ModifyData.class);
                        intent1.putExtra("nSmartContract",nSC);
                        intent1.putExtra("StringContract",contractName);
                        intent1.putExtra("BCAddress",blockchainAddress);
                        intent1.putExtra("Key",key);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent1);

                        break;

                    case 2:
                        Intent intent2 = new Intent(SelectOption.this, ChangeConsent.class);
                        intent2.putExtra("nSmartContract",nSC);
                        intent2.putExtra("StringContract",contractName);
                        intent2.putExtra("BCAddress",blockchainAddress);
                        intent2.putExtra("Key",key);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent2);

                        break;

                    case 3:
                        Intent intent3 = new Intent(SelectOption.this, SmartContractTransactions.class);
                        intent3.putExtra("nSmartContract",nSC);
                        intent3.putExtra("StringContract",contractName);
                        intent3.putExtra("BCAddress",blockchainAddress);
                        intent3.putExtra("Key",key);
                        startActivity(intent3);
                        finish();
                        break;

                    case 4:
                        finish();
                        break;

                }
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
}
