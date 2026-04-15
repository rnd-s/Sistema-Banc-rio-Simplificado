import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TesteStreams {

    private static Conta[] gerarContasTeste() {
        return new Conta[] {
            new Conta(101, "Ana", 250.0, "deposito"),
            new Conta(102, "Bruno", 90.0, "saque"),
            new Conta(103, "Carla", 500.0, "deposito")
        };
    }

    private static void imprimirContas(Conta[] contas) {
        for (Conta conta : contas) {
            System.out.println("Conta=" + conta.getNumero()
                + ", Titular=" + conta.getTitular()
                + ", Valor=" + conta.getSaldo()
                + ", Operacao=" + conta.getOperacao());
        }
    }

    private static void testeSaidaPadrao() throws Exception {
        Conta[] contas = gerarContasTeste();
        ContaOutputStream cos = new ContaOutputStream(contas, contas.length, System.out);
        cos.enviarArray();
    }

    private static void testeSaidaArquivo() throws Exception {
        Conta[] contas = gerarContasTeste();
        try (OutputStream fos = new FileOutputStream("contas.bin")) {
            ContaOutputStream cos = new ContaOutputStream(contas, contas.length, fos);
            cos.enviarArray();
        }
        System.out.println("Arquivo contas.bin gerado com sucesso");
    }

    private static void testeSaidaTcp(String host, int porta) throws Exception {
        Conta[] contas = gerarContasTeste();
        try (Socket socket = new Socket(host, porta)) {
            ContaOutputStream cos = new ContaOutputStream(contas, contas.length, socket.getOutputStream());
            cos.enviarArray();
        }
        System.out.println("Array de contas enviado para servidor TCP");
    }

    private static void testeEntradaPadrao() throws Exception {
        ContaInputStream cis = new ContaInputStream(System.in);
        Conta[] contas = cis.lerArray();
        imprimirContas(contas);
    }

    private static void testeEntradaArquivo() throws Exception {
        try (InputStream fis = new FileInputStream("contas.bin")) {
            ContaInputStream cis = new ContaInputStream(fis);
            Conta[] contas = cis.lerArray();
            imprimirContas(contas);
        }
    }

    private static void testeEntradaTcp(String host, int porta) throws Exception {
        try (Socket socket = new Socket(host, porta)) {
            ContaInputStream cis = new ContaInputStream(socket.getInputStream());
            Conta[] contas = cis.lerArray();
            imprimirContas(contas);
        }
    }

    private static void iniciarServidorDestinoTcp(int porta) throws Exception {
        try (ServerSocket server = new ServerSocket(porta)) {
            System.out.println("Servidor destino TCP aguardando em " + porta);
            try (Socket cliente = server.accept()) {
                ContaInputStream cis = new ContaInputStream(cliente.getInputStream());
                Conta[] contas = cis.lerArray();
                System.out.println("Servidor recebeu:");
                imprimirContas(contas);
            }
        }
    }

    private static void iniciarServidorOrigemTcp(int porta) throws Exception {
        try (ServerSocket server = new ServerSocket(porta)) {
            System.out.println("Servidor origem TCP aguardando em " + porta);
            try (Socket cliente = server.accept()) {
                Conta[] contas = gerarContasTeste();
                ContaOutputStream cos = new ContaOutputStream(contas, contas.length, cliente.getOutputStream());
                cos.enviarArray();
                System.out.println("Servidor enviou array de contas para cliente");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Uso:");
            System.out.println("java TesteStreams out-stdout");
            System.out.println("java TesteStreams out-file");
            System.out.println("java TesteStreams out-tcp [host] [porta]");
            System.out.println("java TesteStreams in-stdin");
            System.out.println("java TesteStreams in-file");
            System.out.println("java TesteStreams in-tcp [host] [porta]");
            System.out.println("java TesteStreams tcp-dest-server [porta]");
            System.out.println("java TesteStreams tcp-src-server [porta]");
            return;
        }

        switch (args[0]) {
            case "out-stdout":
                testeSaidaPadrao();
                break;
            case "out-file":
                testeSaidaArquivo();
                break;
            case "out-tcp":
                testeSaidaTcp(args.length > 1 ? args[1] : "localhost", args.length > 2 ? Integer.parseInt(args[2]) : 2345);
                break;
            case "in-stdin":
                testeEntradaPadrao();
                break;
            case "in-file":
                testeEntradaArquivo();
                break;
            case "in-tcp":
                testeEntradaTcp(args.length > 1 ? args[1] : "localhost", args.length > 2 ? Integer.parseInt(args[2]) : 2346);
                break;
            case "tcp-dest-server":
                iniciarServidorDestinoTcp(args.length > 1 ? Integer.parseInt(args[1]) : 2345);
                break;
            case "tcp-src-server":
                iniciarServidorOrigemTcp(args.length > 1 ? Integer.parseInt(args[1]) : 2346);
                break;
            default:
                System.out.println("Modo invalido: " + args[0]);
        }
    }
}
