# CHECKLIST - VassCommerce: IoC/DI (Controllers x Models)

## Separação confirmada

- [x] Controladores não fazem `new` de implementações; dependem de interfaces.
  `ProdutoController` recebe `ProdutoModel` no construtor e nunca instancia `ProdutoModelMemoria` ou `ProdutoModelSql`.
- [x] Há duas implementações `@Service` de `ProdutoModel`.
  `ProdutoModelMemoria` (`@Profile("mem")`) e `ProdutoModelSql` (`@Profile("sql")`, banco simulado).
- [x] A seleção é feita por **@Profile**.
  `spring.profiles.active=mem` é o padrão; `sql` pode ser ativado na execução.
- [x] A injeção é por construtor; não há `@Autowired` em campo.
- [x] `ProdutoController` continua retornando **201/200/404**.
  `POST /produto` -> 201 + `Location`; `GET /produto/{id}` -> 200 ou 404; `GET /produto` -> 200.
- [x] A validação de `ProdutoCreateRequest` continua ativa com `@Valid`.
- [x] O JSON de erro 400 continua padronizado por `@RestControllerAdvice`.
- [x] O controller faz o mapeamento `ProdutoCreateRequest -> Produto` e `Produto -> ProdutoResponse`; a camada model trabalha com a entidade `Produto`.

## Estrutura principal

```text
src/main/java/br/com/vasscommerce/
├── VassCommerceApplication.java
├── controller/ProdutoController.java
├── dto/ProdutoCreateRequest.java
├── dto/ProdutoResponse.java
├── entity/Produto.java
├── mapper/ProdutoMapper.java
├── model/ProdutoModel.java
├── model/ProdutoModelMemoria.java
├── model/ProdutoModelSql.java
└── exception/ApiError.java, RestExceptionHandler.java

src/main/resources/application.properties
src/test/java/.../ProdutoModelTest.java
postman/VassCommerce-IoC-DI.postman_collection.json
```

## Como demonstrar a troca de implementação

### Profile `mem`

```bash
mvn spring-boot:run
```

Esperado no log:

```text
ProdutoModel em uso: MEMÓRIA (profile 'mem')
```

O primeiro `POST /produto` recebe id `1`.

### Profile `sql`

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=sql
```

Esperado no log:

```text
ProdutoModel em uso: SQL simulado (profile 'sql')
```

O primeiro `POST /produto` recebe id `1001`.

A coleção `postman/VassCommerce-IoC-DI.postman_collection.json` contém os testes de 201, 200, 404 e 400 e pode ser executada nos dois profiles.

## Respostas esperadas

| Requisição | Resultado |
|---|---|
| `POST /produto` com corpo válido | `201 Created` + `Location` + `ProdutoResponse` |
| `GET /produto/{id}` existente | `200 OK` |
| `GET /produto?nome=...&valorMinimo=...&valorMaximo=...` | `200 OK` |
| `GET /produto/999999` | `404 Not Found` |
| `POST /produto` inválido | `400 Bad Request` com `ApiError` |
| `GET /produto/abc` | `400 Bad Request` com `ApiError` |
| JSON malformado em `POST /produto` | `400 Bad Request` com `ApiError` |

> Observação: o arquivo `target/` não faz parte da entrega. Ele é gerado pelo Maven e está ignorado pelo `.gitignore`.
