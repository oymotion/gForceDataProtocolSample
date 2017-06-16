# Sample Code of gForce Data Protocol

## Brief
This source code is to illustrate how an Android device interacts with 
a [gForce Armband][gForceArmBand] via [gForce Data Protocol][gForceDataProtocol]
based on the Android GATT service.



**Note**:
> The supported Android SDK version is Android 4.3 (API level 18) and higher.
> You need an Android phone with these versions as well.

> The version of this sample code must match that of gForce data protocol
>  (see document `gForce Data Protocol`) that your armband is based on.
> The rule of matching is their major numbers must be the same. For instance,
> v2.x of this sample code can be used with v2.x of gForce data protocol that
> your gForce armband is running. Please check with OYMotion customer service
> for such information.

## ChangeLog
### v2.0


## Build and run
1. Open this project in Android Studio, and then build, install and run the demo
on your phone with supported Android version.

2. Scan and select the gForce Armband to connect. As long as the connection
   succeeds, you will be able to see the data (e.g. quaterions and gestures)
   displayed.

[gForceArmBand]: https://oymotion.github.io/doc/gForce100UserGuide/
[gForceDataProtocol]: https://oymotion.github.io/doc/gForceDataProtocol/
