package com.util;

import java.util.List;

/**
 * Utilitário (fachada) para geração de anagramas.
 * Responsável apenas por orquestrar componentes com responsabilidade única:
 * - ValidadorAnagrama: valida a entrada;
 * - NormalizadorAnagrama: normaliza caracteres para minúsculo;
 * - GeradorAnagrama: gera as permutações.
 */
public final class AnagramaUtil {

    private AnagramaUtil() { }

    /**
     * Gera todos os anagramas possíveis para a lista de letras informada.
     * Mantém a API pública existente para compatibilidade com testes e Main.
     */
    public static List<String> gerarAnagramas(List<Character> letras) {
        // 1) Validar
        ValidadorAnagrama validador = new ValidadorAnagrama();
        validador.validar(letras);

        // 2) Normalizar
        NormalizadorAnagrama normalizador = new NormalizadorAnagrama();
        List<Character> normalizado = normalizador.normalizarParaMinusculo(letras);

        // 3) Gerar
        GeradorAnagrama gerador = new GeradorAnagrama();
        return gerador.gerar(normalizado);
    }
}
