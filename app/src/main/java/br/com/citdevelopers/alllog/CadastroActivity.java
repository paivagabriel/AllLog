package br.com.citdevelopers.alllog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import br.com.citdevelopers.alllog.Model.Entregador;
import br.com.citdevelopers.alllog.Model.Usuario;
import br.com.citdevelopers.alllog.util.BaseActivity;
import br.com.citdevelopers.alllog.util.Permissao;

public class CadastroActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    private RadioGroup radioGroup;
    private EditText editNome;
    private TextView pickLocal;
    private TextView mostraEndereco;
    private Button btCadastrar;
    private LatLng latLng;
    private String address;

    private GoogleApiClient googleApiClient;

    public static int PLACE_PICKER_REQUEST = 1100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        radioGroup = findViewById(R.id.rg_tipo_usuario);
        editNome = findViewById(R.id.edit_nome);
        pickLocal = findViewById(R.id.textView4);
        mostraEndereco = findViewById(R.id.textView3);
        btCadastrar = findViewById(R.id.bt_cadastrar);

        pickLocal.setOnClickListener(escolherLocal());
        btCadastrar.setOnClickListener(cadastraUsuario());

    }

    private View.OnClickListener cadastraUsuario() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editNome.getText().length() > 0) {
                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.rb_cliente:

                            Usuario usuario = new Usuario();
                            usuario.setEndereco(address);
                            usuario.setLatLng(latLng);
                            usuario.setNome(editNome.getText().toString());
                            usuario.setId(Usuario.getConfiguracaoUsuario().push().getKey());
                            usuario.salvarDados();

                            toast("Dados salvos com sucesso.");
                            finish();
                            break;
                        case R.id.rb_entregador:

                            Entregador entregador = new Entregador();
                            entregador.setEndereco(address);
                            entregador.setLatLng(latLng);
                            entregador.setNome(editNome.getText().toString());
                            entregador.setId(Entregador.getConfiguracaoUsuario().push().getKey());
                            entregador.salvarDados();

                            toast("Dados salvos com sucesso.");
                            finish();
                            break;
                    }
                } else {
                    toast("Por favor, digite o seu nome.");
                }
            }
        };
    }

    private View.OnClickListener escolherLocal() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] permissoes = {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                };

                Permissao.validaPermissoes(1, CadastroActivity.this, permissoes);

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_DENIED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED) {

                    try {
                        PlacePicker.IntentBuilder intentBuilder =
                                new PlacePicker.IntentBuilder();
                        startActivityForResult(intentBuilder.build(CadastroActivity.this), PLACE_PICKER_REQUEST);

                    } catch (Exception e) {
                        e.printStackTrace();
                        toast("Erro ao capturar localização...");
                    }

                } else {
                    toast("Você precisa autorizar o uso de GPS para enviar sua localização...");
                    hideProgressDialog();
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(this, data);
            address = place.getAddress().toString();
            latLng = place.getLatLng();
            pickLocal.setVisibility(View.INVISIBLE);
            mostraEndereco.setVisibility(View.VISIBLE);
            btCadastrar.setVisibility(View.VISIBLE);
            mostraEndereco.setText(address);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Conexão com problemas. Verifique suas conexões.", Toast.LENGTH_SHORT).show();
    }
}
