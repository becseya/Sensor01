package dte.masteriot.mdp.sensors01;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MySensor implements SensorEventListener, CompoundButton.OnCheckedChangeListener{

    public static SensorManager sensorManager = null;

    protected final TextView myDisplay;
    protected final Switch mySwitch;
    protected final String myName;
    protected final String myNameShort;
    protected final Sensor mySensor;
    protected final Context myContext;

    protected boolean active = false;

    public MySensor(String name, String name_short, int sensor_type, Context context, Switch switch_, TextView display) {
        myDisplay = display;
        mySwitch = switch_;
        myContext = context;
        myName = name;
        myNameShort = name_short;

        if (sensorManager == null)
            throw new RuntimeException("Sensor manager is not set");

        // Get the reference to the sensor:
        mySensor = sensorManager.getDefaultSensor(sensor_type);

        // Listener for the switch:
        mySwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        active = checked;
        updateActivity();
    }

    void updateActivity() {
        if (active) {
            // register listener and make the appropriate changes in the UI:
            mySwitch.setText(myNameShort + myContext.getResources().getString(R.string.sensor_on));
            myDisplay.setText("Waiting for first value");
            sensorManager.registerListener(MySensor.this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // unregister listener and make the appropriate changes in the UI:
            mySwitch.setText(myNameShort + myContext.getResources().getString(R.string.sensor_off));
            myDisplay.setText(myName + " sensor is OFF");
            sensorManager.unregisterListener(MySensor.this, mySensor);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Show the sensor's value in the UI:
        myDisplay.setText(Float.toString(sensorEvent.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // In this app we do nothing if sensor's accuracy changes
    }

    public void loadState(SharedPreferences preferences) {
        //active = preferences.getBoolean(myName, false);
    }

    public void saveState(SharedPreferences.Editor editor) {
        //editor.putBoolean(myName, active);
    }
}
