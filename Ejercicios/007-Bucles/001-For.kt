/*
 * Implementa las dos funciones:
 *   - `printUppercase`: usa `forEach` para imprimir cada elemento en mayúsculas.
 *
 * Salida esperada:
 *   MANZANA
 *   BANANA
 *   KIWI
 */

fun printUppercase(items: List<String>) {
    items.forEach { println(it.uppercase()) }
}

fun main() {
    val items = listOf("manzana", "banana", "kiwi")
    printUppercase(items)
}
