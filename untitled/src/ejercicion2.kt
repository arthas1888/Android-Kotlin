import kotlin.test.assertTrue

fun main(args: Array<String>) {
    val inputCode = readLine()
    val regex = """\d+""".toRegex()
    println("\\d+")

    val product1 = Product(1, "arroz")
    val product2 = Product(2, "papa")
    val product3 = Product(3, "panela")
    val products = arrayOf(product1, product2, product3)

    if (regex.matches(inputCode.toString())) {
        val code = inputCode.toString().toInt()
        println("code: $code")

        val p = products.firstOrNull { x -> x.code == code }

        if (p != null) {
            println("${p.name}")
        }

    }
}