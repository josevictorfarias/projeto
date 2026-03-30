package com.example.myapplication;

public class Desenvolvedor extends Funcionario {

    private static final double imposto = 0.15; // valor definido

    private int horasExtras;

    public Desenvolvedor(String nome, double salarioBase, int horasExtras) {
        super(nome, salarioBase, imposto); // imposto = 0 (não usado nesse exemplo)
        this.horasExtras = horasExtras;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() + (horasExtras * 50);
    }

    public int getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(int horasExtras) {
        this.horasExtras = horasExtras;
    }
}
