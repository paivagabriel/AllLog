package br.com.citdevelopers.alllog.util;


/**
 * Created by Thalles on 05/12/2017.
 */

public class Utils {

/*
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
                switch (imgView){
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
                if(imgView >= 50){
                    Toast.makeText(getApplicationContext(), "Erro, valor Fora da faixa!", Toast.LENGTH_SHORT).show();
                    plus.setVisibility(View.GONE);
                }else{
                    plus.setVisibility(View.VISIBLE);
                    minus.setVisibility(View.VISIBLE);

                }
            }


        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgView -= 10;
                switch (imgView){
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
                if(imgView <= 10){
                    Toast.makeText(getApplicationContext(), "Erro, valor Fora da faixa!", Toast.LENGTH_SHORT).show();
                    minus.setVisibility(View.GONE);
                    plus.setVisibility(View.VISIBLE);

                }else{
                    minus.setVisibility(View.VISIBLE);

                }
            }


        });

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CardEditActivity.class);
                startActivityForResult(i, GET_NEW_CARD);
            }
        });
        return dialog;
    }
*/
}
