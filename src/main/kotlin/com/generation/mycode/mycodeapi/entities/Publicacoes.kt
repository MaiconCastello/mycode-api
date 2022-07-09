package com.generation.mycode.mycodeapi.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.generation.mycode.mycodeapi.model.Comentario
import com.generation.mycode.mycodeapi.model.Reacao
import javax.persistence.*
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

    var imagem: String,

    @field:NotNull
    var usuario: String,

    var good: Int,
    var bad: Int,


    @OneToMany(cascade = [CascadeType.ALL])
    @JsonIgnoreProperties("publicacoes")
    var comentario: MutableList<Comentario>,


    @OneToMany(cascade = [CascadeType.ALL])
    @JsonIgnoreProperties("publicacoes")
    val reacao: MutableList<Reacao>

) {
}