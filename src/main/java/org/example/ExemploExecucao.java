package org.example;

import org.example.model.Cliente;

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
        Cliente cliente3 = entityManager.find(Cliente.class, 1);
        entityManager.getTransaction().begin();
        entityManager.remove(cliente3);
        entityManager.getTransaction().commit();

        /* Estamos fechando a conexão. */
        entityManager.close();

        /* Estamos fechando a factory.*/
        entityManagerFactory.close();
    }
}
