package dte.masteriot.mdp.sensors01;

import android.content.Context;
import android.hardware.SensorEvent;
import android.widget.Switch;
import android.widget.TextView;

public class My3DSensor extends MySensor {

    public My3DSensor(String name, String name_short, int sensor_type, Context context, Switch switch_, TextView display) {
        super(name, name_short, sensor_type, context, switch_, display);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Show the sensor's value in the UI:
        myDisplay.setText("X: " + sensorEvent.values[0] + "\nY:" + sensorEvent.values[1] + "\nZ:" + sensorEvent.values[2]);
    }
}
