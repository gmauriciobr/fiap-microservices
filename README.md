# Trabalho Microservices Development

- Professor: Andre Pontes Sampaio

#### Grupo

- Guilherme Mauricio
- Douglas Mateus
- João Rafael

#### Definição

Este documento tem como objetivo descrever os requisitos para a elaboração de um projeto usado como avaliação da disciplina.

#### Video

### Diagrama
![Diagrama](https://user-images.githubusercontent.com/7011282/164956282-09c4b4b3-eebb-4aa0-9acd-684fa8f22c21.png)

#### Especificações

- Java 11
- Spring Framework
- Spring Data
- SpringFox
- Lombok
- Flyway
- Gradle
- Docker
- MySQL 8
- RabbitMQ

### Testes

- Cadastro
![Teste Cadastro](https://user-images.githubusercontent.com/7011282/164955375-a8aa0cc1-f7e7-4791-b3fc-42bedd45ef01.png)

- Vendas
![Teste Venda](https://user-images.githubusercontent.com/7011282/164955393-432b4137-0b72-438e-80f2-91a5d6717970.png)
     
- Consumer
![Teste Consumer](https://user-images.githubusercontent.com/7011282/164955416-23649172-5a07-4f25-8d29-d721aaefc85e.png)


#### Requisitos

- Docker

#### Como executar o projeto:

\*\* O terminal deve estar na pasta raiz do projeto

1. Executar o comando para fazer o build e aguardar a execução:

   ```
      docker-compose build
   ```

2. Depois de executado o comando anterior, executar o seguinte:

   ```
      docker-compose up -d
   ```

#### Link

**Swagger:**

- Cadastro: http://localhost:8080
- Venda: http://localhost:8081
