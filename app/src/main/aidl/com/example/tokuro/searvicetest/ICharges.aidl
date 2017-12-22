
package com.example.tokuro.searvicetest;

// Declare any non-default types here with import statements
import com.example.tokuro.searvicetest.IChargeCallback;

interface ICharges {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    //void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
      //      double aDouble, String aString);

    oneway void registerCallback(IChargeCallback callback);

    oneway void unregisterCallback(IChargeCallback callback);

    void add(int x);

    int resultsum();

    void update();
}
