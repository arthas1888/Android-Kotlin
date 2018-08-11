var lastName: String? = null
//class Product(code:Int, name:String) {
//    var code:Int? = null
//    var name:String? = null
//}
fun main(args: Array<String>) {

    var product = Product(1, "")
    product.code = 1

    val product2 = Product(2, "arroz")

    val products = arrayOf(product, product2)

    println("hola mundo")
    var num = 1 //variable de tipo mutable
    num = 5
    println("el valor es ${num}")

    val PI = 3.141516
    println("el valor de PI es $PI")

    var nombre = "Aulamatriz"
    var nombre2: String = "Aulamatriz"
    lastName = "Ortiz"

    //Arrays
    val array = arrayOf(1, 0, 20, 5, 7, 9)
    for (num in array) {
        println("el valor de PI es $num")
    }
    array.forEach {
        println("$it")
    }

    println("${array.get(2)}")
    for (x in 0..10) {
        println("el valor es $x")
    }

    for (x in 0..array.size - 1) {
        println("el valor es ${array.get(x)}")
    }
    array.sort()
    array.sortDescending()
    var count = array.count { x -> x < 2 }
    array.forEach {
        println("$it")
    }

    array.filter { num -> num > 6 }.forEach {
        println("$it")
    }

    //casteo
    var a = num.toByte()
}