class Student(name: String, lastName: String, identify: Int, course:String)
    : Person(name, lastName, identify) {
    var course:String? = course
}