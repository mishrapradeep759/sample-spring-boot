package com.example.kotlindemo.controller

import com.example.kotlindemo.Service.ArticleService
import com.example.kotlindemo.model.Article
import com.example.kotlindemo.model.isValid
import com.example.kotlindemo.repository.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@Controller
//@RestController
@RequestMapping("/api")
class ArticleController(private val articleRepository: ArticleRepository) {

    @Autowired
    lateinit var articleService: ArticleService

//    @GetMapping("/articles")
//    fun getAllArticles(): List<Article> =
//            articleRepository.findAll()

    @GetMapping("/articles")
    fun getAllArticles(model: Model): String {
        model.apply{
            addAttribute("article", Article())
            addAttribute("articles", articleService.getAllArticles())
        }
        return "index"
    }


//    @PostMapping("/articles")
//    fun createNewArticle(@Valid @RequestBody article: Article): Article =
//            articleRepository.save(article)

    @RequestMapping("/articles-post", method = [RequestMethod.POST])
    fun createNewArticle(@Valid article: Article, model: Model, errors: Errors): String {
        println("blah blah blah >>>>>>>>>>>")
        println(errors.hasErrors())
        val error: Boolean = article.title.isEmpty() || article.title.isBlank()

        if(error)
            return "errors"

        val result : String
        when {
            //Test for errors
            errors.hasErrors() -> result = "errors"
            else -> {
                //Otherwise proceed to the next page
                articleRepository.save(article)
                model.apply{
                    addAttribute("article", Article())
                    addAttribute("articles", articleRepository.findAll().toList())
                }
                result = "index"
            }
        }
        return result
    }

    @GetMapping("/articles/{id}")
    fun getArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<Article> {
        return articleRepository.findById(articleId).map { article ->
            ResponseEntity.ok(article)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/articles/{id}")
    fun updateArticleById(@PathVariable(value = "id") articleId: Long,
                          @Valid @RequestBody newArticle: Article): ResponseEntity<Article> {

        return articleRepository.findById(articleId).map { existingArticle ->
            val updatedArticle: Article = existingArticle
                    .copy(title = newArticle.title, content = newArticle.content)
            ResponseEntity.ok().body(articleRepository.save(updatedArticle))
        }.orElse(ResponseEntity.notFound().build())

    }

    @DeleteMapping("/articles/{id}")
    fun deleteArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<Void> {

        return articleRepository.findById(articleId).map { article  ->
            articleRepository.delete(article)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    }

    /**
     * Perform some string manipulation on the given value
     * @param value The value to manipulate
     * @param operation The operation to perform
     */
    @RequestMapping("/string/{value}")
    fun manipulateString(@PathVariable("value") value: String,
                         @RequestParam(name = "operation", defaultValue = "none") operation: String) : String {
        return when (operation.toUpperCase()) {
            "REVERSE" -> value.reversed()
            "UPPER" -> value.toUpperCase()
            "LOWER" -> value.toLowerCase()
            else -> value
        }
    }

    @RequestMapping("/find", method = arrayOf(RequestMethod.GET))
    fun findTitleByParam(@RequestParam(name = "title",
            defaultValue = "none") title: String): List<Article?>?{
        println(articleRepository.findByTitleLike(title))
        return articleRepository.findByTitleLike(title)
    }
}