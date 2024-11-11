# Newspaper demo app

REST API response format

Service definition:
The **OpenAPI 3.0** file is available at:
- http://localhost:8080/swagger-ui/index.html
- [comment.proto](https://github.com/P1aner/clevertec-backend-course-2024-final-test/blob/feature/protobuf/newspaper-app/src/main/proto/comment.proto)
- [news.proto](https://github.com/P1aner/clevertec-backend-course-2024-final-test/blob/feature/protobuf/newspaper-app/src/main/proto/news.proto)


## Rest API Endpoint

> http://localhost:8080/api/news
>
> http://localhost:8080/api/news/{newsId}/comments

## News Rest API Success Responses

1- GET - Get single item - HTTP Response Code: **200**

2- GET - Get item list with search and pagination - HTTP Response Code: **200**
Example: http://localhost:8080/api/news?query=Title&page=0&size=10

3- POST - Create a new item - HTTP Response Code: **201**

4- PATCH - Update an item - HTTP Response Code: **200**

5- DELETE - Delete an item - HTTP Response Code: **204**

## News Rest API Error Responses

1- GET - HTTP Response Code: **404**

2- DELETE - HTTP Response Code: **404**

## Comment Rest API Success Responses

1- GET - Get single item - HTTP Response Code: **200**

2- GET - Get item list with search and pagination - HTTP Response Code: **200**
Example: http://localhost:8080/api/news/{newsId}/comments?query=Example&page=0&size=10

3- POST - Create a new item - HTTP Response Code: **201**

4- PATCH - Update an item - HTTP Response Code: **200**

5- DELETE - Delete an item - HTTP Response Code: **204**

## Comment Rest API Error Responses

1- GET - HTTP Response Code: **404**

2- DELETE - HTTP Response Code: **404**
