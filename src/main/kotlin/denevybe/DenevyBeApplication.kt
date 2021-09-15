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
class BlogResource(val service: BlogService) {
    @GetMapping("/")
    fun index(): List<Blog> = service.findBlogs()
}
@RestController
class BlogIdResource(val service: BlogIdService){
    @GetMapping("/id")
    fun id(): List<Blog> = service.findBlogsById()
}
@RestController
class BlogTitleResource(val service: BlogTitleService){
    @GetMapping("/title")
    fun title(): List<Blog> = service.findBlogsByTitle()
}
@RestController
class BlogAuthorIdResource(val service: BlogAuthorIdService){
    @GetMapping("/authorid")
    fun authorId(): List<Blog> = service.findBlogsByAuthorId()
}

@RestController
class BlogPostResource(val service: BlogPostService){
    @PostMapping("/")
    fun post(@RequestBody blog:Blog){
        service.post(blog)
    }
}

@Service
class BlogService(val db:BlogRepository) {
    fun findBlogs(): List<Blog> = db.findBlogs()
}
@Service
class BlogIdService(val db:BlogRepository){
    fun findBlogsById(): List<Blog> = db.findBlogsById()
}
@Service
class BlogTitleService(val db:BlogRepository){
    fun findBlogsByTitle(): List<Blog> = db.findBlogsByTitle()
}
@Service
class BlogAuthorIdService(val db:BlogRepository){
    fun findBlogsByAuthorId(): List<Blog> = db.findBlogsByAuthorId()
}
@Service
class BlogPostService(val db:BlogRepository){
    fun post(blog: Blog): List<Blog> = db.post()
}

interface BlogRepository: CrudRepository<Blog, String> {

    @Query("SELECT * FROM blogs LIMIT 10 OFFSET 20")
    fun findBlogs(): List<Blog>

    @Query("SELECT * FROM blogs WHERE id= :id LIMIT 10 OFFSET 20")
    fun findBlogsById(): List<Blog>

    @Query("SELECT * FROM blogs WHERE title= :title LIMIT 10 OFFSET 20")
    fun findBlogsByTitle(): List<Blog>

    @Query("SELECT * FROM blogs WHERE authorId= :authorId LIMIT 10 OFFSET 20")
    fun findBlogsByAuthorId(): List<Blog>

    @Query("INSERT INTO blogs (id, title, content, authorId) values(id=:id, title=:title, content=:content, authorId=:authorId) LIMIT 10 OFFSET 20")
    fun post(): List<Blog>
}


@Table("BLOGS")
data class Blog(@Id val id: String, val title: String, val content: String, val authorId: String)