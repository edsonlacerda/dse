package com;

import java.util.Objects;
import java.util.Scanner;

/**
 * Responsável apenas por obter a entrada bruta do usuário.
 */
public final class LeitorEntrada {

    private static final String INPUT = "Digite as letras (ex.: a, b, c ou abc):";

    /**
     * Lê a entrada como String única. Se args estiverem presentes, junta-os com espaço.
     * Caso contrário, lê do console após exibir um INPUT.
     */
    public String lerEntrada(final String[] args) {
        if (args != null && args.length > 0) {
            return String.join(" ", args);
        }
        System.out.println(INPUT);
        try (Scanner scanner = new Scanner(System.in)) {
            return Objects.toString(scanner.nextLine(), "");
        }
    }
}
