package com.example.nanni.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.nanni.myapplication.util.Utils;

public class LoginActivity extends AppCompatActivity {

    Button mBT_Login;
    CheckBox mCb_RememberMe;
    EditText mET_Username;
    EditText mEt_Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBT_Login=(Button)findViewById(R.id.bt_Login);
        mCb_RememberMe=(CheckBox)findViewById(R.id.cb_rememberMe);
        mET_Username=(EditText)findViewById(R.id.et_userName);
        mEt_Password=(EditText)findViewById(R.id.et_Password);

        if( Utils.getCheckButtonStatus(getApplicationContext())){
            mCb_RememberMe.setChecked(true);
            mET_Username.setText( Utils.getUserNamePasssword(getApplicationContext()));
            mEt_Password.setText(Utils.getPrefPassword(getApplicationContext()));
        }
        else{
            mCb_RememberMe.setChecked(false);
        }
        mBT_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCb_RememberMe.isChecked()) {
                    Utils.saveCheckButtonStatus(getApplicationContext(),true);
                    Utils.saveUserNamePassword(getApplicationContext(), mET_Username.getText().toString(), mEt_Password.getText().toString());

                }
                else{
                    Utils.saveCheckButtonStatus(getApplicationContext(),false);
                }

                Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });
    }
}
