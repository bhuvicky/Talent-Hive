package com.bhuvanesh.talenthive.dashboard.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.photography.fragment.SelectPhotoFragment;

public class DashboardActivity extends BaseActivity {
    private FloatingActionButton floatingActionButton;
    public static String imageId="0";
    private static final String IMAGEID="imageId";
    public SelectPhotoFragment selectPhotoFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        selectPhotoFragment=SelectPhotoFragment.newInstance();
//        replace(R.id.dashboard_container2, selectPhotoFragment);


    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//       switch(menuItem.getItemId()){
//           case android.R.id.home:
//               pop();
//               break;
//           case R.id.menu_next:
//               Intent intent=new Intent(this, PhotographyFilterActivtiy.class);
//               intent.putExtra(IMAGEID,imageId);
//               startActivity(intent);
//               //pop();
////             replace(R.id.dashboard_container2, PhotoFilterFragment.newInstance(imageId),true);
//               //selectPhotoFragment.replaces(imageId);
//               break;
//       }
//
//        return true;
//
//    }
}
