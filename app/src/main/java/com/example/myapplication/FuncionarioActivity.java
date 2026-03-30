package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FuncionarioActivity extends AppCompatActivity {

    EditText edtNome, edtSalario, edtImposto, edtPorcentagem;
    TextView txtResultado;
    Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);

        edtNome = findViewById(R.id.edtNome);
        edtSalario = findViewById(R.id.edtSalario);
        edtImposto = findViewById(R.id.edtImposto);
        edtPorcentagem = findViewById(R.id.edtPorcentagem);
        txtResultado = findViewById(R.id.txtResultado);

        Button btnCalcular = findViewById(R.id.btnCalcular);
        Button btnAumentar = findViewById(R.id.btnAumentar);

        btnCalcular.setOnClickListener(v -> calcular());
        btnAumentar.setOnClickListener(v -> aplicarAumento());
    }

    private void calcular() {
        String nome = edtNome.getText().toString();
        double bruto = Double.parseDouble(edtSalario.getText().toString());
        double imposto = Double.parseDouble(edtImposto.getText().toString());

        funcionario = new Funcionario(nome, bruto, imposto);

        txtResultado.setText("Funcionário cadastrado:\n\n" + funcionario);
    }

    private void aplicarAumento() {
        if (funcionario == null) {
            txtResultado.setText("Primeiro calcule o salário!");
            return;
        }

        double porcentagem = Double.parseDouble(edtPorcentagem.getText().toString());

        funcionario.salarioAumento(porcentagem);

        txtResultado.setText("Dados atualizados:\n\n" + funcionario);
    }
}
