package net.IFTS11.maquina_Express.maquina_Express.models;

import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAck;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttClientBuilder;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import org.springframework.beans.factory.annotation.Value;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import java.util.UUID;

public class MQhive {

    private static MQhive mQhive;

    @Value("${hivemq.client.server-uri}")
    private String host;

    @Value("${hivemq.client.client-id}")
    private String id;

    @Value("${hivemq.client.server-port}")
    private String port;

    @Value("${hivemq.username}")
    private String username="admin";

    @Value("${hivemq.client.password}")
    private String password="hivemq";

    private Mqtt5BlockingClient client;

    public static MQhive getInstance(){
        if (mQhive==null) {
            mQhive = new MQhive();
        }
        return mQhive;
    }



    private MQhive() {
        MqttClientBuilder clientBuilder = MqttClient.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost("192.168.1.16")
                .serverPort(1883);

        client=clientBuilder.useMqttVersion5().build().toBlocking();

        conectar();
    }

    public boolean conectar(){
        Mqtt5Connect connectMessage = Mqtt5Connect.builder()
                .simpleAuth()
                .username(username)
                .password(password.getBytes())
                .applySimpleAuth()
                .keepAlive(60)
                .build();

        com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck connAckMessage = client.connect(connectMessage);

        if (connAckMessage.getReasonCode()== Mqtt5ConnAckReasonCode.SUCCESS){
            return true;
        }
        return false;
    }

    public boolean publicarMensaje(String topico,String mensaje){
        Mqtt5Publish publishMessage = Mqtt5Publish.builder()
                                                  .topic(topico)
                                                  .payload(mensaje.getBytes())
                                                  .build();


        Mqtt5PublishResult publishResult = client.publish(publishMessage);
        return true;
    }
}
