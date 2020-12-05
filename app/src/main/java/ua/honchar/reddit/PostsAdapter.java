package ua.honchar.reddit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.honchar.reddit.model.Posts;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private static final String TAG = PostsAdapter.class.getSimpleName();

    private List<Posts.DataBean.Post> list = new ArrayList<>();
    private Posts.DataBean.Post post;

    private TextView author;
    private TextView title;
    private TextView comments;
    private TextView posted;
    private ImageView thumbnail;
    private DownloadImageTask downloadImage;

    public PostsAdapter(List<Posts.DataBean.Post> posts) {
        this.list.addAll(posts);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_card, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cv;
        post = list.get(position);

        initialization(cardView);
        setData(post);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void setData(Posts.DataBean.Post post){
        author.setText("u/" + post.getData().getAuthor());
        title.setText(post.getData().getTitle());

        String num_comments = post.getData().getNum_commets()+" comments";
        comments.setText(num_comments);
        posted.setText(getTimePassed(post.getData().getPosted()));
        downloadImage.execute(post.getData().getThumbnail_url());
    }

    private String getTimePassed(long seconds){
        long millis = seconds * 1000;

        Date date = new Date(millis);
        Date currentDate = new Date();

        try {
            //in milliseconds
            long diff = date.getTime() - currentDate.getTime();

            long diffMinutes = diff/(60 *  1000) % 60;
            long diffHours = diff/(60 *  60 *  1000) % 24;
            long diffDays = diff/(24 *  60 *  60 *  1000);

            diffDays *= -1;
            diffHours *= -1;
            diffMinutes *= -1;

            if (diffDays>0){
                if (diffDays>1){
                    return diffDays+" days ago";
                }
                return diffDays+" day ago";
            }
            if (diffHours>0){
                if (diffHours>1){
                    return diffHours+" hours ago";
                }
                return diffHours+" hour ago";
            }
            if (diffMinutes>0){
                if (diffMinutes>1){
                    return diffMinutes+" minutes ago";
                }
                return diffMinutes+" minute ago";
            }

        } catch (Exception e) {
            Log.d(TAG, "getTimePassed: "+e);
        }
        return "";
    }

    private void initialization(CardView cardView){
        author = cardView.findViewById(R.id.author);
        title = cardView.findViewById(R.id.title);
        posted = cardView.findViewById(R.id.posted);
        comments = cardView.findViewById(R.id.comments);
        thumbnail = cardView.findViewById(R.id.thumbnail);
        downloadImage = new DownloadImageTask(thumbnail);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cv = itemView;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urldisplay = strings[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
