# Agro Connect 🍃🌐

Bem-vindo ao Agro Connect, um aplicativo mobile blog especialmente desenvolvido para agricultores compartilharem suas experiências e conhecimentos com outros agricultores.

## Assista ao vídeo explicativo do projeto: [📺 YouTube](https://www.youtube.com/watch?v=RDOAcKtC-go)

---

## Endpoints da API

**Link da API:** [https://agroconnect-api-v1.herokuapp.com/api/v1](https://agroconnect-api-v1.herokuapp.com/api/v1)

**Atenção ⚠:** Ao utilizar os endpoints, lembre-se de incluir o *context path* antes. Bom desenvolvimento! ☺

---

## Usuário

### Cadastro de Usuário (Conta)

- Método: `POST`
- Endpoint: `/api/v1/usuario`

**Exemplo de JSON de cadastro:**

```json
{
	"nome": "vitor",
	"email": "vitor@gmail.com",
	"senha": "teste1233"
}
```

### Edição de Usuário (Conta)

- Método: `PUT`
- Endpoint: `/api/v1/usuario/{id}`

**Exemplo de JSON de edição:**

```json
{
	"nome": "vitor",
	"email": "vitor@gmail.com",
	"senha": "teste1233"
}
```

### Detalhes de um Usuário

- Método: `GET`
- Endpoint: `/api/v1/usuario/{id}`

### Exclusão de um Usuário

- Método: `DELETE`
- Endpoint: `/api/v1/usuario/{id}`

### Listagem de Todos os Usuários

- Método: `GET`
- Endpoint: `/api/v1/usuario`

---

## Postagem

### Cadastro de Postagem

- Método: `POST`
- Endpoint: `/api/v1/postagem`

**Exemplo de JSON de cadastro:**

```json
{
	"usuario": {
		"id": "a3b5cd29-4bfa-4cb3-af96-f5cc00e9883f"
	},
	"titulo": "Título da postagem",
	"conteudo": "Conteúdo sem limite de tamanho",
	"tipo_postagem": "dicas"
}
```

### Edição de uma Postagem

- Método: `PUT`
- Endpoint: `/api/v1/postagem/{id}`

**Exemplo de JSON de edição:**

```json
{
	"usuario": {
		"id": "a3b5cd29-4bfa-4cb3-af96-f5cc00e9883f"
	},
	"titulo": "Título da postagem",
	"conteudo": "Conteúdo sem limite de tamanho",
	"tipo_postagem": "dicas"
}
```

### Detalhes de uma Postagem

- Método: `GET`
- Endpoint: `/api/v1/postagem/{id}`

### Exclusão de uma Postagem

- Método: `DELETE`
- Endpoint: `/api/v1/postagem/{id}`

### Listagem de Todas as Postagens

- Método: `GET`
- Endpoint: `/api/v1/postagem`

### Listagem de Todas as Postagens de um Usuário

- Método: `GET`
- Endpoint: `/api/v1/postagem/usuario/{usuarioId}`

---

## Comentário

### Comentar em uma Postagem

- Método: `POST`
- Endpoint: `/api/v1/com
