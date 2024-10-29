# rest-api-response-format

REST API response format

The **OpenAPI 3.0** file is available at:

http://localhost:8080/swagger-ui/index.html

## Rest API Endpoint

> http://localhost:8080/api/news 
> 
> http://localhost:8080/api/news/{newsId}/comments

## News Rest API Success Responses

1- GET - Get single item - HTTP Response Code: **200**

```json
{
  "id": 1,
  "localDateTime": "2024-10-29T19:19:47.914Z",
  "title": "Title of news",
  "text": "Text of news",
  "commentList": [
    {
      "id": 1,
      "localDateTime": "2024-10-29T19:19:47.914Z",
      "text": "Example",
      "username": "Username"
    }
  ]
}
```

2- GET - Get item list with search and pagination - HTTP Response Code: **200**

Example: http://localhost:8080/api/news? query=Title & page=0 & size=10
```json
[
  {
    "id": 1,
    "title": "Title of news"
  }
]
```

3- POST - Create a new item - HTTP Response Code: **201**

Request:

```json
{
  "localDateTime": "2024-10-29T19:23:16.680Z",
  "title": "Title of news",
  "text": "Text of news"
}
```

Response:

```json
{
  "id": 21,
  "localDateTime": "2024-10-29T19:23:16.68",
  "title": "Title of news",
  "text": "Text of news",
  "commentList": null
}
```

4- PATCH - Update an item - HTTP Response Code: **200**

Request:

```json
{
  "localDateTime": "2024-10-29T19:26:57.298Z",
  "title": "Title of news",
  "text": "Text of news"
}
```

Response:

```json
{
  "id": 1,
  "localDateTime": "2024-10-29T19:26:57.298Z",
  "title": "Title of news",
  "text": "Text of news",
  "commentList": [
    {
      "id": 1,
      "localDateTime": "2024-10-29T19:26:57.298Z",
      "text": "Example",
      "username": "Username"
    }
  ]
}
```

5- DELETE - Delete an item - HTTP Response Code: **204**

## News Rest API Error Responses

1- GET - HTTP Response Code: **404**

```json
{
  "title": "Not Found",
  "status": 404,
  "detail": "News id: 1 not found."
}
```

2- DELETE - HTTP Response Code: **404**

```json
{
  "title": "Not Found",
  "status": 404,
  "detail": "News id: 1 not found."
}
```

## Comment Rest API Success Responses

1- GET - Get single item - HTTP Response Code: **200**

```json
{
  "id": 12,
  "localDateTime": "2004-10-19T11:23:54",
  "text": "Обзор интересный",
  "username": "@user-les"
}
```

2- GET - Get item list with search and pagination - HTTP Response Code: **200**

Example: http://localhost:8080/api/news/{newsId}/comments? query=Example & page=0 & size=10
```json
{
  "id": 1,
  "localDateTime": "2024-10-29T19:40:00.843Z",
  "title": "Title of news",
  "text": "Text of news",
  "commentList": [
    {
      "id": 1,
      "localDateTime": "2024-10-29T19:40:00.843Z",
      "text": "Example",
      "username": "Username"
    }
  ]
}
```

3- POST - Create a new item - HTTP Response Code: **201**

Request:

```json
{
  "localDateTime": "2024-10-29T19:41:18.706Z",
  "text": "Example",
  "username": "Username"
}
```

Response:

```json
{
  "id": 1,
  "localDateTime": "2024-10-29T19:41:18.707Z",
  "text": "Example",
  "username": "Username"
}
```

4- PATCH - Update an item - HTTP Response Code: **200**

Request:

```json
{
  "localDateTime": "2024-10-29T19:41:42.613Z",
  "text": "Example",
  "username": "Username"
}
```

Response:

```json
{
  "id": 1,
  "localDateTime": "2024-10-29T19:41:42.613Z",
  "text": "Example",
  "username": "Username"
}
```

5- DELETE - Delete an item - HTTP Response Code: **204**

## Comment Rest API Error Responses

1- GET - HTTP Response Code: **404**

```json
{
  "title": "Not Found",
  "status": 404,
  "detail": "Comment id: 1 not found."
}
```

2- DELETE - HTTP Response Code: **404**

```json
{
  "title": "Not Found",
  "status": 404,
  "detail": "Comment id: 1 not found."
}
```
