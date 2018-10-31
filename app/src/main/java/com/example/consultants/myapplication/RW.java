package com.example.consultants.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class RW {

    static final int READ_BLOCK_SIZE = 100;

    // write text to file
    public void WriteBtn(View v, Context context, EditText msg) {
        // add-write text into file
        try {
            FileOutputStream fileout=context.openFileOutput("mytextfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(msg.getText().toString());
            outputWriter.close();

            //display file saved message
            Toast.makeText(context, "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read text from file
    public String ReadBtn(View v, Context context) {
        String s="";
        //reading text from file
        try {
            FileInputStream fileIn=context.openFileInput("mytextfile.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];

            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            Toast.makeText(context, s,Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
