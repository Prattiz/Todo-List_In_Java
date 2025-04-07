# To-Do List
![rocket (1)](https://github.com/Prattiz/gestao_vagas/assets/135062914/55c95257-4af3-41ac-b017-94c582fd5ac2)

This is the To-Do List project repository. The goal of this project is to provide an API to manage users and their daily tasks in a to-do list.


## Funcionalidades

- **Cadastro de Usuário:** Permite o registro de novos usuários com nome, username e senha
- **Autenticação:** Cada usuário pode ser autenticado via credenciais únicas
- **Criação de Tarefas:** Usuários autenticados podem criar tarefas com título, descrição, prioridade e data de início/fim
- **Listagem de Tarefas por Usuário:** Permite visualizar apenas as tarefas criadas pelo próprio usuário
- **Atribuição de Autor:** Toda tarefa possui vínculo com o autor (usuário criador)
- **Documentação via Swagger:** API documentada automaticamente para testes e integração

---

## Requisitos Funcionais (RF) ✅

- **RF01:** O sistema deve permitir o cadastro de usuários.
- **RF02:** O sistema deve permitir que o usuário crie tarefas.
- **RF03:** Cada tarefa criada deve estar vinculada ao usuário criador.
- **RF04:** O sistema deve validar o tamanho do título da tarefa (máx. 50 caracteres).
- **RF05:** O sistema deve apresentar uma documentação da API via Swagger.

---

## Requisitos Não Funcionais (RNF) ✅

- **RNF01:** A API deve ser desenvolvida em Java utilizando Spring
- **RNF02:** O sistema deve persistir os dados em um banco de dados relacional (H2)
- **RNF03:** As senhas devem ser armazenadas de forma segura com hashing (BCrypt)
- **RNF04:** O código deve ser documentado e seguir boas práticas com o uso de Lombok


---

## Regras de Negócio (RN) ✅

- **RN01:** Não é permitido criar usuários com usernames duplicados
- **RN02:** Tarefas devem ter título com no máximo 50 caracteres
- **RN03:** Tarefas só podem ser criadas por usuários existentes
- **RN04:** Cada tarefa pertence exclusivamente a um usuário


---

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
  - spring-boot-starter-web
  - spring-boot-starter-data-jpa
  - spring-boot-devtools
  - spring-boot-starter-test
- **H2 Database** (banco de dados em memória)
- **BCrypt** (criptografia)
- **Lombok** 
- **Swagger/OpenAPI** (documentação da API)

---

## Testar a API com Swagger

Acesse:

http://localhost:8080/swagger-ui/index.html

Lá você pode testar todos os endpoints pelo navegador




---

## 💡 Autor

Desenvolvido por **Thiago Pratti** - [LinkedIn](https://www.linkedin.com/in/thiago-pratti-de-mendonca/)

---

