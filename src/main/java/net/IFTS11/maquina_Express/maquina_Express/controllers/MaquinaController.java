package net.IFTS11.maquina_Express.maquina_Express.controllers;


import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import net.IFTS11.maquina_Express.maquina_Express.entities.Factura;
import net.IFTS11.maquina_Express.maquina_Express.entities.MPagos;
import net.IFTS11.maquina_Express.maquina_Express.entities.Maquina;
import net.IFTS11.maquina_Express.maquina_Express.entities.Producto;
import net.IFTS11.maquina_Express.maquina_Express.models.MPagoLink;
import net.IFTS11.maquina_Express.maquina_Express.repositories.FacturaRepository;
import net.IFTS11.maquina_Express.maquina_Express.repositories.MPagosRepository;
import net.IFTS11.maquina_Express.maquina_Express.repositories.MaquinaRepository;
import net.IFTS11.maquina_Express.maquina_Express.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MaquinaController {

    @Autowired
    FacturaRepository facturaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    MaquinaRepository maquinaRepository;

    @Autowired
    MPagosRepository mPagosRepository;

    MPagoLink mPagoLink=MPagoLink.getInstance();

    @GetMapping("/maquina/all")
    public List<Maquina> listMaquinas(){
        List<Maquina> maquinas= new ArrayList<>();

        maquinaRepository.findAll().forEach(maquina1 -> maquinas.add(maquina1));

        return maquinas;
    }

    @GetMapping("/maquina/{id}")
    public Map<String,Object> getMaquina(@PathVariable long id){
        Optional<Maquina> op= maquinaRepository.findById(id);
        Map<String,Object> respuesta = new HashMap<>();
        if (op.isPresent()){
             respuesta.put("",op.get());
             respuesta.put("code",200);

        }else {
            respuesta.put("code",400);
        }

        return respuesta;
    }


    @PostMapping("/maquina")
    public Map<String,Object> createMaquina(@RequestBody Maquina _maquina){
        Map<String,Object> respuesta = new HashMap<>();

        System.out.println(_maquina);

        if(maquinaRepository.findByAlias(_maquina.getAlias()).isPresent()) {
            respuesta.put("code",400);
            respuesta.put("error_message","el alias usado ya existe");
            return respuesta;
        }


        Maquina maquina = maquinaRepository.save(_maquina);

        if (maquina != null) {
            respuesta.put("objeto",maquina);
            respuesta.put("code",200);
        }else {
            respuesta.put("code",400);
            respuesta.put("error_message","error al registrar la maquina");
        }
        return respuesta;
    }

    @GetMapping("/maquina/{id}/producto")
    public List<Producto> listProductos(@PathVariable long id){
        List<Producto> productos=new ArrayList<>();

        Optional<Maquina> optmaquina = maquinaRepository.findById(id);

        if (optmaquina.isPresent()){
            productos= optmaquina.orElseThrow().getProductos();
        }

        return productos;
    }

    @PostMapping("/maquina/{id}/producto")
    public Map<String,Object> createProducto(@RequestBody Producto _producto,@PathVariable long id){
        Map<String,Object> respuesta = new HashMap<>();
        Producto producto= null;
        Optional<Maquina> opt = maquinaRepository.findById(id);
        if (opt.isPresent()){
            System.out.println(_producto.toString());
            if( _producto.getProducto()!=""|| _producto.getPrecio()!=0 ){

                _producto.setMaquina(opt.get());
                producto= productoRepository.save(_producto);
            }
            if (producto != null) {
                respuesta.put("objeto",producto);
                respuesta.put("code",200);
            }else {
                respuesta.put("code",400);
                respuesta.put("error_message","no se encontro el usuario");
            }
        }

        return respuesta;
    }



    @GetMapping("/producto/{id}")
    public Map<String,Object> getProducto(@PathVariable long id){
        Optional<Maquina> op= maquinaRepository.findById(id);
        Map<String,Object> respuesta = new HashMap<>();
        if (op.isPresent()){
            respuesta.put("objeto",op.get());
            respuesta.put("code",200);
        }else {
            respuesta.put("code",400);
        }

        return respuesta;
    }

    @PutMapping("/producto/{id}")
    public Map<String,Object> putProducto(@RequestBody Producto _producto,@PathVariable long id){
        Optional<Producto> op= productoRepository.findById(id);
        Map<String,Object> respuesta = new HashMap<>();
        if (op.isPresent()){
            Producto producto = op.orElseThrow();
            producto.setProducto(_producto.getProducto());
            producto.setActivo(_producto.isActivo());
            producto.setPrecio(_producto.getPrecio());
            producto.setCantidad(_producto.getCantidad());
            producto.setImage(_producto.getImage());
            producto.setFechaactualizacion(_producto.getFechaactualizacion());
            producto.setFechareposicion(_producto.getFechareposicion());
            producto.setFechavencimiento(_producto.getFechavencimiento());

            producto = productoRepository.save(producto);

            respuesta.put("producto",producto);
            respuesta.put("code",200);
        }else {
            respuesta.put("code",400);
        }

        return respuesta;
    }

    @DeleteMapping("/producto/{id}")
    Map<String,Object> deleteEmployee(@PathVariable Long id) {
        Optional<Producto> op= productoRepository.findById(id);
        Map<String,Object> respuesta = new HashMap<>();
        if (op.isPresent()){
            productoRepository.deleteById(id);
            respuesta.put("code",200);
            respuesta.put("message","Borro exitosamente el registro");
        }else {
            respuesta.put("code",400);
            respuesta.put("message","No existe el registro");
        }

        return respuesta;
    }



    /*
    @GetMapping("/producto/?maquina")
    public  getProduct(String maquina){
        Map<String,Objects> lista= new HashMap<>();
        return lista;
    }*/

    /*@GetMapping("/pagar/id?1")
    public Map<String, Objects> generarMPLink(int id){
        Map<String, Objects> resultado="";
        Optional<Producto> opt = productoRepository.findById((long) id);

        if (!opt.isPresent()){
            Producto producto= opt.get();
            producto.getPrecio();

            resultado.put("CODIGO","0");


            resultado.put("CODIGO","-200");
        }else{
            resultado.put("CODIGO","-100");
            resultado.put("ERROR","Producto no encontrado");
        }

        return resultado;
    }*/



    /*
    @GetMapping("/pagar/{precio}")
    public Preference generarMPLink(@PathVariable long precio) throws MPException, MPApiException {
        return mPagoLink.generarPago((int)precio);
    }*/

    @PostMapping("/notificar/{id}/{estado}")
    public void confirmarCompra(@PathVariable long id,@PathVariable String estado,@RequestBody Map<String,Object> body) {
        //Optional<MPagos> opt = mPagosRepository.findById(id);

        System.out.println(body.toString());

        /*if (opt.isPresent()){
            MPagos pagado = opt.get();
            pagado.setEstado(estado);
            mPagosRepository.save(pagado);
        }*/
    }




    @GetMapping("/historial/facturacion/{id}")
    public List<Factura> obtenerFacturas(@PathVariable long id) {
        Optional<Maquina> optmaquina = maquinaRepository.findById(id);
        List<Factura> facturas = new ArrayList<>();
        if(optmaquina.isPresent()) {
            Maquina maquina = optmaquina.orElseThrow();
            facturas=facturaRepository.findByMaquina(maquina);
        }
        return facturas;
    }

    @GetMapping("/historial/link/{id}")
    public List<MPagos> obtenerLinks(@PathVariable long id) {
        Optional<Maquina> optmaquina = maquinaRepository.findById(id);
        List<MPagos> links = new ArrayList<>();
        if(optmaquina.isPresent()) {
            Maquina maquina = optmaquina.orElseThrow();
            links= mPagosRepository.findByMaquina(maquina);
        }
        return links;
    }






}
