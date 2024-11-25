package net.IFTS11.maquina_Express.maquina_Express.controllers;

import net.IFTS11.maquina_Express.maquina_Express.dto.Estadisticas;
import net.IFTS11.maquina_Express.maquina_Express.dto.FacturacionDto;
import net.IFTS11.maquina_Express.maquina_Express.dto.MPLinkDTO;
import net.IFTS11.maquina_Express.maquina_Express.entities.Factura;
import net.IFTS11.maquina_Express.maquina_Express.entities.MPagos;
import net.IFTS11.maquina_Express.maquina_Express.entities.Maquina;
import net.IFTS11.maquina_Express.maquina_Express.models.MPagoLink;
import net.IFTS11.maquina_Express.maquina_Express.repositories.FacturaRepository;
import net.IFTS11.maquina_Express.maquina_Express.repositories.MPagosRepository;
import net.IFTS11.maquina_Express.maquina_Express.repositories.MaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.Map.entry;

@RestController
@RequestMapping("/api/history")
public class HistorialController {

    @Autowired
    MaquinaRepository maquinaRepository;

    @Autowired
    FacturaRepository facturaRepository;

    @Autowired
    MPagosRepository mPagosRepository;



    @GetMapping("/maquina/{id}/facturacion/all")
    public List<FacturacionDto> obtenerFacturaciones(@PathVariable long id){
        List<FacturacionDto> facturasDTO =new ArrayList<>();
        Optional<Maquina> opt= maquinaRepository.findById(id);
        if (opt.isPresent()){
            Maquina maquina = opt.get();

            List<Factura> facturas =facturaRepository.findByMaquina(maquina);
            if (facturas.size()>0)
                facturas.forEach(factura -> facturasDTO.add(new FacturacionDto(factura)));

        }
        return facturasDTO;
    }

    @GetMapping("/maquina/{id}/Facturacion")
    public Map<String, Estadisticas> obtenerMaxFacturacionActual(@PathVariable long id){
        Map<String,Estadisticas> maxFacturacion=new HashMap<String,Estadisticas>();
        Estadisticas estadisticas_total= new Estadisticas(0,0);
        Optional<Maquina> opt= maquinaRepository.findById(id);
        if (opt.isPresent()){
            Maquina maquina = opt.get();
            List<Factura> facturacion =facturaRepository.findByMaquina(maquina);
            facturacion.forEach(factura -> {
                estadisticas_total.setTotal_acumulado(estadisticas_total.getTotal_acumulado()+factura.getPrecio());
                estadisticas_total.setCantida_acumulada(estadisticas_total.getCantida_acumulada()+1);
                if (maxFacturacion.containsKey(factura.getProducto_nombre())){
                    Estadisticas aux= maxFacturacion.get(factura.getProducto_nombre());
                    aux.actualizarCantida_acumulada(1);
                    aux.actualizarTotal_acumulado(factura.getPrecio());
                    maxFacturacion.put(factura.getProducto_nombre(),aux);
                }else
                    maxFacturacion.put(factura.getProducto_nombre(),new Estadisticas(factura.getPrecio(),1));
            });
        }
        maxFacturacion.put("total",estadisticas_total);
        return maxFacturacion;
    }

    @GetMapping("/maquina/{id}/Facturacion/fechas")
    public Map<String, Estadisticas> obtenerFacturacionFechas(@PathVariable long id,@RequestParam Map<String,String> query){
        Map<String,Estadisticas> maxFacturacion=new HashMap<String,Estadisticas>();
        Estadisticas estadisticas_total= new Estadisticas(0,0);
        Date start;
        Date end;
        if(query.isEmpty()){
            Calendar now = Calendar.getInstance();
            now.set(Calendar.HOUR_OF_DAY, 0);
            start=now.getTime();
            now.add(Calendar.DATE,1);
            end=now.getTime();
        }else {
            Calendar now = Calendar.getInstance();
            now.set(Calendar.HOUR_OF_DAY, 0);
            now.add(Calendar.DATE,Integer.valueOf(query.get("fecha_inicio").substring(0,4)));
            now.add(Calendar.MONTH,Integer.valueOf(query.get("fecha_inicio").substring(4,6)));
            now.add(Calendar.YEAR,Integer.valueOf(query.get("fecha_inicio").substring(6,8)));
            start=now.getTime();
            if(query.containsKey("fecha_fin")){
                now.add(Calendar.DATE,Integer.valueOf(query.get("fecha_fin").substring(0,4)));
                now.add(Calendar.MONTH,Integer.valueOf(query.get("fecha_fin").substring(4,6)));
                now.add(Calendar.YEAR,Integer.valueOf(query.get("fecha_fin").substring(6,8)));
                end=now.getTime();
            }else{
                now.add(Calendar.DATE,1);
                end=now.getTime();
            }
        }

        Optional<Maquina> opt= maquinaRepository.findById(id);
        if (opt.isPresent()){
            Maquina maquina = opt.get();
            List<Factura> facturacion =facturaRepository.getMaquinaFecha(maquina,start,end);
            facturacion.forEach(factura -> {
                estadisticas_total.setTotal_acumulado(estadisticas_total.getTotal_acumulado()+factura.getPrecio());
                estadisticas_total.setCantida_acumulada(estadisticas_total.getCantida_acumulada()+1);
                if (maxFacturacion.containsKey(factura.getProducto_nombre())){
                    Estadisticas aux= maxFacturacion.get(factura.getProducto_nombre());
                    aux.actualizarCantida_acumulada(1);
                    aux.actualizarTotal_acumulado(factura.getPrecio());
                    maxFacturacion.put(factura.getProducto_nombre(),aux);
                }else
                    maxFacturacion.put(factura.getProducto_nombre(),new Estadisticas(factura.getPrecio(),1));
            });
        }
        maxFacturacion.put("total",estadisticas_total);
        return maxFacturacion;
    }

    @GetMapping("/maquina/{id}/mplink/all")
    public List<MPLinkDTO> obtenerMPLink(@PathVariable long id){
        List<MPLinkDTO> mpLinkDTO =new ArrayList<>();
        Optional<Maquina> opt= maquinaRepository.findById(id);
        if (opt.isPresent()){
            Maquina maquina = opt.get();
            //System.out.println(maquina.toString());
            List<MPagos> MPagoLinks =mPagosRepository.findByMaquina(maquina);
            //System.out.println(MPagoLinks.toString());
            if (MPagoLinks.size()>0)
                MPagoLinks.forEach(mPagos -> {
                    //System.out.println(mPagos.toString());
                    mpLinkDTO.add(new MPLinkDTO(mPagos));
                });

        }
        return mpLinkDTO;
    }
}
