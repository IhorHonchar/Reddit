package ua.honchar.reddit.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Date;

import ua.honchar.reddit.DownloadImageTask;
import ua.honchar.reddit.R;
import ua.honchar.reddit.model.Posts;

public class PagedPostAdapter extends PagedListAdapter<Posts.DataBean.Post, PagedPostAdapter.ViewHolder> {

    public static final String TAG = PagedPostAdapter.class.getSimpleName();

    private OnImageClickListener listener;

    public interface OnImageClickListener{
        public void onClick(String url);
    }

    public void setListener(OnImageClickListener listener){
        this.listener = listener;
    }


    public PagedPostAdapter() {
        super(Posts.DataBean.Post.Data.CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView author;
        private TextView title;
        private TextView comments;
        private TextView posted;
        private ImageView thumbnail;
        private DownloadImageTask downloadImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            title = itemView.findViewById(R.id.title);
            posted = itemView.findViewById(R.id.posted);
            comments = itemView.findViewById(R.id.comments);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            downloadImage = new DownloadImageTask(thumbnail);
        }

        public void bind(Posts.DataBean.Post item) {
            author.setText("u/" + item.getData().getAuthor());
            title.setText(item.getData().getTitle());

            String num_comments = item.getData().getNum_commets()+" comments";
            comments.setText(num_comments);
            posted.setText(getTimePassed(item.getData().getPosted()));
            new DownloadImageTask(thumbnail).execute(item.getData().getThumbnail_url());

            thumbnail.setOnClickListener(view -> {
                if (listener != null){
                    listener.onClick(item.getData().getThumbnail_url());
                }
            });
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

    }
}
