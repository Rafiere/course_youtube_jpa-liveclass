package org.example.aula01.model;

import javax.persistence.*;
import java.util.Objects;

//Uma tabela no banco de dados equivale a um objeto no Java.
@Entity //Essa entidade permite que o JPA enxergue essa classe como uma entidade.
@Table //Essa anotação permite que configuremos alguns atributos da tabela que será criada.
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column private Integer id; //Essa anotação permite configurarmos a coluna.
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(nome, cliente.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
