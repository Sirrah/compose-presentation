package nl.sirrah.todolist

import nl.sirrah.todolist.Orientation.Horizontal
import nl.sirrah.todolist.Orientation.Vertical

//region View classes

sealed class View {
    val children = mutableListOf<View>()
}

enum class Orientation { Vertical, Horizontal }

class Layout(val orientation: Orientation) : View()

class Text(val contents: String) : View()

class Toggle(val value: Boolean) : View()

/**
 * Render each [View] as plain text.
 *
 * I've left out nested views (except for [Layout]) for
 * simplicity.
 */
fun render(view: View): String = when (view) {
    is Text -> view.contents
    is Toggle -> if (view.value) "[x]" else "[ ]"
    is Layout -> {
        val separator = when (view.orientation) {
            Vertical -> "\n"
            Horizontal -> " "
        }
        view.children.joinToString(separator) {
            render(it)
        }
    }
}

//endregion

//region App specific classes

data class ToDoItem(val description: String,
                    val completed: Boolean)

fun modelToView(items: List<ToDoItem>): View {
    return Layout(Vertical).apply {
        for (item in items) {
            children.add(Layout(Horizontal).apply {
                children.add(Toggle(item.completed))
                children.add(Text(item.description))
            })
        }
    }
}

fun toDoApp() {
    val toDoItems = listOf(
        ToDoItem("Write presentation", true),
        ToDoItem("Do presentation", false)
    )

    val output = render(modelToView(toDoItems))

    println(output)
}

fun main() {
    toDoApp()

    //toDoAppWithComposer()
}

//endregion

//region Composer

/**
 * To get from here to this we need to add some kind of
 * 'composer' class to which we 'emit' the child items
 */
//fun toDoListToView(items: List<ToDoItem>) {
//    Layout(Vertical) {
//        for (item in items) {
//            Layout(Horizontal) {
//                Toggle(item.completed)
//                Text(item.description)
//            }
//        }
//    }
//}

interface Composer {
    fun emit(child: View, block: () -> Unit = {})
}

class SimpleComposer(root: View) : Composer {
    private var current: View = root

    override fun emit(child: View, block: () -> Unit) {
        val parent = current

        parent.children.add(child)
        current = child

        block()

        current = parent
    }
}

fun Composer.modelToView(items: List<ToDoItem>) {
    emit(Layout(Vertical)) {
        for (item in items) {
            emit(Layout(Horizontal)) {
                emit(Toggle(item.completed))
                emit(Text(item.description))
            }
        }
    }
}

fun compose(block: Composer.() -> Unit): View {
    return Layout(Vertical).also { root ->
        SimpleComposer(root).apply(block)
    }
}

fun toDoAppWithComposer() {
    val toDoItems = listOf(
        ToDoItem("Write presentation", true),
        ToDoItem("Do presentation", false),
        ToDoItem("Add `Compose`", true),
        ToDoItem("Add `#emit`", true)
    )

    val output = render(
        compose {
            modelToView(toDoItems)

            // Or with a slightly different name and another
            // helper
            // toDoList(toDoList)
        }
    )

    println(output)
}

/**
 * Add additional helpers that correspond to more complex views,
 * or "Components".
 *
 * No longer do you need to wonder if you need a Fragment, ViewGroup
 * or a custom View.
 */
fun Composer.toDoItem(item: ToDoItem) {
    emit(Layout(Horizontal)) {
        emit(Toggle(item.completed))
        emit(Text(item.description))
    }
}

fun Composer.toDoList(items: List<ToDoItem>) {
    emit(Layout(Vertical)) {
        for (item in items)
            toDoItem(item)
    }
}

//endregion

//region With @Compose

/**
 * In Jetpack Compose the `Composer` extension methods are created by
 * the compiler via the [Composable] annotation.
 */
//@Composable
//fun ToDoItem(item: ToDoItem) {
//    Layout(Horizontal) {
//        Toggle(item.completed)
//        Text(item.description)
//    }
//}
//
//@Composable
//fun ToDoList(items: List<ToDoItem>) {
//    Layout(Vertical) {
//        for (item in items)
//            ToDoItem(item)
//    }
//}

//endregion
