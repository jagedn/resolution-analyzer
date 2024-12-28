package es.edn.attcliente.ia.data


import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository

@JdbcRepository(dialect = Dialect.POSTGRES)
interface ResolutionRepository extends CrudRepository<Resolution, Long>{

    Optional<Resolution> findByCliente(String cliente)

}
