package com.kotlin.crudBasico01

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.Hibernate
import org.hibernate.annotations.NaturalId

@Entity
@Table(name = "users")
class User ( @Id @NaturalId val email: String? = null){

    lateinit var name: String;

    override fun equals(other: Any?): Boolean {
        if (this == other) return true
        if  (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return email == other.email
    }

    override fun hashCode(): Int = email.hashCode()

    override fun toString(): String = "$name <$name>"
}