package com.example.myapplication;

// Esta é a classe modelo para representar um item da sua tabela 'produtos'

public class Produto {
    private int id;
    private String nome;
    private String codigo;
    private String categoria;
    private int quantidade;
    private byte[] imagem; // Usamos byte[] para armazenar a imagem no SQLite (BLOB)

    // Construtor Vazio
    public Produto() {
    }

    // --- GETTERS E SETTERS ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}