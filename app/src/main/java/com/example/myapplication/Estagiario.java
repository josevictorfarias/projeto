package com.example.myapplication;

public class Estagiario extends Funcionario {

    public Estagiario(String nome, double salarioBase) {
        // Estagiário geralmente não usa imposto igual ao Desenvolvedor
        // mas se quiser pode colocar um valor fixo:
        super(nome, salarioBase, 0); // imposto = 0%
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() * 0.8; // 20% de desconto
    }
}
