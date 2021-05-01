package aidan.hughes.homework5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GestureDetectorCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
{

    private Button start;
    static final int REQUEST_IMAGE= 1;
    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                File storage = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
                    File image = File.createTempFile("photo", ".jpg", storage);
                    photoPath = image.getAbsolutePath();
                    Uri uri = FileProvider.getUriForFile(getApplicationContext(),"aidan.hughes.homework5.fileprovider", image);
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(cameraIntent, REQUEST_IMAGE);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
            startActivity(intent);
        }
    }
}