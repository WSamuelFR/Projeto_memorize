Gerenciador de Cartões (Java SE + Swing)
Este é um projeto de aplicação de desktop em Java SE, desenvolvido para gerenciar e visualizar cartões de informação. A interface do usuário é construída com a biblioteca padrão Swing, utilizando uma abordagem de design moderno para um visual mais limpo e agradável. A aplicação opera com um modelo de dados em memória, o que significa que os dados não são persistidos após o fechamento do programa.

Estrutura do Projeto
A estrutura de pastas segue a convenção padrão para projetos Java. O código fonte está organizado em um pacote projeto_save dentro da pasta src.

projeto_save/
├── src/
│   └── projeto_save/
│       ├── Card.java
│       ├── CardManager.java
│       ├── DetailsDialog.java
│       ├── Main.java
│       └── Tabela.java
└── ... (outros arquivos de projeto, como .classpath, .project, etc.)

Main.java: A classe principal da aplicação. Ela inicializa a janela principal (JFrame), o painel de listagem de cartões e o campo de busca.

Card.java: O modelo de dados que representa um cartão. Ele armazena o ID, título, descrição e a data de criação, além de conter uma lista de objetos Tabela.

Tabela.java: O modelo de dados para as informações detalhadas, que compõem uma "tabela" dentro de um cartão.

CardManager.java: A classe de gerenciamento de dados. Ela atua como uma camada de "serviço" que manipula a coleção de cartões e tabelas na memória.

DetailsDialog.java: Uma classe JDialog que exibe os detalhes de um cartão selecionado, permitindo a edição e o gerenciamento das linhas de tabela associadas.

Funcionalidades
Dashboard de Cartões: Exibe uma lista de cartões com título, descrição e data de criação.

Busca Interativa: Permite pesquisar cartões por título ou descrição em tempo real.

Criação de Cartões: Um diálogo modal para adicionar novos cartões à lista.

Visualização e Edição de Detalhes: Ao clicar em um cartão, uma nova janela é aberta para mostrar e permitir a edição de todos os detalhes.

Gerenciamento de Tabelas: Na visualização de detalhes, é possível adicionar e deletar linhas de tabela.

Design Moderno: A interface utiliza uma paleta de cores consistente de tons de azul, cinza e branco para um visual limpo e profissional.

Como Executar o Projeto
Pré-requisitos: Certifique-se de ter um Java Development Kit (JDK) instalado em sua máquina. O projeto foi desenvolvido com uma versão superior a 1.8.

Abrir na IDE: Abra a pasta raiz do projeto em uma IDE como Eclipse, IntelliJ IDEA ou VS Code.

Executar: Dentro da sua IDE, localize a classe Main.java e execute-a. A aplicação de desktop será iniciada.

Nota: Como os dados não são persistidos, a aplicação irá iniciar com dados de exemplo pré-definidos no CardManager a cada execução.
