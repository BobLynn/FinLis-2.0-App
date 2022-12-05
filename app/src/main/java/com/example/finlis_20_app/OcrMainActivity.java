package com.example.finlis_20_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OcrMainActivity extends AppCompatActivity implements View.OnClickListener{

    Button ocrMainToMenuButton, toMLKitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_main);

        ocrMainToMenuButton = (Button) findViewById(R.id.ocrMainToMenuButton);
        ocrMainToMenuButton.setOnClickListener(this);

        toMLKitButton = (Button) findViewById(R.id.toMLKitButton);
        toMLKitButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ocrMainToMenuButton:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.toMLKitButton:
                startActivity(new Intent(this, MLKitOCRActivity.class));
        }
    }
}