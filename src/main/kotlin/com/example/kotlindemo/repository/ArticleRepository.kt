package com.example.kotlindemo.repository

import com.example.kotlindemo.model.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long>{
    fun countByTitleContainingIgnoringCase(title: String): Long

    @Query("SELECT a FROM Article a WHERE LOWER(a.title) = LOWER(:title)", nativeQuery = true)
    fun findByTitleLike(@Param("title") title: String?): List<Article?>?
}
