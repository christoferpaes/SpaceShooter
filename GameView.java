package com.example.releaseofspaceshooter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static final int SCREEN_WIDTH = 1000;
    private static final float SCREEN_HEIGHT =1000 ;
    private GameThread gameThread;
    private ImageView background;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Initialize gameImageView using the provided context
        background = ((Activity) context).findViewById(R.id.backgroundImage);

        getHolder().addCallback(this);
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(holder, this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Not used in this example
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public void update() {
        // Update game state
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            // Clear the canvas
            canvas.drawColor(Color.BLACK);

            // Draw game objects using ImageView
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
//            background.setImageBitmap(bitmap);

        }
    }
    }


