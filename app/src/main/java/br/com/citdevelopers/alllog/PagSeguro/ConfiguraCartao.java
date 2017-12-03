package br.com.citdevelopers.alllog.PagSeguro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import br.com.citdevelopers.alllog.R;
import br.com.uol.pslibs.checkout_in_app.PSCheckout;

public class ConfiguraCartao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configura_cartao);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Fornece controle para LIB de Activity results
        PSCheckout.onActivityResult(this, requestCode, resultCode, data);//Controle Lib Activity Life Cycle
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //Android 6+ fornece controle para LIB para request de permissões
        PSCheckout.onRequestPermissionsResult(this, requestCode, permissions, grantResults);//Controle Lib Activity Life Cycle
    }

    @Override
    public void onBackPressed() {
        if (PSCheckout.onBackPressed(this)) { //Controle Lib Button back
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                PSCheckout.onHomeButtonPressed(this); //Controle Lib Home Button
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PSCheckout.onDestroy(); //Controle Lib Activity Life Cycle
    }

}