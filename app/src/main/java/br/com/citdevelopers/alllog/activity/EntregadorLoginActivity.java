package br.com.citdevelopers.alllog.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.citdevelopers.alllog.CreditosAcvitity;
import br.com.citdevelopers.alllog.R;
import br.com.citdevelopers.alllog.firebase.ConfiguracaoFirebase;
import br.com.citdevelopers.alllog.model.Entregador;
import br.com.citdevelopers.alllog.util.BaseActivity;
import dmax.dialog.SpotsDialog;

public class EntregadorLoginActivity extends BaseActivity {

    private TextView tv_nova_conta;
    private TextView tv_redef_senha_entregador;
    private Context context;
    private EditText edit_email_login_entregador;
    private EditText edit_senha_login_entregador;

    private Button btn_conectar_entregador;
    private FirebaseAuth autenticacao;

    private Entregador entregador = new Entregador();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_entregador);
        btn_conectar_entregador = findViewById(R.id.bt_conectar_login_entregador);
        edit_email_login_entregador = findViewById(R.id.edit_login_email_entregador);
        edit_senha_login_entregador = findViewById(R.id.edit_login_senha_entregador);
        tv_nova_conta = findViewById(R.id.tv_nova_conta_entregador);
        tv_redef_senha_entregador = findViewById(R.id.tv_redef_senha);


        final android.app.AlertDialog waitingDialog = new SpotsDialog(EntregadorLoginActivity.this);




        tv_redef_senha_entregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogRecuperarSenhaEntregador();
            }
        });

        tv_nova_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EntregadorLoginActivity.this, CadastroEntregadorActivity.class));
            }
        });


        btn_conectar_entregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entregador.setEmail(edit_email_login_entregador.getText().toString());
                entregador.setSenha(edit_senha_login_entregador.getText().toString());

                ValidarLogin();
            }

            private void ValidarLogin() {

                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

                if (entregador.getEmail().length() == 0) {
                    snack("Digite seu email.");

                } else if (entregador.getSenha().length() == 0) {
                    snack("Digite sua senha.");
                } else {
                    waitingDialog.show();
                    autenticacao.signInWithEmailAndPassword(
                            entregador.getEmail(),
                            entregador.getSenha()
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                snack("Login com sucesso");
                                startActivity(new Intent(EntregadorLoginActivity.this, CreditosAcvitity.class));
                                waitingDialog.dismiss();
                            } else {
                                waitingDialog.dismiss();
                                snack("Erro ao fazer login , verifique e-mail e senha , e tente novamente");
                            }

                        }
                    });
                }
            }
        });


    }

    private void DialogRecuperarSenhaEntregador() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EntregadorLoginActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_esqueci_senha_entregador, null);

        final Button confirmar = view.findViewById(R.id.bt_confirmar);
        Button cancelar = view.findViewById(R.id.cancelarDialog);

        builder.setView(view);
        final android.app.AlertDialog dialog = builder.create();
        final EditText RecuperarSenhaEdit = view.findViewById(R.id.edit_recuperar_senha);

        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.borda_dialog));
        final android.app.AlertDialog waitingDialog = new SpotsDialog(EntregadorLoginActivity.this);
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
                            waitingDialog.show();
                            if (RecuperarSenhaEdit.getText().toString().isEmpty()) {
                                snack("Digite seu e-mail");
                                waitingDialog.dismiss();

                            } else {
                                FirebaseAuth.getInstance().sendPasswordResetEmail(RecuperarSenhaEdit.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        waitingDialog.dismiss();
                                        snack("Em breve você recebera um link de redefinição de senha no e-mail informado .");
                                    }
                                });


                }
            }
        });
    }
}