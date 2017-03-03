package uca.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tumblr.remember.Remember;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uca.app.R;
import uca.app.api.Api;
import uca.app.models.AccessTokenModel;
import uca.app.models.UserModel;
import uca.app.utils.SessionUtil;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";

    private EditText email;
    private EditText password;
    private Button signUp;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
    }

    /**
     * Initialize all views
     */
    private void initViews() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signUp = (Button) findViewById(R.id.sign_up);
        signIn = (Button) findViewById(R.id.sign_in);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void signIn() {
        if(email.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.activity_sign_up_email_required),
                    Toast.LENGTH_LONG).show();
        } else if(password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.activity_sign_up_password_required),
                    Toast.LENGTH_LONG).show();
        } else {
            // instance user
            UserModel userModel = new UserModel();
            userModel.setEmail(email.getText().toString());
            userModel.setPassword(password.getText().toString());

            // make http request
            Call<AccessTokenModel> call = Api.instance().signIn(userModel);
            call.enqueue(new Callback<AccessTokenModel>() {
                @Override
                public void onResponse(Call<AccessTokenModel> call, Response<AccessTokenModel> response) {
                    if(response != null && response.body() != null) {
                        Remember.putString(SessionUtil.ACCESS_TOKEN, response.body().getId());
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<AccessTokenModel> call, Throwable t) {

                }
            });
        }
    }

}























