public class DepositoService implements OperacaoService {

    @Override
    public boolean suporta(String operacao) {
        return "deposito".equalsIgnoreCase(operacao);
    }

    @Override
    public double aplicar(double saldoAtual, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de deposito deve ser positivo");
        }
        return saldoAtual + valor;
    }
}
