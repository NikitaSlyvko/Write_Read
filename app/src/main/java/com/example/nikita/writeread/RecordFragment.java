package com.example.nikita.writeread;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class RecordFragment extends android.support.v4.app.Fragment {
    private Button buttonStart;
    private Button buttonPlay;
    private Button buttonStop;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String fileName;
    private boolean isRecording;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recorder_fragment, container, false);
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/audiorecord.3gp";

        buttonStart = (Button) view.findViewById(R.id.button_recorder_start);
        buttonPlay = (Button) view.findViewById(R.id.button_recorder_play);
        buttonStop = (Button) view.findViewById(R.id.button_recororder_stop);

        buttonStart.setOnClickListener(onClickListener);
        buttonPlay.setOnClickListener(onClickListener);
        buttonStop.setOnClickListener(onClickListener);

        enableButtonCreate();
        return view;
    }


    private void enableButtonCreate() {
        buttonStart.setEnabled(true);
        buttonStop.setEnabled(false);
        buttonPlay.setEnabled(false);
    }

    private void enableButtonStart() {
        buttonStart.setEnabled(false);
        buttonStop.setEnabled(true);
        buttonPlay.setEnabled(false);
    }

    private void enableButtonStop() {
        buttonPlay.setEnabled(true);
        buttonStart.setEnabled(true);
        buttonStop.setEnabled(false);
    }

    private void Record() {
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setOutputFile(fileName);

            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
        } catch (IOException e) {
            Toast.makeText(getActivity(), "No such file", Toast.LENGTH_SHORT).show();
        }
    }

    private void Play() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "No such file", Toast.LENGTH_SHORT).show();
        }
    }

    private void Stop() {
        if(isRecording) {
            isRecording = false;
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            enableButtonStop();
        } else {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                enableButtonStop();
            } enableButtonStop();
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_recorder_start: { enableButtonStart(); Record(); } break;
                case R.id.button_recorder_play: { enableButtonStart(); Play(); } break;
                case R.id.button_recororder_stop: { Stop(); } break;
            }
        }
    };
}