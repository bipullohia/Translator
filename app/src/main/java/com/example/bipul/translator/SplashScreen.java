package com.example.bipul.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by bipul on 22-04-2016.
 */
public class SplashScreen extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        final ImageView iv = (ImageView) findViewById(R.id.logo);
        Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);
        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(an2);
                SplashScreen.this.finish();
                SplashScreen.this.startActivity(new Intent(SplashScreen.this.getBaseContext(), MainActivity.class));
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
