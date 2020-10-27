/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.example;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import com.owlike.genson.Genson;

@DataType()
public class MyAsset {

    private final static Genson genson = new Genson();

    @Property()
    private String value;

    public MyAsset(){
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toJSONString() {
        return genson.serialize(this).toString();
    }

    public static MyAsset fromJSONString(String json) {
        MyAsset asset = genson.deserialize(json, MyAsset.class);
        return asset;
    }
}
