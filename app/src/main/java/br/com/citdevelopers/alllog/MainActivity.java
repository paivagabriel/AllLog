package br.com.citdevelopers.alllog;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.google.firebase.FirebaseApp;

import br.com.citdevelopers.alllog.util.Permissao;


public class MainActivity extends AppCompatActivity {

    final int GET_NEW_CARD = 2;
    private TextView textView;

    private String[] permissoes = new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int MAIN_CODE = 2121;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permissao.validaPermissoes(MAIN_CODE, this, permissoes);

        textView = findViewById(R.id.helloworld);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CardEditActivity.class);
                startActivityForResult(i, GET_NEW_CARD);
            }
        });
    }

    public void startMaps(View view) {
        startActivity(new Intent(this, Main2Activity.class));
    }
}
