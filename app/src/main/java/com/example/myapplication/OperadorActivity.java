package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class OperadorActivity extends AppCompatActivity {

    Button btnVerProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_main);

        btnVerProdutos = findViewById(R.id.btnVerProdutos);
        btnVerProdutos.setOnClickListener(v ->
                startActivity(new Intent(this, ProductListActivity.class)));
    }
}
