package org.example.aula02;

import org.example.aula02.dtos.UsuarioDTO;

import javax.persistence.*;
import java.util.List;

public class ConsultasComJPQL {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //Còdigo das consultas:
        primeiraConsulta(entityManager);
        primeiraConsultaComWhere(entityManager);
        primeiraConsultaSemTipagem(entityManager);
        escolhendoORetorno(entityManager);
        usandoProjectionsSemDTO(entityManager);
        usandoProjectionsComDTO(entityManager);
        passandoParametros(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    public static void primeiraConsulta(EntityManager entityManager){

        System.out.println("\nPrimeira Query\n");

        String jpql = "SELECT u FROM Usuario u ";

        //Temos uma query tipada abaixo.
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);

        List<Usuario> listaDeUsuarios = typedQuery.getResultList();

        listaDeUsuarios.forEach(System.out::println);
    }

    private static void primeiraConsultaComWhere(EntityManager entityManager) {

        System.out.println("\nSegunda Query\n");

        String jpql = "SELECT u FROM Usuario u WHERE u.id = 1";

        //Temos uma query tipada abaixo.
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);

        /* O "getSingleResult()" obriga que apenas UM registro seja retornado. */
        Usuario usuarioComId1 = typedQuery.getSingleResult();

        System.out.println(usuarioComId1);
    }

    private static void primeiraConsultaSemTipagem(EntityManager entityManager) {

        System.out.println("\nTerceira Query\n");

        String jpql = "SELECT u FROM Usuario u WHERE u.id = 1";

        //Antes do JPA 2.0, o "Query" era utilizado. Ele retornava um "Object" e o
        //casting era feito de forma manual.
        Query query = entityManager.createQuery(jpql);

        Usuario usuarioComId1 = (Usuario) query.getSingleResult();

        System.out.println(usuarioComId1);
    }

    private static void escolhendoORetorno(EntityManager entityManager) {

        System.out.println("\nQuarta Query\n");

        //Estamos utilizando apenas um campo da query.
        String jpql = "SELECT u.dominio FROM Usuario u";

        TypedQuery<Dominio> idQuery = entityManager.createQuery(jpql, Dominio.class);

        List<Dominio> listaDeDominios = idQuery.getResultList();

        listaDeDominios.forEach(System.out::println);
    }

    private static void usandoProjectionsSemDTO(EntityManager entityManager) {

        System.out.println("\nQuinta Query\n");

        //Podemos criar uma Projection para obtermos apenas os dados desejados em uma consulta.

        String jpql = "SELECT u.id, u.login, u.nome FROM Usuario u";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> listaDeUsuarios = typedQuery.getResultList();

        listaDeUsuarios.forEach(u -> System.out.println("ID: " + u[0] + ", Login: " + u[1] + ", Nome: " + u[2]));
    }

    private static void usandoProjectionsComDTO(EntityManager entityManager) {

        System.out.println("\nSexta Query\n");

        //Podemos criar uma Projection para obtermos apenas os dados desejados em uma consulta.

        String jpql = "SELECT new org.example.aula02.dtos.UsuarioDTO(u.id, u.login, u.nome) FROM Usuario u";

        //A classe "UsuarioDTO" deve ter um construtor que receba os campos que serão retornados na query.
        TypedQuery<UsuarioDTO> typedQuery = entityManager.createQuery(jpql, UsuarioDTO.class);

        List<UsuarioDTO> listaDeUsuarios = typedQuery.getResultList();

        listaDeUsuarios.forEach(u -> System.out.println("ID: " + u.getId() + ", Login: " + u.getLogin() + ", Nome: " + u.getNome()));
    }

    private static void passandoParametros(EntityManager entityManager) {

        System.out.println("\nSétima Query\n");

        //Podemos criar uma Projection para obtermos apenas os dados desejados em uma consulta.

        String jpql = "SELECT u FROM Usuario u WHERE u.id = :idUsuario";

        //Estamos passando o valor "1" para o parâmetro "idUsuario".
        //Como é uma interface fluente, podemos juntar os comandos "setParameter()".
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class).setParameter("idUsuario", 1);

        //Estamos executando a query.
        Usuario usuario = typedQuery.getSingleResult();

        System.out.println(usuario);
    }
}
