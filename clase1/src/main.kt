fun main(args: Array<String>) {

    val arrayPerson = fillDb()
    var inputCode: String? = ""

    val regex = """\d+""".toRegex()

    do {
        println("Digite el codigo")
        inputCode = readLine()
    } while (inputCode.toString().isEmpty()
            || !regex.matches(inputCode.toString()))

    if (!inputCode.toString().isEmpty()
            && regex.matches(inputCode.toString())) {
        val code = inputCode.toString().toInt()
        println("code: $code")

        val p: Person? = arrayPerson.firstOrNull { x -> x.identify == code }
        //println("${p!!.name}")

        if (p != null) {
            if (p is Teacher)
                println("Es profesor")
            else if (p is Student)
                println("Es estudiante")
            else
                println("Es otro")
            //println("${p.name}")
        }
    }
}

fun fillDb(): Array<Person> {
    val person1: Person = Student("Andres", "Restrepo", 1, "cuarto")
    val person2: Person = Student("Lucas", "Garcia", 2, "cuarto")
    val person3: Person = Student("Andres", "Rodriguez", 3, "quinto")

    val person4: Person = Teacher("Luis", "Monsalve", 4, 1000000)
    val person5: Person = Teacher("Miguel", "Fuentes", 5, 1000000)
    val person6: Person = Teacher("Paco", "Ramirez", 6, 1200000)
    //val person8: Person = Teacher()
    val person7 = Person("Pepito", "Perez", 7)

    return arrayOf(person1, person2, person3, person4, person5, person6, person7)
}
