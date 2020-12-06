package ua.honchar.reddit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    public static final String IMAGE_URL = "image_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        if (getIntent() != null){
            String url = getIntent().getStringExtra(IMAGE_URL);
            ImageView imageView = findViewById(R.id.large_image);
            DownloadImageTask downloadImage = new DownloadImageTask(imageView);
            downloadImage.execute(url);
        }

    }
}