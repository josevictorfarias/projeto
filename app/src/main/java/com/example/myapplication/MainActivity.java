package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnGerenciarProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGerenciarProdutos = findViewById(R.id.btnGerenciarProdutos);

        btnGerenciarProdutos.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ProductListActivity.class);
            i.putExtra("modo", "admin");
            startActivity(i);
        });
    }
}
