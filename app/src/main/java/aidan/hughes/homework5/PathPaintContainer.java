package aidan.hughes.homework5;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class PathPaintContainer
{
    Path path;
    Paint paint;
    Drawable icon;
    Matrix matrix;
    boolean isIcon;
    private int removalID;

    public PathPaintContainer(Path path, Paint paint)
    {
        this.path = path;
        this.paint = paint;
        isIcon = false;
    }

    public PathPaintContainer(Drawable icon)
    {
        this.icon = icon;
        matrix = new Matrix();
        isIcon = true;
    }

    public Path getPath()
    {
        return path;
    }

    public Paint getPaint()
    {
        return paint;
    }

    public Drawable getIcon()
    {
        return icon;
    }

    public void setIcon(Drawable icon)
    {
        this.icon = icon;

    }

    public Matrix getMatrix()
    {
        return matrix;
    }

    public void setMatrix(Matrix matrix)
    {
        this.matrix = matrix;
    }

    public boolean isIcon()
    {
        return isIcon;
    }

    public int getRemovalID()
    {
        return removalID;
    }

    public void setRemovalID(int removalID)
    {
        this.removalID = removalID;
    }
}
