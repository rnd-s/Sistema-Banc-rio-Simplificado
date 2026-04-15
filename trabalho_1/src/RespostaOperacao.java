import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RespostaOperacao {

    private final boolean sucesso;
    private final String mensagem;
    private final double saldoAtual;

    public RespostaOperacao(boolean sucesso, String mensagem, double saldoAtual) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.saldoAtual = saldoAtual;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public void EscreverPara(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBoolean(sucesso);
        dos.writeUTF(mensagem);
        dos.writeDouble(saldoAtual);
        dos.flush();
    }

    public static RespostaOperacao LerDe(InputStream in) throws IOException {
        DataInputStream dis = new DataInputStream(in);
        boolean sucesso = dis.readBoolean();
        String mensagem = dis.readUTF();
        double saldoAtual = dis.readDouble();
        return new RespostaOperacao(sucesso, mensagem, saldoAtual);
    }
}
