import java.util.ArrayList;
import java.util.List;

public class ProcessadorOperacoes {

    private final List<OperacaoService> servicos;

    public ProcessadorOperacoes() {
        this.servicos = new ArrayList<>();
        this.servicos.add(new DepositoService());
        this.servicos.add(new SaqueService());
    }

    public double processar(double saldoAtual, Conta conta) {
        for (OperacaoService service : servicos) {
            if (service.suporta(conta.getOperacao())) {
                return service.aplicar(saldoAtual, conta.getSaldo());
            }
        }
        throw new IllegalArgumentException("Operacao nao suportada: " + conta.getOperacao());
    }
}
