package com.generation.mycode.mycodeapi.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "reacao")
class Reacao(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    val idUsuario: String,
    var reacao: String
) {
}