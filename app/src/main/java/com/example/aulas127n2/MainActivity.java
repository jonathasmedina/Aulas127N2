package com.example.aulas127n2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText edEmail, edSenha;
    Button btLogar;
    TextView tv1Criar;
    FirebaseAuth mAuthLoga;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edEmail = findViewById(R.id.editTextEmail);
        edSenha = findViewById(R.id.editTextSenha);
        btLogar = findViewById(R.id.buttonLogar);
        tv1Criar = findViewById(R.id.textViewCriarUsuario);
        progressBar  = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);


        mAuthLoga = FirebaseAuth.getInstance();

        tv1Criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        MainActivity.this,
                        MainActivityCriarUsuario.class
                );
                startActivity(i);
            }
        });

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email = edEmail.getText().toString();
                String senha = edSenha.getText().toString();

                if(logar()){
                    mAuthLoga.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent i = new Intent(
                                        MainActivity.this,
                                        MainActivityLogado.class
                                );
                                startActivity(i);
                            }

                            //btLogar.setEnabled(false);

                            else{
                                Toast.makeText(MainActivity.this, "Erro ao logar", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                }

                // Toast.makeText(getApplicationContext(),"O e-mail digitado foi: " + email +" e a senha digitada foi: " + senha,Toast.LENGTH_LONG).show();

            }
        });

    }

    private boolean logar() {
        if(edEmail.getText().toString().trim().equals("")||
                !Patterns.EMAIL_ADDRESS.matcher(edEmail.getText().toString()).matches()){
            edEmail.setError("Preencha.");
            edEmail.requestFocus();
            return false;
        }
        if(edSenha.getText().toString().trim().equals("")){
            edSenha.setError("Preencha.");
            edSenha.requestFocus();
            return false;
        }
        return true;
    }

}