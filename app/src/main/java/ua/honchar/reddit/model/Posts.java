package ua.honchar.reddit.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Posts implements Serializable {
    @SerializedName("data")
    private DataBean dataBean;

    public DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public static class DataBean {
        @SerializedName("dist")
        private int dist;

        @SerializedName("children")
        private List<Post> posts;

        public List<Post> getPosts() {
            return posts;
        }

        public void setPosts(List<Post> posts) {
            this.posts = posts;
        }

        public int getDist() {
            return dist;
        }

        public void setDist(int dist) {
            this.dist = dist;
        }


        public static class Post {

            @SerializedName("data")
            private Data data;

            public Data getData() {
                return data;
            }

            public void setData(Data data) {
                this.data = data;
            }

            public static class Data {

                @SerializedName("author")
                private String author;

                @SerializedName("thumbnail")
                private String thumbnail_url;

                @SerializedName("num_comments")
                private int num_commets;

                @SerializedName("title")
                private String title;

                @SerializedName("created_utc")
                private long posted;

                public long getPosted() {
                    return posted;
                }

                public void setPosted(long posted) {
                    this.posted = posted;
                }

                public String getAuthor() {
                    return author;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }

                public String getThumbnail_url() {
                    return thumbnail_url;
                }

                public void setThumbnail_url(String thumbnail_url) {
                    this.thumbnail_url = thumbnail_url;
                }

                public int getNum_commets() {
                    return num_commets;
                }

                public void setNum_commets(int num_commets) {
                    this.num_commets = num_commets;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }
    }
}

