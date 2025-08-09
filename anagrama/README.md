# Anagrama

Aplicação Java (Maven) para gerar todos os anagramas possíveis de um conjunto de letras, com responsabilidades bem separadas (entrada, extração, validação/normalização, geração e impressão).


## Fluxo do anagrama (passo a passo)

Abaixo o fluxo executado quando você roda `com.Main`:

1. Main
   - Classe: `com.Main`
   - Responsabilidade: apenas delegar a execução para `AnagramaApp` e encerrar o processo com o código de status retornado.
   - Código relevante: `int status = new AnagramaApp().run(args); System.exit(status);`

2. Orquestração
   - Classe: `com.AnagramaApp`
   - Responsabilidade: orquestrar o fluxo end‑to‑end usando componentes com responsabilidade única.
   - Passos internos:
     1) Leitura da entrada: `LeitorEntrada.lerEntrada(args)`
     2) Extração de letras: `ExtratorLetras.extrairLetrasMinusculas(raw)`
     3) Geração dos anagramas: `AnagramaUtil.gerarAnagramas(letras)`
     4) Impressão de entrada e saída: `Impressao.exibirEntrada(...)` e `Impressao.exibirSaida(...)`
     5) Tratamento de erros de validação: imprime mensagem via `Impressao.exibirErro(...)` e retorna status 1

3. Leitura da entrada
   - Classe: `com.LeitorEntrada`
   - Responsabilidade: obter a entrada bruta (String) do usuário.
   - Regras:
     - Se `args` estiverem presentes, junta todos com espaço: `String.join(" ", args)`.
     - Caso contrário, solicita no console: "Digite as letras (ex.: a, b, c ou abc):" e lê uma linha via `Scanner`.

4. Extração das letras
   - Classe: `com.ExtratorLetras`
   - Responsabilidade: extrair apenas caracteres alfabéticos da String bruta e convertê-los para minúsculo.
   - Método: `extrairLetrasMinusculas(String)` → retorna `List<Character>` contendo somente letras [a‑z], todas em minúsculo, na ordem em que aparecem.
   - Observação: entradas nulas ou vazias resultam numa lista vazia.

5. Validação e normalização
   - Fachada: `com.util.AnagramaUtil`
   - Responsabilidade: orquestrar validação, normalização e geração de anagramas mantendo API simples.
   - Componentes internos:
     - `com.util.ValidadorAnagrama`
       - Regras de validação sobre a `List<Character>`:
         - Não pode ser nula ou vazia;
         - Não pode conter `null`;
         - Deve conter apenas caracteres alfabéticos;
         - Letras devem ser distintas (case‑insensitive), ou seja, não pode haver repetição considerando minúsculas/maiúsculas.
       - Em caso de violação, lança `IllegalArgumentException` com mensagem adequada, a qual é capturada por `AnagramaApp` para impressão.
     - `com.util.NormalizadorAnagrama`
       - Converte todos os caracteres para minúsculo e retorna nova lista (pré‑condição: entrada já validada).

6. Geração das permutações (anagramas)
   - Classe: `com.util.GeradorAnagrama`
   - Responsabilidade: gerar todas as permutações possíveis da lista de letras (já normalizada/validada).
   - Estratégia: backtracking usando:
     - Vetor booleano `usados[]` para marcar letras já escolhidas;
     - Vetor `char[] atual` para construir cada permutação passo a passo;
     - Quando o índice `pos` atinge o tamanho da lista, adiciona `new String(atual)` ao resultado.
   - Complexidade: O(n! · n) no tempo e O(n) no espaço adicional (além da saída), onde n é o número de letras. Como as letras devem ser distintas, não há duplicatas na saída.

7. Impressão
   - Classe: `com.Impressao`
   - Responsabilidade: formatar e imprimir entrada, saída e erros.
   - Saídas:
     - Entrada: `Entrada: a, b, c`
     - Saída: lista de anagramas, um por linha, precedida de `Saída:`
     - Erro: `Erro: <mensagem>` (impresso em `System.err`)

8. Códigos de saída do processo
   - `0`: execução normal com sucesso
   - `1`: erro de validação (por exemplo, lista vazia, letras repetidas, caracteres inválidos)


## Exemplo de uso

- Via argumentos da linha de comando:
  - `abc` → serão extraídas as letras `[a, b, c]` e gerados 6 anagramas (abc, acb, bac, bca, cab, cba)
  - `"a b c"` (com espaços) → o extrator ignora espaços; só as letras são consideradas
- Via entrada interativa:
  1) Rode o programa sem argumentos
  2) Quando solicitado, digite algo como `a, b, c` ou `abc`

Erros comuns de exemplo:
- Repetição de letras: `aab` → `Erro: As letras devem ser distintas`
- Entrada vazia: vazio/sem letras → `Erro: Entrada não pode ser vazia`
- Caracteres não alfabéticos somente: `123` → `Erro: Apenas caracteres alfabéticos são permitidos`


## Como compilar e executar

Pré-requisitos:
- Java 21+
- Maven 3.9+

Compilar e rodar testes:

- `mvn test`

Compilar (empacotar classes):

- `mvn -q -DskipTests package`

Executar a aplicação (classe principal `com.Main`):

- Com argumentos na linha de comando:
  - `java -cp target/classes com.Main abc`
  - `java -cp target/classes com.Main A B C` (maiúsculas serão normalizadas para minúsculas)

- Interativo (sem argumentos):
  - `java -cp target/classes com.Main`
  - Em seguida, digite as letras quando solicitado.

Observação: este projeto não inclui plugin de empacotamento em "fat jar". Caso prefira um jar executável, você pode adicionar o Maven Shade Plugin ou o maven-jar-plugin com a `Main-Class` `com.Main`.


## Estrutura do projeto

- `src/main/java/com/Main.java` — ponto de entrada; delega para `AnagramaApp`.
- `src/main/java/com/AnagramaApp.java` — orquestrador do fluxo (leitura → extração → geração → impressão; captura erros).
- `src/main/java/com/LeitorEntrada.java` — leitura de entrada (args ou console).
- `src/main/java/com/ExtratorLetras.java` — extrai apenas letras e converte para minúsculo.
- `src/main/java/com/Impressao.java` — imprime entrada, saída e erros.
- `src/main/java/com/util/AnagramaUtil.java` — fachada que chama `ValidadorAnagrama`, `NormalizadorAnagrama` e `GeradorAnagrama`.
- `src/main/java/com/util/ValidadorAnagrama.java` — validações (não vazio, sem `null`, apenas letras, distintas).
- `src/main/java/com/util/NormalizadorAnagrama.java` — normalização para minúsculo.
- `src/main/java/com/util/GeradorAnagrama.java` — backtracking para gerar permutações.
- `src/test/java/com/util/AnagramaUtilTest.java` — testes unitários da utilidade de anagramas.


## Decisões de design

- Responsabilidade única por classe para favorecer testabilidade e manutenção.
- Validação separada da normalização para deixar claras as regras de negócio e a transformação de dados.
- Geração via backtracking por simplicidade e clareza para n relativamente pequeno. Como o crescimento é fatorial, usar com parcimônia (poucas letras).


## Limitações e considerações de desempenho

- O algoritmo gera todas as permutações; portanto, o número de resultados cresce como n! (fatorial). Por questões práticas, evite entradas com muitas letras (e.g., >10 letras pode ser impraticável).
- Letras devem ser distintas. Caso precise suportar letras repetidas com remoção de duplicatas na saída, o gerador precisaria de lógica adicional para pular escolhas equivalentes em cada nível.


## Licença

- Não especificada no repositório.
