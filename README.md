# Biblioteca API (Spring Boot)

Aplicação simples em Java Spring Boot que demonstra um CRUD completo para livros usando padrão Service + Mapper, com persistência em banco relacional H2 em memória.

## Stack
- Java 17 + Spring Boot 3.2
- Spring Web
- Spring Data JPA
- H2 (relacional, em memória)

## Executando
1. Instale Java 17 e Maven.
2. Rode a aplicação:
   ```bash
   mvn spring-boot:run
   ```
3. Acesse a API em `http://localhost:8080/api/books`.
4. Console do H2: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:library`, usuário `sa`).

## Endpoints
- **POST** `/api/books` – cadastra um livro.
- **PUT** `/api/books/{id}` – altera dados do livro.
- **GET** `/api/books/{id}` – consulta individual.
- **GET** `/api/books` – listagem com todos os livros.
- **DELETE** `/api/books/{id}` – exclusão.

Todas as entradas usam `application/json` com payload:
```json
{
  "title": "Domain-Driven Design",
  "author": "Eric Evans",
  "category": "Arquitetura",
  "publicationYear": 2003
}
```

## Design
- **BookController** recebe requisições REST.
- **BookService** concentra regras e validações, usando `ResponseStatusException` para retornos HTTP elegantes.
- **BookMapper** aplica o padrão Mapper para converter entre DTOs e entidades JPA.
- **BookRepository** (Spring Data JPA) abstrai o acesso ao banco.

## Requisitos atendidos
- Cadastro, alteração, consulta, listagem e exclusão.
- Endpoints REST documentados acima.
- Persistência em banco relacional (H2) configurada em `application.properties`.
- Padrão de camadas (Controller → Service → Mapper/Repository) para manter o código limpo e coeso.
