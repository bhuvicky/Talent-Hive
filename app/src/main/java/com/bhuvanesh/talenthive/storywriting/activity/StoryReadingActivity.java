package com.bhuvanesh.talenthive.storywriting.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.util.THLoggerUtil;

public class StoryReadingActivity extends BaseActivity {

    private View mDecorView;
    private Toolbar mToolBar;
    private Animation mSlideUp;
    private Animation mSlideDown;
    private GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_reading);

        mToolBar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolBar);
        setTitle("Title of the Story");

        mDecorView = getWindow().getDecorView();
        mDecorView.setOnSystemUiVisibilityChangeListener(mOnSystemUiVisibilityChangeListener);
        mSlideUp = AnimationUtils.loadAnimation(this, R.anim.anim_slide_up);
        mSlideDown = AnimationUtils.loadAnimation(this, R.anim.anim_slide_down);
//        showSystemUI();

        TextView textViewStoryContent = (TextView) findViewById(R.id.story_content);
        textViewStoryContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                THLoggerUtil.println("log on touch called");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        hideSystemUI();
                        break;
                    case MotionEvent.ACTION_UP:
                        hideSystemUI();
                        break;
                }
                return gestureDetector.onTouchEvent(event);
            }
        });

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                THLoggerUtil.println("log single tap confirmed called");
                if (getSupportActionBar().isShowing())
                    hideSystemUI();
                else
                    showSystemUI();
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                hideSystemUI();
                THLoggerUtil.println("log on scroll called");
                return true;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        THLoggerUtil.println("log focus change called focus status = " + hasFocus);
        if(hasFocus){
            hideSystemUI();
        }
        super.onWindowFocusChanged(hasFocus);
    }

    private View.OnSystemUiVisibilityChangeListener mOnSystemUiVisibilityChangeListener = new View.OnSystemUiVisibilityChangeListener() {
        @Override
        public void onSystemUiVisibilityChange(int visibility) {
            THLoggerUtil.println("log SystemUiVisibilityChangeListener called");
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == View.VISIBLE) {
                mToolBar.startAnimation(mSlideDown);
                getSupportActionBar().show();
            } else {
                getSupportActionBar().hide();
                mToolBar.startAnimation(mSlideUp);
            }
        }
    };

    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void showSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
