package com.example.app;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class ProcessingConsent extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610e64380380610e6483398101604081905261002f9161011a565b326001600160a01b038416146100b15760405162461bcd60e51b815260206004820152603660248201527f5472616e73616374696f6e2073656e64657220646f6573206e6f74206d61746360448201527f687420776974682074686520436f6e74726f6c6c657200000000000000000000606482015260840160405180910390fd5b600080546001600160a01b03199081163317909155600280546001600160a01b0395861690831617905560018054938516938216939093179092556003805491909316911617905561015d565b80516001600160a01b038116811461011557600080fd5b919050565b60008060006060848603121561012f57600080fd5b610138846100fe565b9250610146602085016100fe565b9150610154604085016100fe565b90509250925092565b610cf88061016c6000396000f3fe608060405234801561001057600080fd5b50600436106100cf5760003560e01c80633018205f1161008c5780634265c707116100665780634265c707146101c65780638753367f146101d7578063b6f96af2146101ea578063b7a7af99146101f257600080fd5b80633018205f1461018d578063373eed8d1461019e5780633a728745146101b157600080fd5b80630561bac3146100d457806316a0042c146100fe5780632178543c146101135780632a22288e146101265780632a26de85146101495780632e6473701461015c575b600080fd5b6001546001600160a01b03165b6040516001600160a01b0390911681526020015b60405180910390f35b61011161010c366004610b2d565b610205565b005b610111610121366004610b46565b610334565b610139610134366004610b2d565b6105e7565b60405190151581526020016100f5565b610111610157366004610b2d565b61060e565b61017f61016a366004610b2d565b60009081526004602052604090206001015490565b6040519081526020016100f5565b6002546001600160a01b03166100e1565b6101116101ac366004610b78565b610718565b6101b96107a3565b6040516100f59190610b9a565b6003546001600160a01b03166100e1565b6101396101e5366004610b2d565b6107fb565b610111610894565b610139610200366004610b2d565b610998565b6002546001600160a01b031632148061022857506001546001600160a01b031632145b8061023d57506003546001600160a01b031632145b6102625760405162461bcd60e51b815260040161025990610bde565b60405180910390fd5b60008181526004602052604090205460ff166102905760405162461bcd60e51b815260040161025990610c22565b6002546001600160a01b031632036102d8576000818152600460208190526040822001815b602091828204019190066101000a81548160ff021916908360ff16021790555050565b6001546001600160a01b0316320361030257600081815260046020819052604082200160016102b5565b6003546001600160a01b0316320361033157600081815260046020819052604090912001805462ff0000191690555b50565b6000546001600160a01b031633146103e65760405162461bcd60e51b815260206004820152606360248201527f4e65772050726f63657373696e6720707572706f73652063616e206f6e6c792060448201527f62652061646465642066726f6d2074686520436f6e73656e742053432066726f60648201527f6d207768696368207468697320507572706f73652053432077617320637265616084820152621d195960ea1b60a482015260c401610259565b6002546001600160a01b031632146104665760405162461bcd60e51b815260206004820152603b60248201527f4f6e6c7920636f6e74726f6c6c65722063616e206164642061206e657720507260448201527f6f63657373696e6720707572706f736520746f207468697320534300000000006064820152608401610259565b60008481526004602052604090205460ff16156104d05760405162461bcd60e51b815260206004820152602260248201527f50726f63657373696e6720707572706f736520616c7265616479206578697374604482015261399760f11b6064820152608401610259565b6104d8610a67565b8160010361050357506040805160608101825260018082526020820152600091810191909152610523565b506040805160608101825260018152600060208201819052918101919091525b6040518060a00160405280600115158152602001858152602001428152602001844261054f9190610c91565b81526020908101839052600087815260048083526040918290208451815460ff1916901515178155928401516001840155908301516002830155606083015160038084019190915560808401516105a892840191610a85565b5050600580546001810182556000919091527f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db001959095555050505050565b60008181526004602052604081205460ff161561060657506001919050565b506000919050565b6002546001600160a01b031632148061063157506001546001600160a01b031632145b8061064657506003546001600160a01b031632145b6106625760405162461bcd60e51b815260040161025990610bde565b60008181526004602052604090205460ff166106905760405162461bcd60e51b815260040161025990610c22565b6002546001600160a01b031632036106bd57600081815260046020819052604082206001929101906102b5565b6001546001600160a01b031632036106ea57600081815260046020819052604090912060019101816102b5565b6003546001600160a01b031632036103315760008181526004602081905260409091206001910160026102b5565b6001546001600160a01b0316321461078e5760405162461bcd60e51b815260206004820152603360248201527f4f6e6c79207468652064617461205375626a65637420697320616c6c6f776564604482015272103a37903237903a3434b99030b1ba34b7b71760691b6064820152608401610259565b60009182526004602052604090912060010155565b606060058054806020026020016040519081016040528092919081815260200182805480156107f157602002820191906000526020600020905b8154815260200190600101908083116107dd575b5050505050905090565b60008181526004602052604081205460ff166108295760405162461bcd60e51b815260040161025990610c22565b60008281526004602081905260408220015442919061010081048116620100009091041660ff161580159061086f57506000848152600460205260409020600201548210155b801561088c57506000848152600460205260409020600301548211155b949350505050565b6002546001600160a01b03163214806108b757506001546001600160a01b031632145b806108cc57506003546001600160a01b031632145b6108e85760405162461bcd60e51b815260040161025990610bde565b6005546109425760405162461bcd60e51b815260206004820152602260248201527f4e6f2050726f63657373696e6720707572706f736573206f6e20746869732053604482015261219760f11b6064820152608401610259565b6002546001600160a01b031632036109605761095e60006109dd565b565b6001546001600160a01b0316320361097c5761095e60016109dd565b6003546001600160a01b0316320361095e5761095e60026109dd565b600081815260046020526040812054819060ff166109b8575060006109d7565b5060008281526004602081905260409091200154610100900460ff1615155b92915050565b60005b600554811015610a635760006004600060058481548110610a0357610a03610c65565b906000526020600020015481526020019081526020016000206004018360038110610a3057610a30610c65565b602091828204019190066101000a81548160ff021916908360ff1602179055508080610a5b90610ca9565b9150506109e0565b5050565b60405180606001604052806003906020820280368337509192915050565b600183019183908215610b085791602002820160005b83821115610ad957835183826101000a81548160ff021916908360ff1602179055509260200192600101602081600001049283019260010302610a9b565b8015610b065782816101000a81549060ff0219169055600101602081600001049283019260010302610ad9565b505b50610b14929150610b18565b5090565b5b80821115610b145760008155600101610b19565b600060208284031215610b3f57600080fd5b5035919050565b60008060008060808587031215610b5c57600080fd5b5050823594602084013594506040840135936060013592509050565b60008060408385031215610b8b57600080fd5b50508035926020909101359150565b6020808252825182820181905260009190848201906040850190845b81811015610bd257835183529284019291840191600101610bb6565b50909695505050505050565b60208082526024908201527f4163746f72206e6f7420616c6c6f77656420746f20646f20746869732061637460408201526334b7b71760e11b606082015260800190565b60208082526023908201527f50726f63657373696e6720707572706f736520646f6573206e6f7420657869736040820152623a399760e91b606082015260800190565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b60008219821115610ca457610ca4610c7b565b500190565b600060018201610cbb57610cbb610c7b565b506001019056fea26469706673582212207afa85c4a97bd23f83839038392d4d7206629a7e794784980b66d975464f4e3e64736f6c634300080f0033";

    public static final String FUNC_EXISTSPURPOSE = "existsPurpose";

    public static final String FUNC_GETCONTROLLER = "getController";

    public static final String FUNC_GETDATAPURPOSE = "getDataPurpose";

    public static final String FUNC_GETDATASUBJECT = "getDataSubject";

    public static final String FUNC_GETPROCESSOR = "getProcessor";

    public static final String FUNC_GETPURPOSES = "getPurposes";

    public static final String FUNC_GRANTCONSENT = "grantConsent";

    public static final String FUNC_MODIFYDATA = "modifyData";

    public static final String FUNC_NEWPURPOSE = "newPurpose";

    public static final String FUNC_REVOKEALLCONSENTS = "revokeAllConsents";

    public static final String FUNC_REVOKECONSENT = "revokeConsent";

    public static final String FUNC_VERIFY = "verify";

    public static final String FUNC_VERIFYDS = "verifyDS";

    @Deprecated
    protected ProcessingConsent(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ProcessingConsent(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ProcessingConsent(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ProcessingConsent(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Boolean> existsPurpose(BigInteger _purpose) {
        final Function function = new Function(FUNC_EXISTSPURPOSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_purpose)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> getController() {
        final Function function = new Function(FUNC_GETCONTROLLER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getDataPurpose(BigInteger _purpose) {
        final Function function = new Function(FUNC_GETDATAPURPOSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_purpose)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getDataSubject() {
        final Function function = new Function(FUNC_GETDATASUBJECT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getProcessor() {
        final Function function = new Function(FUNC_GETPROCESSOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getPurposes() {
        final Function function = new Function(FUNC_GETPURPOSES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> grantConsent(BigInteger _purpose) {
        final Function function = new Function(
                FUNC_GRANTCONSENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_purpose)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> modifyData(BigInteger _purpose, BigInteger _data) {
        final Function function = new Function(
                FUNC_MODIFYDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_purpose), 
                new org.web3j.abi.datatypes.generated.Uint256(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> newPurpose(BigInteger _purpose, BigInteger data, BigInteger duration, BigInteger defaultTrue) {
        final Function function = new Function(
                FUNC_NEWPURPOSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_purpose), 
                new org.web3j.abi.datatypes.generated.Uint256(data), 
                new org.web3j.abi.datatypes.generated.Uint256(duration), 
                new org.web3j.abi.datatypes.generated.Uint256(defaultTrue)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeAllConsents() {
        final Function function = new Function(
                FUNC_REVOKEALLCONSENTS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeConsent(BigInteger _purpose) {
        final Function function = new Function(
                FUNC_REVOKECONSENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_purpose)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> verify(BigInteger _purpose) {
        final Function function = new Function(FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_purpose)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> verifyDS(BigInteger _purpose) {
        final Function function = new Function(FUNC_VERIFYDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_purpose)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static ProcessingConsent load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ProcessingConsent(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ProcessingConsent load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ProcessingConsent(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ProcessingConsent load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ProcessingConsent(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ProcessingConsent load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ProcessingConsent(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ProcessingConsent> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _controller, String _dataSubject, String _processor) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _controller), 
                new org.web3j.abi.datatypes.Address(160, _dataSubject), 
                new org.web3j.abi.datatypes.Address(160, _processor)));
        return deployRemoteCall(ProcessingConsent.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ProcessingConsent> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _controller, String _dataSubject, String _processor) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _controller), 
                new org.web3j.abi.datatypes.Address(160, _dataSubject), 
                new org.web3j.abi.datatypes.Address(160, _processor)));
        return deployRemoteCall(ProcessingConsent.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ProcessingConsent> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _controller, String _dataSubject, String _processor) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _controller), 
                new org.web3j.abi.datatypes.Address(160, _dataSubject), 
                new org.web3j.abi.datatypes.Address(160, _processor)));
        return deployRemoteCall(ProcessingConsent.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ProcessingConsent> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _controller, String _dataSubject, String _processor) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _controller), 
                new org.web3j.abi.datatypes.Address(160, _dataSubject), 
                new org.web3j.abi.datatypes.Address(160, _processor)));
        return deployRemoteCall(ProcessingConsent.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
