package ua.honchar.reddit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.honchar.reddit.model.Posts;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String POSTS_KEY = "posts";
    private static final String TAG = MainActivity.class.getSimpleName();

    private Posts posts;
    private RecyclerView recycler;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);

        if (savedInstanceState == null){
            getData();
        }else {
            posts = (Posts) savedInstanceState.getSerializable(POSTS_KEY);
            setRecyclerData(posts);
        }


    }

    private void setRecyclerData(Posts posts) {
        PostsAdapter adapter = new PostsAdapter(posts.getDataBean().getPosts());
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler.setAdapter(adapter);
        adapter.setListener(url ->{
            Intent intent = new Intent(this, ImageActivity.class);
            intent.putExtra(ImageActivity.IMAGE_URL, url);
            startActivity(intent);
        });
    }

    private void getData() {
        NetworkService.getInstance()
                .getApi()
                .getPosts()
                .enqueue(new Callback<Posts>() {
                    @Override
                    public void onResponse(Call<Posts> call, Response<Posts> response) {
                        if (response.isSuccessful()) {
                            posts = response.body();
                            setRecyclerData(posts);
                        }else {
                            Log.d(TAG, "onResponse: if isn't successful" + response.errorBody());
                            Toast.makeText(getApplicationContext(), R.string.some_problem, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Posts> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), R.string.internet_disabled, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(POSTS_KEY, posts);
    }

    @Override
    public void onRefresh() {
        refreshLayout.post(()->{
            refreshLayout.setRefreshing(true);
            getData();
            refreshLayout.setRefreshing(false);
        });
    }
}