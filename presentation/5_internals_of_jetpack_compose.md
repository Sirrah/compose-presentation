# Internals of Jetpack Compose

- Rendering using Canvas

- Completely separate from regular Android Views

- Requires new implementation for all Views
    -> Simple views like `Text`, `Button` and `Columns` 
    -> Complex views such as `ScrollableView` or `CoordinatorLayout`
        are not yet available 
    -> Will be a long time before Compose is ready for production
    
- Use `@Composable` and `@GenerateView` to hook into regular View
    hierarchy
    
- Everything is a component: `Button`, `Text` but also `Align`
    and `Padding`
    -> Very convoluted view hierarchies
    -> Actually not true anymore! As of last week we have `modifiers` 
