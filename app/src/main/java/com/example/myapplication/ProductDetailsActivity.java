package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView imgProduto;
    TextView txtCodigo, txtNome, txtCategoria, txtQuantidade;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        db = new DatabaseHelper(this);

        imgProduto = findViewById(R.id.imgDetalhe);
        txtCodigo = findViewById(R.id.txtCodigoDetalhe);
        txtNome = findViewById(R.id.txtNomeDetalhe);
        txtCategoria = findViewById(R.id.txtCategoriaDetalhe);
        txtQuantidade = findViewById(R.id.txtQuantidadeDetalhe);

        int id = getIntent().getIntExtra("produto_id", -1);

        if (id == -1) {
            Toast.makeText(this, "Erro ao carregar produto!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Produto p = db.buscarProdutoPorId(id);

        if (p == null) {
            Toast.makeText(this, "Produto não encontrado!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtCodigo.setText("Código: " + p.getCodigo());
        txtNome.setText("Nome: " + p.getNome());
        txtCategoria.setText("Categoria: " + p.getCategoria());
        txtQuantidade.setText("Quantidade: " + p.getQuantidade());

        byte[] imgBytes = p.getImagem();

        if (imgBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            imgProduto.setImageBitmap(bitmap);
        } else {
            imgProduto.setImageResource(R.drawable.ic_launcher_background);
        }
    }
}
