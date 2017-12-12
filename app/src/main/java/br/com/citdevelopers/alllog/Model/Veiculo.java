package br.com.citdevelopers.alllog.Model;

import com.google.firebase.database.DatabaseReference;

import br.com.citdevelopers.alllog.Firebase.ConfiguracaoFirebase;

/**
 * Created by Thalles on 04/12/2017.
 */

public class Veiculo  extends  Entregador{

    private String TIPO[] = {"Bicicleta", "Moto", "Caminhonete", "Furgão", "Caminhão"};
    private String modelo, placa;
    private String capCarga;
    private String id;


    public Veiculo() {

    }

    public String[] getTIPO() {
        return TIPO;
    }

    public void setTIPO(String[] TIPO) {
        this.TIPO = TIPO;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCapCarga() {
        return capCarga;
    }

    public void setCapCarga(String capCarga) {
        this.capCarga = capCarga;
    }





    public void salvarDados() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
        databaseReference.child("usuarios/clientes/").child(getUid()).setValue(this);
    }
}
