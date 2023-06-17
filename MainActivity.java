package com.example.releaseofspaceshooter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends AppCompatActivity {

    private static final float SCREEN_HEIGHT = 1000;
    private Button startButton ;


    // Game variables
    private boolean gameRunning;
    private int score;
    private float startX;
    public float startY;

    // Player spaceship
    private PlayerSpaceship playerSpaceship;

    // Enemy objects
    private CopyOnWriteArrayList<EnemySpaceship> enemySpaceships;

    // Game view
    private GameView gameView;
    private Thread gameThread;
    private TextView textView;
    private  Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.scoreTextView);
        gameView = findViewById(R.id.gameView);
        startButton = findViewById(R.id.startbutton_main);
        stopButton = findViewById(R.id.stopButton);

        // Initialize the game view
        FrameLayout gameContainer = findViewById(R.id.gameContainer);
        gameView = new GameView(MainActivity.this, null);
        gameContainer.addView(gameView);

        // Rest of the code...





    runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Start or manipulate the animation here
            }
        });


        // Example code to post a Runnable to the UI thread
        gameView.post(new Runnable() {
            @Override
            public void run() {
                // Code to be executed on the UI thread
                // Start or manipulate the animation here
                startGame1();

            }
        });


        // Start the game when the game view is clicked
        gameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame1();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startGame1() {
        if (gameRunning) {
            // Game is already running
            return;
        }
        startX = gameView.getWidth() / 2;
        startY = gameView.getHeight() / 2;

        gameRunning = true;
        score = 0;
        playerSpaceship = new PlayerSpaceship(startX, startY);
        enemySpaceships = new CopyOnWriteArrayList<>();


        // Initialize enemy spaceships and other game objects
        initializeEnemySpaceships();

        // Start the game loop
        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                gameLoop();
            }
        });
        gameThread.start();
    }

    private void initializeEnemySpaceships() {
        // Create and add enemy spaceships to the list
        // Adjust the coordinates and other parameters as needed
        enemySpaceships.add(new EnemySpaceship(100, 100));
        enemySpaceships.add(new EnemySpaceship(200, 200));
        enemySpaceships.add(new EnemySpaceship(300, 300));
        // Add more enemy spaceships as desired
    }

    private void checkCollisions() {
        RectF playerHitbox = playerSpaceship.getHitbox();

        // Check collision between player spaceship and enemy spaceships
        for (EnemySpaceship enemy : enemySpaceships) {
            RectF enemyHitbox = enemy.getHitbox();

            if (RectF.intersects(playerHitbox, enemyHitbox)) {
                // Collision detected
                // Add your collision handling logic here
            }
        }
    }

    private void playerShoot() {
        // Create a new bullet object based on the player's position
        Bullet bullet = new Bullet(playerSpaceship.getX(), playerSpaceship.getY());

        // Add the bullet to your game's bullet list or perform any other actions
        // related to player shooting
        // Add your player shooting logic here
    }

    private void updateScore() {
        int pointsPerEnemy = 10; // Define the number of points earned per destroyed enemy

        // Count the number of enemy spaceships destroyed
        int destroyedEnemies = 0;
        for (EnemySpaceship enemy : enemySpaceships) {
            if (enemy.isDestroyed()) {
                destroyedEnemies++;
            }
        }

        // Update the score based on the number of destroyed enemies
        int scoreIncrease = destroyedEnemies * pointsPerEnemy;
        score += scoreIncrease;
    }

    // Game loop for updating game state
    private void gameLoop() {
        long startTime = System.currentTimeMillis();
        long previousTime = startTime;
        long frameTime;

        // Flag to track if the player is shooting
        final boolean[] isPlayerShooting = {false};
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame1();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {;
            }
        });

        // Listener for the shoot button press/release
        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    // Player pressed the shoot button
                    isPlayerShooting[0] = true;
                } else if (action == MotionEvent.ACTION_UP) {
                    // Player released the shoot button
                    isPlayerShooting[0] = false;
                }

                return true;
            }
        });

        while (gameRunning) {
            long currentTime = System.currentTimeMillis();
            frameTime = currentTime - previousTime;
            previousTime = currentTime;

            // Update player spaceship position
            playerSpaceship.update();

            // Update enemy spaceships positions
            Iterator<EnemySpaceship> iterator = enemySpaceships.iterator();
            while (iterator.hasNext()) {
                EnemySpaceship enemy = iterator.next();
                enemy.update(frameTime);

                // Check if enemy spaceship is off the screen and remove it
                if (enemy.getRect().top > SCREEN_HEIGHT) {

                    enemySpaceships.remove(0); // Use iterator.remove() instead of enemySpaceships.remove(enemy)
                    // Respawn enemy spaceship if there are less than 3
                    if (enemySpaceships.size() < 3) {
                        enemySpaceships.add(new EnemySpaceship(0, 0)); // Adjust coordinates as needed
                    }
                }
            }



            // Check for collision between player spaceship and enemies
            checkCollisions();

            // Handle shooting mechanics
            if (isPlayerShooting[0]) {
                playerShoot();
            }

            // Update score
            updateScore();

            // Update game screen
            gameView.update();

            // Delay for smooth gameplay
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawGameObjects(Canvas canvas) {
        // Clear the canvas
        canvas.drawColor(Color.BLACK);

        // Draw player spaceship
        playerSpaceship.draw(canvas);

        // Draw enemy spaceships
        for (EnemySpaceship enemySpaceship : enemySpaceships) {
            enemySpaceship.draw(canvas);
        }

        // Draw score
        Paint scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(48);
        canvas.drawText("Score: " + score, 20, 60, scorePaint);
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        builder.setMessage("Your score: " + score);
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startGame1();
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
