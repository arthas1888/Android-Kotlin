class Teacher : Person {
    var nomina: Int? = null

    constructor():super()

    constructor(name: String, lastName: String, identify: Int, nomina: Int)
            : super(name, lastName, identify) {
        this.nomina = nomina
    }
}