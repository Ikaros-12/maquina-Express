package net.IFTS11.maquina_Express.maquina_Express.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.IFTS11.maquina_Express.maquina_Express.dto.ProductosDto;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;


@RestController
@CrossOrigin
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    MPagosRepository mPagosRepository;

    @Autowired
    MaquinaRepository maquinaRepository;

    @Autowired
    FacturaRepository facturaRepository;

    MPagoLink mPagoLink=MPagoLink.getInstance();

    @GetMapping("/menu/{url}")
    public List<ProductosDto> listProductos(@PathVariable String url){


        Optional<Maquina> optmaquina = maquinaRepository.findByUrl(url);
        List<ProductosDto> productosDtos = new ArrayList<>();

        if (optmaquina.isPresent()){
            List<Producto>productos= optmaquina.orElseThrow().getProductos();
            productos.forEach(producto -> productosDtos.add(new ProductosDto(producto)));
        }

        return productosDtos;
    }


    @PostMapping("/notificar/{id}")
    public void confirmarCompra(@PathVariable long id,@RequestBody(required = false) Object request,@RequestParam(required = false) Map<String,Object> query) throws MPException, MPApiException {
        Optional<MPagos> optMP=mPagosRepository.findById(id);

        if (optMP.isEmpty()){
            return;
        }
        MPagos mpagos=optMP.get();
        if (mpagos.getEstado().equals("pagado")){
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(request, new TypeReference<Map<String, Object>>() {});
        System.out.println(map.toString()+"--"+query.toString());
        System.out.println("query:" + query.keySet().toString());

        //if (query.containsKey("topic") && query.get("topic").equals("merchant_order"))
        //    System.out.println("pase por aca");


        if
        (query.containsKey("id")){
            if (query.get("id") == null || query.get("id") == "")
                return;
            System.out.println(query.get("id").toString());
        }else{return;}

        //System.out.println(query.get("data.id").toString());

        if (query.containsKey("topic") && query.get("topic").equals("merchant_order")) {
            System.out.println("pase por aca2");
            long id_pago = Long.valueOf(query.get("id").toString());
            String status= mPagoLink.EstadoPago(id_pago);

            if(status.equals("opened")){
                mpagos.setEstado("Procesando");
            }
            if(status.equals("paided")){
                mpagos.setEstado("Pagado");
                Factura factura=new Factura();
                factura.setFecha_creacion(new Date());
                factura.setMaquina(mpagos.getmaquina());
                factura.setPrecio(mpagos.getPrecio());
                factura.setProducto(mpagos.getproducto());
                factura.setProducto_nombre(mpagos.getProducto().getProducto());
                facturaRepository.save(factura);
            }
            mPagosRepository.save(mpagos);

        }



    }


    @PostMapping("/pagar")
    public Preference generarLink(@RequestBody Map<String,Object> body) throws MPException, MPApiException {

        Preference respuesta = null;
        if (body.get("id")== null) return respuesta;

        Optional<Producto> op= productoRepository.findById(Integer.toUnsignedLong((int)body.get("id")));

        if (op.isPresent()){
            Producto producto=op.get();
            MPagos mlink = new MPagos();
            mlink.setproducto(producto);
            mlink.setId_maquina(producto.getMaquina());
            mlink.setPrecio(producto.getPrecio());
            mlink.setFecha_creacion(new Date());
            mlink.setEstado("Creado");
            MPagos mresp= mPagosRepository.save(mlink);

            respuesta = mPagoLink.generarPago((int)producto.getPrecio(),mresp.getId());

            //respuesta= preference.getSandboxInitPoint();
            mresp.setLinkMercadoPago(respuesta.getSandboxInitPoint());
            System.out.println(respuesta.toString());
            mresp.setEstado("Pendiente");
            mPagosRepository.save(mresp);
        }

        return respuesta;
    }




    @GetMapping("/notificar/{id}/success2")
    public void aprobado2(@PathVariable long id, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect("http://192.168.1.225:8100/pedido");
        //return "redirect:http://192.168.1.225:8100/pedido";
    }

    @GetMapping("/notificar/{id}/failure")
    public void rechazado(@PathVariable long id,
                          HttpServletResponse httpServletResponse,
                          @RequestParam("collection_id") String collectionId,
                          @RequestParam("collection_status") String collectionStatus,
                          @RequestParam("external_reference") String externalReference,
                          @RequestParam("payment_type") String paymentType,
                          @RequestParam("merchant_order_id") String merchantOrderId,
                          @RequestParam("preference_id") String preferenceId,
                          @RequestParam("site_id") String siteId,
                          @RequestParam("processing_mode") String processingMode,
                          @RequestParam("merchant_account_id") String merchantAccountId,
                          RedirectAttributes attributes) throws IOException {
        String url="http://localhost:8100/pedido/success";
        httpServletResponse.sendRedirect(url);

    }

    @GetMapping("/notificar/{id}/success")
    public void aprobado(@PathVariable long id,
                          HttpServletResponse httpServletResponse,
                          @RequestParam("collection_id") String collectionId,
                          @RequestParam("collection_status") String collectionStatus,
                          @RequestParam("external_reference") String externalReference,
                          @RequestParam("payment_type") String paymentType,
                          @RequestParam("merchant_order_id") String merchantOrderId,
                          @RequestParam("preference_id") String preferenceId,
                          @RequestParam("site_id") String siteId,
                          @RequestParam("processing_mode") String processingMode,
                          @RequestParam("merchant_account_id") String merchantAccountId,
                          RedirectAttributes attributes) throws IOException {


        String url="http://localhost:8100/pedido/success";
        httpServletResponse.sendRedirect(url);



    }
}
