# Wallet API

RESTful сервис для управления виртуальными кошельками

Позволяет:
- Просматривать список кошельков
- Получать баланс кошелька
- Пополнять и списывать средства
## Технологии:
- Java 17
- Spring Boot (Web, JDBC)
- PostgreSQL(хранение данных)
- Maven(сборка)
- Docker
- Liquibase(миграции)
## API Endpoints

Получить баланс кошелька
- GET /api/v1/wallet/{WALLET_UUID}

Пример ответа:
```json
{
  "balance": 1500
}
```
Получить список всех кошельков
- GET /api/v1/wallet

Пример ответа:
```json
{
  "walletList": [
    {
      "id": "8a8a8581-8b8b-4f4f-9c9c-1d1d1d1d1d1d",
      "balance": 1000
    },
    {
      "id": "9d9d9e21-9e9e-4646-0909-a4a4a4a4a4a4",
      "balance": 4500
    }
  ]
}
```
Изменить баланс (пополнение/списание)
- POST /api/v1/wallet

Параметры запроса:
```json
{
  "walletId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "operationType": "DEPOSIT", или "WITHDRAW"
  "amount": 200
}
```
