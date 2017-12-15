package br.com.citdevelopers.alllog.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

import br.com.citdevelopers.alllog.CreditosAcvitity;
import br.com.citdevelopers.alllog.R;
import br.com.citdevelopers.alllog.firebase.ConfiguracaoFirebase;
import br.com.citdevelopers.alllog.model.Usuario;
import br.com.citdevelopers.alllog.util.BaseActivity;
import dmax.dialog.SpotsDialog;

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

        final android.app.AlertDialog waitingDialog = new SpotsDialog(ClienteLoginActivity.this);



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
                    waitingDialog.show();
                    autenticacao.signInWithEmailAndPassword(
                            user.getEmail(),
                            user.getSenha()
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                snack("Login com sucesso");
                                waitingDialog.dismiss();
                                startActivity(new Intent(ClienteLoginActivity.this, CreditosAcvitity.class));
                            } else {
                                waitingDialog.dismiss();

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
        final EditText RecuperarSenhaEdit = view.findViewById(R.id.edit_recuperar_senha);

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
                final android.app.AlertDialog waitingDialog = new SpotsDialog(ClienteLoginActivity.this);
                waitingDialog.show();
                if (RecuperarSenhaEdit.getText().toString().isEmpty()) {
                    snack("Digite seu e-mail");

                } else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(RecuperarSenhaEdit.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            waitingDialog.dismiss();
                            snack("Em breve você recebera um link de redefinição de senha no e-mail informado .");
                        }
                    });


                    dialog.dismiss();

                }
            }
        });
    }
}