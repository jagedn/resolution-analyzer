package es.edn.attcliente.ia.data

import io.micronaut.data.annotation.Id
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue


@Entity
class Resolution {

    @GeneratedValue
    @Id
    Long id

    String client

    @Column(length = 5000)
    String resumen

    String department

    String status

    String action
}
