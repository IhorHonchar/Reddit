package ua.honchar.reddit.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ua.honchar.reddit.model.Posts;

public interface RedditApi {
    @GET("top.json")
    Call<Posts> getPosts(@Query("after") String name, @Query("limit") int limit);
}
