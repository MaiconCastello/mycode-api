package com.generation.mycode.mycodeapi.repository

import com.generation.mycode.mycodeapi.entities.Publicacoes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublicacoesRepository: JpaRepository<Publicacoes, Long> {

    fun findAllByCategoriaContainingIgnoreCase (categoria: String) : List<Publicacoes>
}