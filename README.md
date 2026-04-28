# API de Salas de Reunião

**Nome:** Matheus Bortolotto  
**RM:** RM555189

## Descrição

API REST desenvolvida em Java com Spring Boot para gerenciamento de salas de reunião e reservas. O projeto segue arquitetura em camadas baseada em SOA, separando responsabilidades entre Controller, Service, Repository e DTOs.

A aplicação permite criar, listar, buscar, atualizar e remover salas, além de criar, listar e cancelar reservas. Também possui regra de negócio para impedir reservas conflitantes na mesma sala e no mesmo intervalo de horário.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- H2 Database
- Swagger / OpenAPI
- Spring Cache
- JUnit 5
- Mockito

## Diferenciais implementados

- Paginação nas consultas
- Filtros de busca
- Tratamento global de exceções
- Logging estruturado
- Cache com Spring Cache

## Como executar o projeto

```bash
mvn spring-boot:run
```

A aplicação será executada em:

```text
http://localhost:8080
```

## Acessos úteis

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

H2 Console:

```text
http://localhost:8080/h2-console
```

Configuração do H2:

```text
JDBC URL: jdbc:h2:mem:salasdb
Username: sa
Password: deixe em branco
```

## Usuários para login

```text
Usuário: admin
Senha: admin123
```

```text
Usuário: usuario
Senha: 123456
```

## Autenticação

Primeiro, faça login para gerar o token JWT.

### Login

```http
POST /api/auth/login
```

Body:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Resposta:

```json
{
  "token": "TOKEN_GERADO_AQUI",
  "tipo": "Bearer"
}
```

Depois, envie o token nos demais endpoints:

```text
Authorization: Bearer TOKEN_GERADO_AQUI
```

## Endpoints disponíveis

### Salas

#### Criar sala

```http
POST /api/salas
```

```json
{
  "nome": "Sala Reunião 1",
  "capacidade": 10,
  "localizacao": "1º andar"
}
```

#### Listar salas

```http
GET /api/salas?page=0&size=10
```

Com filtro:

```http
GET /api/salas?busca=andar&page=0&size=10
```

#### Buscar sala por ID

```http
GET /api/salas/1
```

#### Atualizar sala

```http
PUT /api/salas/1
```

```json
{
  "nome": "Sala Atualizada",
  "capacidade": 15,
  "localizacao": "2º andar"
}
```

#### Remover sala

```http
DELETE /api/salas/1
```

### Reservas

#### Criar reserva

```http
POST /api/reservas
```

```json
{
  "salaId": 1,
  "dataHoraInicio": "2026-05-10T09:00:00",
  "dataHoraFim": "2026-05-10T10:00:00",
  "responsavel": "Matheus Ramos"
}
```

#### Listar reservas

```http
GET /api/reservas?page=0&size=10
```

Filtrar por sala:

```http
GET /api/reservas?salaId=1&page=0&size=10
```

Filtrar por período:

```http
GET /api/reservas?inicio=2026-05-10T00:00:00&fim=2026-05-10T23:59:59&page=0&size=10
```

#### Cancelar reserva

```http
PATCH /api/reservas/1/cancelar
```

## Testes

Para executar os testes:

```bash
mvn test
```

Foram criados testes para validar:

- Criação de reserva sem conflito
- Bloqueio de reserva conflitante
- Validação de intervalo de datas
