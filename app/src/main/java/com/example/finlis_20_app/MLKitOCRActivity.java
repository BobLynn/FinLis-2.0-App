package com.example.finlis_20_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class MLKitOCRActivity extends AppCompatActivity implements View.OnClickListener{

    public final static int CAMERA_PERM_CODE = 100;
    public final static int CAMERA_REQUEST_CODE = 101;

    ImageView importImageView;
    Button importImageButton, ocrButton, copyTextButton;
    Button backToMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlkit_ocractivity);


        importImageView = (ImageView) findViewById(R.id.importImageView);

        importImageButton = (Button) findViewById(R.id.importImageButton);
        importImageButton.setOnClickListener(this);

        ocrButton = (Button) findViewById(R.id.ocrButton);
        ocrButton.setOnClickListener(this);

        copyTextButton = (Button) findViewById(R.id.copyTextButton);
        copyTextButton.setOnClickListener(this);

        backToMainButton = (Button) findViewById(R.id.backToMainButton);
        backToMainButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.importImageButton:

                break;

            case R.id.ocrButton:
                break;

            case R.id.copyTextButton:
                break;

            case R.id.backToMainButton:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

    }

    private void recognizeText(InputImage image, Context context, Uri uri){

//        起手式：建立TextRecognizer的執行個體
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

//        使用檔案URI匯入圖片
        try{
            image = InputImage.fromFilePath(context, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        執行傳遞給process方法
//        「開始」
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                // Task completed successfully
                                // ...
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });
//        「結束」
    }

//    框選已辨識之文字
    private void processTextBlock(Text result){
        String resultText = result.getText();
        for (Text.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements()) {
                    String elementText = element.getText();
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                    for (Text.Symbol symbol : element.getSymbols()) {
                        String symbolText = symbol.getText();
                        Point[] symbolCornerPoints = symbol.getCornerPoints();
                        Rect symbolFrame = symbol.getBoundingBox();
                    }
                }
            }
        }
    }

}