package dte.masteriot.mdp.sensors01;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorEvent;
import android.widget.Button;
import android.widget.TextView;

public class MyEventCounterSensor extends MySensor {

    private final String myTotalEventsKey;
    protected Integer totalEvents = 0;

    public MyEventCounterSensor(String name, String name_short, int sensor_type, Context context, Button button, TextView display) {
        super(name, name_short, sensor_type, context, button, display);
        myTotalEventsKey = name + "_steps";
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Show the sensor's value in the UI:
        totalEvents++;
        updateDisplay();
    }

    @Override
    public void loadState(SharedPreferences preferences) {
        totalEvents = preferences.getInt(myTotalEventsKey, 0);
        super.loadState(preferences);
    }

    @Override
    public void saveState(SharedPreferences.Editor editor) {
        editor.putInt(myTotalEventsKey, totalEvents);
        super.saveState(editor);
    }

    @Override
    void updateActivity() {
        super.updateActivity();
        updateDisplay();
    }

    void updateDisplay() {
        if (active)
            myDisplay.setText(Integer.toString(totalEvents));
    }
}
