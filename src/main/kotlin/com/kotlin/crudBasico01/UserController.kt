package com.kotlin.crudBasico01

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException

@RestController
@RequestMapping("/users")
class UserController(private val users : UserRepository) {

    @GetMapping
    fun all(): List<User> = users.findAll().toList()

    @GetMapping("/{email}")
    fun lookup(@PathVariable email: String) : User = users.findById(email).orElseThrow {
        UserNotFoundException(email)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody user: User): User = users.save(user)

    @PutMapping("/{email}")
    fun update(@PathVariable email: String, @RequestBody request: User): User {
        val user = users.findById(email).orElseThrow { UserNotFoundException(email) }

        return users.save(user.apply {
            this.name = request.name
        })
    }

    @DeleteMapping("/{email}")
    fun delete(@PathVariable email: String): ResponseEntity<HttpStatus> {
        if (users.existsById(email)) {
            users.deleteById(email)
        }
         return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(email: String) : RuntimeException("User $email not found.")