package com.example.kotlindemo.model

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity @Table(name = "author")
data class Author(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

//        @Id @GeneratedValue(strategy = GenerationType.AUTO)
//        val uuid: UUID?,

        @get: NotBlank(message = "{name.required}")
        val name: String = "",

        @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.REMOVE))
        var articles: MutableList<Article> = mutableListOf<Article>(),

        @get: NotBlank (message="{email.required}")
        @get: Email(message = "{email.invalid}")
        var email : String = ""
)
    {
        fun addArticle(article: Article) {
                this.articles.add(article)
        }

        override fun toString(): String{
            return "{name: ${this.name}, articles: ${articles.map { it->it.title }}}";
        }
}
