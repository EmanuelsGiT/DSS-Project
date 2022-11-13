# Use Case: Configurar Corridas

## Descrição:
O jogador configura a corrida.

## Cenário:
Após considerar as características do circuito, do carro e do piloto, o Francisco decide alterar a afinação (possível
por se tratar de um C2) e aumenta a downforce de 0.5 (valor neutro) para 0.7. Ao
aumentar a downforce, sacrifica alguma velocidade para ter maior estabilidade em
curva. Deste modo, troca alguma capacidade de ultrapassar em recta por capacidade de ultrapassar em curva, 
compensando a menor propensão para o risco do piloto.

## Pré-Condição:
Os jogadores estão registados no campeonato.

## Pós-Condição:
O jogador está pronto.

## Fluxo Normal:
1. O sistema apresenta o circuito e a situação meteorológica; 
2. O jogador decide se pretende alterar os pneus;
3. Sistema verifica se o valor para o tipo de pneus fornecido pelo jogador é válido;
4. O jogador decide se pretende alterar o modo de funcionamento do motor;
5. Sistema verifica se o valor para o modo de funcionamento do motor fornecido pelo jogador é válido;
6. O jogador decide se pretende alterar a afinação do carro;
7. Sistema verifica se a afinação está entre 0 e 1.
8. Sistema verifica se o jogador fez 2/3 ou menos das alterações de afinação possiveis.
9. O jogador, no fim de fazer alterações, indica que está pronto;
