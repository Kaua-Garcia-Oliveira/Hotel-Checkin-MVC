#  Sistema de Check-in de Hotel

**Aluno:** Kauã · **Matrícula:** 1203947  
**Disciplina:** Aplicações para Internet  
**Entrega:** 18/05/2026

---

##  Sobre o Projeto

Sistema web de gerenciamento de check-in de hotel desenvolvido em **Java** com padrão de arquitetura **MVC** (Model-View-Controller), utilizando **Servlet + JSP + MySQL**. O sistema permite gerenciar hóspedes, quartos, reservas e o processo completo de check-in e checkout.

---

##  Arquitetura MVC

```
hotel-checkin/
├── src/main/java/com/hotel/
│   ├── model/            ← MODEL: entidades/POJOs
│   │   ├── Hospede.java
│   │   ├── Quarto.java
│   │   ├── Reserva.java
│   │   └── CheckIn.java
│   ├── dao/              ← DAO: acesso ao banco de dados
│   │   ├── HospedeDAO.java
│   │   ├── QuartoDAO.java
│   │   ├── ReservaDAO.java
│   │   └── CheckInDAO.java
│   ├── service/          ← SERVICE: regras de negócio
│   │   ├── HospedeService.java
│   │   ├── ReservaService.java
│   │   └── CheckInService.java
│   ├── controller/       ← CONTROLLER: servlets (rotas)
│   │   ├── HomeController.java
│   │   ├── HospedeController.java
│   │   ├── QuartoController.java
│   │   ├── ReservaController.java
│   │   └── CheckInController.java
│   └── util/
│       └── ConnectionFactory.java
└── src/main/webapp/
    ├── css/style.css
    └── WEB-INF/
        ├── web.xml
        └── views/        ← VIEW: páginas JSP
            ├── index.jsp
            ├── hospedes/
            ├── quartos/
            ├── reservas/
            └── checkins/
```

---

## 🗄️ Banco de Dados

4 tabelas principais:

| Tabela | Descrição |
|--------|-----------|
| `hospedes` | Cadastro de hóspedes (nome, CPF, e-mail, telefone) |
| `quartos` | Quartos do hotel (número, tipo, diária, disponibilidade) |
| `reservas` | Reservas vinculando hóspede + quarto + datas |
| `checkins` | Registro do check-in e checkout efetivo |

---

##  Como Rodar

### Pré-requisitos
- Java 11+
- Apache Tomcat 10+
- MySQL 8+
- Maven 3.6+

### Passos

**1. Criar o banco de dados:**
```bash
mysql -u root -p < sql/hotel_checkin.sql
```

**2. Configurar a conexão** em `src/main/java/com/hotel/util/ConnectionFactory.java`:
```java
private static final String USER = "root";
private static final String PASSWORD = "sua_senha_aqui";
```

**3. Compilar e gerar o WAR:**
```bash
mvn clean package
```

**4. Deploy:**  
Copie `target/hotel-checkin.war` para a pasta `webapps/` do Tomcat.

**5. Acessar:**  
`http://localhost:8080/hotel-checkin`

---

##  Funcionalidades (CRUD completo)

### Hóspedes
-  Cadastrar hóspede
-  Listar / buscar por nome
-  Editar dados
-  Excluir

### Quartos
-  Cadastrar quarto (Simples / Duplo / Suíte)
-  Listar com status de disponibilidade
-  Editar
-  Excluir

### Reservas
-  Criar reserva (hóspede + quarto + datas)
-  Confirmar reserva
-  Cancelar reserva
-  Listar todas

### Check-in / Checkout
-  Realizar check-in (a partir de reserva confirmada)
-  Realizar checkout (libera o quarto automaticamente)
-  Listar check-ins ativos e histórico

---

##  Fluxo do Sistema

```
Cadastrar Hóspede → Cadastrar Quarto → Criar Reserva → Confirmar Reserva → Check-in → Checkout
```

---

##  Tecnologias

- **Java 11** — linguagem principal
- **Jakarta Servlet 5.0** — controllers (rotas HTTP)
- **JSP + JSTL** — views dinâmicas
- **MySQL 8** — banco de dados relacional
- **JDBC** — acesso ao banco via DAO
- **Maven** — gerenciamento de dependências
- **Tomcat 10** — servidor de aplicação
- **CSS puro** — estilização da interface

---

##  Padrões Utilizados

- **MVC** — separação de responsabilidades (Model / View / Controller)
- **DAO** (Data Access Object) — encapsula todo acesso ao banco
- **Service Layer** — contém as regras de negócio (validações, fluxos)
-- MVC — separação entre Model, View e Controller
- DAO (Data Access Object) — responsável pelo acesso ao banco de dados
- Service Layer — regras de negócio e validações do sistema
- CRUD completo — cadastro, listagem, edição e remoção de dados
- Arquitetura em camadas — organização do projeto para facilitar manutenção
Projeto desenvolvido para fins acadêmicos na disciplina de Aplicações para Internet.
