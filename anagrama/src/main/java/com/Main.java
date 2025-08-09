package com;

public class Main {
    public static void main(String[] args) {
        // Responsabilidade única: delegar a execução para a aplicação orquestradora
        // e definir o código de saída do processo.
        int status = new AnagramaApp().run(args);
        System.exit(status);
    }
}