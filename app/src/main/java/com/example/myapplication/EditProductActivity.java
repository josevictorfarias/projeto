package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class EditProductActivity extends AppCompatActivity {

    EditText edtNome, edtQuantidade, edtCategoria, edtCodigo;
    ImageView imgPreview;
    Button btnSelecionarImagem, btnSalvar;

    Uri imagemSelecionada = null;
    DatabaseHelper db;
    Produto produto;

    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        db = new DatabaseHelper(this);

        edtNome = findViewById(R.id.edtNomeProduto);
        edtQuantidade = findViewById(R.id.edtQuantidade);
        edtCategoria = findViewById(R.id.edtCategoria);
        edtCodigo = findViewById(R.id.edtCodigoProduto);
        imgPreview = findViewById(R.id.imgPreview);
        btnSelecionarImagem = findViewById(R.id.btnSelecionarImagem);
        btnSalvar = findViewById(R.id.btnSalvarProduto);

        int id = getIntent().getIntExtra("produto_id", -1);
        produto = db.getProdutoPorId(id);

        if (produto == null) {
            Toast.makeText(this, "Erro ao carregar produto!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        preencherCampos();

        btnSelecionarImagem.setOnClickListener(v -> abrirGaleria());
        btnSalvar.setOnClickListener(v -> atualizarProduto());
    }

    private void preencherCampos() {
        edtNome.setText(produto.getNome());
        edtQuantidade.setText(String.valueOf(produto.getQuantidade()));
        edtCategoria.setText(produto.getCategoria());
        edtCodigo.setText(produto.getCodigo());

        // Mostrar imagem armazenada em byte[]
        if (produto.getImagem() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(produto.getImagem(), 0, produto.getImagem().length);
            imgPreview.setImageBitmap(bmp);
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imagemSelecionada = data.getData();
            imgPreview.setImageURI(imagemSelecionada);
        }
    }

    // Converter imagem URI → byte[]
    private byte[] converterImagemParaBytes(Uri uri) {
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            return stream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    private void atualizarProduto() {

        produto.setNome(edtNome.getText().toString());
        produto.setQuantidade(Integer.parseInt(edtQuantidade.getText().toString()));
        produto.setCategoria(edtCategoria.getText().toString());
        produto.setCodigo(edtCodigo.getText().toString());

        if (imagemSelecionada != null) {
            produto.setImagem(converterImagemParaBytes(imagemSelecionada));
        }

        // atualizarProduto RETORNA int (linhas modificadas)
        int linhas = db.atualizarProduto(produto);

        if (linhas > 0) {
            Toast.makeText(this, "Produto atualizado!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao atualizar!", Toast.LENGTH_SHORT).show();
        }
    }
}
