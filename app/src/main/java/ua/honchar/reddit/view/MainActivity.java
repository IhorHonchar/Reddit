package ua.honchar.reddit.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.Executors;

import ua.honchar.reddit.MainThreadExecutor;
import ua.honchar.reddit.adapter.PagedPostAdapter;
import ua.honchar.reddit.R;
import ua.honchar.reddit.model.MyPositionalDataSource;
import ua.honchar.reddit.model.Posts;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String POSTS_KEY = "posts";
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recycler;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);


        setRecyclerData();

    }

    private void setRecyclerData() {

        MyPositionalDataSource dataSource = new MyPositionalDataSource();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(6)
                .build();

        PagedList<Posts.DataBean.Post> pagedList = new PagedList.Builder<>(dataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(new MainThreadExecutor())
                .build();

        PagedPostAdapter adapter = new PagedPostAdapter();
        adapter.submitList(pagedList);
        adapter.setListener(url -> {
            Intent intent = new Intent(this, ImageActivity.class);
            intent.putExtra(ImageActivity.IMAGE_URL, url);
            startActivity(intent);
        });

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh() {
        refreshLayout.post(()->{
            refreshLayout.setRefreshing(true);
            setRecyclerData();
        });
    }
}