package net.IFTS11.maquina_Express.maquina_Express.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.IFTS11.maquina_Express.maquina_Express.entities.MPagos;
import net.IFTS11.maquina_Express.maquina_Express.entities.Maquina;
import net.IFTS11.maquina_Express.maquina_Express.entities.Producto;
import net.IFTS11.maquina_Express.maquina_Express.models.MPagoLink;
import net.IFTS11.maquina_Express.maquina_Express.repositories.MPagosRepository;
import net.IFTS11.maquina_Express.maquina_Express.repositories.MaquinaRepository;
import net.IFTS11.maquina_Express.maquina_Express.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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

    MPagoLink mPagoLink=MPagoLink.getInstance();

    @GetMapping("/menu/{url}")
    public List<Producto> listProductos(@PathVariable String url){


        Optional<Maquina> optmaquina = maquinaRepository.findByUrl(url);
        List<Producto> productos = new ArrayList<>();

        if (optmaquina.isPresent()){
            productos= optmaquina.orElseThrow().getProductos();
        }

        return productos;
    }


    @PostMapping("/notificar/{id}")
    public void confirmarCompra(@PathVariable long id,@RequestBody Map<String,Object> body) {
        //Optional<MPagos> opt = mPagosRepository.findById(id);

        System.out.println(body.toString());



        /*if (opt.isPresent()){
            MPagos pagado = opt.get();
            pagado.setEstado(estado);
            mPagosRepository.save(pagado);
        }*/
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
            mlink.setEstado("Creado");
            MPagos mresp= mPagosRepository.save(mlink);

            respuesta = mPagoLink.generarPago((int)producto.getPrecio(),mresp.getId());

            //respuesta= preference.getSandboxInitPoint();
            mresp.setLinkMercadoPago(respuesta.getSandboxInitPoint());
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
        Optional<MPagos> opt= mPagosRepository.findById(id);

        if(opt.isPresent()){
            MPagos mpagos = opt.get();
            //redirectView.setUrl("mpagos.getLinkMercadoPago();");
            httpServletResponse.sendRedirect(mpagos.getLinkMercadoPago());

        }

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


        String url="hola";
            httpServletResponse.sendRedirect(url);



    }
}
