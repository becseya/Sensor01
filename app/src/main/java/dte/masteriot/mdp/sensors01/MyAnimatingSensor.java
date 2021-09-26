package dte.masteriot.mdp.sensors01;

import android.content.Context;
import android.hardware.SensorEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MyAnimatingSensor extends MySensor {

    private static final float PROXY_THRESHOLD = 0.1f;

    private final ImageView myImage;
    protected boolean handIsNearPrev = false; // original size of image in activity_main.xml corresponds to hand away

    public MyAnimatingSensor(String name, String name_short, int sensor_type, Context context, Switch switch_, TextView display, ImageView img) {
        super(name, name_short, sensor_type, context, switch_, display);
        myImage = img;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        super.onSensorChanged(sensorEvent);

        boolean handIsNear = (sensorEvent.values[0] < PROXY_THRESHOLD);

        if (handIsNear != handIsNearPrev) {
            animateHand(handIsNear);
        }

        handIsNearPrev = handIsNear;
    }

    private void animateHand(boolean hand_is_near) {
        float start = hand_is_near ? 1.f : 2.f;
        float end = hand_is_near ? 2.f : 1.f;

        Animation anim = new ScaleAnimation(
                start, end, // Start and end values for the X axis scaling
                start, end, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Keep it in center
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(500);
        myImage.startAnimation(anim);
    }
}
