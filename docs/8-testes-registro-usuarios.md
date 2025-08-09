# 8. Testes de registro de usuários

---

## 1. Tipos de Testes e Cenários

Para garantir a qualidade e a robustez da funcionalidade, eu aplicaria uma estratégia de testes em múltiplas camadas, cada uma com um foco específico.

### a. Testes Unitários

**Foco:** Isolar e testar as menores partes do código, como funções, métodos de serviço ou componentes de UI, sem depender de sistemas externos (banco de dados, APIs).

**Cenários de Teste:**

- **Validação do Modelo de Usuário:**
    - Testar se o modelo aceita um objeto com nome e e-mail válidos.
    - Testar se o modelo rejeita (ou lança um erro) um objeto sem o campo nome.
    - Testar se o modelo rejeita um objeto sem o campo e-mail.

- **Serviço de Validação de E-mail:**
    - Testar o método `isEmailUnico()` com um e-mail que não existe no banco (mockado). Resultado esperado: `true`.
    - Testar o método `isEmailUnico()` com um e-mail que já existe no banco (mockado). Resultado esperado: `false`.

- **Formato do E-mail:**
    - Testar uma função utilitária de validação de e-mail com entradas válidas (ex: `teste@dominio.com`). Resultado esperado: `true`.
    - Testar a mesma função com entradas inválidas (ex: `teste.com`, `teste@`, `@dominio.com`). Resultado esperado: `false`.

---

### b. Testes de Integração

**Foco:** Verificar se diferentes partes do sistema funcionam corretamente em conjunto. Principalmente, testar a comunicação entre a API (backend) e o banco de dados.

**Cenários de Teste:**

- **Criação de Usuário (API):**
    - Enviar uma requisição `POST` para `/api/usuarios` com dados válidos. Resultado esperado: O usuário é criado no banco de dados e a API retorna um status **201 Created**.
    - Enviar uma requisição `POST` com um e-mail que já foi cadastrado. Resultado esperado: O usuário não é criado e a API retorna um status de erro, como **409 Conflict** ou **400 Bad Request**, com uma mensagem clara.

- **Atualização de Usuário (API):**
    - Enviar uma requisição `PUT` para `/api/usuarios/{id}` com dados válidos. Resultado esperado: Os dados do usuário são atualizados no banco e a API retorna um status **200 OK**.

- **Exclusão de Usuário (Segurança):**
    - Enviar uma requisição `DELETE` para `/api/usuarios/{id}` autenticado como administrador. Resultado esperado: O usuário é removido do banco e a API retorna **204 No Content**.
    - Enviar a mesma requisição autenticado como usuário comum. Resultado esperado: A exclusão falha e a API retorna **403 Forbidden**.

---

### c. Testes de Ponta a Ponta (End-to-End - E2E)

**Foco:** Simular a jornada completa do usuário através da interface gráfica, garantindo que todo o fluxo funcione como esperado, do clique do usuário no navegador até a resposta do banco de dados.

**Cenários de Teste:**

- **Caminho Feliz:**  
  Um usuário abre a tela, preenche todos os campos com dados válidos, clica em "Salvar" e recebe uma mensagem de sucesso. O novo usuário aparece na lista.

- **Validação de Formulário:**  
  O usuário tenta submeter o formulário com o campo "nome" ou "e-mail" em branco. Resultado esperado: A submissão é bloqueada e uma mensagem de erro é exibida abaixo do campo correspondente.

- **Fluxo de Exclusão (Admin):**  
  Um usuário administrador faz login, navega até a lista de usuários, clica no ícone de lixeira de um usuário, confirma a ação em um pop-up e verifica se o usuário foi removido da lista.

---

## 2. Casos Extremos e Como Lidar com Eles

Casos extremos testam os limites da aplicação e situações incomuns.

- **Caso Extremo 1: Concorrência**  
  **Cenário:** Dois usuários tentam se cadastrar com o mesmo e-mail exatamente ao mesmo tempo.  
  **Como Lidar:**  
  A primeira defesa é uma restrição UNIQUE na coluna de e-mail no banco de dados. Isso garante a integridade dos dados na fonte. A aplicação (backend) deve ser programada para capturar o erro de violação de unicidade do banco e traduzi-lo em uma mensagem amigável para o segundo usuário, como:  
  *"Este e-mail já está em uso."*

- **Caso Extremo 2: Formatação de Entrada**  
  **Cenário:** O usuário insere um e-mail com espaços no início/fim (`" joao@email.com "`) ou com letras maiúsculas (`"Joao@Email.com"`).  
  **Como Lidar:**  
  O backend deve sempre normalizar os dados antes de validar e salvar: aplicar `trim()` para remover espaços e `toLowerCase()` para padronizar o e-mail. Isso evita duplicatas sutis e melhora a experiência do usuário na autenticação.

- **Caso Extremo 3: Autoexclusão de Administrador**  
  **Cenário:** Um usuário administrador tenta excluir a sua própria conta.  
  **Como Lidar:**  
  Esta é uma regra de negócio a ser definida. Uma abordagem segura é proibir a ação. O backend deve verificar se o ID do usuário a ser excluído é o mesmo do usuário autenticado na sessão. Se for, a operação deve ser bloqueada com uma mensagem como:  
  *"Você não pode excluir sua própria conta de administrador."*

---

## 3. Exemplo de Caso de Teste (Java com JUnit 5 e Mockito)

Este é um exemplo de teste unitário para a classe de serviço que cria um usuário, focando na regra de negócio de e-mail único.

```java
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar criar um usuário com e-mail duplicado")
    void deveLancarExcecaoAoCriarUsuarioComEmailDuplicado() {
        // 1. Arrange
        Usuario novoUsuario = new Usuario("Maria Silva", "email.existente@teste.com", "Rua X", "123");

        // Configura o comportamento do mock:
        // Quando o método findByEmail for chamado com este e-mail,
        // ele deve retornar um Optional contendo um usuário (simulando que já existe).
        when(usuarioRepository.findByEmail("email.existente@teste.com"))
            .thenReturn(Optional.of(new Usuario())); // Retorna um usuário existente

        // 2. Act & 3. Assert (Ação e Verificação)

        // Verifica se a chamada ao método `criar` lança a exceção esperada.
        assertThrows(EmailDuplicadoException.class, () -> {
            usuarioService.criar(novoUsuario);
        });

        // Garante que o método 'save' NUNCA foi chamado, pois a operação
        // deveria ter sido interrompida pela validação de e-mail duplicado.
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}

// (Classes de exemplo para o contexto)
// public class UsuarioService { ... }
// public interface UsuarioRepository extends JpaRepository<Usuario, Long> { ... }
// public class EmailDuplicadoException extends RuntimeException { ... }

