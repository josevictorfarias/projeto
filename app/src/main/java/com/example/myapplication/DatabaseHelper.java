package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "estoque.db";
    private static final int VERSAO = 1;

    // ============================
    // TABELA USUÁRIOS
    // ============================
    private static final String TABELA_USUARIO = "usuarios";
    private static final String COL_USU_ID = "id";
    private static final String COL_USU_NOME = "nome";
    private static final String COL_USU_EMAIL = "email";
    private static final String COL_USU_LOGIN = "login";
    private static final String COL_USU_SENHA = "senha";
    private static final String COL_USU_NIVEL_ACESSO = "nivelAcesso";

    // ============================
    // TABELA PRODUTOS
    // ============================
    private static final String TABELA_PRODUTO = "produtos";
    private static final String COL_PROD_ID = "id";
    private static final String COL_PROD_CODIGO = "codigo";
    private static final String COL_PROD_NOME = "nome";
    private static final String COL_PROD_CATEGORIA = "categoria";
    private static final String COL_PROD_QTD = "quantidade";
    private static final String COL_PROD_IMAGEM = "imagem";

    public DatabaseHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cria tabela de usuários
        String sqlUsuarios = "CREATE TABLE " + TABELA_USUARIO + " (" +
                COL_USU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USU_NOME + " TEXT NOT NULL, " +
                COL_USU_EMAIL + " TEXT NOT NULL UNIQUE, " +
                COL_USU_LOGIN + " TEXT NOT NULL UNIQUE, " +
                COL_USU_SENHA + " TEXT NOT NULL, " +
                COL_USU_NIVEL_ACESSO + " TEXT NOT NULL)";
        db.execSQL(sqlUsuarios);

        // Usuários padrão
        db.execSQL("INSERT INTO " + TABELA_USUARIO +
                " (nome, email, login, senha, nivelAcesso) VALUES " +
                "('Administrador', 'admin@app.com', 'admin', '123', 'admin')");
        db.execSQL("INSERT INTO " + TABELA_USUARIO +
                " (nome, email, login, senha, nivelAcesso) VALUES " +
                "('Operador', 'operador@app.com', 'operador', '123', 'operador')");

        // Cria tabela de produtos
        String sqlProdutos = "CREATE TABLE " + TABELA_PRODUTO + " (" +
                COL_PROD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PROD_CODIGO + " TEXT NOT NULL UNIQUE, " +
                COL_PROD_NOME + " TEXT NOT NULL, " +
                COL_PROD_CATEGORIA + " TEXT NOT NULL, " +
                COL_PROD_QTD + " INTEGER NOT NULL, " +
                COL_PROD_IMAGEM + " BLOB)";
        db.execSQL(sqlProdutos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PRODUTO);
        onCreate(db);
    }

    // ============================
    // USUÁRIOS
    // ============================

    public Usuario buscarUsuario(String login, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABELA_USUARIO + " WHERE login=? AND senha=?",
                new String[]{login, senha}
        );

        if (cursor.moveToFirst()) {
            Usuario u = new Usuario();
            u.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_USU_ID)));
            u.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COL_USU_NOME)));
            u.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_USU_EMAIL)));
            u.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(COL_USU_LOGIN)));
            u.setSenha(cursor.getString(cursor.getColumnIndexOrThrow(COL_USU_SENHA)));
            u.setNivelAcesso(cursor.getString(cursor.getColumnIndexOrThrow(COL_USU_NIVEL_ACESSO)));
            cursor.close();
            return u;
        }

        cursor.close();
        return null;
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_USUARIO, null);

        if (cursor.moveToFirst()) {
            do {
                Usuario u = new Usuario();
                u.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_USU_ID)));
                u.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COL_USU_NOME)));
                u.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_USU_EMAIL)));
                u.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(COL_USU_LOGIN)));
                u.setSenha(cursor.getString(cursor.getColumnIndexOrThrow(COL_USU_SENHA)));
                u.setNivelAcesso(cursor.getString(cursor.getColumnIndexOrThrow(COL_USU_NIVEL_ACESSO)));
                lista.add(u);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public long inserirUsuario(Usuario u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USU_NOME, u.getNome());
        cv.put(COL_USU_EMAIL, u.getEmail());
        cv.put(COL_USU_LOGIN, u.getLogin());
        cv.put(COL_USU_SENHA, u.getSenha());
        cv.put(COL_USU_NIVEL_ACESSO, u.getNivelAcesso());
        return db.insert(TABELA_USUARIO, null, cv);
    }

    public int atualizarUsuario(Usuario u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USU_NOME, u.getNome());
        cv.put(COL_USU_EMAIL, u.getEmail());
        cv.put(COL_USU_LOGIN, u.getLogin());
        cv.put(COL_USU_SENHA, u.getSenha());
        cv.put(COL_USU_NIVEL_ACESSO, u.getNivelAcesso());
        return db.update(TABELA_USUARIO, cv, "id=?", new String[]{String.valueOf(u.getId())});
    }

    public int deletarUsuario(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABELA_USUARIO, "id=?", new String[]{String.valueOf(id)});
    }

    // ============================
    // PRODUTOS
    // ============================

    public long inserirProduto(Produto p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PROD_CODIGO, p.getCodigo());
        cv.put(COL_PROD_NOME, p.getNome());
        cv.put(COL_PROD_CATEGORIA, p.getCategoria());
        cv.put(COL_PROD_QTD, p.getQuantidade());
        cv.put(COL_PROD_IMAGEM, p.getImagem());
        return db.insert(TABELA_PRODUTO, null, cv);
    }

    public List<Produto> listarProdutos() {
        List<Produto> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_PRODUTO, null);

        if (cursor.moveToFirst()) {
            do {
                Produto p = new Produto();
                p.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PROD_ID)));
                p.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(COL_PROD_CODIGO)));
                p.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COL_PROD_NOME)));
                p.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(COL_PROD_CATEGORIA)));
                p.setQuantidade(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PROD_QTD)));
                p.setImagem(cursor.getBlob(cursor.getColumnIndexOrThrow(COL_PROD_IMAGEM)));
                lista.add(p);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public Produto getProdutoPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_PRODUTO + " WHERE id=?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            Produto p = new Produto();
            p.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PROD_ID)));
            p.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(COL_PROD_CODIGO)));
            p.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COL_PROD_NOME)));
            p.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(COL_PROD_CATEGORIA)));
            p.setQuantidade(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PROD_QTD)));
            p.setImagem(cursor.getBlob(cursor.getColumnIndexOrThrow(COL_PROD_IMAGEM)));
            cursor.close();
            return p;
        }

        cursor.close();
        return null;
    }

    // Alias para manter compatibilidade
    public Produto buscarProdutoPorId(int id) {
        return getProdutoPorId(id);
    }

    public int atualizarProduto(Produto p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PROD_NOME, p.getNome());
        cv.put(COL_PROD_CATEGORIA, p.getCategoria());
        cv.put(COL_PROD_QTD, p.getQuantidade());
        cv.put(COL_PROD_IMAGEM, p.getImagem());
        return db.update(TABELA_PRODUTO, cv, "id=?", new String[]{String.valueOf(p.getId())});
    }

    public int deletarProduto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABELA_PRODUTO, "id=?", new String[]{String.valueOf(id)});
    }
}
