package dte.masteriot.mdp.sensors01;

import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Context;
import android.hardware.SensorEvent;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyVibratingCounterSensor extends MyEventCounterSensor {

    private static final int MULTIPLIER = 20;
    private static final long VIBRATION_TIME_MS = 500;

    private final Vibrator myVibrator;

    public MyVibratingCounterSensor(String name, String name_short, int sensor_type, Context context, Button button, TextView display) {
        super(name, name_short, sensor_type, context, button, display);

        myVibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);

        if (!isVibratorAvailable())
            Toast.makeText(context, "Device does not support vibration", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        super.onSensorChanged(sensorEvent);

        if ((totalEvents % MULTIPLIER == 0) && isVibratorAvailable())
            myVibrator.vibrate(VIBRATION_TIME_MS);

    }

    private boolean isVibratorAvailable() {
        return (myVibrator != null) && myVibrator.hasVibrator();
    }
}
