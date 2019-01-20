# zagwork-code-challenge

Este projeto consolida construção do desafio proposto.

## Requisitos
Para rodar o projeto, é preciso:
- Java 8;
- NPM;
- CLI do Angular (`$ npm install -g @angular/cli`).

## Para executar
Backend:
`$ server/mvnw spring-boot:run`

Frontend:
`$ npm install && ng serve`

## Sobre a solução
Do lado do Backend, foi criado o projeto utilizando o Initializer do Spring Boot, versão 2. A persistência foi realizada sobre uma base in-memory. Foram escritos testes _end to end_ e um teste unitário para garantir o comportamento da regra de negócio de taxa de juros.

Os testes foram escritos na linguagem Groovy, utilizando o framework Spock. Prefiro este ao JUnit por questões de mais clareza e maior semântica.

Do lado do Frontend, o projeto foi criado utilizando o CLI do Angular, versão 7. Minha experiência anterior me permitiu ter bastante contato com o Angular JS (versão 1.6), mas optei por desbravar a nova versão do Angular.

Levei em torno de 3 horas para fazer este teste. Honestamente me faltaram escrever testes para o frontend, porque preciso pesquisar mais quanto ao que deve ser realmente testado. Como entendo que o NodeJS apoia bastante o Angular, é bem provável que os mecanismos de testes que conheço se apliquem aqui: mocking com Sinon, mock de requisição HTTP com Nock, etc.