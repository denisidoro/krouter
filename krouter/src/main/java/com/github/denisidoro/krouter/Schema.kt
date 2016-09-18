package com.github.denisidoro.krouter

class Schema(val regex: String? = null, val default: String? = null) {

    constructor(type: Type) : this(type.regex)

    enum class Type(val regex: String) {
        INT("^[-+]?[0-9]+$"),
        FLOAT("^[+-]?([0-9]*[.])?[0-9]+$"),
        STRING("");

        companion object {
            fun from(regex: String): Type = values().associateBy(Type::regex)[regex] ?: STRING
        }
    }
}