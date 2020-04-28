package com.example.kotlindemo.Service

import com.example.kotlindemo.model.Article
import com.example.kotlindemo.repository.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

//@Autowired
//articleRepo : ArticleRepository

@Service
class ArticleService(private val articleRepository: ArticleRepository){
    fun getAllArticles(): List<Article> {
        return articleRepository.findAll()
    }


}