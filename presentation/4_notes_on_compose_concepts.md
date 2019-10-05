# Notes on DSL and Compose concepts

- All UI is a "Tree"

- Each `View` is a Node in the Tree

- "Composer" and "emit"

- Jetpack Compose adds two more things:
    - `@Composable` annotation to minimize boilerplate
    - Positional memoization to cache components for performance
    - See this [blog post][1] by Leland Richardson on how this is 
      implemented

[1]: http://intelligiblebabble.com/compose-from-first-principles/ "Compose from first principles" by Leland Richardson
