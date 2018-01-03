package com.example.namgiwon.hangul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button ok;
    Button cancle;
    Intent paintActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.main_editText);
        ok = (Button) findViewById(R.id.main_ok);
        cancle = (Button) findViewById(R.id.main_cancle);
        paintActivity = new Intent(this,PaintText.class);
        ok.setOnClickListener(bListener);
        cancle.setOnClickListener(bListener);

    }

    Button.OnClickListener bListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.main_ok :
                    String text = editText.getText().toString();
                    paintActivity.putExtra("text",text);
                    startActivity(paintActivity);
                    break;

                case R.id.main_cancle:
                    editText.setText("");
                    break;

            }
        }
    };
}
