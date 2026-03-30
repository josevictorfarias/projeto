package com.example.myapplication;

public class Produto2 {

    private String nome;
    private float preco;
    private int quantidade;

    public Produto2(String nome, float preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getNome() { return nome; }
    public float getPreco() { return preco; }
    public int getQuantidade() { return quantidade; }

    public void setNome(String nome) { this.nome = nome; }
    public void setPreco(float preco) { this.preco = preco; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public float valorTotalStock() {
        return preco * quantidade;
    }

    public void adicionarProduto(int quantidade) {
        if (quantidade > 0) this.quantidade += quantidade;
    }

    // nome do enunciado
    public void quantidadeProduto(int quantidade) {
        adicionarProduto(quantidade);
    }

    public void removerProduto(int quantidade) {
        if (quantidade > 0) {
            if (quantidade > this.quantidade) {
                this.quantidade = 0;
            } else {
                this.quantidade -= quantidade;
            }
        }
    }

    @Override
    public String toString() {
        return nome + " — Preço: R$ " + String.format("%.2f", preco) +
                " — Qtde: " + quantidade +
                " — Total: R$ " + String.format("%.2f", valorTotalStock());
    }
}
