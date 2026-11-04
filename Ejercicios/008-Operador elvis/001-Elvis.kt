/*
 * Implementa ambas funciones con el operador Elvis `?:`:
 *   - `displayName`: devuelve el apodo, o "Anónimo" si es null.
 *   - `timeoutSeconds`: devuelve el valor configurado, o 30 si es null.
 *
 * Salida esperada:
 *   Neo
 *   Anónimo
 *   10
 *   30
 */

fun displayName(nickname: String?): String = nickname ?: "Anónimo"

fun timeoutSeconds(configured: Int?): Int = configured ?: 30

fun main() {
    println(displayName("Neo"))
    println(displayName(null))
    println(timeoutSeconds(10))
    println(timeoutSeconds(null))
}

