# Basic Example

- Simple layouts, Row, Column, Align, Padding
  
- Keep views up to date with state changes
    -> `+state { }` generic helper for `@Model`

- Easy to mix code with UI:
    ```
    Column {
        repeat(2) {
            listOf("zicke", "zacke").forEach {
                Text(it)
            }
        }
    }
    ```
    
- Easy to extract and reuse components
    -> No uncertainty on when to use a `Fragment`,
       `ViewGroup` or custom `View`
