package com.oymotion.gforce.demo;

import android.util.Log;
import android.util.SparseArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Created by Zhou Yu (local) on 2016/10/11.
 */

public class GForceData {
    private final static String TAG = GForceData.class.getSimpleName();

    public static final int QUATERNION_FLOAT      = 0x2;
    public static final int GESTURE               = 0xF;

    public static final int GESTURE_RELAX                  = 0x0;
    public static final int GESTURE_GIST                   = 0x1;
    public static final int GESTURE_SPREAD_FINGERS         = 0x2;
    public static final int GESTURE_WAVE_TOWARD_PALM       = 0x3;
    public static final int GESTURE_WAVE_BACKWARD_PALM     = 0x4;
    public static final int GESTURE_TUCK_FINGERS           = 0x5;
    public static final int GESTURE_SHOOT                  = 0x6;
    public static final int GESTURE_MAX                    = GESTURE_SHOOT;
    public static final int GESTURE_UNKNOWN                = 0xFF;

    private static final SparseArray<String> mGestureNames = new SparseArray<>();

    static {
        mGestureNames.put(GESTURE_RELAX, "GESTURE_RELAX");
        mGestureNames.put(GESTURE_GIST, "GESTURE_GIST");
        mGestureNames.put(GESTURE_SPREAD_FINGERS, "GESTURE_SPREAD_FINGERS");
        mGestureNames.put(GESTURE_WAVE_TOWARD_PALM, "GESTURE_WAVE_TOWARD_PALM");
        mGestureNames.put(GESTURE_WAVE_BACKWARD_PALM,  "GESTURE_WAVE_BACKWARD_PALM");
        mGestureNames.put(GESTURE_TUCK_FINGERS, "GESTURE_TUCK_FINGERS");
        mGestureNames.put(GESTURE_SHOOT,  "GESTURE_SHOOT");
        mGestureNames.put(GESTURE_UNKNOWN,  "GESTURE_UNKNOWN");
    }

    private int mType;
    private float[] mQuaternionFloat;
    private int mGesture;

    private GForceData(int type, float[] quaternion) {
        mType = type;
        mQuaternionFloat = quaternion;
    }

    private GForceData(int type, int gesture) {
        mType = type;
        mGesture = gesture;
    }

    public int getType() {
        return mType;
    }
    public static GForceData build(byte[] data) {
        // byte[0]  - type
        // byte[1]  - length of the subsequent data byte[2:]
        // byte[2:] - data
        int type = (int)data[0];
        if (type == QUATERNION_FLOAT) {
            if (data[1] != 16) {
                return null;
            }
            float[] q = new float[4];

            for (int i = 2; i < 18; i += 4) {
                byte[] bytes = Arrays.copyOfRange(data, i, i + 4);
                q[(i-2)/4] = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
            }

            return new GForceData(type, q);
        }
        else if (type == GESTURE) {
            if (data[1] != 1) {
                return null;
            }
            int gesture = (int)data[2];
            if (gesture > GESTURE_MAX && gesture != GESTURE_UNKNOWN) {
                Log.e(TAG, String.format("Illegal gesture value: %d", gesture));
                return null;
            }
            else {
                return new GForceData(type, gesture);
            }
        }

        return null;
    }
    public float[] getQuaternionFloat() {
        return mQuaternionFloat;
    }

    public int getGesture() {
        return mGesture;
    }

    public String getGestureName(){
        return mGestureNames.get(mGesture);
    }

    public static String getGestureName(int gesture){
        return mGestureNames.get(gesture);
    }

}
