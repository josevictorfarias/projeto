package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {

    EditText edtNome, edtCodigo, edtCategoria, edtQuantidade;
    Button btnSalvar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        edtNome = findViewById(R.id.edtNomeProduto);
        edtCodigo = findViewById(R.id.edtCodigoProduto);
        edtCategoria = findViewById(R.id.edtCategoria);
        edtQuantidade = findViewById(R.id.edtQuantidade);
        btnSalvar = findViewById(R.id.btnSalvarProduto);

        db = new DatabaseHelper(this);

        btnSalvar.setOnClickListener(v -> {
            String nome = edtNome.getText().toString().trim();
            String codigo = edtCodigo.getText().toString().trim();
            String categoria = edtCategoria.getText().toString().trim();
            String qtdStr = edtQuantidade.getText().toString().trim();

            if(nome.isEmpty() || codigo.isEmpty() || categoria.isEmpty() || qtdStr.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantidade;
            try {
                quantidade = Integer.parseInt(qtdStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Quantidade inválida!", Toast.LENGTH_SHORT).show();
                return;
            }

            Produto p = new Produto();
            p.setNome(nome);
            p.setCodigo(codigo);
            p.setCategoria(categoria);
            p.setQuantidade(quantidade);

            long id = db.inserirProduto(p);

            if(id > 0) {
                Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                // Limpar campos
                edtNome.setText("");
                edtCodigo.setText("");
                edtCategoria.setText("");
                edtQuantidade.setText("");
            } else {
                Toast.makeText(this, "Erro ao cadastrar produto!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
