package com.example.sriyanksiddhartha.tweenanimation;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements Animation.AnimationListener{
    public static final String LOG_TAG=MainActivity.class.getSimpleName();

    private ImageView mImageView;

    private Animation mRotateAnim;
    private Animation mScaleAnim;
    private Animation mTranslateAnim;
    private Animation mFadeOutAnim;
    private Animation mFadeInAnim;
    private Animation mFallAnim;

    private AnimationSet mFallAnimSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.volleyball);

        // get display height and width in pixels
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        // load animation from anim xml file
        mFadeOutAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out_anim);
        mFadeOutAnim.setAnimationListener(this);

        // use tween animation constructors
        mFadeInAnim = new AlphaAnimation(0,1);
        mFadeInAnim.setDuration(2000);
        mFadeInAnim.setAnimationListener(this);

//        mScaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
//        mScaleAnim.setAnimationListener(this);
        mScaleAnim= new ScaleAnimation(
                1.0f, 1.5f, // fromXScale="1.0" toXScale="1.5"
                1.0f, 1.5f, // same with YScale
                Animation.RELATIVE_TO_SELF, 0.5f, // pivotX="50%"
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        mScaleAnim.setDuration(1000); // duration="1000"
        mScaleAnim.setRepeatCount(1); // repeatCount="1"
        mScaleAnim.setRepeatMode(Animation.REVERSE); //repeatMode="reverse"

        mTranslateAnim = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
        mTranslateAnim.setAnimationListener(this);
        // retain parameters after anim ends
        // image still presents at original position, but is shown 150px to right
        mTranslateAnim.setFillAfter(true);

        mRotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        mRotateAnim.setAnimationListener(this);

        mFallAnim = new TranslateAnimation(
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, height
        );
        mFallAnim.setDuration(2000);
        // g=9.18 m/s^2
        mFallAnim.setInterpolator(new AccelerateInterpolator());
        mFallAnim.setAnimationListener(this);
        // animation set is always played together
        mFallAnimSet= new AnimationSet(false);
        mFallAnimSet.addAnimation(mFallAnim);
        mFallAnimSet.addAnimation(mFadeOutAnim);
//        mFallAnimSet.addAnimation(mRotateAnim);
        mFallAnimSet.setAnimationListener(this);

    }


    public void scaleAnimation(View view) {
        mImageView.startAnimation(mScaleAnim);
    }

    public void translateAnimation(View view) {

        mImageView.startAnimation(mTranslateAnim);
    }

    public void alphaAnimation(View view) {
        if(mImageView.getVisibility()==View.INVISIBLE){

            mImageView.startAnimation(mFadeInAnim);
        }else{
            mImageView.startAnimation(mFadeOutAnim);
        }
    }

    public void rotateAnimation(View view) {

        mImageView.startAnimation(mRotateAnim);
    }



    public void fallAnimation(View view) {
        mImageView.startAnimation(mFallAnimSet);
    }


    @Override
    public void onAnimationStart(Animation animation) {
        Log.d(LOG_TAG, animation.getClass().getSimpleName()+" Started");
        Toast.makeText(MainActivity.this, animation.getClass().getSimpleName()+" Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationEnd(Animation animation) {

        Log.d(LOG_TAG, animation.getClass().getSimpleName()+" Ended");
        Toast.makeText(MainActivity.this, animation.getClass().getSimpleName()+" Ended", Toast.LENGTH_SHORT).show();

        if(animation== mFadeOutAnim){
            mImageView.setVisibility(View.INVISIBLE);
        }else if(animation==mFadeInAnim){
            mImageView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        Log.d(LOG_TAG,animation.getClass().getSimpleName()+" Repeated");
        Toast.makeText(MainActivity.this, animation.getClass().getSimpleName()+" Repeated", Toast.LENGTH_SHORT).show();

    }

}
