package uca.app.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uca.app.R;
import uca.app.api.Api;
import uca.app.models.UserModel;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private EditText email;
    private EditText password;
    private Button signUp;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
                signUp();
            }
        });



    }

    /**
     * Use to validate fields
     */
    private boolean validate() {
        boolean success = false;
        if(email.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.activity_sign_up_email_required),
                    Toast.LENGTH_LONG).show();
        } else if (password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.activity_sign_up_password_required),
                    Toast.LENGTH_LONG).show();
        } else {
            success = true;
        }
        return success;
    }

    // make http request
    private void signUp() {
        if (validate()) {
            // instance user
            UserModel userModel = new UserModel();
            userModel.setEmail(email.getText().toString());
            userModel.setPassword(password.getText().toString());

            // make http request
            Call<UserModel> call = Api.instance().signUp(userModel);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if(response != null) {
                        Log.i(TAG, response.body().getEmail());
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable throwable) {
                    Log.i(TAG, throwable.getMessage());
                }
            });
        }
    }

}













