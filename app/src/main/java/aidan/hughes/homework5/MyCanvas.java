package aidan.hughes.homework5;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Rectangle;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MyCanvas extends View {
    HashMap <Integer, PathPaintContainer> activePaths;
    Paint pathPaint;
    private boolean drawable;

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        activePaths = new HashMap<>();
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(70);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        for(PathPaintContainer ppc: activePaths.values()) {
            if (!ppc.isIcon()) {
                canvas.drawPath(ppc.getPath(), ppc.getPaint());
            } else {
                ppc.getIcon().draw(canvas);
            }
        }
        super.onDraw(canvas);
    }


    public void addPath(int id, float x, float y) {
        if (!drawable)
        {
            return;
        }
        Path path = new Path();
        path.moveTo(x, y);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(pathPaint.getColor());
        p.setStyle(pathPaint.getStyle());
        p.setStrokeWidth(pathPaint.getStrokeWidth());
        activePaths.put(id, new PathPaintContainer(path, p));
        invalidate();
    }

    public void updatePath(int id, float x, float y) {
        if (!drawable)
        {
            return;
        }
        PathPaintContainer temp = activePaths.get(id);
        if (temp != null)
        {
            Path path = activePaths.get(id).getPath();
            if(path != null){
                path.lineTo(x, y);
            }
        }

        invalidate();
    }

    public void addIcon(int id, Drawable icon)
    {
        activePaths.put(id, new PathPaintContainer(icon));
        invalidate();
    }

    public void removePath(int id) {
        if(activePaths.containsKey(id)){
            activePaths.remove(id);
        }
        invalidate();
    }

    public void clear()
    {
        activePaths.clear();
        invalidate();
    }



    public void switchColor(int color)
    {
        drawable = true;
        pathPaint.setColor(color);
    }

    public boolean isDrawable()
    {
        return drawable;
    }

}
