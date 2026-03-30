package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cep);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        EditText txtCep = findViewById(R.id.txtCep);
        Button btnBuscarCep = findViewById(R.id.btnBuscarCep);
        TextView txtResultadoCep = findViewById(R.id.txtResultadoCep);

        btnBuscarCep.setOnClickListener(v -> {
            String cep = txtCep.getText().toString().trim();

            if (cep.isEmpty() || cep.length() != 8) {
                txtResultadoCep.setText("Digite um CEP válido (8 dígitos)");
                return;
            }

            try {
                URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String linha;

                while ((linha = br.readLine()) != null) sb.append(linha);

                JSONObject json = new JSONObject(sb.toString());

                if (json.has("erro")) {
                    txtResultadoCep.setText("CEP não encontrado!");
                } else {
                    // JSON formatado "bonito"
                    String resultadoFormatado =
                            "CEP: " + json.getString("cep") +
                                    "\nRua: " + json.getString("logradouro") +
                                    "\nBairro: " + json.getString("bairro") +
                                    "\nCidade: " + json.getString("localidade") +
                                    "\nEstado: " + json.getString("uf");

                    txtResultadoCep.setText(resultadoFormatado);
                }

            } catch (Exception e) {
                txtResultadoCep.setText("Erro ao consultar. Tente novamente!");
                e.printStackTrace();
            }
        });
    }
}
