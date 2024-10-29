package net.IFTS11.maquina_Express.maquina_Express.repositories;

import net.IFTS11.maquina_Express.maquina_Express.entities.Maquina;
import net.IFTS11.maquina_Express.maquina_Express.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaquinaRepository extends CrudRepository<Maquina,Long> {

    @Query("select count(m) from Maquina m where m.alias = ?1")
    Long getcountAlias(String alias);

    Optional<Maquina> findByAlias(String alias);
}
