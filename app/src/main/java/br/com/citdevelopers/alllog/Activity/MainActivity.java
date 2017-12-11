package br.com.citdevelopers.alllog.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.citdevelopers.alllog.R;

public class MainActivity extends AppCompatActivity {
    private Button btn_inicio_entregador;
    private Button btn_inicio_cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        btn_inicio_entregador = findViewById(R.id.btn_inicio_entregador);
        btn_inicio_entregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EntregadorLoginActivity.class));
            }
        });

        btn_inicio_cliente = findViewById(R.id.btn_inicio_cliente);
        btn_inicio_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ClienteLoginActivity.class));
            }
        });
    }
}
