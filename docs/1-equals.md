# 1.Equals

Em sistemas corporativos, a igualdade de objetos é definida por regras de negócio (como um ID de funcionário), não pela referência de memória. Para isso, é necessário sobrescrever o método equals().
Se dois objetos são iguais pelo método equals(), eles devem ter o mesmo hashCode(). Isso garante que coleções como HashSet e HashMap funcionem corretamente, evitando, por exemplo, o cadastro de funcionários duplicados.
Abaixo, a classe Funcionario considera dois objetos iguais se eles tiverem o mesmo idFuncionario.

```java
public class Funcionario {

    private int idFuncionario;
    private String nome;

    public Funcionario(int id, String nome) {
        this.idFuncionario = id;
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return idFuncionario == that.idFuncionario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFuncionario);
    }

}

