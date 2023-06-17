package com.example.releaseofspaceshooter;
import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

class GameThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private boolean isRunning;
    private GameView gameView; // Add this member variable

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView; // Assign the GameView instance
        isRunning = false;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (isRunning) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    if (canvas != null) {
                        // Perform update and draw operations on GameView
                        gameView.update();
                        gameView.draw(canvas);
                    }
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
