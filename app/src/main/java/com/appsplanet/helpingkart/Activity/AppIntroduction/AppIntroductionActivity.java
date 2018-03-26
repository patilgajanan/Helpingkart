package com.appsplanet.helpingkart.Activity.AppIntroduction;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsplanet.helpingkart.Activity.AppIntroduction.Adapter.TabsPagerAdapter;
import com.appsplanet.helpingkart.Activity.LoginActivity;
import com.appsplanet.helpingkart.Activity.MainActivity;
import com.appsplanet.helpingkart.Activity.ProfileActivity;
import com.appsplanet.helpingkart.Activity.RegisterActivity;
import com.appsplanet.helpingkart.Activity.SplashScreenActivity;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AppIntroductionActivity extends FragmentActivity implements
        ActionBar.TabListener {
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ImageView dot_1, dot_2, dot_3;
    private TextView tv_idont_have_account;
    private Button btn_logins_app_introduction, btn_get_started_app_introduction;
    //2305

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_introduction);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = AppIntroductionActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
        }
        viewPager = (ViewPager) findViewById(R.id.view_pager_introduction);
        dot_1 = (ImageView) findViewById(R.id.dot_1);
        dot_2 = (ImageView) findViewById(R.id.dot_2);
        dot_3 = (ImageView) findViewById(R.id.dot_3);
        tv_idont_have_account = (TextView) findViewById(R.id.tv_idont_have_account);
        btn_logins_app_introduction = (Button) findViewById(R.id.btn_logins_app_introduction);
        //btn_get_started_app_introduction = (Button) findViewById(R.id.btn_get_started_app_introduction);
        btn_logins_app_introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppIntroductionActivity.this, LoginActivity.class));
                finish();
            }
        });
       /* btn_get_started_app_introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppIntroductionActivity.this, MainActivity.class));
            }
        });
*/
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        tv_idont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppIntroductionActivity.this, RegisterActivity.class));
                finish();

            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(final int position) {

                switch (position) {
                    case 0:
                        dot_1.setBackgroundResource(R.drawable.circle);
                        dot_2.setBackgroundResource(R.drawable.circle_gray);
                        dot_3.setBackgroundResource(R.drawable.circle_gray);
                        break;
                    case 1:
                        dot_1.setBackgroundResource(R.drawable.circle_gray);
                        dot_2.setBackgroundResource(R.drawable.circle);
                        dot_3.setBackgroundResource(R.drawable.circle_gray);
                        break;
                    case 2:
                        dot_1.setBackgroundResource(R.drawable.circle_gray);
                        dot_2.setBackgroundResource(R.drawable.circle_gray);
                        dot_3.setBackgroundResource(R.drawable.circle);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {


            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


                    @Override
                    public void onPageSelected(final int position) {

                        switch (position) {
                            case 0:
                                dot_1.setBackgroundResource(R.drawable.circle);
                                dot_2.setBackgroundResource(R.drawable.circle_gray);
                                dot_3.setBackgroundResource(R.drawable.circle_gray);
                                break;
                            case 1:
                                dot_1.setBackgroundResource(R.drawable.circle_gray);
                                dot_2.setBackgroundResource(R.drawable.circle);
                                dot_3.setBackgroundResource(R.drawable.circle_gray);
                                break;
                            case 2:
                                dot_1.setBackgroundResource(R.drawable.circle_gray);
                                dot_2.setBackgroundResource(R.drawable.circle_gray);
                                dot_3.setBackgroundResource(R.drawable.circle);
                                break;
                            default:
                                break;
                        }

                    }

                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {


                    }

                    @Override
                    public void onPageScrollStateChanged(int arg0) {
                    }
                });

            }
        }, 3000);
    }


    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabSelected(final ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

}
