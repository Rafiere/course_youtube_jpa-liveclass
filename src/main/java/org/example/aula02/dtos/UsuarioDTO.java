package org.example.aula02.dtos;

//Essa classe será utilizada para obtermos apenas os campos desejados em uma consulta.
public class UsuarioDTO {

    private Integer id;
    private String login;
    private String nome;

    public UsuarioDTO(Integer id, String login, String nome) {
        this.id = id;
        this.login = login;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
