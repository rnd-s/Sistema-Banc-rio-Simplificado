public class SaqueService implements OperacaoService {

    @Override
    public boolean suporta(String operacao) {
        return "saque".equalsIgnoreCase(operacao);
    }

    @Override
    public double aplicar(double saldoAtual, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo");
        }
        if (valor > saldoAtual) {
            throw new IllegalArgumentException("Saldo insuficiente para saque");
        }
        return saldoAtual - valor;
    }
}
