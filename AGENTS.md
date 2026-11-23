# AGENTS.md

## Convenciones del proyecto

- **Idioma**: El código del repositorio debe estar en castellano (nombres de clases de dominio, variables, métodos, endpoints, paquetes de dominio cuando corresponda, y descripciones de tests).
- **Sufijos arquitecturales en inglés**: Se mantienen los sufijos técnicos/arquitecturales en inglés para dejar claro el rol de cada componente, por ejemplo: `Repository`, `Service`, `Controller`, `Test`, `DTO`, `Config`, `Application`, `Bootstrap`, `Exception`.
- **README y documentación**: El texto explicativo del `README.md` va en castellano. El Javadoc/KDoc también va en castellano.

## Prácticas de diseño y código

- Aplicar buen diseño orientado a objetos y seguir **SOLID** y **DRY**.
- Preferir abstracciones con responsabilidades claras y cohesivas.
- Usar nombres representativos que revelen la intención.
- Preferir operaciones de orden superior/colecciones (`map`, `filter`, `sum`, etc.) por sobre loops imperativos cuando mejore la claridad.
- Evitar patrones imperativos como `for` + `break`/`continue` cuando haya una alternativa declarativa más clara.
- Manejar errores de forma explícita y consistente; usar excepciones apropiadas al dominio/HTTP.
- Mantener los tests legibles y alineados al comportamiento; preferir tests de integración que verifiquen flujos reales cuando corresponda.
