package com;

import com.util.AnagramaUtil;

import java.util.List;

/**
 * Orquestra o fluxo da aplicação utilizando classes com responsabilidade única.
 */
public class AnagramaApp {

    private final LeitorEntrada leitorEntrada;
    private final ExtratorLetras extratorLetras;
    private final Impressao impressao;

    public AnagramaApp() {
        this(new LeitorEntrada(), new ExtratorLetras(), new Impressao());
    }

    public AnagramaApp(final LeitorEntrada leitorEntrada,
                       final ExtratorLetras extratorLetras,
                       final Impressao impressao) {
        this.leitorEntrada = leitorEntrada;
        this.extratorLetras = extratorLetras;
        this.impressao = impressao;
    }

    /**
     * Executa o fluxo principal.
     * @return 0 em caso de sucesso; 1 em caso de erro de validação
     */
    public int run(String[] args) {
        final String raw = leitorEntrada.lerEntrada(args);
        final List<Character> letras = extratorLetras.extrairLetrasMinusculas(raw);
        try {
            final List<String> anagramas = AnagramaUtil.gerarAnagramas(letras);
            impressao.exibirEntrada(letras);
            impressao.exibirSaida(anagramas);
            return 0;
        } catch (IllegalArgumentException ex) {
            impressao.exibirErro(ex.getMessage());
            return 1;
        }
    }
}
