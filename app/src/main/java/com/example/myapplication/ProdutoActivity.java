package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProdutoActivity extends AppCompatActivity {

    EditText edtNome, edtPreco, edtQuantidade, edtEntrada, edtSaida;
    TextView txtResultado;
    Produto2 produto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        edtNome = findViewById(R.id.edtNome);
        edtPreco = findViewById(R.id.edtPreco);
        edtQuantidade = findViewById(R.id.edtQuantidade);
        edtEntrada = findViewById(R.id.edtEntrada);
        edtSaida = findViewById(R.id.edtSaida);
        txtResultado = findViewById(R.id.txtResultado);

        Button btnMostrar = findViewById(R.id.btnMostrar);
        Button btnAdicionar = findViewById(R.id.btnAdicionar);
        Button btnRemover = findViewById(R.id.btnRemover);

        btnMostrar.setOnClickListener(v -> mostrarDados());
        btnAdicionar.setOnClickListener(v -> adicionarEstoque());
        btnRemover.setOnClickListener(v -> removerEstoque());
    }

    private void mostrarDados() {
        if (!criarProdutoSeNecessario()) return;
        txtResultado.setText(produto.toString());
    }

    private boolean criarProdutoSeNecessario() {
        if (produto != null) return true;

        String nome = edtNome.getText().toString().trim();
        String precoS = edtPreco.getText().toString().trim().replace(",", ".");
        String qtdS = edtQuantidade.getText().toString().trim();

        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(precoS) || TextUtils.isEmpty(qtdS)) {
            Toast.makeText(this, "Preencha Nome, Preço e Quantidade.", Toast.LENGTH_SHORT).show();
            return false;
        }

        float preco;
        int qtd;
        try {
            preco = Float.parseFloat(precoS);
            qtd = Integer.parseInt(qtdS);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Preço ou quantidade inválidos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        produto = new Produto2(nome, preco, qtd);
        return true;
    }

    private void adicionarEstoque() {
        if (!criarProdutoSeNecessario()) return;

        String s = edtEntrada.getText().toString().trim();
        if (TextUtils.isEmpty(s)) {
            Toast.makeText(this, "Digite a quantidade para entrada.", Toast.LENGTH_SHORT).show();
            return;
        }
        int qtd;
        try {
            qtd = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantidade inválida.", Toast.LENGTH_SHORT).show();
            return;
        }

        produto.adicionarProduto(qtd);
        txtResultado.setText("Entrada aplicada.\n" + produto.toString());
    }

    private void removerEstoque() {
        if (!criarProdutoSeNecessario()) return;

        String s = edtSaida.getText().toString().trim();
        if (TextUtils.isEmpty(s)) {
            Toast.makeText(this, "Digite a quantidade para saída.", Toast.LENGTH_SHORT).show();
            return;
        }
        int qtd;
        try {
            qtd = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantidade inválida.", Toast.LENGTH_SHORT).show();
            return;
        }

        produto.removerProduto(qtd);
        txtResultado.setText("Remoção aplicada.\n" + produto.toString());
    }
}
