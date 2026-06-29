# Estapar Parking — Backend Challenge

Sistema de gerenciamento de estacionamento desenvolvido como desafio técnico para a Estapar.

## Tecnologias

- Java 21
- Spring Boot 4.1
- Spring Data JPA
- MySQL 8
- Docker / Docker Compose
- Maven

## Arquitetura

```
parking/
├── controller/
│   ├── WebhookController.java   # Recebe eventos do simulador
│   └── RevenueController.java   # Consulta de faturamento
├── service/
│   ├── WebhookService.java      # Lógica de negócio dos eventos
│   └── RevenueService.java      # Cálculo de receita
├── model/
│   ├── GarageEntity.java
│   ├── SectorEntity.java
│   ├── ParkingSpotEntity.java
│   └── ParkingSessionEntity.java
├── repository/
├── request/
├── response/
├── enums/
│   └── SessionStatusEnum.java   # ACTIVE, PARKED, EXIT
└── InitiateRunner.java          # Seed de dados na inicialização
```

## Como executar

### Pré-requisitos

- Docker
- Docker Compose

### Subir o ambiente

```bash
docker-compose up --build -d
```

Isso sobe três serviços:
- **mysql** — banco de dados na porta `3307`
- **parking-app** — aplicação Spring Boot na porta `3003`
- **simulator** — simulador de eventos na porta `3000`

### Subir o simulador separadamente (opcional)

```bash
docker run -d -p 3000:3000 \
  --network estapar_estapar-net \
  --name simulator \
  -e EXTERNAL_API_URL=http://parking-app:3003/webhook \
  cfontes0estapar/garage-sim:1.0.0
```

### Resetar o banco

```bash
docker-compose down -v
docker-compose up --build -d
```

## Fluxo de eventos

Na inicialização, a aplicação busca os dados da garagem no simulador via `GET /garage` e persiste setores e vagas no banco.

O simulador envia eventos via `POST /webhook` na seguinte ordem:

```
ENTRY → PARKED → EXIT
```

| Evento | O que acontece |
|--------|----------------|
| `ENTRY` | Valida disponibilidade, calcula preço dinâmico, cria sessão |
| `PARKED` | Identifica vaga por lat/lng, marca como ocupada, vincula à sessão |
| `EXIT` | Calcula valor final, libera vaga, fecha sessão |

## Regras de negócio

### Preço dinâmico (calculado no ENTRY)

| Lotação do setor | Ajuste |
|------------------|--------|
| < 25% | -10% (desconto) |
| 25% a 50% | sem ajuste |
| 50% a 75% | +10% |
| 75% a 100% | +25% |

### Cálculo no EXIT

- Até 30 minutos → **grátis**
- Após 30 minutos → cobra por hora, arredondando para cima (`Math.ceil`)

### Lotação

Com 100% de ocupação, novas entradas são bloqueadas até a saída de um veículo.

## API

### `POST /webhook`

Recebe eventos do simulador.

**ENTRY**
```json
{
  "license_plate": "ZUL0001",
  "entry_time": "2025-01-01T12:00:00.000Z",
  "event_type": "ENTRY"
}
```

**PARKED**
```json
{
  "license_plate": "ZUL0001",
  "lat": -23.561684,
  "lng": -46.655981,
  "event_type": "PARKED"
}
```

**EXIT**
```json
{
  "license_plate": "ZUL0001",
  "exit_time": "2025-01-01T13:00:00.000Z",
  "event_type": "EXIT"
}
```

### `GET /revenue`

Retorna a receita total de um setor em uma data.

**Request**
```json
{
  "date": "2025-01-01",
  "sector": "A"
}
```

**Response**
```json
{
  "amount": 50.00,
  "currency": "BRL",
  "timestamp": "2025-01-01T12:00:00.000Z"
}
```

## Variáveis de ambiente

| Variável | Descrição | Default |
|----------|-----------|---------|
| `SPRING_DATASOURCE_URL` | URL do banco MySQL | `jdbc:mysql://localhost:3306/DB_GARAGE` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco | `thiago` |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco | `thiago` |
| `GARAGE_CLIENT_URL` | URL do simulador | `http://localhost:3000` |
