package br.com.citdevelopers.alllog.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.citdevelopers.alllog.R;
import br.com.citdevelopers.alllog.firebase.ConfiguracaoFirebase;
import br.com.citdevelopers.alllog.model.Usuario;

public class PerfilActivity extends AppCompatActivity {
    private TextView tv_email;
    private FirebaseAuth auth;
    private Usuario usuario = new Usuario();
    private DatabaseReference firebaseDatabase;
    private ValueEventListener valueEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tv_email = findViewById(R.id.tv_email_perfil);
        auth = FirebaseAuth.getInstance();

        firebaseDatabase = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("usuarios/clientes"+ usuario.getUid()+ "/email");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_email.setText(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };


    }
}
