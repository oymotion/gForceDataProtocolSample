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

    // Types of data
    public static final int QUATERNION_FLOAT      = 0x2;
    public static final int GESTURE               = 0xF;

    // Types of gesture data
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

    public static final int NO_PACKAGE_ID = -1;

    private int mType;
    private int mPackageId;             // 0-255, for check continuity of packages
    private float[] mQuaternionFloat;
    private int mGesture;

    private GForceData(int type, float[] quaternion, int package_id) {
        mType = type;
        mQuaternionFloat = quaternion;
        mPackageId = package_id;
    }

    private GForceData(int type, int gesture, int package_id) {
        mType = type;
        mGesture = gesture;
        mPackageId = package_id;
    }

    public static class Builder {
        byte[] mData;

        public Builder(byte[] data) {
            mData = data;
        }

        GForceData build() {
            // bytes[0][0:6] - type
            // bytes[1]  - length of the subsequent data byte[2:]
            // bytes[2:] - data
            //      if (bytes[0][7] == 1)
            //          bytes[2] - Package ID for checking integrity of packages sequence.
            int type = (int)(mData[0] & 0x7F);
            int package_id_shift = (mData[0] & 0x80) == 0x80 ? 1 : 0;
            int length = mData[1];
            int package_id = NO_PACKAGE_ID;
            if (package_id_shift == 1) {
                package_id = mData[2];
            }
            int payload_start_index = 2 + package_id_shift;

            if (type == QUATERNION_FLOAT) {
                if (length != 16 + package_id_shift) {
                    return null;
                }
                float[] q = new float[4];

                for (int i = 0; i < 4; i++) {
                    int copy_index = payload_start_index + i * 4;
                    byte[] bytes = Arrays.copyOfRange(mData,  copy_index , copy_index + 4);
                    q[i] = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                }

                return new GForceData(type, q, package_id);
            }
            else if (type == GESTURE) {
                if (length != 1 + package_id_shift) {
                    return null;
                }
                int gesture = (int)mData[payload_start_index];
                if (gesture > GESTURE_MAX && gesture != GESTURE_UNKNOWN) {
                    Log.e(TAG, String.format("Illegal gesture value: %d", gesture));
                    return null;
                }
                else {
                    return new GForceData(type, gesture, package_id);
                }
            }

            return null;
        }
    }

    public int getType() {
        return mType;
    }

    public int getPackageId() {
        return mPackageId;
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
