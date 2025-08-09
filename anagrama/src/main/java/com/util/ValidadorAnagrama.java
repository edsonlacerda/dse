package com.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Responsável apenas por validar a entrada para geração de anagramas.
 */
public class ValidadorAnagrama {

    /**
     * Valida a lista de letras para geração de anagramas.
     * Regras:
     * - Não pode ser nula ou vazia;
     * - Não pode conter nulls;
     * - Deve conter apenas caracteres alfabéticos;
     * - Letras devem ser distintas (case-insensitive).
     *
     * @param letras lista de caracteres fornecida pelo cliente
     * @throws IllegalArgumentException em caso de violação das regras
     */
    public void validar(List<Character> letras) {
        if (letras == null || letras.isEmpty()) {
            throw new IllegalArgumentException("Entrada não pode ser vazia");
        }
        Set<Character> vistosLower = new HashSet<>();
        for (Character ch : letras) {
            if (ch == null) {
                throw new IllegalArgumentException("Entrada contém valor nulo");
            }
            char lower = Character.toLowerCase(ch);
            if (!Character.isLetter(lower)) {
                throw new IllegalArgumentException("Apenas caracteres alfabéticos são permitidos");
            }
            if (!vistosLower.add(lower)) {
                throw new IllegalArgumentException("As letras devem ser distintas");
            }
        }
    }
}
