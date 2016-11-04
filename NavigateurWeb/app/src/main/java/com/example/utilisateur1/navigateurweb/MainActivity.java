package com.example.utilisateur1.navigateurweb;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button); // The button on the FIRST activity layout (loaded in the line above)
        b.setOnClickListener(new View.OnClickListener() { // whatever happens, we make sure we call the next function on the MAIN thread
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doClickOnButton();
                    }
                });
            }
        });

        Button b2 = (Button) findViewById(R.id.button2); // The button on the FIRST activity layout (loaded in the line above)
        b2.setOnClickListener(new View.OnClickListener() { // whatever happens, we make sure we call the next function on the MAIN thread
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doClickOnButton2();
                    }
                });
            }
        });
    }
    private void doClickOnButton2() {
        Intent i = new Intent(this, SecondActivity.class);
        EditText et = (EditText) findViewById(R.id.editText);
        String url = et.getText().toString();
        i.putExtra("valueUrl", url);
        startActivity(i);

    }
    // What to do when we click?
    private void doClickOnButton() {
        EditText et = (EditText) findViewById(R.id.editText);
        String url = et.getText().toString();
        MessageDigest mdEnc = null;
        try{
            mdEnc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mdEnc.update(url.getBytes(), 0, url.length());
        String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
        System.out.println(md5);

        try {
            Log.v("log1", "log1");
            URL u = new URL(url);
            final URLConnection c = u.openConnection();
            File internal = getFilesDir();
            String test = internal.toString();
            Button b = (Button) findViewById(R.id.button);
            b.setText(test);
            final File f = new File(internal, md5);
            if (f.exists()){
                Log.v("log2", "log2");
                FileInputStream is = new FileInputStream(f);

                InputStreamReader isr = new InputStreamReader(is);

                BufferedReader br = new BufferedReader(isr);


                StringBuilder sb = new StringBuilder();
                String line;
                while( (line = br.readLine()) != null) {
                sb.append(line);
                }
                String fileString = sb.toString();

                Log.v("log72", "log2");
                TextView t = (TextView) findViewById(R.id.textView2);
                Log.v(sb.toString(), "crash");
                t.setText(Html.fromHtml(fileString));
            }
            else{
                Log.v("log3", "log3");
                //Créer le fichier
                f.createNewFile();
                Log.v("log7", "log7");
                //récupérer les données de la page via serveur
                //du coup Thread
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("log4", "log4");
                        BufferedReader br = null;
                        try {
                            Log.v("log5", "log5");
                            InputStream is = c.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            br = new BufferedReader(isr);
                            final StringBuilder sb = new StringBuilder();
                            String line = br.readLine();
                            while (line != null){
                                Log.v("log125", "log1");
                                sb.append(line);
                                line = br.readLine();
                            }
                            runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                      // System.out.println(finalInputLine);
                                        FileOutputStream fos = null;
                                        try {
                                            TextView t = (TextView) findViewById(R.id.textView2);
                                            Log.v("crash", sb.toString());
                                            t.setText(Html.fromHtml(sb.toString()));
                                            Log.v("log123", "log1");
                                            fos = new FileOutputStream(f);
                                            OutputStreamWriter fosw = new OutputStreamWriter(fos);
                                            BufferedWriter wr = new BufferedWriter(fosw);
                                            wr.write(sb.toString());
                                            Log.v("log12", "log1");

                                        } catch (FileNotFoundException e1) {
                                            e1.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });


                        }
                        catch (IOException e) {
                            Log.v("log6", "log6");
                            e.printStackTrace();
                        }


                    };
                });
                t.start();
                //paf on récupère
                //poof on met ça dans un fichier
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
