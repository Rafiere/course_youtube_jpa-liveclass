package org.example.aula01;

import org.example.aula01.model.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ExemploExecucao {

    public static void main(String[] args) {

        //Essa factory é gerada através da "PersistenceUnit", definida no "persistence.xml".
        //Essa instância deverá durar durante todo o ciclo de vida da aplicação.
        //Podemos ter várias factories, uma para cada unidade de persistência, mas isso é algo pouco comumm.
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");

        //Quando uma requisição web nasce, o entity manager é criado. Quando ela é encerrada, ele é fechado.
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //Devemos passar o tipo da classe e a primary key.
        Cliente cliente = entityManager.find(Cliente.class, 1);

        System.out.println(cliente.getNome());

        Cliente cliente2 = new Cliente();

//        cliente2.setId(3);
        cliente2.setNome("Teste");

        /* Aqui, o Hibernate persistirá o objeto. Estamos abrindo uma transação para garantir a
        * consistência dos dados. */
        entityManager.getTransaction().begin();
        entityManager.persist(cliente2);
        //Antes do "commit" ser utilizado, o "flush" automaticamente é utilizado, também.
        entityManager.getTransaction().commit();

        //O entity manager não enviará o comando para a base de dados quando utilizarmos o
        //"persist", mas sim quando utilizarmos o "flush". Ele pode ocorrer ou não quando persistimos o
        //objeto.
//        entityManager.flush();

        //Deletando:

        //Nós só conseguimos remover objetos em que o Entity Manager está ciente da sua existência.

        //O EntityManager gerencia as entidades para fazer uma espécie de "cache". Se buscarmos dois objetos com o mesmo ID, na primeira
        //busca, ele irá no banco de dados. Na segunda, ele irá no cache, pois ele já está armazenado na memória. Isso é chamado de
        //cache de primeiro nível.
        Cliente cliente3 = entityManager.find(Cliente.class, 1);
        entityManager.getTransaction().begin();
        entityManager.remove(cliente3);
        entityManager.getTransaction().commit();

        //Apenas a busca pelo "cliente4" será feita, pois ele já estará na memória
        //quando a segunda busca for realizada.
        Cliente cliente4 = entityManager.find(Cliente.class, 4);
        Cliente cliente5 = entityManager.find(Cliente.class, 4);

        //Atualização - Será feita automaticamente pois o objeto já está sendo gerenciado pelo Hibernate.:
        Cliente cliente6 = entityManager.find(Cliente.class, 5);
        entityManager.getTransaction().begin();
        cliente6.setNome("Teste 2");
        entityManager.getTransaction().commit();

        //Outra forma de atualizar objetos:

        Cliente cliente7 = new Cliente();
        cliente7.setId(1);
        cliente7.setNome("Teste 3");

        entityManager.getTransaction().begin();
        entityManager.merge(cliente7);
        entityManager.getTransaction().commit();

        //Criando objetos com o merge
        Cliente cliente8 = new Cliente();
        cliente8.setNome("Teste 4");
        entityManager.getTransaction().begin();
        entityManager.merge(cliente8);
        entityManager.getTransaction().commit();

        /* Estamos fechando a conexão. */
        entityManager.close();

        /* Estamos fechando a factory.*/
        entityManagerFactory.close();
    }
}
