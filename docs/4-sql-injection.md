# 4.Sql Injection

A principal defesa contra injeção de SQL é nunca concatenar entradas do usuário diretamente em consultas. A técnica mais segura é o uso de Consultas Parametrizadas (Prepared Statements), que separam o código SQL dos dados, impedindo que a entrada do usuário altere a lógica da consulta.
Frameworks ORM (como Hibernate/JPA) automatizam essa proteção, sendo uma alternativa segura e de alto nível.

```java
// Exemplo Seguro com Spring Data JPA

// 1. Crie uma interface de repositório. Spring Data JPA implementará o método.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * O Spring Data JPA pega os argumentos do método e os associa de forma segura
     * aos parâmetros da consulta (:email, :senhaHash), prevenindo SQL Injection.
     */
    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.senhaHash = :senhaHash")
    Optional<Usuario> findByCredentials(@Param("email") String email, @Param("senhaHash") String senhaHash);
}

// 2. Em seu serviço, injete o repositório e use o método definido.
@Service
public class AutenticacaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean autenticar(String email, String senha) {
        // A lógica de consulta é abstraída e a execução é segura.
        Optional<Usuario> usuario = usuarioRepository.findByCredentials(email, senha);
        return usuario.isPresent();
    }
}

