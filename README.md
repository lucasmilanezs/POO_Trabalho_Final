# Biblioteca API (Spring Boot)

Aplicação em Java Spring Boot que demonstra um CRUD completo e sofisticado para livros usando padrão Service + Mapper, com persistência em banco relacional H2 em memória, filtros avançados e operações de empréstimo/devolução.

## Stack
- Java 17 + Spring Boot 3.2
- Spring Web
- Spring Data JPA
- H2 (relacional, em memória)
- Spring MVC Test (sanidade dos endpoints)

## Executando
1. Instale Java 17 e Maven.
2. Rode a aplicação:
   ```bash
   mvn spring-boot:run
   ```
3. Acesse a API em `http://localhost:8080/api/books`.
4. Console do H2: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:library`, usuário `sa`).

## Endpoints
- **POST** `/api/books` – cadastra um livro (ISBN único).
- **PUT** `/api/books/{id}` – altera dados do livro.
- **GET** `/api/books/{id}` – consulta individual.
- **GET** `/api/books` – listagem paginada com filtros opcionais (`author`, `category`, `title`, `available`, `yearFrom`, `yearTo`).
- **PATCH** `/api/books/{id}/checkout` – registra empréstimo e bloqueia novo empréstimo enquanto indisponível.
- **PATCH** `/api/books/{id}/return` – registra devolução e libera nova retirada.
- **DELETE** `/api/books/{id}` – exclusão.

Todas as entradas usam `application/json` com payload:
```json
{
  "isbn": "978-0134494166",
  "title": "Domain-Driven Design",
  "author": "Eric Evans",
  "category": "Arquitetura",
  "publicationYear": 2003,
  "summary": "Breve sinopse opcional"
}
```

## Design
- **BookController** recebe requisições REST.
- **BookService** concentra regras e validações (unicidade de ISBN, ano plausível, fluxo de empréstimo/devolução), usando `ResponseStatusException` para retornos HTTP elegantes.
- **BookMapper** aplica o padrão Mapper para converter entre DTOs e entidades JPA.
- **BookRepository** (Spring Data JPA) abstrai o acesso ao banco com Specifications para filtros.

## Requisitos atendidos
- Cadastro, alteração, consulta, listagem e exclusão.
- Filtro avançado com paginação por autor, categoria, título, disponibilidade e intervalo de anos.
- Fluxo de empréstimo e devolução com validações de disponibilidade.
- Endpoints REST documentados acima.
- Persistência em banco relacional (H2) configurada em `application.properties`.
- Padrão de camadas (Controller → Service → Mapper/Repository) para manter o código limpo e coeso.
