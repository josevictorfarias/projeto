package com.example.myapplication;

public class TesteFuncionario {
    public static void main(String[] args) {

        Funcionario g = new Gerente("Carlos", 5000, 1500);
        Funcionario d = new Desenvolvedor("Ana", 3000, 10);
        Funcionario e = new Estagiario("Lucas", 2000);

        System.out.println("Gerente: " + g.calcularSalario());
        System.out.println("Desenvolvedor: " + d.calcularSalario());
        System.out.println("Estagiário: " + e.calcularSalario());
    }
}
