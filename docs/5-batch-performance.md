# 5. Batch Performance

Para diagnosticar um processo em lote, inicio com profiling e logging detalhado para medir o tempo de cada etapa. Utilizo ferramentas como EXPLAIN PLAN (ou o profiler do banco de dados) para identificar consultas lentas, que são os gargalos mais comuns. A otimização envolve adicionar índices nas colunas usadas em WHERE/JOIN e, crucialmente, usar operações em lote (batching) para minimizar a comunicação com o banco. Para o FTP, a compressão de arquivos antes do envio e o aumento do buffer de I/O são as principais melhorias para acelerar a transferência.

```java    
// Exemplo de Otimização com Spring Data JPA
// 1. Configure o batching no seu arquivo `application.properties`:
// spring.jpa.properties.hibernate.jdbc.batch_size=50
// spring.jpa.properties.hibernate.order_inserts=true

// 2. Em seu serviço, use o método saveAll() do repositório.
// O Spring Data JPA, com a configuração acima, agrupará as inserções
// em lotes de 50, reduzindo drasticamente a sobrecarga da rede.

@Service
public class RelatorioService {
    @Autowired
    private RelatorioProcessadoRepository repository;

    @Transactional
    public void salvarRelatorios(List<RelatorioProcessado> relatorios) {
        // O saveAll() aproveitará a configuração de batching automaticamente,
        // enviando os dados em lotes para o banco de dados.
        repository.saveAll(relatorios);
    }
}

