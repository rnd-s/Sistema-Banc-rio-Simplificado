

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class ContaInputStream extends InputStream {

    private final DataInputStream in;

    public ContaInputStream(InputStream in) {
        this.in = new DataInputStream(in);
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    public Conta[] lerArray() throws IOException {
        int quantidade = in.readInt();
        if (quantidade < 0) {
            throw new IOException("Quantidade de contas invalida recebida no stream");
        }

        Conta[] contas = new Conta[quantidade];
        for (int i = 0; i < quantidade; i++) {
            int tamanho = in.readInt();
            if (tamanho <= 0) {
                throw new IOException("Tamanho de payload invalido para conta " + i);
            }
            byte[] payload = new byte[tamanho];
            in.readFully(payload);
            contas[i] = desserializarConta(payload);
        }
        return contas;
    }

    public Conta ler() throws IOException {
        Conta[] contas = lerArray();
        if (contas.length == 0) {
            throw new IOException("Nenhuma conta recebida no stream");
        }
        return contas[0];
    }

    private Conta desserializarConta(byte[] payload) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(payload);
        DataInputStream dis = new DataInputStream(bis);

        int numero = dis.readInt();
        String nome = dis.readUTF();
        double saldo = dis.readDouble();
        String operacao = dis.readUTF();

        return new Conta(numero, nome, saldo, operacao);
    }
}