import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class RequisicaoOperacao {

    private final ClienteBanco cliente;
    private final Conta conta;

    public RequisicaoOperacao(ClienteBanco cliente, Conta conta) {
        this.cliente = cliente;
        this.conta = conta;
    }

    public ClienteBanco getCliente() {
        return cliente;
    }

    public Conta getConta() {
        return conta;
    }

    public void EscreverPara(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeUTF(cliente.getLogin());
        dos.writeUTF(cliente.getNome());
        dos.writeUTF(cliente.getPerfil());

        ContaOutputStream contaOut = new ContaOutputStream(new Conta[] { conta }, 1, out);
        contaOut.enviarArray();
    }

    public static RequisicaoOperacao LerDe(InputStream in) throws IOException {
        DataInputStream dis = new DataInputStream(in);
        String login = dis.readUTF();
        String nome = dis.readUTF();
        String perfil = dis.readUTF();

        ContaInputStream contaIn = new ContaInputStream(dis);
        Conta[] contas = contaIn.lerArray();
        if (contas.length == 0) {
            throw new IOException("Requisicao sem conta");
        }

        ClienteBanco cliente = new ClienteBanco(login, nome, perfil);
        return new RequisicaoOperacao(cliente, contas[0]);
    }
}
