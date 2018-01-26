package com.appsplanet.helpingkart.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appsplanet.helpingkart.Class.CropingOption;
import com.appsplanet.helpingkart.Class.CropingOptionAdapter;
import com.squareup.picasso.Callback;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewFoodActivity;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewServiceActivity;
import com.appsplanet.helpingkart.Class.CircleImageView;
import com.appsplanet.helpingkart.Class.Functions;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private final static int REQUEST_PERMISSION_REQ_CODE = 34;
    private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private Toolbar toolbar_profile;
    private File outPutFile = null;
    private CoordinatorLayout cordinatelayout_profile;
    private ProgressBar pb_profile;
    private String name_profile, mobile_profile, email_profile, address_profile;
    private TextView tv_profile_name, tv_profile_phone, tv_profile_email, tv_address_user;
    private Button btn_edit_profile;
    private CircleImageView circleImageView_profile;
    private Uri mImageCaptureUri;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ProfileActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
        }
        circleImageView_profile = (CircleImageView) findViewById(R.id.circleImageView_profile);
        btn_edit_profile = (Button) findViewById(R.id.btn_edit_profile);
        tv_profile_name = (TextView) findViewById(R.id.tv_profile_name);
        tv_profile_phone = (TextView) findViewById(R.id.tv_profile_phone);
        tv_profile_email = (TextView) findViewById(R.id.tv_profile_email);
        tv_address_user = (TextView) findViewById(R.id.tv_address_user);
        cordinatelayout_profile = (CoordinatorLayout) findViewById(R.id.cordinatelayout_profile);
        pb_profile = (ProgressBar) findViewById(R.id.pb_profile);
        toolbar_profile = (Toolbar) findViewById(R.id.toolbar_profile);
        String img = SplashScreenActivity.sharedPreferencesDatabase.getData("profile_img");
        if (!TextUtils.isEmpty(img)) {

            Picasso.with(ProfileActivity.this).load(img).into(circleImageView_profile);
        }
        outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), ".temp.jpg");
        mobile_profile = SplashScreenActivity.sharedPreferencesDatabase.getData(Config.DB_REGISTER_MOBILE);
        circleImageView_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageOption();
            }
        });
        setProfile(mobile_profile);

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfileAlert();
            }
        });
        setSupportActionBar(toolbar_profile);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        //setProfile(mobile_profile);
        toolbar_profile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void selectImageOption() {
        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Capture Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
                    mImageCaptureUri = Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    startActivityForResult(intent, CAMERA_CODE);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {
            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            CropingIMG();

        } else if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {

            System.out.println("Camera Image URI : " + mImageCaptureUri);
            CropingIMG();
        } else if (requestCode == CROPING_CODE) {
            try {
                if (outPutFile.exists()) {
                    Picasso.with(ProfileActivity.this).load(outPutFile).skipMemoryCache().into(circleImageView_profile, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Error while save image", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void CropingIMG() {
        final ArrayList<CropingOption> cropOptions = new ArrayList<CropingOption>();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        String profile_img = mImageCaptureUri.toString();
        SplashScreenActivity.sharedPreferencesDatabase.addData("profile_img", profile_img);

        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "Cann't find image croping app", Toast.LENGTH_SHORT).show();
            return;
        } else {
            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", 512);
            intent.putExtra("outputY", 512);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = (ResolveInfo) list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROPING_CODE);
            } else {
                for (ResolveInfo res : list) {
                    final CropingOption co = new CropingOption();
                    co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);
                    co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    cropOptions.add(co);
                }

                CropingOptionAdapter adapter = new CropingOptionAdapter(getApplicationContext(), cropOptions);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Croping App");
                builder.setCancelable(false);
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        startActivityForResult(cropOptions.get(item).appIntent, CROPING_CODE);
                    }
                });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            getContentResolver().delete(mImageCaptureUri, null, null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }


    public void setProfile(final String mobile) {
        btnVisiblity(false);
        AndroidNetworking.post(Config.getProfileUser)
                .addBodyParameter("mobile", mobile)
                .addBodyParameter("edit", "true")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String array = response.getString("userDetails");
                            JSONObject parentObject = new JSONObject(array);
                            name_profile = parentObject.getString("name");
                            email_profile = parentObject.getString("email");
                            address_profile = parentObject.getString("address");
                            SplashScreenActivity.sharedPreferencesDatabase.removeDataByKey(Config.DB_REGISTER_NAME);
                            SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_REGISTER_NAME, name_profile);
                            SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_REGISTER_EMAIL, email_profile);
                            SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_Updated_address, address_profile);
                            tv_profile_name.setText(name_profile);
                            tv_profile_email.setText(email_profile);
                            tv_profile_phone.setText(mobile_profile);
                            tv_address_user.setText(address_profile);
                            btnVisiblity(true);

                        } catch (Exception e) {
                            Snackbar.make(cordinatelayout_profile, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            btnVisiblity(true);

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Snackbar.make(cordinatelayout_profile, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(cordinatelayout_profile, error.getErrorDetail(), Snackbar.LENGTH_SHORT).show();
                        }
                        btnVisiblity(true);
                    }
                });
    }

    public void btnVisiblity(boolean status) {
        if (status) {

            pb_profile.setVisibility(View.GONE);
        } else {

            pb_profile.setVisibility(View.VISIBLE);
        }
    }

    public void EditProfileAlert() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.profile_layout_custom_dialog, null);
        final EditText etUsername = (EditText) alertLayout.findViewById(R.id.et_username_update);
        final EditText etemail = (EditText) alertLayout.findViewById(R.id.et_password_update);
        final EditText et_address_update = (EditText) alertLayout.findViewById(R.id.et_address_update);
        String profile_name = SplashScreenActivity.sharedPreferencesDatabase.getData("register_name");
        String profile_email = SplashScreenActivity.sharedPreferencesDatabase.getData("register_email");
        String profile_address = SplashScreenActivity.sharedPreferencesDatabase.getData("updated_address");
        etUsername.setText(profile_name);
        etemail.setText(profile_email);
        et_address_update.setText(profile_address);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Update Profile");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = etUsername.getText().toString();
                String email = etemail.getText().toString();
                String address = et_address_update.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(ProfileActivity.this, "Name can not be blank", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ProfileActivity.this, "Email can not be blank", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address)) {
                    Toast.makeText(ProfileActivity.this, "Address can not be blank", Toast.LENGTH_SHORT).show();

                } else if (!email.matches(emailPattern)) {
                    Toast.makeText(ProfileActivity.this, "Email is not valid", Toast.LENGTH_SHORT).show();
                } else {
                    SplashScreenActivity.sharedPreferencesDatabase.removeDataByKey(Config.DB_REGISTER_NAME);
                    SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_REGISTER_NAME, username);
                    SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_REGISTER_EMAIL, email);
                    SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_Updated_address, address);
                    editProfile(mobile_profile, username, email, address);
                }

            }

        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }


    public void editProfile(final String mobile, final String name, final String email, final String address) {
        btnVisiblity(false);
        AndroidNetworking.post(Config.getProfileUser)
                .addBodyParameter("mobile", mobile)
                .addBodyParameter("name", name)
                .addBodyParameter("email", email)
                .addBodyParameter("address", address)
                .addBodyParameter("update", "true")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("error") && response.getString("error").equals("false")) {
                                detailsDialog("Update Profile", "User Updated Successfully...");

                            } else if (response.has("error") && response.getString("error").equals("true")) {
                                Snackbar.make(cordinatelayout_profile, response.getString("message").toString(), Snackbar.LENGTH_LONG).show();
                            }

                            btnVisiblity(true);
                        } catch (Exception e) {
                            Snackbar.make(cordinatelayout_profile, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            btnVisiblity(true);

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Snackbar.make(cordinatelayout_profile, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(cordinatelayout_profile, error.getErrorDetail(), Snackbar.LENGTH_SHORT).show();
                        }
                        btnVisiblity(true);
                    }
                });
    }

    public void detailsDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "  CLOSE  ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


}

