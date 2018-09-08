package co.edu.aulamatriz.netapplication.models

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

@DatabaseTable(tableName = "Student")
class Student : Serializable {
    @DatabaseField(generatedId = true)
    var _id: Int? = null
    @DatabaseField
    var name: String? = null
    @DatabaseField
    var lastName: String? = null

    constructor()
    constructor(name: String?, lastName: String?) {
        this.name = name
        this.lastName = lastName
    }
}