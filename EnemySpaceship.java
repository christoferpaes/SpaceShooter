package com.example.releaseofspaceshooter;



import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public class EnemySpaceship {
    private static final float SPEED_PIXELS_PER_SECOND = 200;

    private RectF rect;
    private float yVelocity;
    private Paint paint;
    private boolean destroyed;
    private long respawnTime;
    private long respawnDelay;

    public EnemySpaceship(float startX, float startY) {
        rect = new RectF();
        rect.left = startX;
        rect.top = startY;
        rect.right = startX + 200; // Adjust the width as needed
        rect.bottom = startY + 200; // Adjust the height as needed

        yVelocity = SPEED_PIXELS_PER_SECOND;

        paint = new Paint();
        paint.setColor(Color.RED);

        destroyed = false;
        this.respawnDelay = respawnDelay;
        respawnTime = System.currentTimeMillis() + respawnDelay;
    }

    public RectF getRect() {
        return rect;
    }

    public RectF getHitbox() {
        RectF hitbox = new RectF(rect);
        return hitbox;
    }

    public void update(long deltaTime) {
        if (destroyed && System.currentTimeMillis() >= respawnTime) {
            respawn();
        }

        if (!destroyed) {
            float distance = yVelocity * deltaTime / 1000;
            rect.top += distance;
            rect.bottom += distance;
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
        if (destroyed) {
            respawnTime = System.currentTimeMillis() + respawnDelay;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }

    private void respawn() {
        // Reset the position of the enemy spaceship
        Random random = new Random();
        float startX = random.nextInt(GameView.SCREEN_WIDTH - 200); // Adjust the range as needed
        float startY = -200; // Start from above the screen
        rect.left = startX;
        rect.top = startY;
        rect.right = startX + 200; // Adjust the width as needed
        rect.bottom = startY + 200; // Adjust the height as needed

        destroyed = false;
    }

    public static EnemySpaceship spawnRandomEnemySpaceship(float screenWidth) {
        Random random = new Random();
        float startX = random.nextFloat() * (screenWidth - 200); // Adjust the range as needed
        float startY = -200; // Start from above the screen
        return new EnemySpaceship(startX, startY);
    }
}
