package net.IFTS11.maquina_Express.maquina_Express.repositories;

import net.IFTS11.maquina_Express.maquina_Express.entities.Maquina;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaquinaRepository extends CrudRepository<Maquina,Long> {
}
