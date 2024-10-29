package net.IFTS11.maquina_Express.maquina_Express.repositories;

import net.IFTS11.maquina_Express.maquina_Express.entities.MPagoLink;
import net.IFTS11.maquina_Express.maquina_Express.entities.Maquina;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MPagoLinkRepository extends CrudRepository<MPagoLink,Long> {

    @Query("update MPagoLink set estado = 'pagado' where id = ?1")
    Long updateConfirmarCompra(Long id);
}
