package com.example.myapplication;

public class Gerente extends Funcionario {

    private double bonus;

    public Gerente(String nome, double salarioBase, double bonus) {
        super(nome, salarioBase, 0); // imposto = 0 (ou coloque a porcentagem que quiser)
        this.bonus = bonus;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() + bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}
