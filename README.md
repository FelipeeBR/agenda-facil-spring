# Sistema de agendamento de serviços - Agenda Fácil

### O Que é?
É um sistema de serviços com agendamento online que permitem que clientes marquem compromissos, consultas ou serviços, otimizando o processo para ambos, clientes e prestadores de serviço.
Esse é um sistema desenvolvido para colocar em pratica e aprendizado sobre Java Spring e Microsserviços RabbitMQ

### Tecnologias
- Spring Boot
- Jpa
- Flyway
- RabbitMQ

### Testes
- Mockito
- Junit5

### Principais características
#### MVC (Model-View-Controller)
- Controller: AddressController, AgendaController, UserControllerV1j, etc.
- Model: Entidades como Address, Agenda, User, Role
- DTOs: Classes de transferência de dados separadas

### Camadas Adicionais Típicas do Spring
- Repository: Camada de acesso a dados
- Service: Camada de negócio/lógica
- DTO: Padrão Data Transfer Object para transferência entre camadas

#### Estrutura de Pacotes por Funcionalidade
- controller: Controladores REST
- model: Entidades JPA
- dto: Objetos de transferência
- service: Lógica de negócio
- repository: Interfaces de acesso a dados
- exceptions: Tratamento de exceções

#### DTO Pattern: Separação entre entidades de domínio e objetos de transferência
#### Exception Handling: Tratamento centralizado de exceções
#### Layered Architecture: Separação clara de responsabilidades

### O RabbitMQ é introduzido sempre que você tem uma tarefa que pode ou deve ser feita fora do ciclo de vida principal da requisição do usuário.
![Captura de tela 2025-08-25 225158](https://github.com/user-attachments/assets/bb97660e-74a4-40a6-92d9-e715f0e5def5)
