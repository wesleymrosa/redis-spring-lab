
# Redis Spring Lab

📚 **Projeto Didático para Estudo de Cache com Redis e Docker**

Este repositório contém um projeto desenvolvido com fins **exclusivamente didáticos**, voltado para o **estudo de cache com Redis**, integrado a uma aplicação Spring Boot rodando em containers Docker. A aplicação simula um sistema de cadastro de veículos em manutenção, permitindo estudar o comportamento do cache em chamadas REST.

> **O que é cache?**
> 
> Cache é uma técnica de armazenamento temporário de dados que visa melhorar a performance das aplicações, reduzindo o tempo de resposta e a sobrecarga nos sistemas de origem (como o banco de dados). Ao invés de buscar repetidamente os mesmos dados no banco, a aplicação os armazena em memória por um tempo determinado.
>
> **E o Redis?**
>
> O Redis é um banco de dados em memória, do tipo chave-valor (key-value), amplamente utilizado como mecanismo de cache por sua velocidade e eficiência. Ele armazena os dados em RAM, o que permite recuperações extremamente rápidas, ideal para sistemas que exigem alta performance e baixa latência.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA
- Spring Cache (Redis)
- PostgreSQL (via Docker)
- Redis (via Docker)
- Swagger / Springdoc OpenAPI
- Maven
- Postman
- IntelliJ IDEA

---

## 💡 Competências e Habilidades Aplicadas

- Desenvolvimento de APIs RESTful com Spring Boot
- Integração de cache com Redis utilizando Spring Cache
- Manipulação de banco de dados PostgreSQL com JPA/Hibernate
- Contêineres com Docker e orquestração via `docker-compose`
- Documentação interativa de APIs com Swagger
- Testes e simulações com Postman
- Uso de DTOs para isolamento entre camadas
- Organização do projeto em arquitetura MVC tradicional (Controller-Service-Repository)

---

## 📁 Estrutura do Projeto

```
br.wesley.redis
├── controller       # Endpoints REST
├── dto             # Data Transfer Object (placa, modelo, oficina, dataEntrega)
├── mapper          # Conversão entre Entity e DTO
├── model           # Entidade JPA (VeiculoManutencao)
├── repository      # Interface JpaRepository
├── service         # Regras de negócio e operações com cache
└── RedisSpringLabApplication.java
```

---

## 🏛️ Arquitetura de Cache com Redis

### 1. Primeira Carga: Banco de Dados
- A primeira vez que você chama o endpoint `GET /veiculos`, o sistema **consulta o banco de dados PostgreSQL**.
- Os dados são convertidos para DTOs e **armazenados no Redis automaticamente**.
- Esse processo é relativamente **mais demorado**, pois exige acesso ao disco, conexão JDBC, transformação e resposta HTTP.

### 2. Segunda Carga: Cache Redis
- Ao repetir a chamada `GET /veiculos`, o sistema **não acessa mais o banco de dados**.
- Ele **lê diretamente do Redis**, retornando os mesmos dados com **alta performance** (em milissegundos).

### 3. Controle Manual de Cache
- Se você incluir novos dados após a primeira chamada, **eles não aparecem na listagem** até que o cache seja limpo.
- Para isso, criamos o endpoint:

```http
DELETE /veiculos/limpar-cache
```

Esse método zera o conteúdo do cache Redis para as chaves `veiculos` e `veiculoByPlaca`, forçando nova consulta ao banco na próxima chamada. Esse tipo de limpeza é especialmente útil em ambientes de teste ou aprendizado, onde o desenvolvedor precisa controlar exatamente o que está ou não armazenado no cache.

Internamente, essa operação utiliza a anotação `@CacheEvict` para excluir os registros do cache armazenado em memória no Redis. Assim, na próxima requisição, o sistema volta a consultar diretamente o banco de dados PostgreSQL, regenerando o cache automaticamente com os dados atualizados.

Esse comportamento é fundamental para entender o ciclo de vida do cache: **armazenamento → reutilização → invalidação → regeneração**.

> Embora este projeto seja didático, **essa mesma abordagem é amplamente utilizada em ambientes profissionais**, especialmente em aplicações que exigem controle fino sobre a consistência dos dados, como sistemas financeiros, logísticos ou de e-commerce com alta demanda. O uso de `@CacheEvict` garante previsibilidade e performance, ao mesmo tempo que evita dados obsoletos em produção.

---

## 🛠️ Docker e Postman (passo a passo)

```bash
# 1. Clonar o projeto
$ git clone https://github.com/wesleymrosa/redis-spring-lab.git
$ cd redis-spring-lab

# 2. Subir Redis e PostgreSQL com Docker
$ docker compose up -d

# Obs: Se a porta 5432 já estiver em uso, pare o container que está atrapalhando:
$ docker stop postgres_docker_spring_lab
$ docker rm postgres_docker_spring_lab

# 3. Compilar e executar a aplicação
$ mvn clean install
$ mvn spring-boot:run

# 4. Acessar a API via navegador ou Swagger UI
http://localhost:8080/swagger-ui.html
```

---

## 📄 JSONs de Teste (5 exemplos)

```json
{
  "placa": "AAA-1111",
  "modelo": "Fiat Uno",
  "oficina": "Oficina Central",
  "dataEntrega": "2025-05-05"
}
```
```json
{
  "placa": "BBB-2222",
  "modelo": "Gol",
  "oficina": "Auto Mecânica DF",
  "dataEntrega": "2025-05-10"
}
```
```json
{
  "placa": "CCC-3333",
  "modelo": "Onix",
  "oficina": "Oficina Real",
  "dataEntrega": "2025-05-12"
}
```
```json
{
  "placa": "DDD-4444",
  "modelo": "HB20",
  "oficina": "Manutenção Sul",
  "dataEntrega": "2025-05-15"
}
```
```json
{
  "placa": "EEE-5555",
  "modelo": "Kwid",
  "oficina": "Oficina Norte",
  "dataEntrega": "2025-05-20"
}
```

---

## 🧪 Como Testar a API

### ✅ Via Navegador (métodos GET)
```
http://localhost:8080/veiculos               # Buscar todos os veículos
http://localhost:8080/veiculos/AAA-1111      # Buscar veículo por placa
http://localhost:8080/swagger-ui/index.html  # Interface interativa do Swagger
```

### ✅ Via Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

### ✅ Via Postman
- Crie uma nova requisição com o método POST para:
  ```
  http://localhost:8080/veiculos
  ```
- Vá até a aba `Body`, selecione `raw` e `JSON`
- Cole um dos **JSONs de exemplo** listados acima
- Envie a requisição e observe o retorno

#### Testes adicionais:
- **GET**: listar todos os veículos
  ```
  http://localhost:8080/veiculos
  ```
- **GET**: buscar por placa
  ```
  http://localhost:8080/veiculos/AAA-1111
  ```
- **PUT**: atualizar veículo
  ```
  http://localhost:8080/veiculos/AAA-1111
  ```
  ```json
  {
    "placa": "AAA-1111",
    "modelo": "Gol G6",
    "oficina": "Oficina Central",
    "dataEntrega": "2025-05-12"
  }
  ```
- **DELETE**: deletar por placa
  ```
  http://localhost:8080/veiculos/AAA-1111
  ```
- **DELETE**: limpar cache
  ```
  http://localhost:8080/veiculos/limpar-cache
  ```

> Use o endpoint `/veiculos/limpar-cache` para forçar nova consulta ao banco de dados e observar o cache sendo renovado com dados atualizados.

---

## ✅ Considerações Finais

Este projeto oferece um ambiente ideal para **entender, testar e dominar** o uso de cache com Redis no contexto do Spring Boot. 
Foi estruturado com foco didático, clareza no código, e facilidade de teste com Postman e Swagger. Ao observar a diferença entre chamadas com e sem cache, você internaliza conceitos essenciais de desempenho e eficiência em aplicações web modernas.

> Essa abordagem de cache controlado também é **altamente recomendada em ambientes de produção**, quando aplicada com critérios claros, especialmente em sistemas de leitura intensiva que demandam alta performance com consistência eventual.

Sinta-se à vontade para clonar, modificar, e adaptar conforme sua necessidade de estudo!

---

## 👤 Autor
**Wesley Martins Rosa**  
Email: wesleymrosa@gmail.com  
GitHub: [github.com/wesleymrosa](https://github.com/wesleymrosa)  
LinkedIn: [linkedin.com/in/wesley-martins-rosa-5118aa15a](https://linkedin.com/in/wesley-martins-rosa-5118aa15a)

---

## 📅 Licença
Projeto com fins **educacionais**. Sinta-se à vontade para estudar, adaptar e aprimorar.
