package aidan.hughes.homework5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.media.MediaScannerConnection.*;

public class EditActivity extends AppCompatActivity
{
    private MyCanvas myCanvas;
    private TouchListener touchListener;
    private Button red;
    private Button blue;
    private Button green;
    private Button undo;
    private Button clear;
    private Button done;
    private PencilPlayer pencilPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        String imagePath = this.getIntent().getStringExtra(MediaStore.EXTRA_OUTPUT);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap image = BitmapFactory.decodeFile(imagePath, options);
        Bitmap portrait = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), portrait);

        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);
        touchListener = new TouchListener(this);
        myCanvas.setOnTouchListener(touchListener);
        myCanvas.setBackground(bitmapDrawable);

        red = findViewById(R.id.red);
        blue = findViewById(R.id.blue);
        green = findViewById(R.id.green);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvas.switchColor(Color.RED);
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvas.switchColor(Color.BLUE);
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvas.switchColor(Color.GREEN);
            }
        });


        undo = findViewById(R.id.undo);
        clear = findViewById(R.id.clear);
        done = findViewById(R.id.done);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undo();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });

        pencilPlayer = new PencilPlayer(this.getApplicationContext());
        myCanvas.setDrawingCacheEnabled(true);
    }


    public void undo()
    {
        myCanvas.removePath(touchListener.removeId());
    }

    public void clear()
    {
        myCanvas.clear();
    }

    public void done()
    {
        Bitmap b = myCanvas.getDrawingCache();
        File storageDirectory = Environment.getExternalStorageDirectory();
        File dir = new File(storageDirectory.getAbsolutePath() + "/MyPics");
        dir.mkdirs();
        File file = new File(dir, "Homework5image.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, new String[]{file.getName()}, null);
        clear();
        finish();
    }

    public void addPath(int id, float x, float y) {
        myCanvas.addPath(id, x, y);
    }

    public void updatePath(int id, float x, float y) {
        myCanvas.updatePath(id, x, y);
    }

    public void addIcon(int id, Drawable icon)
    {
        myCanvas.addIcon(id, icon);
    }

    public void startSound()
    {
        pencilPlayer.start();
    }

    public void stopSound()
    {
        pencilPlayer.stop();
    }

    public boolean isDrawable()
    {
        return myCanvas.isDrawable();
    }
}