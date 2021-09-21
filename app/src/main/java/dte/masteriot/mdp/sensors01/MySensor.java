package dte.masteriot.mdp.sensors01;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MySensor implements SensorEventListener, View.OnClickListener{

    public static SensorManager sensorManager = null;

    protected final TextView myDisplay;
    protected final Button myButton;
    protected final Sensor mySensor;
    protected final Context myContext;

    protected boolean active = false;

    public MySensor(int sensor_type, Context context, Button button, TextView display) {
        myDisplay = display;
        myButton = button;
        myContext = context;

        if (sensorManager == null)
            throw new RuntimeException("Sensor manager is not set");

        // Get the reference to the sensor:
        mySensor = sensorManager.getDefaultSensor(sensor_type);

        // Listener for the button:
        button.setOnClickListener(this);

        // Set default text of button on startup
        updateActivity();
    }

    @Override
    public void onClick(View view) {
        active = !active;
        updateActivity();
    }

    void updateActivity() {
        if (active) {
            // register listener and make the appropriate changes in the UI:
            myButton.setText(myContext.getResources().getString(R.string.light_sensor_on));
            myButton.setBackground(myContext.getResources().getDrawable(R.drawable.round_button_on));
            myDisplay.setText("Waiting for first light sensor value");
            sensorManager.registerListener(MySensor.this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // unregister listener and make the appropriate changes in the UI:
            myButton.setText(myContext.getResources().getString(R.string.light_sensor_off));
            myButton.setBackground(myContext.getResources().getDrawable(R.drawable.round_button_off));
            myDisplay.setText("Light sensor is OFF");
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
}
