package ch.zhaw.facerecognitionlibrary.Helpers;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.List;


public class CustomCameraView extends JavaCameraView {
    private Camera.Parameters params;
    private int mExposure;

    public CustomCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getExposure() {
        return mExposure;
    }

    public void setExposure(int exposure) {
        this.mExposure = exposure;
        params = mCamera.getParameters();
        float minEx = params.getMinExposureCompensation();
        float maxEx = params.getMaxExposureCompensation();

        exposure = Math.round((maxEx - minEx) / 100 * exposure + minEx);

        params.setExposureCompensation(exposure);
        Log.d("JavaCameraViewSettings", "Exposure Compensation " + String.valueOf(exposure));
        mCamera.setParameters(params);

    }

    public void setNightPortrait() {
        params = mCamera.getParameters();

        List<String> sceneModes = params.getSupportedSceneModes();
        if (sceneModes != null) {
            if (sceneModes.contains(Camera.Parameters.SCENE_MODE_NIGHT_PORTRAIT)) {
                Log.d("JavaCameraViewSettings", "Night portrait mode supported");
                params.setSceneMode(Camera.Parameters.SCENE_MODE_NIGHT_PORTRAIT);
            } else {
                Log.d("JavaCameraViewSettings", "Night portrait mode supported");
            }

            mCamera.setParameters(params);
        } else {
            Toast.makeText(getContext(), "The selected camera doesn't support Night Portrait Mode", Toast.LENGTH_SHORT).show();
        }

    }


    public void setDisplayOrientation(int angle) {
        Method downPolymorphic;
        try {
            downPolymorphic = mCamera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
            if (downPolymorphic != null)
                downPolymorphic.invoke(mCamera, new Object[]{angle});
        } catch (Exception e1) {
        }
    }
}
