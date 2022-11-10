# Use Case: Configurar Campeonato

## Descrição:
Utilizador inscreve-se num campeonato da lista de campeonatos.

## Cenário:
O Francisco e três amigos resolvem jogar um campeonato
de *Racing Manager*. O Francisco faz *login* como jogador, escolhe um campeonato
e avalia os circuitos que o compõem. Como a maioria são circuitos rápidos, decide
participar com um Ferrari 488 GTE (um carro da categoria C2). Escolhe como piloto
Battery Voltas, por considerar ser um piloto equilibrado em termos de desempenho.
Após inscrever-se, cada um dos amigos escolhe também o seu carro e piloto.

## Pré-Condição:
Utilizador autenticou-se como jogador e existe pelo menos um campeonato para jogar, um carro e um piloto.

## Pós-Condição:
Utilizador fica inscrito no campeonato.

## Fluxo Normal:
1. Escolhe o campeonato que se pretende inscrever;
2. Utilizador escolhe o carro;
3. Escolhe o piloto;
4. Inscreve-se.

## Fluxo de Exceção(1): [Utilizador decide cancelar inscrição]
1. Utilizador decide nao inscrever-se no campeonato.
2. Sistema retorna ao menu principal.

