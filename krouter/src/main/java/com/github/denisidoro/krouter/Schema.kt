package com.github.denisidoro.krouter

class Schema(val regex: String? = null, val default: String? = null) {

    constructor(type: Type) : this(type.regex)

    enum class Type(val regex: String) {
        INT("^[-+]?[0-9]+$"),
        FLOAT("^[+-]?([0-9]*[.])?[0-9]+[f]?$"),
        DOUBLE("^[+-]?([0-9]*[.])?[0-9]+d$"),
        LONG("^[-+]?[0-9]+L$"),
        STRING("");

        companion object {
            fun from(regex: String): Type = values().associateBy(Type::regex)[regex] ?: STRING
        }
    }
}