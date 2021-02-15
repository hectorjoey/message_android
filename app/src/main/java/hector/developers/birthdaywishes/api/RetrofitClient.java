package hector.developers.birthdaywishes.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://localhost:8080/";
    private static hector.developers.birthdaywishes.api.RetrofitClient mInstance;
    private Retrofit retrofit;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private RetrofitClient() {
        OkHttpClient m_client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).client(m_client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized hector.developers.birthdaywishes.api.RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new hector.developers.birthdaywishes.api.RetrofitClient();
        }
        return mInstance;
    }

    public hector.developers.birthdaywishes.api.Api getApi() {
        return retrofit.create(hector.developers.birthdaywishes.api.Api.class);
    }
}