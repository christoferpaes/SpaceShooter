package com.example.releaseofspaceshooter;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.releaseofspaceshooter.databinding.ActivityTitleBinding;

// TitleActivity.java
public class TitleActivity extends AppCompatActivity {

    private Button btnStartGame;
    private Button btnHighScores;
    private ImageView viewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        btnStartGame = findViewById(R.id.btnStartGame);
        btnHighScores = findViewById(R.id.btnHighScores);
        viewImage = findViewById(R.id.titleImageView);



        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        btnHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHighScores();
            }
        });
    }

    private void startGame() {
        // Start the game
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showHighScores() {
        // Show the high scores activity
        Intent intent = new Intent(this, HighScoresActivity.class);
        startActivity(intent);
    }
}