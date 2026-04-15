public class ClienteBanco {

    private final String login;
    private final String nome;
    private final String perfil;

    public ClienteBanco(String login, String nome, String perfil) {
        this.login = login;
        this.nome = nome;
        this.perfil = perfil;
    }

    public String getLogin() {
        return login;
    }

    public String getNome() {
        return nome;
    }

    public String getPerfil() {
        return perfil;
    }
}
