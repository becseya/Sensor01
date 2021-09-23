package dte.masteriot.mdp.sensors01;

import android.app.Activity;
import android.hardware.SensorEvent;
import android.widget.Switch;
import android.widget.TextView;

public class NewColorChanger3DSensor extends My3DSensor {

    private enum Orientation {FLAT, PORTRAIT, LANDSCAPE}

    private final Activity myActivity;

    public NewColorChanger3DSensor(String name, String name_short, int sensor_type, Activity activity, Switch switch_, TextView display) {
        super(name, name_short, sensor_type, activity, switch_, display);
        myActivity = activity;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        super.onSensorChanged(sensorEvent);

        Orientation orientation = getOrientationByEvent(sensorEvent);
        myActivity.getWindow().getDecorView().setBackgroundColor(getColorByOrientation(orientation));
    }

    private int getColorByOrientation(Orientation orientation) {
        switch (orientation) {
            case FLAT:
                return myActivity.getResources().getColor(R.color.color_when_flat);
            case PORTRAIT:
                return myActivity.getResources().getColor(R.color.color_when_portrait);
            case LANDSCAPE:
            default:
                return myActivity.getResources().getColor(R.color.color_when_landscape);
        }
    }

    private Orientation getOrientationByEvent(SensorEvent sensorEvent) {
        switch (getIndexOfMaxAxis(sensorEvent.values)) {
            case 2:
                return Orientation.FLAT;
            case 1:
                return Orientation.PORTRAIT;
            case 0:
            default:
                return Orientation.LANDSCAPE;
        }
    }

    private int getIndexOfMaxAxis(float[] array) {
        float max = -1;
        int idx = 0;

        for (int i = 0; i < 3; i++) {
            float abs = Math.abs(array[i]);

            if (abs > max) {
                max = abs;
                idx = i;
            }
        }

        return idx;
    }
}
