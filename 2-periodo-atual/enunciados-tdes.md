
## TDE 1
Você deverá criar a classe Empresa (String cnpj, String nome, int numEmpregados) colocando o método construtor, gets/sets e métodos de validação (veja os exemplos presentes no Exemplo #01). Crie a classe controller.CtrlPrograma e coloque nela o método main. Nesse método instancie quatro objetos Empresa e coloque-os em um objeto List. Ordene esses objetos pelo nome e liste-os na tela. Depois ordene esses objetos pelo número de Empregados e liste-os. Depois disso, recupere o objeto Class associado à classe e liste os atributos e métodos presentes na classe.

A entrega deverá ser feita com a postagem dos arquivos Empresa.java e CtrlPrograma.java (são serão aceitos outros formatos como .zip, .doc ou .pdf). Coloque no cabeçalho de cada classe um comentário com seu nome completo.

## TDE 2 

Você deverá implementar um programa que leia o nome completo de uma classe e, com o uso do método Class.forName, instancie um objeto dessa classe. Após instanciar o objeto, você deverá determinar o valor de cada um dos seus atributos. Para isso, veja quais são os atributos presentes na classe e, para cada um deles, solicite ao usuário que indique o valor a ser determinado ao atributo e invoque a execução do método "set" vinculado ao atributo. Por questão de simplificação, você só fará essa ação para os atributos "int" e "String". Ao final, chame o método toString() para vermos que os valores foram atribuídos ao objeto criado. Como base, pegue a parte final do trecho de código do CtrlPrograma do primeiro exemplo. A classe a ser instanciada pode ser qualquer uma; logo use as classes do exemplo, mais a classe Empresa do TDE #01 para seus testes.

A entrega deverá ser feita com a postagem do arquivo CtrlPrograma.java (são serão aceitos outros formatos como .zip, .doc ou .pdf). Coloque no cabeçalho de cada classe um comentário com seu nome completo. Não haverá alteração da data e hora de entrega deste TDE.


#TDE 3 

Imagine que você foi contratado para desenvolver um simulador de corridas de cavalos. O programa deverá simular uma corrida entre vários cavalos, sendo que cada cavalo deverá ser executado por uma thread diferente. A pista terá uma distância de 400 metros  e participarão da corrida, por exemplo, 5 cavalos.

Cada cavalo deverá possuir:

    um nome;
    uma posição atual na pista;
    uma velocidade;
    uma thread responsável por sua execução.

A corrida funcionará da seguinte maneira:

    Todos os cavalos começam na posição 0.
    Cada cavalo deverá executar sua própria thread.
    A cada instante da corrida, o cavalo deverá sortear sua velocidade utilizando a classe Random.
    A velocidade sorteada deverá ser um número entre 1 e 10.
    A posição do cavalo deverá ser atualizada de acordo com a velocidade sorteada.
    Depois de cada movimento, o programa deverá exibir algo semelhante a:

---- Cavalo Relâmpago avançou 7 metros. Posição: 42m
---- Cavalo Trovão avançou 3 metros. Posição: 38m
---- Cavalo Ventania avançou 9 metros. Posição: 51m

    A corrida termina quando um dos cavalos atingir ou ultrapassar os 400 metros. 
    Ao final, deverá ser informado qual cavalo venceu.


⚠️ Um detalhe importante: Existe uma informação que é compartilhada por todas as threads: o resultado da corrida. Quando um cavalo ultrapassar a linha de chegada, ele deverá registrar que foi o vencedor. O problema é que vários cavalos podem chegar praticamente ao mesmo tempo. Portanto, não basta verificar:

if (posicao >= 400) {
     vencedor = nome;
}
O programa deverá garantir que apenas um cavalo possa ser declarado vencedor. Para isso, utilize uma das técnicas de sincronização estudadas em aula:

    um método synchronized, ou
    um bloco synchronized.

Requisitos técnicos. O programa deverá obrigatoriamente:

    Criar uma classe que represente um cavalo.
    Utilizar Thread ou implementar Runnable.
    Criar uma thread para cada cavalo.
    Utilizar Random para determinar a velocidade de cada cavalo.
    Executar os cavalos simultaneamente.
    Utilizar synchronized para controlar o acesso à informação compartilhada.
    Exibir a evolução da corrida no console.
    Informar o vencedor ao final.

⭐ Desafio extra: Depois de fazer o programa funcionar, modifique-o para mostrar a classificação final dos cavalos. Por exemplo:

🏆 1º lugar: Relâmpago
🥈 2º lugar: Ventania
🥉 3º lugar: Trovão
4º lugar: Foguete
5º lugar: Tempestade

Faça a entrega dos arquivos .java (Não aceitarei .zip, .pdf, ou outros formatos) para resolução do TDE e coloque na primeira linha um comentário com seu nome.