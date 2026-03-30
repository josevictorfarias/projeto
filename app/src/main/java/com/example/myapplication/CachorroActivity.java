package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;


public class CachorroActivity extends AppCompatActivity {

    Button btnMostrar;
    ImageView imgCachorro;
    TextView txtInfo;

    String url = "https://dog.ceo/api/breeds/image/random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cachorro);

        btnMostrar = findViewById(R.id.btnMostrar);
        imgCachorro = findViewById(R.id.imgCachorro);
        txtInfo = findViewById(R.id.txtInfo);

        btnMostrar.setOnClickListener(v -> carregarImagem());
    }

    private void carregarImagem() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String imagemUrl = response.getString("message");

                        // Mostrar imagem na tela
                        Picasso.get().load(imagemUrl).into(imgCachorro);
                        imgCachorro.setVisibility(View.VISIBLE);

                        txtInfo.setText("Cachorro gerado pela API!");
                        txtInfo.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Erro ao carregar imagem!", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}
