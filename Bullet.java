package com.example.releaseofspaceshooter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Bullet {
    private static final float SPEED = 10.0f; // Speed of the bullet
    private static final float SIZE = 10.0f; // Size of the bullet

    private float x; // X-coordinate of the bullet
    private float y; // Y-coordinate of the bullet
    private RectF rect; // Rectangular bounds of the bullet
    private Paint paint; // Paint object for drawing the bullet
    private float speed;


    public Bullet(float x, float y) {
        this.x = x;
        this.y = y;

        rect = new RectF();
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }
    public void update() {
        // Update bullet position based on speed
        y -= speed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void draw(Canvas canvas) {
        // Draw the bullet
        canvas.drawRect(rect, paint);
    }

    public RectF getRect() {
        return rect;
    }
}