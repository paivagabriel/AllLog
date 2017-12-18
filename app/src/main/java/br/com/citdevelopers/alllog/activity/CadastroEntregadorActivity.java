package br.com.citdevelopers.alllog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.rengwuxian.materialedittext.MaterialEditText;

import br.com.citdevelopers.alllog.CreditosAcvitity;
import br.com.citdevelopers.alllog.R;
import br.com.citdevelopers.alllog.firebase.ConfiguracaoFirebase;
import br.com.citdevelopers.alllog.model.Entregador;
import br.com.citdevelopers.alllog.model.Usuario;
import br.com.citdevelopers.alllog.model.Veiculo;
import br.com.citdevelopers.alllog.util.BaseActivity;
import dmax.dialog.SpotsDialog;


public class CadastroEntregadorActivity extends BaseActivity {
    private MaterialEditText editNomeEntregadorCompleto, editEntregadorEmail, editEntregadorTelefone, editEntregadorCpf, editEntregadorEndereco, editEntregadorCarga, editEntregadorPlacaVeiculo, editEntregadorModeloVeiculo, editEntregadorSenha;
    private Button btn_cadastrar_entregador;
    private FirebaseAuth autenticacao;
    private Entregador entregador = new Entregador();
    private Veiculo veiculo = new Veiculo();
    private CheckBox checkBox;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastro_entregador);



        final android.app.AlertDialog waitingDialog = new SpotsDialog(CadastroEntregadorActivity.this);



        checkBox = findViewById(R.id.checkboxPedestre);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {

                    editEntregadorModeloVeiculo.setVisibility(View.GONE);
                    editEntregadorPlacaVeiculo.setVisibility(View.GONE);
                    editEntregadorModeloVeiculo.setText("Não utiliza veiculo automotor");
                    editEntregadorPlacaVeiculo.setText("Não utiliza veiculo automotor");

                } else if (!checkBox.isChecked()) {
                    editEntregadorModeloVeiculo.setText("");
                    editEntregadorPlacaVeiculo.setText("");

                    editEntregadorModeloVeiculo.setVisibility(View.VISIBLE);
                    editEntregadorPlacaVeiculo.setVisibility(View.VISIBLE);



                }

            }
        });


        editEntregadorCarga = findViewById(R.id.edit_cadastro_carga_entregador);
        editEntregadorCpf = findViewById(R.id.edit_cadastro_cpf_entregador);
        editEntregadorEmail = findViewById(R.id.edit_cadastro_email_entregador);
        editEntregadorEndereco = findViewById(R.id.edit_cadastro_endereco_entregador);
        editNomeEntregadorCompleto = findViewById(R.id.edit_cadastro_nome_entregador);
        editEntregadorTelefone = findViewById(R.id.edit_cadastro_telefone_entregador);
        editEntregadorPlacaVeiculo = findViewById(R.id.edit_cadastro_placa_entregador);
        editEntregadorModeloVeiculo = findViewById(R.id.edit_cadastro_veiculo_modelo_entregador);
        editEntregadorSenha = findViewById(R.id.edit_cadastro_senha_entregador);
        btn_cadastrar_entregador = findViewById(R.id.btn_cadastrar_entregador);


        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(editEntregadorTelefone, simpleMaskTelefone);
        editEntregadorTelefone.addTextChangedListener(maskTelefone);


        SimpleMaskFormatter simpleMaskPlaca = new SimpleMaskFormatter("LLL-NNNN");
        MaskTextWatcher maskPlaca = new MaskTextWatcher(editEntregadorPlacaVeiculo, simpleMaskPlaca);
        editEntregadorPlacaVeiculo.addTextChangedListener(maskPlaca);


        btn_cadastrar_entregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veiculo.setCapCarga(editEntregadorCarga.getText().toString());
                entregador.setCpfCnpj(editEntregadorCpf.getText().toString());
                entregador.setEmail(editEntregadorEmail.getText().toString());
                entregador.setEndereco(editEntregadorEndereco.getText().toString());
                entregador.setNome(editNomeEntregadorCompleto.getText().toString());
                entregador.setTelefone(editEntregadorTelefone.getText().toString());
                veiculo.setPlaca(editEntregadorPlacaVeiculo.getText().toString());
                veiculo.setModelo(editEntregadorModeloVeiculo.getText().toString());
                entregador.setSenha(editEntregadorSenha.getText().toString());


                if (String.valueOf(veiculo.getCapCarga()).isEmpty() || entregador.getCpfCnpj().isEmpty() || entregador.getEmail().isEmpty()
                        || entregador.getEndereco().isEmpty() || entregador.getNome().isEmpty() || entregador.getTelefone().isEmpty()
                        || veiculo.getModelo().isEmpty() || veiculo.getPlaca().isEmpty()|| entregador.getSenha().isEmpty()) {
                    snack("Preencha os campos ...");
                } else {
                    waitingDialog.show();
                    cadastrarEntregador();
                }


            }

            private void cadastrarEntregador() {


                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.createUserWithEmailAndPassword(
                        entregador.getEmail(),
                        entregador.getSenha()


                ).addOnCompleteListener(CadastroEntregadorActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            snack("Conta criada com sucesso");
                            entregador.setId(task.getResult().getUser().getUid());
                            entregador.salvarDados();






                            waitingDialog.dismiss();
                            startActivity(new Intent(CadastroEntregadorActivity.this, CreditosAcvitity.class));
                        } else {
                            String erro = null;
                            waitingDialog.dismiss();
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                erro = "A senha deve conter letras e numeros.";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erro = "E-mail invalido";
                            } catch (FirebaseAuthUserCollisionException e) {
                                erro = "E-mail já cadastrado! Esqueceu a senha?";
                            } catch (Exception e) {
                                erro = "Erro não identificado";
                                e.printStackTrace();
                            }
                            snack(erro);
                        }
                    }
                });
            }


        });


    }
}
