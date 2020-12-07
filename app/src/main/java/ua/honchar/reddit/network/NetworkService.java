package ua.honchar.reddit.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.honchar.reddit.BuildConfig;

public class NetworkService {
    private static final String BASE_URL = "https://www.reddit.com/";
    private static NetworkService mInstance;
    private Retrofit mRetrofit;

    private NetworkService() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (!BuildConfig.BUILD_TYPE.contains("release")) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        OkHttpClient client = builder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null){
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public RedditApi getApi(){
        return mRetrofit.create(RedditApi.class);
    }
}
