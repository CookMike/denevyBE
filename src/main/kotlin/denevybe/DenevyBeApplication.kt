package denevybe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@RestController
class BlogResource(val service: BlogService){
    @GetMapping
    fun index(): List<Blog> = service.findBlogs()

    @GetMapping
    fun id(): List<Blog> = service.findBlogsById()

    @GetMapping
    fun title(): List<Blog> = service.findBlogsByTitle()

    @GetMapping
    fun authorId(): List<Blog> = service.findBlogsByAuthorId()

    @PostMapping
    fun post(@RequestBody blog:Blog){
        service.post(blog)
    }
}

@Service
class BlogService(val db:BlogRepository){
    fun findBlogs(): List<Blog> = db.findBlogs()

    fun findBlogsById(): List<Blog> = db.findBlogsById()

    fun findBlogsByTitle(): List<Blog> = db.findBlogsByTitle()

    fun findBlogsByAuthorId(): List<Blog> = db.findBlogsByAuthorId()

    fun post(blog: Blog){
        db.save(blog)
    }
}

interface BlogRepository: CrudRepository<Blog, String> {

    @Query("select * from blogs")
    fun findBlogs(): List<Blog>

    @Query("select * from blogs WHERE id= :id LIMIT 10 OFFSET 20")
    fun findBlogsById(): List<Blog>

    @Query("select * from blogs WHERE title= :title LIMIT 10 OFFSET 20")
    fun findBlogsByTitle(): List<Blog>

    @Query("select * from blogs WHERE authorId= :authorId LIMIT 10 OFFSET 20")
    fun findBlogsByAuthorId(): List<Blog>
}


@Table("BLOGS")
data class Blog(@Id val id: String, val title: String, val content: String, val authorId: String){

}