/**Personal Data Access Control System
 * CollectionConsentManager class
 *
 * Implements the transactions needed to interact with the Collection Consent SCs deployed in the blockchain.
 * Also implements a menu to use all SC methods.
 *
 * Author: Cristòfol Daudén Esmel
 */

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import src.main.java.contracts.CollectionConsent;
import src.main.java.contracts.ProcessingConsent;

import javax.xml.crypto.Data;
import java.math.BigInteger;
import java.security.*;
import java.util.*;
import java.util.stream.Collectors;


public class CollectionConsentManager {

    private static CollectionConsentManager manageConsent = null;
    private Web3j web3j;
    private DefaultGasProvider gasProvider;
    private static final String RSA = "RSA";
    public int gasUsed;

    private ArrayList<CollectionConsent> collectionConsentContractList;
    Scanner sn;
    Random rand;

    public  static CollectionConsentManager getConsentManager(Web3j web3j, DefaultGasProvider gasProvider ) {
        if (manageConsent==null) {
            manageConsent = new CollectionConsentManager( web3j, gasProvider );
        }
        return manageConsent;
    }

    private CollectionConsentManager(Web3j web3j, DefaultGasProvider gasProvider ){
        this.web3j = web3j;
        this.gasProvider = gasProvider;

        this.collectionConsentContractList = new ArrayList<>();
        sn = new Scanner(System.in);
        rand = new Random();
        gasUsed = 0;

    }

    public void newConsentContract(ActorsManager actors, DataBaseUser db) throws NoSuchAlgorithmException {
        ClientTransactionManager transactionManager=actors.transactionManagerDataSubject;
        String dataController=actors.controller;
        List<String> dataRecipients=actors.dataRecipients;

        List<BigInteger> defaultPurposes = new ArrayList<BigInteger>();
        defaultPurposes.add(new BigInteger("0"));
        defaultPurposes.add(new BigInteger("1"));
        defaultPurposes.add(new BigInteger("2"));
        BigInteger duration = new BigInteger("1000000");
        BigInteger data = new BigInteger( "4294967295");

        Credentials credentials= db.getCredential();


        try {

            CollectionConsent aux = CollectionConsent.deploy(web3j, credentials, gasProvider, dataController, dataRecipients, data, duration, defaultPurposes).send();
            System.out.println( "Consent Contract Created"
                    + "\n\tTransaction Hash: " + aux.getTransactionReceipt().orElse(null).getTransactionHash()
                    + "\n\tContract address: " + aux.getContractAddress() );
            //Add Transaction Gas to total Gas Used
            gasUsed += aux.getTransactionReceipt().orElse( null ).getGasUsed().intValue();
            //Save contract address in the database:
            db.storeSC(aux.getContractAddress());
            collectionConsentContractList.add(aux);


            //Grant Consent as dataSubject
            //grantConsent(aux);

            //Grant Consent as dataController
            CollectionConsent test= selectConsentContract(actors.transactionManagerController,collectionConsentContractList.size()-1);
            grantConsent(test);
            //checkValidity(test);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }



    private void grantConsent( CollectionConsent contract ){
        try{
            TransactionReceipt receipt = contract.grantConsent().send();
            //Add Transaction Gas to total Gas Used
            gasUsed += receipt.getGasUsed().intValue();
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void checkValidity( CollectionConsent contract ){
        try{
            if( contract.verify().send() )
                System.out.println("Valid");
            else
                System.out.println("Not Valid");
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private CollectionConsent selectConsentContract( ClientTransactionManager transactionManager,int num ){
        if( collectionConsentContractList.isEmpty() ) return null;
        while (true) {
            try {
                return CollectionConsent.load( collectionConsentContractList.get(num).getContractAddress(),
                        web3j, transactionManager, gasProvider );
            } catch (Exception e) {
                System.out.println("Insert a valid option");
            }
        }
    }
}
