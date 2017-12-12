package br.com.citdevelopers.alllog.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.citdevelopers.alllog.Firebase.ConfiguracaoFirebase;
import br.com.citdevelopers.alllog.Model.Usuario;
import br.com.citdevelopers.alllog.R;
import br.com.citdevelopers.alllog.Util.BaseActivity;

public class ClienteLoginActivity extends BaseActivity {
    private TextView tvClienteNovaConta;
    private TextView tv_redef_senha_cliente;

    private EditText editClienteLoginEmail, editClienteLoginSenha;
    private Button btnClienteLogin;

    private Usuario user = new Usuario();
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_login);
        editClienteLoginEmail = findViewById(R.id.edit_login_email_entregador);
        editClienteLoginSenha = findViewById(R.id.edit_login_senha_entregador);
        btnClienteLogin = findViewById(R.id.bt_cliente_conectar);
        tvClienteNovaConta = findViewById(R.id.tv_nova_conta_cliente);
        tv_redef_senha_cliente = findViewById(R.id.tv_redef_senha);


        tv_redef_senha_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialogRecuperarSenhaCliente();
            }
        });


        tvClienteNovaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClienteLoginActivity.this, CadastroClienteAcvitiy.class));
            }
        });


        btnClienteLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                user.setEmail(editClienteLoginEmail.getText().toString());
                user.setSenha(editClienteLoginSenha.getText().toString());


                validarLogin();

            }

            private void validarLogin() {


                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

                if (user.getEmail().length() == 0) {
                    snack("Digite seu email.");

                } else if (user.getSenha().length() == 0) {
                    snack("Digite sua senha.");
                } else {
                    showProgressDialog();
                    autenticacao.signInWithEmailAndPassword(
                            user.getEmail(),
                            user.getSenha()
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                snack("Login com sucesso");
                                hideProgressDialog();
                            } else {
                                hideProgressDialog();
                                snack("erro ao fazer login");
                            }

                        }
                    });
                }
            }
        });


    }

    private void dialogRecuperarSenhaCliente() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ClienteLoginActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_esqueci_senha_cliente, null);

        final Button confirmar = view.findViewById(R.id.bt_confirmar);
        Button cancelar = view.findViewById(R.id.cancelarDialog);

        builder.setView(view);
        final android.app.AlertDialog dialog = builder.create();
        final EditText RecuperarSenhaEdit = view.findViewById(R.id.edit_recuperar_senha_entregador);

        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.borda_dialog));

        dialog.show();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                if (RecuperarSenhaEdit.getText().toString().isEmpty()) {
                    snack("Digite seu e-mail");

                } else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(RecuperarSenhaEdit.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            hideProgressDialog();
                            snack("Em breve você recebera um link de redefinição de senha no e-mail informado .");
                        }
                    });
                    dialog.dismiss();

                }
            }
        });
    }
}