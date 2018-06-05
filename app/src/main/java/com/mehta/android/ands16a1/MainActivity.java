package com.mehta.android.ands16a1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "ThreadingAsyncTask";
    private int mDelay = 500;

    EditText editText;
    Button btnAdd;
    Button btnDelete;
    TextView txtView;

    String fileName = "file.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
        btnAdd = (Button) findViewById(R.id.btnAddText);
        btnDelete = (Button) findViewById(R.id.btnDeleteFile);
        txtView = (TextView) findViewById(R.id.txtView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAndReadFile runner = new SaveAndReadFile();
                runner.execute(fileName,editText.getText().toString());
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                deleteFile(fileName);
                Toast.makeText(MainActivity.this, "File Deleted",Toast.LENGTH_LONG).show();
            }
        });


    }

   // AsyncTask feature
    class SaveAndReadFile extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            //mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            String text = "";
            try{

                FileOutputStream fos = openFileOutput(params[0], Context.MODE_APPEND);
                fos.write(params[1].getBytes());
                fos.close();


                FileInputStream fis = openFileInput(params[0]);
                int size = fis.available();
                byte[] buffer = new byte[size];
                fis.read(buffer);
                fis.close();
                text = new String(buffer);


            } catch ( Exception e) {
                e.printStackTrace();

            }
            return text;

        }

        @Override
        protected void onProgressUpdate(String... paramss) {
           // mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            //mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            //mImageView.setImageBitmap(result);
            txtView.setText(result);
        }

        private void sleep() {
            try {
                Thread.sleep(mDelay);
            } catch (InterruptedException e) {
                Log.e(TAG, e.toString());
            }
        }
    }
}
