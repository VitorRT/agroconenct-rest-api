# Agro Connect 🍃🌐

## Lindo do vídeo explicando o projeto: https://www.youtube.com/watch?v=RDOAcKtC-go


## Rest Api Endpoints

## - Usuário 

Cadastro de usuário (conta)
`POST` /usuario

json de cadastro:
```js
{
	"nome": "vitor",
	"email": "vitor@gmail.com",
	"senha": "teste1233"
}
```

Edição de usuário (conta)
`PUT` /usuario/{id}

json de edição:
```js
{
	"nome": "vitor",
	"email": "vitor@gmail.com",
	"senha": "teste1233"
}
```

Para detalhar um usuário
`GET` /usuario/{id}

Para deletar um usuário
`DELETE` /usuario/{id}

Para listar todos os usuários
`GET` /usuario


<br />
<br />
<br />

## - Postagem 

Cadastro de postagem 
`POST` /postagem

json de cadastro:
```js
{
	"usuario": {
		"id": "a3b5cd29-4bfa-4cb3-af96-f5cc00e9883f"
	},
	"titulo": "Titulo da postagem",
	"conteudo": "conteudo sem limite de tamanho",
	"tipo_postagem": "dicas"
}
```

Edição de uma postagem
`PUT` /postagem/{id}

json de edição:
```js
{
	"usuario": {
		"id": "a3b5cd29-4bfa-4cb3-af96-f5cc00e9883f"
	},
	"titulo": "Titulo da postagem",
	"conteudo": "conteudo sem limite de tamanho",
	"tipo_postagem": "dicas"
}
```

Para detalhar uma postagem
`GET` /postagem/{id}

Para deletar uma postagem
`DELETE` /postagem/{id}

Para listar todos as postagens
`GET` /postagem

Para listar todas as postagens de um usuário
`GET` /postagem/usuario/{usuarioId}


<br />
<br />
<br />


## - Comentario 

Comentar em uma postagem
`POST` /comentario

json de cadastro:
```js
{
	"usuario": {
		"id": "1679df71-01d9-42d8-862e-2da1b6254ac4"
	},
 	"postagem": {
		"id": "288b560d-4f4c-4548-ab8f-b735478855d0"
	},
	"conteudo": "conteudo do comentario por enquanto sem limite de tamanho"
}
```

Edição de um comentário
`PUT` /comentario/{id}

json de edição:
```js
{
	"usuario": {
		"id": "1679df71-01d9-42d8-862e-2da1b6254ac4"
	},
 	"postagem": {
		"id": "288b560d-4f4c-4548-ab8f-b735478855d0"
	},
	"conteudo": "conteudo do comentario por enquanto sem limite de tamanho"
}
```

Para detalhar um comentário
`GET` /comentario/{id}

Para deletar um comentario
`DELETE` /comentario/{id}

Para listar todos os comentários
`GET` /comentario

Para listar todas os comentários de uma postagem
`GET` /comentario/postagem/{postagemId}




<br />
<br />
<br />

## - Curtida

Curtir uma postagem
`POST` /curtida

json da requisição:
```js
{
	"usuario": {
		"id": "1679df71-01d9-42d8-862e-2da1b6254ac4"
	},
	"postagem": {
		"id": "288b560d-4f4c-4548-ab8f-b735478855d0"
	}
}
```

Para descurtir uma postagem
`DELETE` /curtida/{id}


Para listar todos as curtidas
`GET` /curtida

Para listar todos as curtidas de uma postagem
`GET` /curtida/postagem/{postagemId}
