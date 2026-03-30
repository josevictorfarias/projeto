package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText edtLogin, edtSenha;
    Button btnEntrar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = findViewById(R.id.edtLogin);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        db = new DatabaseHelper(this);

        btnEntrar.setOnClickListener(v -> {
            String login = edtLogin.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();

            if (login.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = db.buscarUsuario(login, senha);

            if (usuario == null) {
                Toast.makeText(this, "Usuário ou senha incorretos!", Toast.LENGTH_SHORT).show();
                return;
            }

            // NORMALIZA O NÍVEL DE ACESSO: tira espaços e converte tudo para minúsculo
            String nivel = usuario.getNivelAcesso();
            if (nivel != null) {
                nivel = nivel.trim().toLowerCase();
            } else {
                nivel = "";
            }

            switch (nivel) {
                case "administrador":
                    startActivity(new Intent(this, AdminMainActivity.class));
                    break;
                case "operador":
                    startActivity(new Intent(this, OperadorActivity.class));
                    break;
                default:
                    Toast.makeText(this, "Nível de acesso desconhecido!", Toast.LENGTH_SHORT).show();
                    return;
            }

            finish();
        });
    }
}
