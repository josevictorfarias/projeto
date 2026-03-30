package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ConversorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor);

        final double taxaDolar = 0.20; // 1 BRL = 0.20 USD
        final double taxaEuro = 0.18;  // 1 BRL = 0.18 EUR

        // Spinner com opções
        String[] moedas = {"Dólar", "Euro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moedas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Referenciar componentes
        EditText txtValor = findViewById(R.id.txtValor);
        Spinner spMoeda = findViewById(R.id.spMoeda);
        Button btnConversor = findViewById(R.id.btnConversor);
        TextView txtResultado = findViewById(R.id.txtResultado);

        // Aplicar adapter
        spMoeda.setAdapter(adapter);

        // Ação do botão
        btnConversor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorTexto = txtValor.getText().toString().trim();

                if (valorTexto.isEmpty()) {
                    txtResultado.setText("Digite um valor!");
                    return;
                }

                double valor = Double.parseDouble(valorTexto);
                String moedaSelecionada = spMoeda.getSelectedItem().toString();
                double resultado = 0.0;

                if (moedaSelecionada.equals("Dólar")) {
                    resultado = valor * taxaDolar;
                } else if (moedaSelecionada.equals("Euro")) {
                    resultado = valor * taxaEuro;
                }

                txtResultado.setText(String.format("Resultado: %.2f %s", resultado, moedaSelecionada));
            }
        });
    }
}
