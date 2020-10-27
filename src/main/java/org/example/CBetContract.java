/*
 * SPDX-License-Identifier: Apache-2.0
 */
package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import static java.nio.charset.StandardCharsets.UTF_8;

@Contract(name = "CBetContract",
    info = @Info(title = "CBet contract",
                description = "My Smart Contract",
                version = "0.0.1",
                license =
                        @License(name = "Apache-2.0",
                                url = ""),
                                contact =  @Contact(email = "MyFirstSmartContract@example.com",
                                                name = "MyFirstSmartContract",
                                                url = "http://MyFirstSmartContract.me")))
@Default
public class CBetContract implements ContractInterface {
    public  CBetContract() {

    }
    @Transaction()
    public boolean cBetExists(Context ctx, String cBetId) {
        byte[] buffer = ctx.getStub().getState(cBetId);
        return (buffer != null && buffer.length > 0);
    }

    @Transaction()
    public void createCBet(Context ctx, String cBetId, String value) {
        boolean exists = cBetExists(ctx,cBetId);
        if (exists) {
            throw new RuntimeException("The asset "+cBetId+" already exists");
        }
        CBet asset = new CBet();
        asset.setValue(value);
        ctx.getStub().putState(cBetId, asset.toJSONString().getBytes(UTF_8));
    }

    @Transaction()
    public CBet readCBet(Context ctx, String cBetId) {
        boolean exists = cBetExists(ctx,cBetId);
        if (!exists) {
            throw new RuntimeException("The asset "+cBetId+" does not exist");
        }

        CBet newAsset = CBet.fromJSONString(new String(ctx.getStub().getState(cBetId),UTF_8));
        return newAsset;
    }

    @Transaction()
    public void updateCBet(Context ctx, String cBetId, String newValue) {
        boolean exists = cBetExists(ctx,cBetId);
        if (!exists) {
            throw new RuntimeException("The asset "+cBetId+" does not exist");
        }
        CBet asset = new CBet();
        asset.setValue(newValue);

        ctx.getStub().putState(cBetId, asset.toJSONString().getBytes(UTF_8));
    }

    @Transaction()
    public void deleteCBet(Context ctx, String cBetId) {
        boolean exists = cBetExists(ctx,cBetId);
        if (!exists) {
            throw new RuntimeException("The asset "+cBetId+" does not exist");
        }
        ctx.getStub().delState(cBetId);
    }

}
