package com.example.finlis_20_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.security.keystore.SecureKeyImportUnavailableException;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions;
import com.google.mlkit.vision.text.devanagari.DevanagariTextRecognizerOptions;
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.nio.ByteBuffer;

public class MLKitOCRActivity extends AppCompatActivity implements View.OnClickListener{

    public final static int CAMERA_PERM_CODE = 100;
    public final static int CAMERA_REQUEST_CODE = 101;

    ImageView importImageView;
    Button importImageButton, ocrButton, copyTextButton;
    Button backToMainButton;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlkit_ocractivity);


        importImageView = findViewById(R.id.importImageView);

        importImageButton = findViewById(R.id.importImageButton);
        importImageButton.setOnClickListener(this);

//        ocrButton = findViewById(R.id.ocrButton);
//        ocrButton.setOnClickListener(this);

        copyTextButton = findViewById(R.id.copyTextButton);
        copyTextButton.setOnClickListener(this);

        resultTextView = findViewById(R.id.resultTextView);

        backToMainButton = findViewById(R.id.backToMainButton);
        backToMainButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.importImageButton:
                askCameraPermissions();
                break;

//            case R.id.ocrButton:
//                break;

            case R.id.copyTextButton:
                String scanned_text = resultTextView.getText().toString();
                copyToClipboard(scanned_text);
                startActivity(new Intent(this, AddPropertyActivity.class));


                break;

            case R.id.backToMainButton:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

    }

    //獲取相機拍照權限
    private void askCameraPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else{
            openCamera();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERM_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else {
                Toast.makeText(this, "Camera permission is required to use camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //拍照階段
    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);


    }
    //拍照後顯示相片以及辨識文字
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            importImageView.setImageBitmap(image);


//            //TODO: Firebase 策略

//            //PROCESS IMAGES
//            //TODO:1. 從一個Bitmap物件創建一個FirebaseVisionImage物件
//            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(image);
//            //TODO:2. 從FirebaseVision獲取實例
//            FirebaseVision firebaseVision = FirebaseVision.getInstance();
//            //TODO:3. 從FirebaseVision創建一個實例
//            FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();
//            //TODO:4. 創建一個Task以處理圖像
//            Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
//            //TODO:5. 若Task執行成功
//            task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
//                @Override
//                public void onSuccess(FirebaseVisionText firebaseVisionText) {
//                    String s = firebaseVisionText.getText();
//                    resultTextView.setText(s);
//
//                }
//            });
//            //TODO:6. 若Task執行失敗
//            task.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//
//                }
//            });


            //TODO: ML KIT 策略

            // When using Chinese script library
            TextRecognizer recognizer =
                    TextRecognition.getClient(new ChineseTextRecognizerOptions.Builder().build());

            InputImage MLKITimage = InputImage.fromBitmap(image, 0);

            Task<Text> OCRresult = recognizer.process(MLKITimage);

            OCRresult.addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text visionText) {
                            String resultText = visionText.getText();
                            resultTextView.setText(resultText);

                            // Task completed successfully
                            // ...
                        }
                    });

            OCRresult.addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                        }
                    });



        }
    }



    private void copyToClipboard(String text){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Data copied!", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this,"Copied to clipboard ;)", Toast.LENGTH_SHORT).show();
    }

    private void readAccountName(){

    }




    //轉正照片副函式（尚未作用於App）
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }
    /**
     * Get the angle by which an image must be rotated given the device's current
     * orientation.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, boolean isFrontFacing)
            throws CameraAccessException {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        // Get the device's sensor orientation.
        CameraManager cameraManager = (CameraManager) activity.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);

        if (isFrontFacing) {
            rotationCompensation = (sensorOrientation + rotationCompensation) % 360;
        } else { // back-facing
            rotationCompensation = (sensorOrientation - rotationCompensation + 360) % 360;
        }
        return rotationCompensation;
    }


}