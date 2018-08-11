open class Person() {

    var name: String? = null
    var lastName: String? = null
    var identify: Int? = null

    constructor(name: String, lastName: String, identify: Int) : this() {
        this.name = name
        this.lastName = lastName
        this.identify = identify
    }

}