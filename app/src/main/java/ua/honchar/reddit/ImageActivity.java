package ua.honchar.reddit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageActivity extends AppCompatActivity {

    public static final String IMAGE_URL = "image_url";
    public static final int WRITE_PERMISSION_RC = 123;

    private ImageView imageView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.large_image);

        if (getIntent() != null) {
            url = getIntent().getStringExtra(IMAGE_URL);
            DownloadImageTask downloadImage = new DownloadImageTask(imageView);
            downloadImage.execute(url);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.download_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.download) {
            saveImageWithPermissionRequestIfNeeded();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveImageWithPermissionRequestIfNeeded() {
        if (isWritePermissionGranted()) {
            saveToGallery();
        } else {
            requestWritePermission();
        }
    }

    private void requestWritePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //show rationale
            new AlertDialog.Builder(this)
                    .setMessage(R.string.rationale_permission)
                    .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_RC);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_RC);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != WRITE_PERMISSION_RC) return;
        if (grantResults.length != 1) return;

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            saveToGallery();
        }else {
            new AlertDialog.Builder(this) //
                    .setMessage(R.string.get_permission_in_settings)
                    .setPositiveButton(R.string.OK, null)
                    .show();
        }
    }

    private boolean isWritePermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }


    private void saveToGallery() {
        imageView.setDrawingCacheEnabled(true);
        Bitmap b = imageView.getDrawingCache();
        MediaStore.Images.Media.insertImage(getContentResolver(), b, String.valueOf(R.string.save_image_title), R.string.save_image_description + url);
        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
    }
}