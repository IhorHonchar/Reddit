package ua.honchar.reddit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static final String BASE_URL = "https://www.reddit.com/";
    private static NetworkService mInstance;
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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
