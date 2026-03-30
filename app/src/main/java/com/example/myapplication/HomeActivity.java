package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView txtFrase;
    ImageView imgFrase;
    Button btnTrocar, btnVoltarMenu;

    List<String> frases = Arrays.asList(
            "Bem-vindo ao Home!",
            "Acredite em você!",
            "Você é capaz!",
            "Sempre dê o seu melhor!",
            "Nunca desista!",
            "Com grandes poderes, vem grandes responsabilidades!"
    );

    int[] imagens = {
            R.drawable.f1,
            R.drawable.f2
    };

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtFrase = findViewById(R.id.txtFrase);
        imgFrase = findViewById(R.id.avatar);
        btnTrocar = findViewById(R.id.btnTrocar);
        btnVoltarMenu = findViewById(R.id.btnVoltarMenu);

        atualizarTela();

        btnTrocar.setOnClickListener(v -> {
            index++;
            if(index >= frases.size()) index = 0;
            atualizarTela();
        });

        btnVoltarMenu.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MenuActivity.class));
            finish();
        });
    }

    private void atualizarTela() {
        txtFrase.setText(frases.get(index));
        imgFrase.setImageResource(imagens[index % imagens.length]);
    }
}
