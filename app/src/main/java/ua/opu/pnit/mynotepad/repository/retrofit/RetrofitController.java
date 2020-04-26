package ua.opu.pnit.mynotepad.repository.retrofit;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.opu.pnit.mynotepad.MyNotepad;
import ua.opu.pnit.mynotepad.repository.retrofit.model.AuthRequest;
import ua.opu.pnit.mynotepad.repository.retrofit.model.AuthResponse;

public class RetrofitController {

    private static final String BASE_URL = "http://192.168.0.107:8080";

    private RetrofitClient client;

    private Retrofit buildRetrofit() {

        Gson gson = new GsonBuilder().serializeNulls().create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).callTimeout(10,TimeUnit.SECONDS).build())
                .build();
    }

    public RetrofitController() {
        client = buildRetrofit().create(RetrofitClient.class);
    }

    public void checkToken(MutableLiveData<Boolean> authResult) {
        Call<Void> c = client.checkToken(MyNotepad.getInstance().getUserToken());

        c.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    authResult.postValue(true);
                } else {
                    authResult.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MyNotepad.getInstance(), "Error connecting to server", Toast.LENGTH_SHORT).show();
                authResult.postValue(false);
            }
        });
    }

    public void loginAttempt(String login, String password, MutableLiveData<Boolean> loginResult) {
        Call<AuthResponse> c = client.auth(new AuthRequest(login, password));

        c.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MyNotepad.getInstance().setUserToken(response.body().getToken());
                    loginResult.postValue(true);
                    return;
                }
                loginResult.postValue(false);
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(MyNotepad.getInstance(), "Error connecting to server", Toast.LENGTH_SHORT).show();
                loginResult.postValue(false);
            }
        });
    }
}
