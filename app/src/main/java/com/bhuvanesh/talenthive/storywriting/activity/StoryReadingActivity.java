package com.bhuvanesh.talenthive.storywriting.activity;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.util.THLoggerUtil;

public class StoryReadingActivity extends BaseActivity {

    private View mDecorView;
    private Toolbar mToolBar;
    private Animation mSlideUp, mSlideDown;
    private BottomNavigationView mBnv;
    private CoordinatorLayout mParentLayout;
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

        mParentLayout = (CoordinatorLayout) findViewById(R.id.layout_parent);
        mParentLayout.setFitsSystemWindows(true);
//        mParentLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        mBnv = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        mBnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        });

        TextView textViewStoryContent = (TextView) findViewById(R.id.story_content);
        textViewStoryContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (getSupportActionBar().isShowing())
                    hideSystemUI();
                else
                    showSystemUI();

                ViewGroup.LayoutParams params = mBnv.getLayoutParams();

                params.height = params.height == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;

                mBnv.setLayoutParams(params);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                THLoggerUtil.println("log on long press clicked");
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                hideSystemUI();
                return true;
            }
        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return gestureDetector.onTouchEvent(ev);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            showSystemUI();
        }
        super.onWindowFocusChanged(hasFocus);
    }

    private View.OnSystemUiVisibilityChangeListener mOnSystemUiVisibilityChangeListener = new View.OnSystemUiVisibilityChangeListener() {
        @Override
        public void onSystemUiVisibilityChange(int visibility) {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == View.VISIBLE) {
                getSupportActionBar().show();
                mToolBar.startAnimation(mSlideDown);
                mParentLayout.setFitsSystemWindows(true);
                mBnv.startAnimation(mSlideDown);
            } else {
                getSupportActionBar().hide();
                mToolBar.startAnimation(mSlideUp);
                mParentLayout.setFitsSystemWindows(false);
                mBnv.startAnimation(mSlideUp);
            }
        }
    };

    private void hideSystemUI() {
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
