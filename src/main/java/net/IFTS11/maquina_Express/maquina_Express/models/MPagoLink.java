package net.IFTS11.maquina_Express.maquina_Express.models;

import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.merchantorder.MerchantOrderPayment;

import com.mercadopago.resources.paymentmethod.PaymentMethod;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceBackUrls;
import jakarta.persistence.*;
import com.mercadopago.MercadoPagoConfig;
import net.IFTS11.maquina_Express.maquina_Express.entities.Producto;
import org.springframework.beans.factory.annotation.Value;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

public class MPagoLink {

    private static MPagoLink mPagoLink;
    //private String token="TEST-5526255074427456-092407-9150e7b0eb0f6546cce3a4a88e2973c5-133465137";
    private String token="APP_USR-7043932654924606-112500-d1808081efb2914151b585b6e7514c5f-2117711312";

    //@Value("${ngrok.dir}")
    //@Value("${ngrok.dir}")
    private String ngrok="https://94c1-186-137-118-63.ngrok-free.app";

    private String url = "http://192.168.1.225:8100/pedido/";

    private MPagoLink() {
        MercadoPagoConfig.setAccessToken(token);
    }

    public static MPagoLink getInstance(){
        if (mPagoLink==null){
            mPagoLink=new MPagoLink();
        }
        return mPagoLink;
    }

    public Preference generarPago(String categoria,String producto,String imagen,int monto,String moneda) throws MPException, MPApiException {
        PreferenceItemRequest itemRequest =
                PreferenceItemRequest.builder()
                        .id("1")
                        .title(producto)
                        .description(producto)
                        .pictureUrl(imagen)
                        .categoryId(categoria)
                        .quantity(1)
                        .currencyId(moneda)
                        .unitPrice(new BigDecimal(monto))
                        .build();
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);
        PreferenceRequest request = PreferenceRequest.builder()
                .items(items).build();
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(request);
        System.out.println(preference.getResponse());
        return preference;
    }

    public Preference generarPago(Producto producto, long mlink_id) throws MPException, MPApiException {
        PreferenceItemRequest itemRequest =
                PreferenceItemRequest.builder()
                        .id(String.valueOf(producto.getId()))
                        .title(producto.getProducto())
                        .description(producto.getDescripcion())
                        //.pictureUrl("")
                        //.categoryId("Alimentos")
                        .quantity(1)
                        .currencyId("ARS")
                        .unitPrice(new BigDecimal(producto.getPrecio()))
                        .build();
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                        .success(url+producto.getMaquina().getUrl()+"/success")
                        //.pending("http://localhost:8090/api/notificar/"+mlink_id+"/pending")
                        .failure(url+producto.getMaquina().getUrl()+"/failure")
                        .build();

        // sacar efectivo de las opciones
        List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());

        PreferencePaymentMethodsRequest paymentMethods =
                PreferencePaymentMethodsRequest.builder()
                        //.excludedPaymentMethods(excludedPaymentMethods)
                        .excludedPaymentTypes(excludedPaymentTypes)
                        .installments(12)
                        .build();

        String notificationUrl = ngrok+"/pedido/notificar/"+mlink_id;
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);
        ZoneId zone = ZoneId.of("America/Argentina/Buenos_Aires");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zone);
        ZoneOffset zoneOffset = zonedDateTime.getOffset();
        OffsetDateTime fecha_generado= OffsetDateTime.now(zoneOffset);

        OffsetDateTime fecha_expiacion=fecha_generado.plusHours(2);
        System.out.println("fecha generado:"+fecha_generado.toString()+"-fecha expiacion:"+fecha_expiacion.toString());
        PreferenceRequest request = PreferenceRequest.builder()
                .autoReturn("approved")
                .binaryMode(true)
                .expires(true)
                .dateOfExpiration(fecha_expiacion)
                .expirationDateFrom(fecha_generado)
                .expirationDateTo(fecha_expiacion)
                //.items(items).build();
                .items(items).backUrls(backUrls).notificationUrl(notificationUrl).paymentMethods(paymentMethods).build();
                //.items(items).notificationUrl(notificationUrl).build();
                //.items(items).notificationUrl(notificationUrl).build();https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=133465137-41bbc0de-29a1-4ac6-8eb1-f6dc83d8bc58",
        PreferenceClient client = new PreferenceClient();

        Preference preference = client.create(request);
        System.out.println(preference.getInitPoint());
        return preference;
    }

    public String EstadoPago(long id) throws MPException, MPApiException {
        MerchantOrderClient merchantOrderClient= new MerchantOrderClient();
        MerchantOrder merchantOrder = merchantOrderClient.get(id);
        if (merchantOrder.getStatus().equals("opened"))
            return merchantOrder.getStatus();
        //System.out.println(merchantOrder.getPayments().toString());
        List<MerchantOrderPayment> merchantOrderPaymentsList= merchantOrder.getPayments();
        float totalpagodo=(float) merchantOrderPaymentsList.stream().mapToDouble(merchantOrderPayment -> (float) merchantOrderPayment.getTotalPaidAmount().floatValue()).sum();

        if (merchantOrder.getTotalAmount().floatValue()>=totalpagodo){
            System.out.println("Pague");
            return "paided";
        }else {
            System.out.println("otro estado");
            return merchantOrder.getStatus();
        }



    }

}
