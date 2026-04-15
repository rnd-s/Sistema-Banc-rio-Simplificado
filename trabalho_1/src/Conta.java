

public class Conta {

    private int numero;
    private String titular;
    private double saldo;
    private String operacao;

    public Conta(int numero, String titular, double saldo, String operacao) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
        this.operacao = operacao;
    }

    public int getNumero() { return numero; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public String getOperacao() { return operacao; }
}

