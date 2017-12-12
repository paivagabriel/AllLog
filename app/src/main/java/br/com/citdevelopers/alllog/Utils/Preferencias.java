package br.com.citdevelopers.alllog.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Thalles on 17/08/2017.
 */


public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "allog.pref";
    private SharedPreferences.Editor editor;

    private final int MODE = 0;
    private final String CHAVE_IDENTIFICADOR = "idLoggedUser";
    private final String CHAVE_NUMERO_CARTAO = "numeroCatao";
    private final String CHAVE_VALIDADE_CARTAO = "validadeCartao";


    public Preferencias(Context context) {

        contexto = context;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
        editor.apply();

    }


    public void SalvarDados(String idLoggedUser, String numercoCartao, String validadeCartao) {
        editor.putString(CHAVE_IDENTIFICADOR, idLoggedUser);
        editor.putString(CHAVE_NUMERO_CARTAO, numercoCartao);
        editor.putString(CHAVE_VALIDADE_CARTAO, validadeCartao);
        editor.commit();

    }

    public String getIdentificador() {
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

}

