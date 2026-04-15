public interface OperacaoService {
    boolean suporta(String operacao);
    double aplicar(double saldoAtual, double valor) throws IllegalArgumentException;
}
