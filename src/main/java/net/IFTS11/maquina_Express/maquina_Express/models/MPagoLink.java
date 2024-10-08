package net.IFTS11.maquina_Express.maquina_Express.models;

import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.preference.Preference;
import jakarta.persistence.*;
import com.mercadopago.MercadoPagoConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MPagoLink {

    private static MPagoLink mPagoLink;
    private String token="TEST-5526255074427456-092407-9150e7b0eb0f6546cce3a4a88e2973c5-133465137";

    private MPagoLink() {
        MercadoPagoConfig.setAccessToken("TEST-5526255074427456-092407-9150e7b0eb0f6546cce3a4a88e2973c5-133465137");
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

    public Preference generarPago(int monto) throws MPException, MPApiException {
        PreferenceItemRequest itemRequest =
                PreferenceItemRequest.builder()
                        .id("1")
                        .title("Alimentos")
                        .description("Alimentos")
                        .pictureUrl("")
                        .categoryId("Alimentos")
                        .quantity(1)
                        .currencyId("ARS")
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


}
