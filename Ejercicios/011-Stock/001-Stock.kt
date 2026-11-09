/*
 * Crea un programa que revise el stock de los productos de una tienda a partir
 * de una lista de unidades escrita en el código.
 *
 * Requisitos:
 *   1. Guarda las unidades de cada producto en una variable inmutable stock
 *      usando listOf(25, 0, 8, -3, 50, 3)
 *   2. El proveedor puede guardarse en una variable inmutable proveedor. Puede
 *      o no haber proveedor, por lo que se espera que tenga un valor por defecto.
 *   3. Recorre el stock y numera los productos. Muestra el estado de cada
 *      producto: Agotado (0), Stock bajo (1-9), Stock normal (10-29) y
 *      Stock alto (30 o más).
 *   4. Si un producto tiene unidades negativas, muestra "Dato erróneo" y pasa
 *      al siguiente.
 *   5. Cuenta los productos agotados y los de stock bajo, y calcula el total y
 *      la media de unidades. Indica "Hay que hacer un pedido" si hay algún
 *      producto agotado o con stock bajo, o "No hace falta pedido" en caso
 *      contrario.
 *
 * Salida esperada:
 *   Proveedor: Sin proveedor
 *   Producto 1: 25 uds (Stock normal)
 *   Producto 2: 0 uds (Agotado)
 *   Producto 3: 8 uds (Stock bajo)
 *   Producto 4: Dato erróneo
 *   Producto 5: 50 uds (Stock alto)
 *   Producto 6: 3 uds (Stock bajo)
 *   Agotados: 1. Stock bajo: 2. Total: 86 uds. Media: 17. Hay que hacer un pedido.
 */

fun main() {
    val stock = listOf(25, 0, 8, -3, 50, 3)
    val proveedor: String? = null

    var agotados = 0
    var stockBajo = 0
    var total = 0
    var productosValidos = 0

    println("Proveedor: ${proveedor ?: "Sin proveedor"}")

    for (i in stock.indices) {
        val unidades = stock[i]
        val numero = i + 1

        if (unidades < 0) {
            println("Producto $numero: Dato erróneo")
            continue
        }

        val estado = when (unidades) {
            0 -> "Agotado"
            in 1..9 -> "Stock bajo"
            in 10..29 -> "Stock normal"
            else -> "Stock alto"
        }

        if (unidades == 0) {
            agotados++
        } else if (unidades < 10) {
            stockBajo++
        }

        total += unidades
        productosValidos++

        println("Producto $numero: $unidades uds ($estado)")
    }

    val media = if (productosValidos > 0) total / productosValidos else 0

    val pedido = if (agotados > 0 || stockBajo > 0) "Hay que hacer un pedido" else "No hace falta pedido"

    println("Agotados: $agotados. Stock bajo: $stockBajo. Total: $total uds. Media: $media. $pedido.")
}
