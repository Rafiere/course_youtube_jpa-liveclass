package org.example.aula02;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Dominio {

    @Id private Integer id;
    private String nome;
    @OneToMany(mappedBy = "dominio")
    private List<Usuario> usuarios;

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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String toString() {
        return "Dominio{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
