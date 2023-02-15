package com.kotlin.crudBasico01

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(email: String) : RuntimeException("User $email not found.")