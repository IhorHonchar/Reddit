package ua.honchar.reddit.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.honchar.reddit.network.NetworkService;

public class MyPositionalDataSource extends PositionalDataSource<Posts.DataBean.Post> {

    private static final String TAG = MyPositionalDataSource.class.getSimpleName();

    private String lastPostName = "";

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Posts.DataBean.Post> callback) {
        Log.d(TAG, "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        NetworkService.getInstance()
                .getApi()
                .getPosts(null, params.requestedLoadSize)
                .enqueue(new Callback<Posts>() {
                    @Override
                    public void onResponse(Call<Posts> call, Response<Posts> response) {
                        if (response.isSuccessful()) {
                            List<Posts.DataBean.Post> result = new ArrayList<>(response.body().getDataBean().getPosts());
                            lastPostName = result.get(result.size()-1).getData().getName();
                            callback.onResult(result, 0);
                        } else
                            Log.d(TAG, "onResponse: "+response.errorBody());
                    }

                    @Override
                    public void onFailure(Call<Posts> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t);
                    }
                });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Posts.DataBean.Post> callback) {
        Log.d(TAG, "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        NetworkService.getInstance()
                .getApi()
                .getPosts(lastPostName, params.loadSize)
                .enqueue(new Callback<Posts>() {
                    @Override
                    public void onResponse(Call<Posts> call, Response<Posts> response) {
                        if (response.isSuccessful()) {
                            List<Posts.DataBean.Post> result = new ArrayList<>(response.body().getDataBean().getPosts());
                            lastPostName = result.get(result.size()-1).getData().getName();
                            callback.onResult(result);
                        } else
                            Log.d(TAG, "onResponse: "+response.errorBody());
                    }

                    @Override
                    public void onFailure(Call<Posts> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t);
                    }
                });
    }
}
