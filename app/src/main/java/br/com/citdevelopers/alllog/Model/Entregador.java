package br.com.citdevelopers.alllog.Model;

import android.location.GpsStatus;

import com.google.firebase.database.DatabaseReference;

import br.com.citdevelopers.alllog.Firebase.ConfiguracaoFirebase;

/**
 * Created by Thalles on 04/12/2017.
 */

public class Entregador extends Usuario {

// atributos especificos

    private float classificacao;
    private int categoria;
    private Veiculo veiculo;

    private DatabaseReference data = ConfiguracaoFirebase.getFirebaseDatabase().child("Users/entregadores");

    /**
     * @Javadoc *Construtor vazio, para implementar somente oque for necessário
     */
    public Entregador() {

    }

    // metodos especificos


    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
//////////////////////////////////////////////

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getUid() {
        return super.getUid();
    }

    @Override
    public void setUid(String uid) {
        super.setUid(uid);
    }

    @Override
    public String getNome() {
        return super.getNome();
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getSenha() {
        return super.getSenha();
    }

    @Override
    public void setSenha(String senha) {
        super.setSenha(senha);
    }

    @Override
    public String getCartaoCred() {
        return super.getCartaoCred();
    }

    @Override
    public void setCartaoCred(String cartaoCred) {
        super.setCartaoCred(cartaoCred);
    }

    @Override
    public double getCredito() {
        return super.getCredito();
    }

    @Override
    public void setCredito(double credito) {
        super.setCredito(credito);
    }

    @Override
    public String getCpfCnpj() {
        return super.getCpfCnpj();
    }

    @Override
    public void setCpfCnpj(String cpfCnpj) {
        super.setCpfCnpj(cpfCnpj);
    }

    @Override
    public GpsStatus getGpsStatus() {
        return super.getGpsStatus();
    }

    @Override
    public void setGpsStatus(GpsStatus gpsStatus) {
        super.setGpsStatus(gpsStatus);
    }

    @Override
    public void salvarDados() {
        data.child(getId()).setValue(this);
    }

    public static DatabaseReference getConfiguracaoUsuario() {
        DatabaseReference data = ConfiguracaoFirebase.getFirebaseDatabase().child("Users/entregadores");
        return data;
    }
}
