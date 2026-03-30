package com.example.myapplication;

public class Funcionarioo {

    private String nome;
    private double salarioBruto;
    private double imposto;

    public Funcionarioo(String nome, double salarioBruto, double imposto) {
        this.nome = nome;
        this.salarioBruto = salarioBruto;
        this.imposto = imposto;
    }

    public double salarioLiquido() {
        return salarioBruto - imposto;
    }

    public void salarioAumento(double porcentagem) {
        salarioBruto += salarioBruto * (porcentagem / 100);
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                "\nSalário Bruto: R$ " + String.format("%.2f", salarioBruto) +
                "\nImposto: R$ " + String.format("%.2f", imposto) +
                "\nSalário Líquido: R$ " + String.format("%.2f", salarioLiquido());
    }
}
