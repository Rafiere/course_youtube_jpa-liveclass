package org.example.aula02;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Configuracao {

    @Id
    private Integer id;

    @MapsId
    @OneToOne
    private Usuario usuario;

    private boolean receberNotificacoes;
    private boolean encerrarSessaoAutomaticamente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isReceberNotificacoes() {
        return receberNotificacoes;
    }

    public void setReceberNotificacoes(boolean receberNotificacoes) {
        this.receberNotificacoes = receberNotificacoes;
    }

    public boolean isEncerrarSessaoAutomaticamente() {
        return encerrarSessaoAutomaticamente;
    }

    public void setEncerrarSessaoAutomaticamente(boolean encerrarSessaoAutomaticamente) {
        this.encerrarSessaoAutomaticamente = encerrarSessaoAutomaticamente;
    }

    @Override
    public String toString() {
        return "Configuracao{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", receberNotificacoes=" + receberNotificacoes +
                ", encerrarSessaoAutomaticamente=" + encerrarSessaoAutomaticamente +
                '}';
    }
}
