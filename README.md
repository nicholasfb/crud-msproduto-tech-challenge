# PÓS TECH - [Produtos]

## Features do sistema

### Cadastrar um novo produto

Esse cadastro será efetuado pela rota POST /api/product, a documentação deste endpoint pode ser
acessada [aqui](http://localhost:8082/swagger-ui/index.html#/product-controller/createProduct)

#### Atualizar um produto

Após o cadastro, caso necessário poderemos atualizar o registro utilizando o endpoint PUT
/api/product/{id}. A documentação deste endpoint pode ser acessada
aqui [aqui](http://localhost:8082/swagger-ui/index.html#/product-controller/editProduct)

### Buscar por um pedido

Para obter os dados de cadastro do produto, pode-se utilizar o endpoint GET /api/order/{id}. Sua
documentação
está [aqui](http://localhost:8082/swagger-ui/index.html#/product-controller/findProductById)

#### Consultar todos os produtos cadastrados

Para obter uma lista com todos os pedidos cadastrados, utiliza-se o endpoint GET /api/product.
Cuja documentação
está [aqui](http://localhost:8082/swagger-ui/index.html#/product-controller/findProducts).

### Atualizar ou cadastrar produtos por planilha

Para atualizar ou cadastrar um ou mais produtos por uma planilha, pode-se utilizar o endpoint PUT
/api/product/batch.
Sua documentação
está [aqui](http://localhost:8082/swagger-ui/index.html#/product-controller/editProducts).
O arquivo, que possui um exemplo no .\resources\product.csv, deve ser um CSV separado por ,(virgula)
seguindo a seguinte estrutura:

* id do produto
* descrição
* quantidade
* preço de compra
* preço de venda
* estoque mínimo

### Atualizar quantidade do item

A quantidade do item é atualizado de forma automatica via comunicação SQS com o sistema de pedidos

## Inicializando localmente a API

### Docker

Para inicializar a aplicação localmente é necessário o uso do Docker, caso não tenha instalado, faça
o download [aqui](https://docs.docker.com/engine/install/).

#### Docker compose

O docker compose, arquivo responsável por configurar os containers, está no package .\docker,
portanto pode rodar através da sua IDE diretamente ou pelo comando `docker compose up` diretamente
na pasta do arquivo docker-compose.yml

#### Iniciando a aplicação

Utilize a IDE que melhor lhe agrade para iniciar a API

## Utilizando a API

Para fazer requisições a API é necessário o uso de algum software que tenha essa funcionalidade (
Postman, Insomnia, Apidog, ...) ou pode utilizar o terminal para rodar comandos cURL.

Caso utilize o postman, pode importar a collection que está no package .\postman
