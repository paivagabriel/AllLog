package br.com.citdevelopers.alllog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cooltechworks.creditcarddesign.CardEditActivity;



public class MainActivity extends AppCompatActivity {

    final int GET_NEW_CARD = 2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// teste commit gabriel 03
        textView = findViewById(R.id.helloworld);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CardEditActivity.class);
                startActivityForResult(i, GET_NEW_CARD);
            }
        });

    }
}
