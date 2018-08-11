class Product(code:Int, name:String) {
    var code:Int? = null
    var name:String? = null
    init {
        this.code = code
        this.name = name
    }
    constructor(code:Int) : this(code, "") {
        this.code = code
    }
}