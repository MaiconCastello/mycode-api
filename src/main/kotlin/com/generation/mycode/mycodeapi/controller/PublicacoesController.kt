package com.generation.mycode.mycodeapi.controller

import com.generation.mycode.mycodeapi.entities.Publicacoes
import com.generation.mycode.mycodeapi.model.Comentario
import com.generation.mycode.mycodeapi.model.Reacao
import com.generation.mycode.mycodeapi.repository.PublicacoesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityNotFoundException

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

    @GetMapping("/categoria/{categoria}")
    fun getByCategoria(@PathVariable categoria: String) : ResponseEntity<List<Publicacoes>>{
        return ResponseEntity.ok(repository.findAllByCategoriaContainingIgnoreCase(categoria))
    }

    @GetMapping("/usuario/{usuario}")
    fun getByUsuario(@PathVariable usuario: String) : ResponseEntity<List<Publicacoes>>{
        return ResponseEntity.ok(repository.findAllByUsuarioContainingIgnoreCase(usuario))
    }

    @PutMapping("/{id}")
    fun updatePublicacao(@PathVariable id: Long, @RequestBody novaPublicacoes: Publicacoes) : ResponseEntity<Publicacoes> {
        val publicacoes = repository.findById(id).orElseThrow { EntityNotFoundException() }

        publicacoes.apply {
            this.categoria = novaPublicacoes.categoria
            this.conteudo = novaPublicacoes.conteudo
            this.imagem = novaPublicacoes.imagem
        }

        return ResponseEntity.ok(repository.save(publicacoes))
    }

    @PutMapping("/comentarios/{id}")
    fun createComentarios(@PathVariable id: Long, @RequestBody novoComentario: Comentario) : ResponseEntity<Publicacoes> {
        val publicacoes = repository.findById(id).orElseThrow { EntityNotFoundException() }
       publicacoes.comentario.add(novoComentario)
            return ResponseEntity.ok(repository.save(publicacoes))

    }

    @PutMapping("/comentarios/{id}/{id2}")
    fun updateComentarios(@PathVariable id: Long, @PathVariable id2: Long, @RequestBody novoComentario: Comentario) : ResponseEntity<Publicacoes> {
        val publicacoes = repository.findById(id).orElseThrow { EntityNotFoundException() }

        publicacoes.apply {
            this.comentario.forEach {
                if (it.id == id2) {
                    it.descricao = novoComentario.descricao
                }
            }
            return ResponseEntity.ok(repository.save(publicacoes))
        }
    }

    @DeleteMapping("/{id}")
    fun deletePublicacao(@PathVariable id: Long) : ResponseEntity<Void> =
        repository.findById(id).map{
            repository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    @PutMapping("/delete/{id}/{id2}")
    fun deleteComentario(@PathVariable id: Long, @PathVariable id2: Long) : ResponseEntity<Void> {
        val publicacoes = repository.findById(id).orElseThrow { EntityNotFoundException() }
        lateinit var delete : Comentario
        publicacoes.apply {
            this.comentario.forEach {
                if (it.id == id2) {
                   delete = it
                }
            }
        }
        publicacoes.comentario.remove(delete)
        repository.save(publicacoes)
        return ResponseEntity<Void>(HttpStatus.OK)
    }

    @PutMapping("/reacao/{id}")
    fun createReacao(@PathVariable id: Long, @RequestBody novoReacao: Reacao) : ResponseEntity<Publicacoes> {
        val publicacoes = repository.findById(id).orElseThrow { EntityNotFoundException() }
        publicacoes.reacao.add(novoReacao)
        if (novoReacao.reacao == "good"){
            publicacoes.good++
        }else{
            if (novoReacao.reacao == "bad"){
                publicacoes.bad++
            }
        }
        return ResponseEntity.ok(repository.save(publicacoes))

    }
    @PutMapping("/reacao/{id}/{id2}")
    fun updateReacao(@PathVariable id: Long, @PathVariable id2: Long, @RequestBody novaReacao: Reacao) : ResponseEntity<Publicacoes> {
        val publicacoes = repository.findById(id).orElseThrow { EntityNotFoundException() }
        lateinit var controle: String
        publicacoes.apply {
            this.reacao.forEach {
                if (it.id == id2) {
                    controle = it.reacao
                    it.reacao = novaReacao.reacao
                }
            }
            when(novaReacao.reacao){

                "good" -> {
                    when(controle){
                        "good" ->{}
                        "bad" ->{
                            publicacoes.good++
                            publicacoes.bad--
                        }
                        "" ->{
                            publicacoes.good++
                        }
                    }
                }
                "bad" -> {
                    when(controle){
                        "good" ->{
                            publicacoes.good--
                            publicacoes.bad++
                        }
                        "bad" ->{}
                        "" ->{
                            publicacoes.bad++
                        }
                    }

                }
                "" -> {
                    when(controle){
                        "good" ->{publicacoes.good--}
                        "bad" ->{publicacoes.bad--}
                        "" ->{}
                    }
                }
            }

            return ResponseEntity.ok(repository.save(publicacoes))
        }
    }

}