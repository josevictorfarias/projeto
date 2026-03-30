package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AdminMainActivity extends AppCompatActivity {

    Button btnProdutos, btnUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        btnProdutos = findViewById(R.id.btnProdutos);
        btnUsuarios = findViewById(R.id.btnUsuarios);

        btnProdutos.setOnClickListener(v -> startActivity(new Intent(this, ProductListActivity.class)));
        btnUsuarios.setOnClickListener(v -> startActivity(new Intent(this, UserListActivity.class)));
    }
}
