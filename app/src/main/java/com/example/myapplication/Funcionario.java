package com.example.myapplication;

public class Funcionario {
    private String nome;
    private double salarioBase;

    // Construtor
    public Funcionario(String nome, double salarioBase, double imposto) {
        this.nome = nome;
        this.salarioBase = salarioBase;
    }

    // Getter do salário base
    public double getSalarioBase() {
        return salarioBase;
    }

    // Método que pode ser sobrescrito pelas subclasses
    public double calcularSalario() {
        return salarioBase;
    }

    // Getter do nome (opcional)
    public String getNome() {
        return nome;
    }

    public void salarioAumento(double porcentagem) {
    }
}
