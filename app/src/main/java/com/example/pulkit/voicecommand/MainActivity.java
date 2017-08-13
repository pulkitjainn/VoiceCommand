package com.example.pulkit.voicecommand;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tv ;
    ImageView sp;

    TextToSpeech tts,tts1;
    String text1="Could not open the app";
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitleColor(0x0);


        tv = (TextView) findViewById(R.id.tv);

        tts = new TextToSpeech(MainActivity.this,new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status){
                if (status==TextToSpeech.SUCCESS){
                    result = tts.setLanguage(Locale.UK);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Feature not Supported", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sp = (ImageView) findViewById(R.id.sp);


        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechInput();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        speechInput();

    }

    public void speechInput() {

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "What should I do?");


        try {
            startActivityForResult(i, 6);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(MainActivity.this, "bla bla bla", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==6 && resultCode==RESULT_OK && data!=null)
        {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            tv.setText(result.get(0));
            check();
        }

    }


    public  void check()
    {
      try
      {
          {
              Intent r = null;
              String s= tv.getText().toString();
              if (s.contains("dialler")) {
                   r = new Intent(Intent.ACTION_DIAL);
                  startActivity(r);
              } else if (s.contains("camera")) {
                   r = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  startActivity(r);
              } else if (s.contains("music")) {
                   r = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
                  startActivity(r);
              } else if (s.contains("Browser") || s.contains("Google")) {
                   r = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                  startActivity(r);
              } else if (s.contains("WhatsApp")) {
                  r = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                  startActivity(r);
               } else if (s.contains("Facebook")) {
                  r = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                 startActivity(r);
               } else if (s.contains("Instagram")) {
                  r = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                     startActivity(r);
               } else if (s.contains("Snapchat")) {
                  r = getPackageManager().getLaunchIntentForPackage("com.snapchat.android");
                  startActivity(r);
              } else if (s.contains("Chrome")) {
                  r = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                  startActivity(r);
              } else if (s.contains("Gmail")) {
                  r = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                  startActivity(r);
              } else if (s.contains("Maps")) {
                  r = getPackageManager().getLaunchIntentForPackage("com.google.android.maps");
                  startActivity(r);
              } else if (s.contains("keep")) {
                  r = getPackageManager().getLaunchIntentForPackage("com.google.android.keep");
                  startActivity(r);
              } else if (s.contains("YouTube")) {
                  r = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                  startActivity(r);
              } else if (s.contains("Truecaller")) {
                  r = getPackageManager().getLaunchIntentForPackage("com.truecaller");
                  startActivity(r);
              } else if (s.contains("Paytm")) {
                  r = getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
                  startActivity(r);
              }else if (s.contains("settings") || s.contains("setting")) {
                  r = new Intent(Settings.ACTION_SETTINGS);
                  startActivity(r);
              }else if (s.contains("calendar")) {
                  r = getPackageManager().getLaunchIntentForPackage("com.android.calendar");
                  startActivity(r);
              } else if (s.contains("contacts")||s.contains("phone book")) {
                  r=new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
                      startActivity(r);
              } else if (s.contains("close")||s.contains("bye")||s.contains("buy")){
                  MainActivity.this.finishAffinity();
              } else if (s.contains("Play Store")){
                  r = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                    startActivity(r);
              } else if (s.contains("messages")){
                  r = getPackageManager().getLaunchIntentForPackage("com.android.mms");
                  startActivity(r);
              }
              else{
                //  pname();
                  //tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
              }
          }
      }catch (Exception e){
         tts.speak(text1,TextToSpeech.QUEUE_FLUSH,null);
      }
    }

}