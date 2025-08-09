# 2.Design Pattern

Para desacoplar o código de uma biblioteca de terceiros, como um serviço de pagamentos, utiliza-se o padrão de design Adapter (Adaptador).
A abordagem consiste em criar uma interface própria (ex: GatewayPagamento) que define os métodos que sua aplicação usará, como realizarPagamento(valor). Em seguida, cria-se uma classe concreta (XAdapter) que implementa essa interface, fazendo a "ponte" e traduzindo as chamadas para os métodos específicos da biblioteca de terceiro.
A principal vantagem é a flexibilidade para trocar de provedor no futuro, bastando criar um novo adaptador (ex: WAdapter) sem alterar o código de negócio. A limitação é o acréscimo de uma camada extra de código.

```java
public interface GatewayPagamento {
    boolean processarPagamento(double valor);
}

public class XAdapter implements GatewayPagamento {
    // Objeto da biblioteca de terceiro
    private final XGateway x = new XGateway();

    @Override
    public boolean processarPagamento(double valor) {
        return x.cobranca(valor);
    }
}

// Classe hipotética da biblioteca de terceiro
class XGateway {
    public boolean cobranca(double amount) {
        return true; 
    }
}

