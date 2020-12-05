package ua.honchar.reddit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.honchar.reddit.model.Posts;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycler = findViewById(R.id.recycler);

        NetworkService.getInstance()
                .getApi()
                .getPosts()
                .enqueue(new Callback<Posts>() {
                    @Override
                    public void onResponse(Call<Posts> call, Response<Posts> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "isnt successful", Toast.LENGTH_SHORT).show();
                        }
                        PostsAdapter adapter = new PostsAdapter(response.body().getDataBean().getPosts());
                        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recycler.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<Posts> call, Throwable t) {

                    }
                });
    }
}