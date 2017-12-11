package br.com.citdevelopers.alllog.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import br.com.citdevelopers.alllog.Firebase.ConfiguracaoFirebase;
import br.com.citdevelopers.alllog.Model.Usuario;
import br.com.citdevelopers.alllog.R;
import br.com.citdevelopers.alllog.Util.BaseActivity;


public class CadastroClienteAcvitiy extends BaseActivity {
    private EditText editNomeClienteCompleto, editClienteEmail, editClienteTelefone, editClienteCpf, editClienteEndereco, editClienteSenha;
    private Button btnCadastrarCliente;

    private FirebaseAuth autenticacao;
    private DatabaseReference base;
    private Usuario user = new Usuario();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente_acvitiy);




        editNomeClienteCompleto = findViewById(R.id.edit_cadastro_nome_cliente);
        editClienteEmail = findViewById(R.id.edit_cadastro_email_cliente);
        editClienteTelefone = findViewById(R.id.edit_cadastro_telefone_cliente);
        editClienteCpf = findViewById(R.id.edit_cadastro_cpf_cliente);
        editClienteEndereco = findViewById(R.id.edit_cadastro_endereco_cliente);
        editClienteSenha = findViewById(R.id.edit_cadastro_senha_cliente);
        btnCadastrarCliente = findViewById(R.id.btn_cliente_cadastrar);


        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(editClienteTelefone, simpleMaskTelefone);
        editClienteTelefone.addTextChangedListener(maskTelefone);


        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCpf = new MaskTextWatcher(editClienteCpf, simpleMaskCpf);
        editClienteCpf.addTextChangedListener(maskCpf);







        btnCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setNome(editNomeClienteCompleto.getText().toString());
                user.setEmail(editClienteEmail.getText().toString());
                user.setSenha(editClienteSenha.getText().toString());
                user.setTelefone(editClienteTelefone.getText().toString());
                user.setCpfCnpj(editClienteCpf.getText().toString());
                user.setEndereco(editClienteEndereco.getText().toString());
                user.setCliente(true);


                if (user.getNome().isEmpty() || user.getEmail().isEmpty() ||
                        user.getSenha().isEmpty() || user.getTelefone().isEmpty() ||
                        user.getCpfCnpj().isEmpty() || user.getEndereco().isEmpty()) {
                    snack("Preencha os campos ...");
                } else {
                    showProgressDialog();
                    cadastrarCliente();
                }


            }

            private void cadastrarCliente() {
                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.createUserWithEmailAndPassword(
                        user.getEmail(),
                        user.getSenha()
                ).addOnCompleteListener(CadastroClienteAcvitiy.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroClienteAcvitiy.this, "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
                            user.setId(task.getResult().getUser().getUid());
                            user.salvarDados();
                            hideProgressDialog();
                        } else {
                            String erro = null;
                            hideProgressDialog();
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
