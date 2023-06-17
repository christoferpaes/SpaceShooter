package com.example.releaseofspaceshooter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class PlayerSpaceship {
    private static final float SPEED_PIXELS_PER_SECOND = 400;

    private RectF rect;
    private float xVelocity;
    private float yVelocity;
    private Paint paint;
    private boolean isShooting;

    public PlayerSpaceship(float startX, float startY) {
        rect = new RectF();
        rect.left = startX;
        rect.top = startY;
        rect.right = startX + 200; // Adjust the width as needed
        rect.bottom = startY + 200; // Adjust the height as needed

        xVelocity = 0;
        yVelocity = 0;
        isShooting = false;

        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    public RectF getRect() {
        return rect;
    }

    public RectF getHitbox() {
        // Adjust the hitbox if needed, relative to the spaceship's rect
        // For example, if you want a smaller hitbox, you can reduce the size
        // or change the coordinates to fit the desired hitbox dimensions.
        RectF hitbox = new RectF(rect);
        return hitbox;
    }

    public void update() {
        rect.left += xVelocity / 60; // 60 FPS assumption
        rect.right = rect.left + 200; // Adjust the width as needed
        rect.top += yVelocity / 60; // 60 FPS assumption
        rect.bottom = rect.top + 200; // Adjust the height as needed
    }

    public void handleTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                handleTouchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                handleTouchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                handleTouchUp(event);
                break;
        }
    }

    private void handleTouchDown(MotionEvent event) {
        // Check if double tap occurred
        if (event.getEventTime() - event.getDownTime() < 300) {
            shootProjectile();
        }
    }

    private void handleTouchMove(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        // Calculate the distance between touch position and player ship center
        float deltaX = touchX - rect.centerX();
        float deltaY = touchY - rect.centerY();

        // Adjust velocities based on touch position
        xVelocity = deltaX / 10;
        yVelocity = deltaY / 10;
    }

    private void handleTouchUp(MotionEvent event) {
        xVelocity = 0;
        yVelocity = 0;
    }

    private void shootProjectile() {
        isShooting = true;

        // Create a new projectile object based on the player's position
        float projectileX = rect.centerX(); // Adjust as needed
        float projectileY = rect.centerY(); // Adjust as needed
        Projectile projectile = new Projectile(projectileX, projectileY);

        // Add the projectile to your game's projectile list or perform any other actions
        // related to player shooting
        // Add your player shooting logic here
    }

    public void stopShooting() {
        isShooting = false;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }

    public float getX() {
        return rect.left;
    }

    public float getY() {
        return rect.top;
    }

    public boolean isShooting() {
        return isShooting;
    }
}
