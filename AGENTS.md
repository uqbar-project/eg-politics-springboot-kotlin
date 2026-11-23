# AGENTS.md

## Project conventions

- **Language**: Use English across the codebase (class names, variables, methods, endpoints, packages, tests, comments in code when applicable). The only Spanish content allowed is:
  - Explanatory text in `README.md`
  - Javadoc/KDoc documentation
- **Domain naming**: Prefer clear, representative English names for abstractions, variables, and methods (e.g. `Zone` instead of `Zona`).

## Design and coding practices

- Apply good object-oriented design and follow **SOLID** and **DRY**.
- Prefer meaningful abstractions and small, cohesive responsibilities.
- Use representative names that reveal intent.
- Prefer higher-order/functional collection operations (`map`, `filter`, `sum`, etc.) over imperative loops when they improve clarity.
- Avoid imperative control-flow patterns like `for` + `break`/`continue` when a declarative alternative is clearer.
- Handle errors explicitly and consistently; use domain/HTTP-appropriate exceptions and messages.
- Keep tests readable and aligned with behavior; prefer integration tests that verify real application flows when appropriate.
