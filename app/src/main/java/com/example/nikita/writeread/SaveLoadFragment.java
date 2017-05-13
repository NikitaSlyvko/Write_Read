package com.example.nikita.writeread;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SaveLoadFragment extends Fragment implements View.OnClickListener {
    private EditText editText;
    private TextView textView;

    private Button buttonSave;
    private Button buttonView;
    private Button buttonClear;

    private Button buttonSaveSD;
    private Button buttonViewSD;
    private Button buttonClearSD;

    private FileOutputStream outputStream;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.save_load_fragment, container, false);

        editText = (EditText) view.findViewById(R.id.edit_text_write);
        textView = (TextView) view.findViewById(R.id.text_view_read);

        buttonSave = (Button) view.findViewById(R.id.button_save_text);
        buttonView = (Button) view.findViewById(R.id.button_view_text);
        buttonClear = (Button) view.findViewById(R.id.button_clear);

        buttonSaveSD = (Button) view.findViewById(R.id.button_save_text_sd);
        buttonViewSD = (Button) view.findViewById(R.id.button_view_text_sd);
        buttonClearSD = (Button) view.findViewById(R.id.button_clear_sd);

        buttonSave.setOnClickListener(this);
        buttonView.setOnClickListener(this);
        buttonSaveSD.setOnClickListener(this);
        buttonViewSD.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonClearSD.setOnClickListener(this);

        return view;
    }

    private void saveText() {
        try {
            OutputStreamWriter out = new OutputStreamWriter(getActivity()
                    .openFileOutput("myfilename.txt", Context.MODE_APPEND));
            String text = editText.getText().toString();
            out.write(text); out.write("\n"); out.close();
            Toast.makeText(getActivity(), "Text Saved!", Toast.LENGTH_LONG).show();
        } catch(java.io.IOException e) {
            Toast.makeText(getActivity(), "Sorry text could't be added", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void viewText() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = getActivity().openFileInput("myfilename.txt");
            if(inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                for(String line = null; (line = bufferedReader.readLine()) != null;) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                bufferedReader.close(); inputStreamReader.close(); inputStream.close();
                textView.setText(stringBuilder);
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(), "File not found!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTextToSD() {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath()+"/MyFiles");
        dir.mkdir();

        File file = new File(dir, "myfilename.txt");
        String text = editText.getText().toString();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(text.getBytes()); fileOutputStream.close();
            Toast.makeText(getActivity(), "Text Saved!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Sorry text could't be added", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void viewTextSD() {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/MyFiles");
        File file = new File(dir, "myfilename.txt");
        int lenght = (int) file.length();
        byte[] bytes = new byte[lenght];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes); fileInputStream.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(), "File not found!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String contents = new String(bytes);
        textView.setText(contents);
    }

    private void clear() {
        File dir = getActivity().getFilesDir();
        File file = new File(dir, "myfilename.txt");
        boolean deleted = file.delete();
        if(deleted) Toast.makeText(getActivity(), "File has been deleted!", Toast.LENGTH_LONG).show();
        else Toast.makeText(getActivity(), "File not found!", Toast.LENGTH_LONG).show();
        textView.setText("");
    }

    private void clearSD() {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath()+"/MyFiles");
        File file = new File(dir, "myfilename.txt");
        boolean deleted = file.delete();
        if(deleted) Toast.makeText(getActivity(), "File has been deleted!", Toast.LENGTH_LONG).show();
        else Toast.makeText(getActivity(), "File not found!", Toast.LENGTH_LONG).show();
        textView.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save_text: saveText(); break;
            case R.id.button_save_text_sd: saveTextToSD(); break;
            case R.id.button_view_text: viewText(); break;
            case R.id.button_view_text_sd: viewTextSD(); break;
            case R.id.button_clear: clear(); break;
            case R.id.button_clear_sd: clearSD(); break;
        }
    }
}
