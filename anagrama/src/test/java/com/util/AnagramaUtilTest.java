package com.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AnagramaUtilTest {

    @Test
    void casoPadrao_abc_deveGerarSeisAnagramas() {
        List<Character> letras = Arrays.asList('a', 'b', 'c');
        List<String> anagramas = AnagramaUtil.gerarAnagramas(letras);

        Set<String> esperado = new HashSet<>(Arrays.asList("abc", "acb", "bac", "bca", "cab", "cba"));
        assertEquals(6, anagramas.size(), "Deve gerar 6 anagramas para 3 letras distintas");
        assertEquals(esperado, new HashSet<>(anagramas));
    }

    @Test
    void casoUmaLetra_deveGerarUmaStringIgual() {
        List<Character> letras = List.of('x');
        List<String> anagramas = AnagramaUtil.gerarAnagramas(letras);
        assertEquals(List.of("x"), anagramas);
    }

    @Test
    void entradaVazia_deveLancarExcecao() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                AnagramaUtil.gerarAnagramas(new ArrayList<>())
        );
        assertTrue(ex.getMessage().toLowerCase().contains("vazia"));
    }
}
