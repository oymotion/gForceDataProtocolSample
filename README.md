# Sample Code of gForce Data Protocol

## Overview
This source code is intended to illustrate how an Android device interacts 
with a [gForce Armband][gForceArmBand] via the 
[gForce Data Protocol][gForceDataProtocol] based on the Android GATT service.


**Note**:
> The supported Android versions are Android 4.3 (API level 18) and higher.

> The version of this sample code must match that of gForce data protocol
>  (see document `gForce Data Protocol`) that your armband is based on.
> The rule is their major numbers must be the same. For instance, v2.x of 
> this sample code can be used with v2.x of gForce data protocol on which
> your gForce armband is based. Please check with OYMotion customer service
> for such information.

## Build and run
1. Open this project in Android Studio, and then build, install and run it
   on a phone with supported Android versions.

2. Scan and select the gForce armband to connect. Once the connection 
   succeeds, wear it on your right forearm and you will be able to see 
   the data (e.g. quaterions and gestures) gets displayed.

[gForceArmBand]: https://oymotion.github.io/doc/gForce100UserGuide/
[gForceDataProtocol]: https://oymotion.github.io/doc/gForceDataProtocol/
