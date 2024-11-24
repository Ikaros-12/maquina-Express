package net.IFTS11.maquina_Express.maquina_Express.repositories;

import net.IFTS11.maquina_Express.maquina_Express.entities.MPagos;
import net.IFTS11.maquina_Express.maquina_Express.entities.Maquina;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MPagosRepository extends CrudRepository<MPagos,Long> {

    @Query("update MPagos set estado = 'pagado' where id = ?1")
    Long updateConfirmarCompra(Long id);

    List<MPagos> findByMaquina(Maquina maquina);
}
