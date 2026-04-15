

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

public class Cliente {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 1234);

        ClienteBanco cliente = new ClienteBanco("ron", "Ron", "USER");

        Conta c = new Conta(
            (int)(Math.random()*100),
            "Ron",
            150,
            "deposito"
        );

        RequisicaoOperacao requisicao = new RequisicaoOperacao(cliente, c);

        OutputStream out = socket.getOutputStream();

        requisicao.EscreverPara(out);

        InputStream in = socket.getInputStream();
        RespostaOperacao resposta = RespostaOperacao.LerDe(in);

        out.close();
        in.close();
        socket.close();

        System.out.println("Resposta do servidor:");
        System.out.println("Sucesso: " + resposta.isSucesso());
        System.out.println("Mensagem: " + resposta.getMensagem());
        System.out.println("Saldo atual: " + resposta.getSaldoAtual());
    }
}