package br.com.citdevelopers.alllog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.citdevelopers.alllog.firebase.ConfiguracaoFirebase;
import br.com.uol.pslibs.checkout_in_app.PSCheckout;
import br.com.uol.pslibs.checkout_in_app.wallet.util.PSCheckoutConfig;
import br.com.uol.pslibs.checkout_in_app.wallet.vo.PSWalletMainCardVO;

public class MainActivity extends AppCompatActivity {

    final int GET_NEW_CARD = 2;
    private TextView textView;
    private int imgView = 10;
    private android.support.v7.widget.Toolbar toolbar;
    private ImageView credit;
    private TextView creditValue;
    private DatabaseReference firebaseDatabase;
    private ValueEventListener valueEventListener;
    private PSWalletMainCardVO mainCard;
    private Button btGmaps;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainCard = new PSWalletMainCardVO();

        firebaseDatabase = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("usuarios/clientes/pushid/creditos");

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        btGmaps = findViewById(R.id.bt_maps);

        btGmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });

        credit = findViewById(R.id.creditos_toolbar);
        creditValue = findViewById(R.id.text_creditos_toolbar);
        textView = findViewById(R.id.helloworld);

        // creditValue.setText("Teste");

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog ok = dialogOK();
                ok.show();
            }
        });

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                creditValue.setText(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        PSCheckoutConfig psCheckoutConfig = new PSCheckoutConfig();
        psCheckoutConfig.setSellerEmail("heavythalles@hotmail.com");
        psCheckoutConfig.setSellerToken("469EA93A034646CB93F46E5C117E2E6E");
//Informe o fragment container
        psCheckoutConfig.setContainer(R.layout.activity_main);

//Inicializa apenas os recursos da carteira
        PSCheckout.initWallet(this, psCheckoutConfig);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CardEditActivity.class);
                startActivityForResult(i, GET_NEW_CARD);
            }
        });
    }


    public AlertDialog dialogOK() {
        View view = getLayoutInflater().inflate(R.layout.dialog_buy_credit, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        final TextView btOK = view.findViewById(R.id.bt_comprar);
        final ImageView preco = view.findViewById(R.id.iv_preco);
        final ImageView minus = view.findViewById(R.id.iv_minus);
        final ImageView plus = view.findViewById(R.id.iv_plus);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgView += 10;
                switch (imgView) {
                    case 10:
                        preco.setImageResource(R.drawable.compra10);
                        break;
                    case 20:
                        preco.setImageResource(R.drawable.compra20);
                        break;
                    case 30:
                        preco.setImageResource(R.drawable.compra30);
                        break;
                    case 40:
                        preco.setImageResource(R.drawable.compra40);
                        break;
                    case 50:
                        preco.setImageResource(R.drawable.compra50);
                        break;
                }
                if (imgView >= 50) {
                    // Toast.makeText(getApplicationContext(), "Erro, valor Fora da faixa! " + String.valueOf(imgView), Toast.LENGTH_SHORT).show();
                    plus.setVisibility(View.GONE);
                    imgView = 50;
                } else {
                    plus.setVisibility(View.VISIBLE);
                    minus.setVisibility(View.VISIBLE);
                }
            }


        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgView -= 10;
                switch (imgView) {
                    case 10:
                        preco.setImageResource(R.drawable.compra10);
                        break;
                    case 20:
                        preco.setImageResource(R.drawable.compra20);
                        break;
                    case 30:
                        preco.setImageResource(R.drawable.compra30);
                        break;
                    case 40:
                        preco.setImageResource(R.drawable.compra40);
                        break;
                    case 50:
                        preco.setImageResource(R.drawable.compra50);
                        break;
                }
                if (imgView <= 10) {
                    //  Toast.makeText(getApplicationContext(), "Erro, valor Fora da faixa! " + imgView, Toast.LENGTH_SHORT).show();
                    minus.setVisibility(View.GONE);
                    plus.setVisibility(View.VISIBLE);
                    imgView = 10;
                } else {
                    plus.setVisibility(View.VISIBLE);

                }
            }


        });

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//////////////
                //////////////
            }
        });
        return dialog;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseDatabase.onDisconnect();

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseDatabase.addValueEventListener(valueEventListener);
    }

}