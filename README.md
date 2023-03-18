
# Spring Boot - Politics - Mapeo OR/M

[![build](https://github.com/uqbar-project/eg-politics-springboot-kotlin/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/uqbar-project/eg-politics-springboot/actions/workflows/build.yml) [![codecov](https://codecov.io/gh/uqbar-project/eg-politics-springboot-kotlin/branch/master/graph/badge.svg)](https://codecov.io/gh/uqbar-project/eg-politics-springboot-kotlin)

## Prerrequisitos

Solo hace falta tener instalado Docker Desktop (el pack docker engine y docker compose), seguí las instrucciones de [esta página](https://phm.uqbar-project.org/material/software) en el párrafo `Docker`.


```bash
docker compose up
```

Eso levanta tanto PostgreSQL como el cliente pgAdmin, como está explicado en [este ejemplo](https://github.com/uqbar-project/eg-manejo-proyectos-sql).

La conexión a la base se configura en el archivo [`application.yml`](./src/main/resources/application.yml):

```yml
  datasource:
    url: jdbc:postgresql://0.0.0.0:5432/politics
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
```

- `0.0.0.0` apunta a nuestro contenedor de Docker
- el usuario y contraseña está definido en el archivo `docker-compose.yml`

## Material relacionado

- [Apunte con la explicación completa](https://docs.google.com/document/d/13vAmPKbWfWpRWze3AhLwnCHfWktfIIXnju3PD_tzyW4/edit)

## Swagger - Open API

Un chiche interesante es que pueden explorar y testear con Swagger el presente ejemplo, levantando la aplicación y navegando en la siguiente URL:

http://localhost:8080/swagger-ui/index.html#/

[Swagger](https://swagger.io/) busca los controllers y arma un entorno web para probar los endpoints (puede resultar más cómodo que POSTMAN sobre todo para métodos POST o PUT).

Para conseguir el mismo efecto en tu proyecto solo tenés que agregar dos dependencias:

```kts
implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
```

Luego en los controllers la annotation `@Operation` es la que permite agregar una descripción al endpoint

```kt
@GetMapping("/zonas")
@Operation(summary = "Devuelve todas las zonas de votación")
@JsonView(View.Zona.Plana::class)
fun getZonas(): Iterable<Zona> = zonaService.getZonas()
```

que luego tomará Swagger para publicar en la página.

![swagger](./images/swagger.png)

## Testeo de integración

Podés ver la implementación de varios casos de prueba:

- para el controller de zonas
    - al traer todas las zonas no trae las personas candidatas
    - al traer una zona trae todas las personas candidatas (para este caso de prueba necesitamos convertir el JSON a una clase DTO especial para el test, ya que la serialización se hace con una clase ZonaParaGrillaSerializer en lugar de manejar el mapeo con anotaciones de Jackson o un DTO, quizás una razón más para utilizar como técnica el DTO)
    - si buscamos una zona inexistente debe devolver código de http 404
- para el controller de candidates
    - si actualizamos un candidate mediante un PUT eso se refleja en la base de datos. Dado que tiene efecto colateral **debemos utilizar la anotación @Transactional para este test**. Si bien utilizamos una base de datos in-memory y podríamos pensar "los cambios realmente no persisten", sí lo hacen en el contexto de los tests. Es decir, si repetimos el mismo test sin revertir el cambio, el primer test pasará correctamente **y el segundo fallará cuando espere que nuestro candidate tenga 0 votos**.
  
## Cómo testear la app

Podés descargarte [este archivo json](Insomnia_Politics.json) para importarlo en Insomnia.
