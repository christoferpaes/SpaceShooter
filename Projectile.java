package com.example.releaseofspaceshooter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Projectile {
    private static final float SPEED_PIXELS_PER_SECOND = 800;

    private RectF rect;
    private float yVelocity;
    private Paint paint;

    public Projectile(float startX, float startY) {
        rect = new RectF();
        rect.left = startX;
        rect.top = startY;
        rect.right = startX + 20; // Adjust the width as needed
        rect.bottom = startY + 20; // Adjust the height as needed

        yVelocity = -SPEED_PIXELS_PER_SECOND;

        paint = new Paint();
        paint.setColor(Color.RED);
    }

    public RectF getRect() {
        return rect;
    }

    public void update() {
        rect.top += yVelocity / 60; // 60 FPS assumption
        rect.bottom = rect.top + 20; // Adjust the height as needed
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }
}
