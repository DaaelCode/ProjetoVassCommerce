# VassCommerce - Parte 02 (IoC/DI)

Projeto da etapa de IoC/DI do VassCommerce.

## Requisitos

- Java 17 ou superior
- Maven

## Executar com a implementação em memória

```bash
mvn spring-boot:run
```

Profile ativo por padrão: `mem`.

## Executar com a implementação SQL simulada

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=sql
```

## Testes

```bash
mvn test
```

## Postman

Importe:

`postman/VassCommerce-IoC-DI.postman_collection.json`

A coleção demonstra criação (`201`), consulta (`200`), recurso inexistente (`404`), validação/JSON inválido (`400`) e permite executar o mesmo fluxo nos dois profiles.
