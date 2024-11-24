package net.IFTS11.maquina_Express.maquina_Express.repositories;

import net.IFTS11.maquina_Express.maquina_Express.entities.Factura;
import net.IFTS11.maquina_Express.maquina_Express.entities.Maquina;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends CrudRepository<Factura,Long> {

    List<Factura> findByMaquina(Maquina maquina);
}
