package com.generation.mycode.mycodeapi.controller

import com.generation.mycode.mycodeapi.entities.Publicacoes
import com.generation.mycode.mycodeapi.repository.PublicacoesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/publicacoes")
class PublicacoesController {
    @Autowired
    lateinit var repository: PublicacoesRepository

    @PostMapping
    fun create (@RequestBody publicacoes: Publicacoes): Publicacoes = repository.save(publicacoes)

    @GetMapping
    fun getAll() : List<Publicacoes> = repository.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) : ResponseEntity<Publicacoes> =
        repository.findById(id).map{
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())

    @GetMapping("/{categoria}")
    fun getByCategoria(@PathVariable categoria: String) : ResponseEntity<List<Publicacoes>>{
        return ResponseEntity.ok(repository.findAllByCategoriaContainingIgnoreCase(categoria))
    }

}