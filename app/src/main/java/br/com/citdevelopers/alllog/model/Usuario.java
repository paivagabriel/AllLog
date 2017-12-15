package br.com.citdevelopers.alllog.model;

import android.location.GpsStatus;

import com.google.firebase.database.DatabaseReference;

import br.com.citdevelopers.alllog.firebase.ConfiguracaoFirebase;

/**
 * Created by Thalles on 04/12/2017.
 */

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private String cartaoCred;
    private String id;
    private String uid;





    private String endereco;
    private String telefone;
    private double credito;
    private String cpfCnpj;
    private GpsStatus gpsStatus;


    /**
     * @Javadoc *Construtor vazio, para implementar somente oque for necessário
     */
    public Usuario() {

    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }




    public void salvarDados() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
        databaseReference.child("usuarios/clientes/").child(getId()).setValue(this);
    }

    public static DatabaseReference getConfiguracaoUsuario() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
        return databaseReference.child("usuarios/clientes");
    }



}
