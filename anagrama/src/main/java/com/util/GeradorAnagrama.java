package com.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsável apenas por gerar anagramas a partir de uma lista de letras (já normalizadas/validadas).
 */
public class GeradorAnagrama {

    /**
     * Gera todas as permutações possíveis das letras informadas.
     */
    public List<String> gerar(final List<Character> letras) {
        final List<String> resultado = new ArrayList<>();
        retroceder(letras, new boolean[letras.size()], new char[letras.size()], 0, resultado);
        return resultado;
    }

    private void retroceder(final List<Character> letras, final boolean[] usados,
                            final char[] atual, final int pos, final List<String> resultado) {
        if (pos == letras.size()) {
            resultado.add(new String(atual));
            return;
        }
        for (int i = 0; i < letras.size(); i++) {
            if (usados[i]) continue;
            usados[i] = true;
            atual[pos] = letras.get(i);
            retroceder(letras, usados, atual, pos + 1, resultado);
            usados[i] = false;
        }
    }
}
