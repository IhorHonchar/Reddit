package ua.honchar.reddit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ua.honchar.reddit.model.Posts;

public interface RedditApi {
    @GET("top.json")
    Call<Posts> getPosts();
}
