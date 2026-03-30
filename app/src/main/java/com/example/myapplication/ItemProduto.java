package com.example.myapplication;

public class ItemProduto {
    private long id;
    private String nome;
    private int quantidade;
    private String categoria;
    private String imagemUri; // armazena URI como String

    public ItemProduto() {}

    public ItemProduto(long id, String nome, int quantidade, String categoria, String imagemUri) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.imagemUri = imagemUri;
    }

    // getters e setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getImagemUri() { return imagemUri; }
    public void setImagemUri(String imagemUri) { this.imagemUri = imagemUri; }
}
