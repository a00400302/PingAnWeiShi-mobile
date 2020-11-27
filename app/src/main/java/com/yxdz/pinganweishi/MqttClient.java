package com.yxdz.pinganweishi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.net.URISyntaxException;


public class MqttClient {

    private static Context context;
    public boolean isConnect;


      private final static String CONNECTION_STRING = "tcp://139.159.189.37:1883";
//    private final static String CONNECTION_STRING = "tcp://139.159.230.78:1883";
//    private final static String CONNECTION_STRING = "tcp://192.168.2.12:1883";
    private final static short KEEP_ALIVE = 60;
    public final static String TOPIC_DEVICE = "/Device/Status/";
    public final static String TOPIC_CONTROL = "/APP/Control/";


    private final static String USERNAME = "admin";
    private final static String PASSWORD = "admin";
    private CallbackConnection connection;
    private static MqttClient client;
    private MQTT mqtt = new MQTT();


    public static MqttClient getInstance(Context context) {
        MqttClient.context = context;
        if (client == null) {
            synchronized (MqttClient.class) {
                if (client == null) {
                    client = new MqttClient();
                }
            }
        }
        return client;
    }

    public MqttClient() {
        if (mqtt == null) {
            mqtt = new MQTT();
        }

        try {
            mqtt.setHost(CONNECTION_STRING);
            mqtt.setKeepAlive(KEEP_ALIVE);
            mqtt.setUserName(USERNAME);
            mqtt.setPassword(PASSWORD);
            mqtt.setVersion("3.1.1");

            connection = mqtt.callbackConnection();
            connection.listener(new Listener() {

                public void onDisconnected() {
                }

                public void onConnected() {
                }

                public void onPublish(UTF8Buffer topic, final Buffer payload, Runnable ack) {
                    Log.e("mqtt", "success");
                    Intent intent = new Intent("MQTT_DOOR_LOOK");
                    intent.putExtra("topic", topic.toString());
                    intent.putExtra("data", new String(payload.getData()));
                    Log.d("mqtt", topic.toString());
                    Log.d("mqtt", new String(payload.getData()));
                    context.sendBroadcast(intent);
                    ack.run();
                }

                public void onFailure(Throwable value) {
                    value.printStackTrace();
                }
            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        connection.connect(new Callback<Void>() {
            public void onFailure(Throwable value) {
                isConnect = false;
                value.printStackTrace();
            }

            public void onSuccess(Void v) {
                isConnect = true;
                Log.e("mqtt", "success");
            }
        });
    }


    public void subscribe(Topic[] topics) {
        connection.subscribe(topics, new Callback<byte[]>() {
            public void onSuccess(byte[] qoses) {
                Log.e("mqtt", "订阅成功");
            }

            public void onFailure(Throwable value) {
                value.printStackTrace();
            }
        });
    }


    public void sendMessage(String topic, String s) {
        if (connection != null) {
            connection.publish(topic, s.getBytes(), QoS.AT_MOST_ONCE, false, new Callback<Void>() {

                @Override
                public void onSuccess(Void value) {
                    Log.e("mqtt", "success");
                }

                @Override
                public void onFailure(Throwable value) {
                    Log.e("mqtt", "fail");
                }
            });
        }
    }


    public void sendMessage(String topic, byte[] s) {
        if (connection != null) {
            connection.publish(topic, s, QoS.AT_MOST_ONCE, false, new Callback<Void>() {

                @Override
                public void onSuccess(Void value) {
                    Log.e("mqtt", "success");
                }

                @Override
                public void onFailure(Throwable value) {
                    Log.e("mqtt", "fail");
                }
            });
        }
    }

    public void unSubscribe(UTF8Buffer[] topics) {
        if (connection != null) {
            connection.unsubscribe(topics, new Callback<Void>() {
                @Override
                public void onSuccess(Void v) {
                    Log.e("mqtt", "取消订阅");
                    Log.e("mqtt", "success");
                }

                @Override
                public void onFailure(Throwable value) {
                    Log.e("mqtt", "fail");
                }
            });
        }
    }


    public static byte[] OPENDOOR = new byte[]{0x04, 0x01, 0x03, 0x0D, 0x01};

}
