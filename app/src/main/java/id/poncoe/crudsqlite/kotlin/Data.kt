package id.poncoe.crudsqlite.kotlin

class Data {
    var id: String? = null
    var name: String? = null
    var address: String? = null

    constructor() {}

    constructor(id: String, name: String, address: String) {
        this.id = id
        this.name = name
        this.address = address
    }
}