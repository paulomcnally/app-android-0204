package uca.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uca.app.api.Api;
import uca.app.models.TweetModel;
import uca.app.ui.activities.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
        setData("Paulo McNally");

        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);

    }

    private void setData(String text) {
        TweetModel tweetModel = new TweetModel();
        tweetModel.setText(text);

        Call<TweetModel> call = Api.instance().setTweet(tweetModel);
        call.enqueue(new Callback<TweetModel>() {
            @Override
            public void onResponse(Call<TweetModel> call, Response<TweetModel> response) {
                if(response !=null) {
                    Log.i(TAG, response.body().getText());
                } else {
                    Log.i(TAG, "Error en peticion");
                }
            }

            @Override
            public void onFailure(Call<TweetModel> call, Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void getData() {
        Call<List<TweetModel>> call = Api.instance().getTweets();
        call.enqueue(new Callback<List<TweetModel>>() {
            @Override
            public void onResponse(Call<List<TweetModel>> call, Response<List<TweetModel>> response) {
                if (response != null) {
                    for(TweetModel tweetModel : response.body()) {
                        Log.i(TAG, tweetModel.getText());
                    }
                }
                else {
                    Log.i(TAG, "La respuesta es incorrecta");
                }
            }

            @Override
            public void onFailure(Call<List<TweetModel>> call, Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });

    }
}
