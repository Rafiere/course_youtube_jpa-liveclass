package org.example.aula03;

import org.example.aula02.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

public class JoinsComJPQL {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        utilizandoInnerJoin(entityManager);
        utilizandoLeftJoin(entityManager);
        utilizandoJoinFetch(entityManager);
        filtrandoRegistros(entityManager);

        operadoresLogicos(entityManager);
        ordenacaoComJpql(entityManager);

        paginacaoComJpql(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void utilizandoInnerJoin(EntityManager entityManager) {

        System.out.println("\nPrimeira Query: \n");

        /* Buscaremos os usuários que fazem parte de um domínio. */

        /* Para fazer o JOIN, podemos utilizar o "u.dominio", pois um usuário possui um
        * domínio, ou seja, possui um atributo do tipo "dominio". */

        /* Todo "JOIN", por padrão, é um "INNER JOIN". */
        String jpql =
                "SELECT u " +
                "FROM Usuario u " +
                "JOIN u.dominio d " +
                "WHERE d.id = 1";

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);

        List<Usuario> lista = typedQuery.getResultList();

        lista.forEach(System.out::println);
    }

    private static void utilizandoLeftJoin(EntityManager entityManager) {

        System.out.println("\nSegunda Query: \n");

        /* Todo LEFT JOIN, por padrão, é um "LEFT OUTER JOIN". */

        /* Quando utilizamos um LEFT JOIN, vamos trazer todos os usuários que tiverem alguma
        * relação com um registro de configuração, assim como o JOIN faz, porém, se tivermos algum
        * usuário que NÃO ESTEJA relacionado com nenhum registro de configuração, esse usuário será
        * obtido mesmo assim. */

        /* No lado esquerdo, temos o "Usuario", no lado direito, temos o "Configuracao". O "LEFT JOIN" buscará
        * TODOS os usuários do lado "esquerdo", com eles tendo ou não uma configuração. */
        String jpql =
                "SELECT u " +
                "FROM Usuario u " +
                "LEFT JOIN u.configuracao c";

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);

        List<Usuario> lista = typedQuery.getResultList();

        lista.forEach(System.out::println);
    }

    private static void utilizandoJoinFetch(EntityManager entityManager) {

        //Não entendi ainda muito bem o uso de JOIN FETCH.

        System.out.println("\nTerceira Query: \n");

        /* O "JOIN FETCH" é utilizado para fazer um "JOIN" e já trazer os dados do lado direito. */

        /* O "JOIN FETCH" é um "INNER JOIN". */
        String jpql =
                "SELECT u " +
                "FROM Usuario u " +
                "JOIN FETCH u.configuracao c";

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);

        List<Usuario> lista = typedQuery.getResultList();

        lista.forEach(System.out::println);
    }

    private static void filtrandoRegistros(EntityManager entityManager) {

        //No JPQL, temos os seguintes filtros:

        //LIKE, IS NULL, IS EMPTY, BETWEEN, >, <, >=, <=, =, <>, IN, NOT IN, MEMBER OF, NOT MEMBER OF.

        String jpqlLike = "SELECT u FROM Usuario u WHERE u.nome LIKE :nomeUsuario";
        String jpqlIsNull = "SELECT u FROM Usuario u WHERE u.senha IS NULL";
        String jpqlIsEmpty = "SELECT d FROM Dominio d WHERE d.usuarios IS EMPTY"; //Usar com listas. Traz todos os registros em que a lista está vazia.
        String jpqlBetween = "SELECT u FROM Usuario u WHERE u.ultimoAcesso BETWEEN :dataInicio AND :dataFim"; //Verifica se está entre dois valores.

        /* O "IN" verifica se um valor está dentro de uma lista. */
        String jpqlComIn = "SELECT u FROM Usuario u WHERE u.id IN (1, 2, 3)";

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpqlLike, Usuario.class)
                .setParameter("nomeUsuario", "Cal%");

        TypedQuery<Usuario> typedQueryIsNull = entityManager.createQuery(jpqlBetween, Usuario.class)
                .setParameter("dataInicio", LocalDateTime.now().minusDays(1))
                .setParameter("dataFim", LocalDateTime.now().plusDays(1));

        List<Usuario> lista = typedQuery.getResultList();

        lista.forEach(System.out::println);
    }

    private static void operadoresLogicos(EntityManager entityManager) {

        //Estamos substituindo o uso do BETWEEN por operadores lógicos.
        String jpql = "SELECT u " +
                "FROM Usuario u " +
                "WHERE u.ultimoAcesso > :dataInicio " +
                "AND u.ultimoAcesso < :dataFim";

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
                .setParameter("dataInicio", LocalDateTime.now().minusDays(1))
                .setParameter("dataFim", LocalDateTime.now().plusDays(1));

        List<Usuario> lista = typedQuery.getResultList();

        lista.forEach(System.out::println);
    }

    private static void ordenacaoComJpql(EntityManager entityManager) {

        String jpql = "SELECT u FROM Usuario u ORDER BY u.nome ASC";

        /* Poderíamos utilizar uma "Path Expression", ou seja, utilizar o "." para acessar
        * um objeto. É como se utilizássemos um "get()" no Java. */

        String jpqlComPathExpression = "SELECT u FROM Usuario u ORDER BY u.dominio.nome ASC";

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);

        List<Usuario> lista = typedQuery.getResultList();

        lista.forEach(System.out::println);
    }

    private static void paginacaoComJpql(EntityManager entityManager) {

        String jpql = "SELECT u FROM Usuario u ORDER BY u.nome ASC";

        /* O "setFirstResult()" define a partir de qual registro queremos começar a busca. */
        /* O "setMaxResults()" define quantos registros queremos buscar por página. */

        /* Com o Spring Data JPA, podemos usar o "Pageable", que é mais simples, porém, podemos definir
        * a paginação dessa forma, também. */
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
                .setFirstResult(0)
                .setMaxResults(2);

        List<Usuario> lista = typedQuery.getResultList();

        lista.forEach(System.out::println);
    }
}
