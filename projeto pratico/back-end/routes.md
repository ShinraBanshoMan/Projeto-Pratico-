## Criar conta

Cria nova conta se não existir.

>``POST`` http://localhost:8080/account/create

**Body**
```json
{
    "name":"name",
    "password":"password",
    "type":"type",
    "excessLimit": 0, 
    "balance": 0.0,
    "income": 0
}

```

## Acessar conta

Retorna a conta acessada.

>``POST``  http://localhost:8080/account/auth

**Body**
```json
{
  "name":"name",
  "password":"password"
}
```

## Atualizar saldo

Atualiza o valor do saldo e retorna a conta com o valor alterado.

>``PATCH``  http://localhost:8080/account/balance-movement

**Body**
```json
{
  "accountId": 1,
  "balance": 1080
}
```