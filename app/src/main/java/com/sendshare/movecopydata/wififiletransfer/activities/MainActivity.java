package com.sendshare.movecopydata.wififiletransfer.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sendshare.movecopydata.wififiletransfer.adapters.DrawerListAdapter2;
import com.sendshare.movecopydata.wififiletransfer.adapters.MyPageAdapter;
import com.sendshare.movecopydata.wififiletransfer.fragments.JustFragmentFiles;
import com.sendshare.movecopydata.wififiletransfer.singletons.AdViewBanner;
import com.sendshare.movecopydata.wififiletransfer.utilities.FileManager;
import com.wifi.mitko.sharewifiles3.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
  {
    private ActionBarDrawerToggle actionBarDrawerToggle;

      private ViewPager viewPager;
      private BottomNavigationView bottomNavigationView;
      private MenuItem prevMenuItem;
      private AdView adView;

      private boolean permissionGranded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // do not delete this line code

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

           if( FileManager.verifyStoragePermissions(this) ) {
             permissionGranded = true;
           } else {
               // permission granted in onRequestPermissionsResult()
           }
        } else {
            permissionGranded = true;
        }

        setContentView(R.layout.activity_main);

        initDrawerLayout();

        initActionBar();

         if(permissionGranded) {
             initViewPager2();
         }

         initBottomNavigationView();

       /* Interestial interestial = Interestial.getInstance();
        interestial.createAd(getApplicationContext());*/

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
      @Override
      public void onRequestPermissionsResult(int requestCode,
                                             String[] permissions, int[] grantResults) {
          switch (requestCode) {
              case FileManager.REQUEST_EXTERNAL_STORAGE:
                  if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                      Log.e("!!!","Permission granted");
                     initViewPager2();
                  } else {
                      Toast.makeText(this, "Access Denied",
                              Toast.LENGTH_SHORT).show();
                  }
                  break;
              default:
                  super.onRequestPermissionsResult(requestCode, permissions,
                          grantResults);
          }
      }
      @Override
      protected void onDestroy() {
        destroyAds();

        super.onDestroy();

       /* RefWatcher refWatcher = MyApplication.getRefWather(this);
        refWatcher.watch(this);*/
    }

      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
      @Override
      protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            Uri uri = data.getData();
            if(uri != null) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    final int flags = data.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION |
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    getContentResolver().takePersistableUriPermission(uri, flags);// this line is very important !!!
                    FileManager.saveUserUri(uri,this);
                  //  String realPath = FileManager.getRealPath(uri,this);
                 //   MyConsole.println("real path = " + realPath);
                }
            }
        }
    }

      @Override
      protected void onStop() {
          destroyAds();
          super.onStop();
      }
      @Override
      protected void onResume() {
          super.onResume();
        //  createRealAds();
      }
      @Override
      public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
      @Override
      protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
     //  comment this otherwise overriding toggle icon not work
        actionBarDrawerToggle.syncState();
    }
      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
       if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
      @Override
      public boolean onPrepareOptionsMenu(Menu menu) {
       // boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        return super.onPrepareOptionsMenu(menu);
    }

      private void initDrawerLayout() {
          DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

          ListView drawerList = findViewById(R.id.drawer_list);
          String[] titles;
          if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
              titles = getResources().getStringArray(R.array.drawer_values_on_and_above_kitkat);
          } else {
              titles = getResources().getStringArray(R.array.drawer_values_below_kitkat);
          }
          ArrayList<String> tit = new ArrayList<>(Arrays.asList(titles));
          DrawerListAdapter2 drawerListAdapter = new DrawerListAdapter2(this, R.layout.custom_listview,
                  tit);
          actionBarDrawerToggle  =
                  new ActionBarDrawerToggle(this, drawerLayout,R.string.open_drawer,
                          R.string.close_drawer) {
                      @Override
                      public void onDrawerOpened(View drawerView) {
                          super.onDrawerOpened(drawerView);
                          invalidateOptionsMenu();
                      }

                      @Override
                      public void onDrawerClosed(View drawerView) {
                          super.onDrawerClosed(drawerView);
                          invalidateOptionsMenu();
                      }
                  };
      /*  actionBarDrawerToggle.getDrawerArrowDrawable().
                setColor(getResources().getColor(android.R.color.holo_blue_bright));*/
          drawerList.setAdapter(drawerListAdapter);
          drawerLayout.setDrawerListener(actionBarDrawerToggle);
      }
      private void initActionBar() {
          ActionBar actionBar = getSupportActionBar();
          if(actionBar != null) {
              actionBar.setDisplayHomeAsUpEnabled(true);
              actionBar.setHomeButtonEnabled(true);
              actionBar.setDisplayShowCustomEnabled(true);
              ActionBar.LayoutParams layoutParams =
                      new ActionBar.LayoutParams(
                              LinearLayout.LayoutParams.MATCH_PARENT,
                              LinearLayout.LayoutParams.MATCH_PARENT
                      );
              View view = LayoutInflater.from(this).inflate(R.layout.ads_container,null);// LayoutInflater.from(this).inflate(R.layout.custom_actionbar2,null);
              actionBar.setCustomView(view,layoutParams);
              Toolbar parent =(Toolbar) view.getParent();
              parent.setPadding(0,0,0,0);//for tab otherwise give space in tab
              parent.setContentInsetsAbsolute(0,0);
          }

      }
      private void initBottomNavigationView() {
          bottomNavigationView = findViewById(R.id.bottom_navigation);
          bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                  switch (item.getItemId()) {
                      case R.id.home_apk :
                          viewPager.setCurrentItem(0);
                          break;
                      case R.id.home_video:
                          viewPager.setCurrentItem(1);
                          break;
                      case R.id.home_image:
                          viewPager.setCurrentItem(2);
                          break;
                      case R.id.home_audio:
                          viewPager.setCurrentItem(3);
                          break;
                      case R.id.home_document:
                          viewPager.setCurrentItem(4);
                          break;

                  }
                  return true;
              }
          });
      }
      private void initViewPager2() {
          viewPager = findViewById(R.id.vew_pager);
          final MyPageAdapter pagerAdapter = new MyPageAdapter(getSupportFragmentManager());
          viewPager.setAdapter(pagerAdapter);
          viewPager.setOffscreenPageLimit(4);
          viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
              @Override
              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

              @Override
              public void onPageSelected(int position) {
                  if(position > 0) {
                      ((JustFragmentFiles) pagerAdapter.getCurrentFragment(position)).
                              requestData(position);
                  }
                  if (prevMenuItem != null) {
                      prevMenuItem.setChecked(false);
                  } else {
                      bottomNavigationView.getMenu().getItem(0).setChecked(false);
                  }
                  bottomNavigationView.getMenu().getItem(position).setChecked(true);
                  prevMenuItem = bottomNavigationView.getMenu().getItem(position);

              }

              @Override
              public void onPageScrollStateChanged(int state) { }
          });

      }
      private void destroyAds() {
          LinearLayout adsParent = findViewById(R.id.ads_parent);
          if(adsParent != null)
              adsParent.removeAllViews();
          if(adView != null) {
              adView.destroy();
              adView = null;
          }
      }
      private void createRealAds() {
          LinearLayout parent = findViewById(R.id.ads_parent);
          AdView adView = AdViewBanner.getInstance()
                  .createRealAdView(getApplicationContext(),
                          getString(R.string.real_banner_unitId));
          parent.removeAllViews(); ;// IMPORTANT !!!! because onResume() called twice
          parent.addView(adView);
      }
      private void createTestAds() {
          LinearLayout parent = findViewById(R.id.ads_parent);
          AdView adView = AdViewBanner.getInstance().
                  createTestAdView(getApplicationContext(),getString(R.string.test_banner_unitId));
          parent.removeAllViews();
          parent.addView(adView);
      }

}
