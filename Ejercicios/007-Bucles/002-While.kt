/*
 * Implementa:
 *   - `countdown`: con un `while`, imprime los números desde `from` hasta 1 y
 *     después "Fin".
 *   - `countDigits`: con un `do-while`, cuenta los dígitos de un número no
 *     negativo dividiendo entre 10.
 *
 * Salida esperada:
 *   3
 *   2
 *   1
 *   Fin
 *   Dígitos en 0: 1
 *   Dígitos en 2026: 4
 */

fun countdown(from: Int) {
    var number = from
    
    while (number >= 1) {
        println(number)
        number--
    }
    println("Fin")
}

fun countDigits(number: Int): Int {
	var n = number
    var digits = 0

    do {
        digits++
        n /= 10
    } while (n > 0)

    return digits
}

fun main() {
    countdown(3)
    println("Dígitos en 0: ${countDigits(0)}")
    println("Dígitos en 2026: ${countDigits(2026)}")
}
