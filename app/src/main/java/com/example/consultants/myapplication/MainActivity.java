package com.example.consultants.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName() + "_tag";

    private InfoDatabase infoDatabase;
    private ArrayAdapter<String> infoImageAdapter;
    private ListView tvImagen;
    private Button btn;
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    private byte[] imageData = null;
    private EditText etDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) { //Creates the database
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoDatabase = new InfoDatabase(getApplicationContext());

        tvImagen = findViewById(R.id.tvImage);
        btn = findViewById(R.id.btn);
        imageview = findViewById(R.id.iv);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        infoImageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        tvImagen.setAdapter(infoImageAdapter);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, GALLERY);
                                break;
                            case 1:
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAMERA);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageData = bitmapToByte(bitmap);
                    imageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            imageData = bitmapToByte(thumbnail);
            Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public byte[] bitmapToByte(Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //bitmap to byte[] stream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] x = stream.toByteArray();
            //close stream to save memory
            stream.close();
            return x;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onInsert(View view) { //insert directly on the database.

        etDesc = findViewById(R.id.etDesc);
        InfoImage infoImage = new InfoImage(imageData, etDesc.getText().toString(), "1");
        long rowId = infoDatabase.saveInfo(infoImage);
        //Toast.makeText(this,String.valueOf(rowId),Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onInsert: ");
    }

    public void onDeleteDB(View view) {
    }

    public void onUpdateRowDB(View view) { //Update a Row, using the key
        EditText etUpdate = findViewById(R.id.etUpdate);
        InfoImage infoImage = new InfoImage(imageData, etDesc.getText().toString(), etUpdate.getText().toString());
        int rowId = infoDatabase.updateRow(infoImage);
        //Toast.makeText(this,String.valueOf(rowId),Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onUpdateRowDB: " + String.valueOf(rowId));
    }

    public void onDisplay(View view) { //Shows the database
        infoImageAdapter.clear();
        for (InfoImage infoImage : infoDatabase.showTable()) {
            infoImageAdapter.add(infoImage.toString());
        }
    }

    public void onDeleteRowDB(View view) {
        int a = infoDatabase.deleteRow("1");
        //Log.d(TAG, "onDeleteRowDB, number of columns modified :  "+a);
    }


    public void onSaveFile(View view) {
        RW write = new RW();
        EditText etString = findViewById(R.id.etString);
        write.WriteBtn(view, getApplicationContext(), etString);
    }

    public void onReadFile(View view) {
        RW read = new RW();
        String s = read.ReadBtn(view,getApplicationContext());
        TextView textView = findViewById(R.id.tvResult);
        textView.setText(s);
    }
}
