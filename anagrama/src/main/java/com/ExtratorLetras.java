package com;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsável apenas por extrair letras alfabéticas de uma String.
 * Converte para minúsculo para manter a saída idêntica ao comportamento atual do Main.
 */
public final class ExtratorLetras {

    /**
     * Extrai letras alfabéticas e retorna em minúsculo.
     */
    public List<Character> extrairLetrasMinusculas(final String texto) {
        final List<Character> letras = new ArrayList<>();
        if (texto == null || texto.isEmpty()) {
            return letras;
        }
        for (char ch : texto.toCharArray()) {
            if (Character.isLetter(ch)) {
                letras.add(Character.toLowerCase(ch));
            }
        }
        return letras;
    }
}
