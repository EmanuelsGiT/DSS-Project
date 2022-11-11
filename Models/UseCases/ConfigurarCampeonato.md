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
1. Sistema procura campeonatos;
2. Sistema apresenta lista de campeoantos;
3. Jogador escolhe o campeonato que se pretende inscrever;
4. Sistema apresenta campeonato;
5. Sistema apresenta menu onde escolhe o carro e o piloto;
6. Jogador escolhe escolher carro;

6. Sistema procura categoria de carros;
7. Sistema apresenta categoria de carros;
8. Utilizador escolhe uma categoria;
9. Sistema procura carros disponíveis;
10. Sistema apresenta carros disponíveis;
11. Utilizador escolhe um carro;
12. Sistema procura pilotos disponíveis;
13. Sistema apresenta pilotos disponíveis;
14. Utilizador escolhe um piloto; 
15. Utilizador confirma inscrição;
16. Sistema efetua registo da inscrição.

## Fluxo Alternativo(1) [Utilizador decide trocar de categoria]

## Fluxo de Exceção(1): [Utilizador decide cancelar inscrição]
1. Utilizador decide nao inscrever-se no campeonato.
2. Sistema retorna ao menu principal.



------------------
- Carro -> 
- Piloto -> 
