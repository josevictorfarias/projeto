package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        EditText txtValorTemp = findViewById(R.id.txtValorTemp);
        Spinner spDe = findViewById(R.id.spDe);
        Spinner spPara = findViewById(R.id.spPara);
        Button btnConverterTemp = findViewById(R.id.btnConverterTemp);
        TextView txtResultadoTemp = findViewById(R.id.txtResultadoTemp);

        String[] unidades = {"Celsius", "Fahrenheit", "Kelvin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, unidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spDe.setAdapter(adapter);
        spPara.setAdapter(adapter);

        btnConverterTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorText = txtValorTemp.getText().toString().trim();

                if (valorText.isEmpty()) {
                    txtResultadoTemp.setText("Digite um valor!");
                    return;
                }

                double valor = Double.parseDouble(valorText);
                String de = spDe.getSelectedItem().toString();
                String para = spPara.getSelectedItem().toString();
                double resultado = converterTemperatura(valor, de, para);

                txtResultadoTemp.setText(
                        String.format("Resultado: %.2f %s", resultado, para)
                );
            }
        });
    }

    private double converterTemperatura(double valor, String de, String para) {
        // Primeiro: converte tudo para Celsius
        if (de.equals("Fahrenheit"))
            valor = (valor - 32) * 5 / 9;
        else if (de.equals("Kelvin"))
            valor = valor - 273.15;

        // Depois converte para o destino
        if (para.equals("Fahrenheit"))
            valor = (valor * 9 / 5) + 32;
        else if (para.equals("Kelvin"))
            valor = valor + 273.15;

        return valor;
    }
}
