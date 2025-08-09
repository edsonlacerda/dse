package com;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Responsável apenas por imprimir a entrada, saída e erros.
 */
public final class Impressao {

    public void exibirEntrada(final List<Character> letras) {
        final String conteudo = (letras == null || letras.isEmpty())
                ? ""
                : letras.stream()
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        System.out.println("Entrada: " + conteudo);
    }

    public void exibirSaida(final List<String> anagramas) {
        System.out.println("Saída:");
        if (anagramas != null) {
            anagramas.forEach(System.out::println);
        }
    }

    public void exibirErro(final String mensagem) {
        System.err.println("Erro: " + mensagem);
    }
}
