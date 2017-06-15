# Sample Code of gForce Data Protocol

## Brief
This source code is intended to illustrate how an Android phone gets and 
parse the data of gForce armband through Android GATT service.



**Note**:
> The supported Android versions are Android 4.3 (API level 18) and higher.

> The version of this sample code must match that of gForce data protocol
>  (see document `gForce Data Protocol`) that your armband is based on.
> The rule is their major numbers must be the same. For instance, v2.x of 
> this sample code can be used with v2.x of gForce data protocol on which
> your gForce armband is based. Please check with OYMotion customer service
> for such information.

## Build and run
1. Open this project in Android Studio, and then build, install and run the demo
on your phone with supported Android version.

2. Scan and select the gForce armband to connect. As long as the connection
   succeeds, you will be able to see the data (e.g. quaterions and gestures)
   displayed.
