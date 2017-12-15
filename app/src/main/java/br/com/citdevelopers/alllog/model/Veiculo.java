package br.com.citdevelopers.alllog.model;

/**
 * Created by Thalles on 04/12/2017.
 */

public class Veiculo  extends  Usuario{

    private String TIPO[] = {"Bicicleta", "Moto", "Caminhonete", "Furgão", "Caminhão"};
    private String modelo, placa;
    private String capCarga;

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
}
