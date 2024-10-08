package net.IFTS11.maquina_Express.maquina_Express.controllers;


import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import net.IFTS11.maquina_Express.maquina_Express.entities.Maquina;
import net.IFTS11.maquina_Express.maquina_Express.entities.Producto;
import net.IFTS11.maquina_Express.maquina_Express.models.MPagoLink;
import net.IFTS11.maquina_Express.maquina_Express.repositories.MaquinaRepository;
import net.IFTS11.maquina_Express.maquina_Express.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MaquinaController {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    MaquinaRepository maquinaRepository;

    MPagoLink mPagoLink=MPagoLink.getInstance();

    @GetMapping("/maquina/all")
    public List<Maquina> listMaquinas(){
        List<Maquina> maquinas= new ArrayList<>();

        maquinaRepository.findAll().forEach(maquina1 -> maquinas.add(maquina1));

        return maquinas;
    }

    @GetMapping("/maquina/{id}")
    public Map<String,Object> getMaquina(long id){
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

    @PostMapping("/maquina/")
    public Map<String,Object>  createMaquina(@RequestBody Maquina maquina){

        Map<String,Object> respuesta = new HashMap<>();
        if (op.isPresent()){
            respuesta.put("",op.get());
            respuesta.put("code",200);
        }else {
            respuesta.put("code",400);
            respuesta.put("error_message","no se encontro el usuario");
        }
        return respuesta;
    }

    @GetMapping("/maquina/{id}/producto/")
    public List<Producto> listMaquinas(long id){
        List<Producto> productos=new ArrayList<>();
        productoRepository.findAll().forEach(producto1 -> productos.add(producto1));

        return productos;
    }

    @GetMapping("/producto/{id}")
    public Map<String,Object> getProducto(long id){
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

    @PostMapping("/producto/{id}")
    public Map<String,Object> createProducto(long id){
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

    @GetMapping("/producto/{precio}")
    public Preference generarMPLink(@PathVariable int precio) throws MPException, MPApiException {
        return mPagoLink.generarPago(precio);
    }




}
