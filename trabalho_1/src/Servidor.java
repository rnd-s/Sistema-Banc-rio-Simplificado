import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Servidor {

    static List<Conta> operacoes = new ArrayList<>();
    static final ProcessadorOperacoes processador = new ProcessadorOperacoes();
    static double saldoServidor = 1000.0;

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(1234);
        System.out.println("Servidor bancário iniciado...");

        while (true) {
            Socket cliente = server.accept();

            new Thread(() -> {
                atenderCliente(cliente);
            }).start();
        }
    }

    public static void atenderCliente(Socket cliente) {
        try {
            RequisicaoOperacao requisicao = RequisicaoOperacao.LerDe(cliente.getInputStream());
            ClienteBanco clienteBanco = requisicao.getCliente();
            Conta c = requisicao.getConta();

            operacoes.add(c);

            System.out.println("Operacao recebida:");
            System.out.println("Login cliente: " + clienteBanco.getLogin());
            System.out.println("Nome cliente: " + clienteBanco.getNome());
            System.out.println("Perfil cliente: " + clienteBanco.getPerfil());
            System.out.println("Titular: " + c.getTitular());
            System.out.println("Número: " + c.getNumero());
            System.out.println("Saldo: " + c.getSaldo());
            System.out.println("Operacao: " + c.getOperacao());

            RespostaOperacao resposta;
            try {
                synchronized (Servidor.class) {
                    saldoServidor = processador.processar(saldoServidor, c);
                }
                resposta = new RespostaOperacao(true, "Operacao aplicada com sucesso", saldoServidor);
            } catch (IllegalArgumentException e) {
                resposta = new RespostaOperacao(false, e.getMessage(), saldoServidor);
            }

            resposta.EscreverPara(cliente.getOutputStream());

            cliente.close();

        } catch (Exception e) {
            System.out.println("Erro ao atender cliente: " + e.getMessage());
        }
    }
}