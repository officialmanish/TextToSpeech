    package com.example.texttospeech;

    import android.speech.tts.TextToSpeech;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.SeekBar;

    import java.util.Locale;

    public class MainActivity extends AppCompatActivity {
        private TextToSpeech mTTS;
        private EditText mEditText;
        private SeekBar mSeekBarPitch;
        private SeekBar mSeekBarSpeed;
        private Button mButtonSpeak;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mTTS =new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {

                        int result = mTTS.setLanguage(Locale.ENGLISH);

                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTs", "Language not Supported");
                        } else {
                            mButtonSpeak.setEnabled(true);
                        }
                        } else{
                            Log.e("TTs", "Language not Supported");
                        }
                    }
            });
            mEditText= findViewById(R.id.edit_text);
            mSeekBarPitch= findViewById(R.id.seek_bar_pitch);
            mSeekBarPitch= findViewById(R.id.seek_bar_speed);

            mButtonSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    speak();
                }
            });
        }
        private void speak(){
            String text=mEditText.getText().toString();
            float pitch= (float)mSeekBarPitch.getProgress() /50;
            if (pitch < 0.1) pitch = 0.1f;
            float speed= (float)mSeekBarSpeed.getProgress() /50;
            if (speed < 0.1) pitch = 0.1f;

            mTTS.setPitch(pitch);
            mTTS.setSpeechRate(speed);

            mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        }

        @Override
        protected void onDestroy() {
            if (mTTS !=null){
                mTTS.stop();
                mTTS.shutdown();
            }
            super.onDestroy();
        }
    }
