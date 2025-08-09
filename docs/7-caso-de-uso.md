# 7. Caso de Uso

# Documentação de Requisitos - Gerenciamento de Plantas

---

## 1. Caso de Uso / História de Usuário

Para representar os requisitos de forma ágil e centrada no usuário, utilizarei o formato de Histórias de Usuário (User Stories).

### Épico: Gerenciamento de Plantas

#### História 1: Cadastro de Plantas
**Como** um Operador do Sistema,  
**eu quero** registrar uma nova planta no sistema informando um código numérico único e uma descrição opcional,  
**para que** eu possa habilitar a entrada de novos dados que serão utilizados em fases futuras do projeto.

#### História 2: Edição de Plantas
**Como** um Operador do Sistema,  
**eu quero** modificar a descrição de uma planta já existente,  
**para que** as informações se mantenham sempre atualizadas.

#### História 3: Consulta de Plantas
**Como** um Operador do Sistema,  
**eu quero** pesquisar e visualizar a lista de plantas cadastradas,  
**para que** eu possa consultar rapidamente as informações disponíveis.

#### História 4: Exclusão de Plantas
**Como** um Administrador,  
**eu quero** excluir uma planta que não é mais relevante para a operação,  
**para que** a base de dados permaneça limpa e consistente, evitando erros.

---

## 2. Regras de Negócio e Premissas

### Regras de Negócio (Derivadas dos Requisitos)

- **Unicidade do Código:** O Código da planta é o seu identificador principal e não pode ser repetido no sistema.
- **Formato do Código:** O Código deve ser estritamente numérico.
- **Obrigatoriedade do Código:** Uma planta não pode ser salva sem um Código.
- **Flexibilidade da Descrição:** A Descrição é um campo opcional, com um limite de 10 caracteres alfanuméricos.
- **Controle de Acesso para Exclusão:** Apenas usuários com perfil "Administrador" têm permissão para executar a ação de exclusão.

### Premissas Relevantes (Suposições para a Solução)

- **Imutabilidade do Código:** Assume-se que o Código de uma planta, uma vez criado, não pode ser alterado. A alteração de um identificador único é uma má prática que pode quebrar relacionamentos de dados.
- **Existência de Autenticação e Perfis:** Assume-se que o sistema XYZ já possui (ou terá) um mecanismo de autenticação de usuários e um sistema de perfis (ex: ADMINISTRADOR, OPERADOR).
- **Bloqueio de Exclusão por Dependência:** Assume-se que uma planta não poderá ser excluída se houver dados associados a ela (criados na fase 2 ou posteriores). O sistema deverá informar ao administrador sobre a dependência existente. A alternativa seria uma exclusão lógica (soft delete), onde o registro é apenas marcado como inativo.

---

## 3. Validações e Medidas de Segurança

### Validações de Dados (Frontend e Backend)

- **Código:**
   - *Frontend:* Utilizar máscara de entrada no campo para permitir apenas a digitação de números. Validar se o campo não está vazio antes de submeter o formulário.
   - *Backend:* Realizar uma validação definitiva no servidor. Verificar se o valor é nulo, se é numérico e, principalmente, consultar o banco de dados para garantir que o código não existe antes de salvar um novo registro. Esta é a validação mais importante para a regra de unicidade.

- **Descrição:**
   - *Frontend:* Limitar o campo de texto a 10 caracteres (`maxlength="10"`).
   - *Backend:* Remover espaços em branco no início e no fim (`trim()`) e validar o comprimento da string antes de persistir no banco de dados.

### Medidas de Segurança

- **Autenticação:** Todas as funcionalidades de gerenciamento de plantas (criar, ler, atualizar, excluir) devem exigir que o usuário esteja autenticado no sistema.
- **Autorização:**  
  A medida de segurança mais crítica é na API do backend. O endpoint responsável pela exclusão (ex: `DELETE /api/plantas/{id}`) deve ser protegido por um middleware ou interceptor que verifique se o perfil do usuário autenticado é ADMINISTRADOR. Caso um usuário com outro perfil tente acessar essa funcionalidade, o sistema deve retornar um erro **403 Forbidden** (Acesso Negado), mesmo que ele tente forçar a chamada diretamente pela API. Apenas ocultar o botão na interface não é suficiente.

---

## 4. Estratégia de Testes e Casos Extremos

### Testes Funcionais (Caminho Feliz)

- Cadastrar uma nova planta com código e descrição válidos.
- Cadastrar uma nova planta apenas com o código (sem descrição).
- Atualizar a descrição de uma planta existente.
- Pesquisar e encontrar uma planta pelo seu código.
- Um usuário ADMINISTRADOR consegue excluir uma planta com sucesso.

### Testes de Validação e Erro

- Tentar cadastrar uma planta com um Código que já existe.  
  *Resultado esperado:* O sistema exibe uma mensagem de erro clara: **"O código informado já está em uso."**

- Tentar cadastrar uma planta com um Código não numérico (ex: "ABC").
- Tentar cadastrar uma planta sem informar um Código.
- Tentar cadastrar/atualizar uma planta com uma Descrição com mais de 10 caracteres.

### Testes de Segurança e Casos Extremos

- **Caso Extremo de Segurança:**  
  Logar como um usuário OPERADOR e tentar fazer uma chamada direta à API para excluir uma planta (ex: usando Postman ou as ferramentas de desenvolvedor do navegador).  
  *Resultado esperado:* O servidor retorna o código de status **403 Forbidden** e a exclusão não ocorre.

- **Caso Extremo de Integridade:**  
  Tentar excluir uma planta que possui dados vinculados (considerando a premissa 3).  
  *Resultado esperado:* O sistema impede a exclusão e informa ao usuário:  
  **"Não é possível excluir esta planta, pois existem registros associados a ela."**

- **Caso Extremo de Concorrência:**  
  Dois usuários tentam cadastrar uma planta com o mesmo Código exatamente ao mesmo tempo.  
  *Resultado esperado:* A restrição UNIQUE no banco de dados deve garantir que apenas uma das operações tenha sucesso, enquanto a outra falha.

---
