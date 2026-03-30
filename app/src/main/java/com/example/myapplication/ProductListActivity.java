package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    RecyclerView recyclerProdutos;
    Button btnAddProduto;
    DatabaseHelper db;
    ProdutoAdapter adapter;

    String modo = "admin"; // padrão

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerProdutos = findViewById(R.id.recyclerProdutos);
        btnAddProduto = findViewById(R.id.btnAddProduto);

        db = new DatabaseHelper(this);

        recyclerProdutos.setLayoutManager(new LinearLayoutManager(this));

        // VERIFICA PERMISSÃO
        if (getIntent() != null && getIntent().hasExtra("modo"))
            modo = getIntent().getStringExtra("modo");

        // OPERADOR NÃO PODE ADICIONAR
        if (modo.equals("operador")) {
            btnAddProduto.setVisibility(View.GONE);
        } else {
            btnAddProduto.setOnClickListener(v -> {
                Intent i = new Intent(ProductListActivity.this, AddProductActivity.class);
                startActivity(i);
            });
        }

        carregarProdutos();
    }

    private void carregarProdutos() {
        List<Produto> lista = db.listarProdutos();
        adapter = new ProdutoAdapter(lista, modo);
        recyclerProdutos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProdutos();
    }
}
