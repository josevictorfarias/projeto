package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class FuncionarioHerancaActivity extends AppCompatActivity {

    EditText edtNome, edtSalarioBase, edtInfoExtra;
    Spinner spinnerTipo;
    TextView txtResultado;
    Button btnCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_heranca);

        edtNome = findViewById(R.id.edtNome);
        edtSalarioBase = findViewById(R.id.edtSalarioBase);
        edtInfoExtra = findViewById(R.id.edtInfoExtra);
        spinnerTipo = findViewById(R.id.spinnerTipo);
        txtResultado = findViewById(R.id.txtResultado);
        btnCalcular = findViewById(R.id.btnCalcular);

        // Spinner com os tipos
        String[] tipos = {"Gerente", "Desenvolvedor", "Estagiário"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, tipos);
        spinnerTipo.setAdapter(adapter);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });
    }

    private void calcular() {
        String nome = edtNome.getText().toString();
        float salarioBase = Float.parseFloat(edtSalarioBase.getText().toString());
        String tipo = spinnerTipo.getSelectedItem().toString();

        Funcionario f;   // <-- corrigido

        if (tipo.equals("Gerente")) {
            float bonus = Float.parseFloat(edtInfoExtra.getText().toString());
            f = new Gerente(nome, salarioBase, bonus);
        } else if (tipo.equals("Desenvolvedor")) {
            int horas = Integer.parseInt(edtInfoExtra.getText().toString());
            f = new Desenvolvedor(nome, salarioBase, horas);
        } else {
            f = new Estagiario(nome, salarioBase);
        }

        txtResultado.setText("Salário Final: R$ " + f.calcularSalario());  // <-- corrigido
    }
}
