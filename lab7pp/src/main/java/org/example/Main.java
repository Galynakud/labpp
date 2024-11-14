package org.example;
import bank.BankSystem;

public class Main {
    private static BankSystem bankSystem;

    public static void main(String[] args) {
        bankSystem = new BankSystem();
        bankSystem.run();
    }

    public static void setBankSystem(BankSystem bankSystem) {
        Main.bankSystem = bankSystem;
    }
}