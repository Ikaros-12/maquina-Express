package net.IFTS11.maquina_Express.maquina_Express.repositories;

import net.IFTS11.maquina_Express.maquina_Express.entities.Factura;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends CrudRepository<Factura,Long> {
}
