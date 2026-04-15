

import java.io.IOException;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ContaOutputStream extends OutputStream {

    private final DataOutputStream out;
    private final Conta[] contas;
    private final int quantidade;

    public ContaOutputStream(OutputStream out) {
        this.out = new DataOutputStream(out);
        this.contas = null;
        this.quantidade = 0;
    }

    public ContaOutputStream(Conta[] contas, int quantidade, OutputStream destino) {
        if (contas == null) {
            throw new IllegalArgumentException("O array de contas nao pode ser nulo");
        }
        if (quantidade < 0 || quantidade > contas.length) {
            throw new IllegalArgumentException("Quantidade invalida para o array informado");
        }
        this.contas = contas;
        this.quantidade = quantidade;
        this.out = new DataOutputStream(destino);
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    public void enviarArray() throws IOException {
        if (contas == null) {
            throw new IllegalStateException("Use o construtor com array para enviar conjunto de contas");
        }

        out.writeInt(quantidade);
        for (int i = 0; i < quantidade; i++) {
            byte[] payload = serializarConta(contas[i]);
            out.writeInt(payload.length);
            out.write(payload);
        }
        out.flush();
    }

    public void enviar(Conta c) throws IOException {
        Conta[] unico = new Conta[] { c };
        ContaOutputStream writer = new ContaOutputStream(unico, 1, out);
        writer.enviarArray();
    }

    private byte[] serializarConta(Conta c) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        dos.writeInt(c.getNumero());
        dos.writeUTF(c.getTitular());
        dos.writeDouble(c.getSaldo());
        dos.writeUTF(c.getOperacao());
        dos.flush();

        return bos.toByteArray();
    }
}