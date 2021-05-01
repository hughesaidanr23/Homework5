package aidan.hughes.homework5;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

public class TouchListener implements View.OnTouchListener
{
    EditActivity activity;
    GestureDetectorCompat gestureDetectorCompat;
    private int id = 0;
    private int numActive = 0;
    private boolean started = false;

    public TouchListener(EditActivity a)
    {
        activity = a;
        gestureDetectorCompat = new GestureDetectorCompat(activity, new MyGestureListener());
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int maskedAction = motionEvent.getActionMasked();
        switch(maskedAction){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                for(int i= 0, size = motionEvent.getPointerCount(); i< size; i++){
                    activity.addPath(id + i, motionEvent.getX(i), motionEvent.getY(i));
                }
                if (activity.isDrawable()) {
                    if (!started) {
                        started = true;
                        activity.startSound();

                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for(int i = 0, size = motionEvent.getPointerCount(); i< size; i++){
                    activity.updatePath(id + i, motionEvent.getX(i), motionEvent.getY(i));

                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                if (activity.isDrawable()) {
                    if (motionEvent.getPointerCount() == 1 && started) {
                        activity.stopSound();
                        started = false;
                    }
                }
                id++;
                break;
        }
        boolean x = gestureDetectorCompat.onTouchEvent(motionEvent);
        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            activity.undo();
            Drawable icon = activity.getResources().getDrawable(R.drawable.star, null);
            icon.setBounds((int)e.getX(), (int)e.getY(), (int)e.getX() + 100, (int)e.getY() + 100);
            activity.addIcon(id, icon);
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Drawable icon = activity.getResources().getDrawable(R.drawable.hokiebird, null);
            icon.setBounds((int)e.getX(), (int)e.getY(), (int)e.getX() + 100, (int)e.getY() + 150);
            activity.addIcon(id, icon);
            super.onLongPress(e);
        }
    }

    public int removeId()
    {
        if (id == 0)
        {
            return -1;
        }
        id--;
        return id;
    }
}
