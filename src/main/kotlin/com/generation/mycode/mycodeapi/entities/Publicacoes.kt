package com.generation.mycode.mycodeapi.entities

import com.generation.mycode.mycodeapi.model.Comentario
import com.generation.mycode.mycodeapi.model.Reacao
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity(name = "publicacoes")
class Publicacoes(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @field:Size(min =3, max =25)
    var categoria: String,

    @field:Size(min =3, max =1000)
    var conteudo: String,

    @field:NotNull
    var usuario: String,

    var good: Int,
    var bad: Int,
    @kotlin.jvm.Transient
    val comentario: List<Comentario>,
    @kotlin.jvm.Transient
    val reacao: List<Reacao>
) {
}