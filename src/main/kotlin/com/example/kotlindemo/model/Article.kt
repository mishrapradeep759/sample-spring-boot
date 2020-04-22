package com.example.kotlindemo.model

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
data class Article (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @get: NotBlank (message = "{title.required}")
        @Size(min=1, max=100)
        val title: String = "",

        @get: NotBlank (message = "{content.required}")
        val content: String = "",

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "author_id")
        var author: Author? = null,

        @get: NotNull(message= "Created date can not be null")
        val createDateTime: LocalDateTime = LocalDateTime.now(),

        @get: NotNull(message = "datetime can't be null")
        val updateDateTime: LocalDateTime = LocalDateTime.now()

//        @CreationTimestamp
//        @get: NotNull(message= "{Created date can not be null}")
//        val createDateTime: LocalDateTime = LocalDateTime.now()
)
{
        override fun toString(): String{
                return "{title: ${title}, author: ${author?.name}}"
        }
}
fun Article.isValid(): Boolean{
        return this.title.isEmpty() || this.content.isEmpty()
}

//        @ManyToMany(mappedBy = "article")
//        val author: MutableSet<Author> = mutableSetOf(),
//
//        @ManyToOne(PrimaryKeyJoinColumn)
//        val author: MutableSet<Author> = mutableSetOf()

//){
//        fun addAuthor(author: Author) {
//                this.author.add(post)
//                post.tags.add(this)
//        }
//
//}

//@Entity
//data class Author(
//        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//        val id: Long = 0,
//        @get: NotNull (message = "name.required")
//        val name: String = "",
//        @ManyToOne
//        val articleId: Long
//)

//@Entity
//class SomeInfo(
//        @field:NotNull
//        @field:Pattern(regexp = Constraints.EMAIL_REGEX)
//        var value: String
//) {
//        var id: Long? = null
//}
