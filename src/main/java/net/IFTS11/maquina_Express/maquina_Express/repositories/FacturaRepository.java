package net.IFTS11.maquina_Express.maquina_Express.repositories;

import net.IFTS11.maquina_Express.maquina_Express.entities.Factura;
import net.IFTS11.maquina_Express.maquina_Express.entities.Maquina;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends CrudRepository<Factura,Long> {

    List<Factura> findByMaquina(Maquina maquina);

    @Query(value ="from Factura f where f.maquina = :maquina and f.fecha_creacion >= :startDate and f.fecha_creacion <= :endDate")
    public List<Factura> getMaquinaFecha(@Param("maquina") Maquina maquina,@Param("startDate")Date startDate, @Param("endDate")Date endDate);
}
