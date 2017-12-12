package br.com.citdevelopers.alllog.model;

import android.location.GpsStatus;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;

import br.com.citdevelopers.alllog.firebase.ConfiguracaoFirebase;


/**
 * Created by Thalles on 04/12/2017.
 */

public class Usuario {

    private String nome, email, senha, endereco, cartaoCred, id, uid;
    private double credito;
    private String cpfCnpj;
    private GpsStatus gpsStatus;
    private LatLng latLng;

    private DatabaseReference data = ConfiguracaoFirebase.getFirebaseDatabase().child("Users/clientes");

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @Javadoc *Construtor vazio, para implementar somente oque for necessário
     */
    public Usuario() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCartaoCred() {
        return cartaoCred;
    }

    public void setCartaoCred(String cartaoCred) {
        this.cartaoCred = cartaoCred;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public GpsStatus getGpsStatus() {
        return gpsStatus;
    }

    public void setGpsStatus(GpsStatus gpsStatus) {
        this.gpsStatus = gpsStatus;
    }


    public void salvarDados() {
        data.child(id).setValue(this);
    }

    public static DatabaseReference getConfiguracaoUsuario() {
        DatabaseReference data = ConfiguracaoFirebase.getFirebaseDatabase().child("Users/clientes");
        return data;
    }

}
