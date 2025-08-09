package com.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsável apenas por normalizar a entrada (minúsculas) sem alterar as regras de validação.
 */
public class NormalizadorAnagrama {

    /**
     * Converte todos os caracteres para minúsculo e retorna nova lista.
     * Pré-condição: a lista foi validada previamente.
     */
    public List<Character> normalizarParaMinusculo(List<Character> letras) {
        List<Character> normalizado = new ArrayList<>(letras.size());
        for (Character ch : letras) {
            normalizado.add(Character.toLowerCase(ch));
        }
        return normalizado;
    }
}
