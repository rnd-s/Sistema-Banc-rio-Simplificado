# Sistema Bancario Simplificado - Trabalho 1

Este projeto foi ajustado para cobrir os itens de comunicacao entre processos com Java Sockets e Streams.

## Estrutura do trabalho

- POJOs:
	- `Conta`
	- `ClienteBanco`
- Classes de modelo de servico:
	- `DepositoService`
	- `SaqueService`

## Compilacao

```bash
javac Cliente.java Servidor.java Conta.java ContaInputStream.java ContaOutputStream.java \
	ClienteBanco.java OperacaoService.java DepositoService.java SaqueService.java \
	ProcessadorOperacoes.java RequisicaoOperacao.java RespostaOperacao.java TesteStreams.java
```

## Item 2 - ContaOutputStream (array + bytes por objeto)

O construtor exigido foi implementado em `ContaOutputStream`:

```java
new ContaOutputStream(Conta[] contas, int quantidade, OutputStream destino)
```

Cada objeto `Conta` e serializado em payload proprio e enviado no formato:

1. quantidade de objetos
2. para cada objeto: tamanho em bytes do payload
3. payload com pelo menos 3 atributos (numero, titular, saldo, operacao)

### Testes do Item 2

1. Saida padrao:

```bash
java TesteStreams out-stdout
```

2. Arquivo:

```bash
java TesteStreams out-file
```

3. Servidor remoto TCP:

Terminal 1:

```bash
java TesteStreams tcp-dest-server 2345
```

Terminal 2:

```bash
java TesteStreams out-tcp localhost 2345
```

## Item 3 - ContaInputStream

O construtor exigido foi implementado em `ContaInputStream`:

```java
new ContaInputStream(InputStream origem)
```

### Testes do Item 3

1. Entrada padrao (System.in):

```bash
java TesteStreams out-file
cat contas.bin | java TesteStreams in-stdin
```

2. Arquivo (FileInputStream):

```bash
java TesteStreams out-file
java TesteStreams in-file
```

3. Origem em servidor TCP:

Terminal 1:

```bash
java TesteStreams tcp-src-server 2346
```

Terminal 2:

```bash
java TesteStreams in-tcp localhost 2346
```

## Item 4 - Cliente/Servidor com serializacao de request/reply

A comunicacao e feita por socket TCP com empacotamento e desempacotamento explicito:

- Cliente empacota request (`RequisicaoOperacao.writeTo`)
- Servidor desempacota request (`RequisicaoOperacao.readFrom`)
- Servidor empacota reply (`RespostaOperacao.writeTo`)
- Cliente desempacota reply (`RespostaOperacao.readFrom`)

### Teste cliente-servidor

Terminal 1:

```bash
java Servidor
```

Terminal 2:

```bash
java Cliente
```

## Observacao sobre Item 5

O item 5 (votacao com login, prazo, multicast UDP para notas e representacao externa JSON/XML/Protocol Buffers) e um sistema maior e pode ser construído sobre esta base TCP/Streams.
