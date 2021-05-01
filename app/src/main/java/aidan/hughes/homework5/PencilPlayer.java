package aidan.hughes.homework5;

import android.content.Context;
import android.media.MediaPlayer;

public class PencilPlayer
{
    MediaPlayer mediaPlayer;
    Context context;

    public PencilPlayer(Context context)
    {
        this.context = context;

    }

    public void start()
    {
        mediaPlayer = MediaPlayer.create(context, R.raw.night);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                start();
            }
        });
        mediaPlayer.seekTo(17000);
        mediaPlayer.start();
    }

    public void stop()
    {
        mediaPlayer.stop();
    }

}
