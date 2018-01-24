package com.best.subtasks.PPE;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.best.R;
import com.best.subtasks.RBE.RBEInstructions;

public class PPE2Test extends AppCompatActivity {

    private Button button;
    private ToneGenerator toneGen;
    private double time;
    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ppe_test);

        button = findViewById(R.id.ppeTestButton);
        toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        time = 0;
        done = false;

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(time == 0) {
                        time = System.currentTimeMillis();
                    }
                    else {
                        time = System.currentTimeMillis() - time;
                        done = true;
                    }

                    toneGen.startTone(ToneGenerator.TONE_DTMF_1);
                    button.setPressed(true);
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    toneGen.stopTone();
                    button.setPressed(false);

                    if(done) {
                        ppe2Done();
                    }
                }
                return true;
            }
        });
    }

    // go back to instructions if we return to this activity
    @Override
    protected void onRestart() {
        finish();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        toneGen.release();
        super.onDestroy();
    }

    private void ppe2Done() {
        String ppe2Result = String.valueOf(Math.round(time / 1000));

        Bundle bundle = getIntent().getExtras();
        String date = "";
        String id = "";
        String rveResult = "";
        String pre1Result = "";
        String pre2Result = "";
        String pve1Result = "";
        String pve2Result = "";
        String ppe1Result = "";

        if (bundle != null) {
            date = (String) bundle.get("bestDate");
            id = (String) bundle.get("id");
            rveResult = (String) bundle.get("rveResult");
            pre1Result = (String) bundle.get("pre1Result");
            pre2Result = (String) bundle.get("pre2Result");
            pve1Result = (String) bundle.get("pve1Result");
            pve2Result = (String) bundle.get("pve2Result");
            ppe1Result = (String) bundle.get("ppe1Result");
        }

        Intent intent = new Intent(this, RBEInstructions.class);
        intent.putExtra("bestDate", date);
        intent.putExtra("id", id);
        intent.putExtra("rveResult", rveResult);
        intent.putExtra("pre1Result", pre1Result);
        intent.putExtra("pre2Result", pre2Result);
        intent.putExtra("pve1Result", pve1Result);
        intent.putExtra("pve2Result", pve2Result);
        intent.putExtra("ppe1result", ppe1Result);
        intent.putExtra("ppe2result", ppe2Result);
        startActivity(intent);
    }
}
