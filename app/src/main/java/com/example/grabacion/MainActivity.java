package com.example.grabacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOError;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder grabacion;
    private String archivoSalida = null;
    private Button btn_grabar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_grabar = (Button) findViewById(R.id.btn_grabar);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }

    //Métedo para grabar
    public void Recorder(View view){
        if(grabacion == null){
            archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grabacion.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            grabacion.setOutputFile(archivoSalida);

            try {
                grabacion.prepare();
                grabacion.start();
            }catch (IOException e){}

            btn_grabar.setBackgroundResource(R.drawable.rec);
            Toast.makeText(this, "Grabando....", Toast.LENGTH_SHORT).show();
        }else {
            grabacion.stop();
            grabacion.release();
            grabacion = null;
            btn_grabar.setBackgroundResource(R.drawable.stop_rec);
            Toast.makeText(this, "Grabación finalizada", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para reproducir
    public void reproducir(View view){
        MediaPlayer r = new MediaPlayer();

        try {
            r.setDataSource(archivoSalida);
            r.prepare();
        }catch (IOException e){}

        r.start();
        Toast.makeText(this, "Reproduciendo Audio", Toast.LENGTH_SHORT).show();
    }
}