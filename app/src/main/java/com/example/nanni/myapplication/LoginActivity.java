package com.example.nanni.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nanni.myapplication.apiutils.APIclient;
import com.example.nanni.myapplication.apiutils.ApiInterface;
import com.example.nanni.myapplication.apiutils.LoginRequest;
import com.example.nanni.myapplication.apiutils.LoginResponse;
import com.example.nanni.myapplication.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    Button mBT_Login;
    CheckBox mCb_RememberMe;
    EditText mET_Username;
    EditText mEt_Password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!Utils.getUserNamePasssword(this).isEmpty() && !Utils.getPrefPassword(this).isEmpty()){
            startActivity(new Intent(this,NavigationDrawerActivity.class));
        }
        setContentView(R.layout.activity_login);
        mBT_Login = (Button) findViewById(R.id.bt_Login);
        mCb_RememberMe = (CheckBox) findViewById(R.id.cb_rememberMe);
        mET_Username = (EditText) findViewById(R.id.et_userName);
        mEt_Password = (EditText) findViewById(R.id.et_Password);

        if (Utils.getCheckButtonStatus(getApplicationContext())) {
            mCb_RememberMe.setChecked(true);
            mET_Username.setText(Utils.getUserNamePasssword(getApplicationContext()));
            mEt_Password.setText(Utils.getPrefPassword(getApplicationContext()));
        } else {
            mCb_RememberMe.setChecked(false);
        }
        mBT_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                invokeLoginWithEmail();          }
        });
    }

    private void invokeLoginWithEmail() {

        String e1 = mET_Username.getText().toString();
        String p1 = mEt_Password.getText().toString();

        if (mET_Username.getText().toString().length() == 0) {
            mET_Username.setError("This field is Required");
        }
        if (mEt_Password.getText().toString().length() == 0) {
            mEt_Password.setError("This field is Required");
        }
        if (!e1.equalsIgnoreCase("") && !p1.equalsIgnoreCase("")) {

            progressDialog = ProgressDialog.show(this, "Please wait ...", "Logging User...", true);

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setIdentity(e1);
            loginRequest.setPassword(p1);
            ApiInterface apiService =
                    APIclient.getClient().create(ApiInterface.class);

            Call<LoginResponse> responseCall = apiService.getloginResponse(loginRequest);
            responseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    int statusCode = response.code();
                    LoginResponse loginResponse = response.body();
                    Log.d("RESPONSE_Login", "onResponse: " + statusCode);
                    Log.d("Status", loginResponse.getStatus());
                    progressDialog.dismiss();
                    if (loginResponse.getStatus().equalsIgnoreCase("failed")) {
                        Log.d("ERROR", loginResponse.getError());
                        Toast.makeText(getApplicationContext(), loginResponse.getError(), Toast.LENGTH_SHORT).show();
                    } else {
                        // Toast.makeText(getApplicationContext(), "User Login Successful", Toast.LENGTH_SHORT).show();
                        if (mCb_RememberMe.isChecked()) {
                            Utils.saveCheckButtonStatus(getApplicationContext(), true);
                            Utils.saveUserNamePassword(getApplicationContext(), mET_Username.getText().toString(), mEt_Password.getText().toString());

                        } else {
                            Utils.saveCheckButtonStatus(getApplicationContext(), false);
                        }
                        Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                        startActivity(intent);

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    Log.d("RESPONSE_Login", "onFailure: " + t.getMessage());
                    Toast.makeText(getApplicationContext(), "Internal error. Please Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
