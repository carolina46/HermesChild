package com.laboratorio.hermesperezmunoa;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.util.Calendar;
import java.util.List;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class EnvioNotificaciones extends AsyncTask<String, Void, Void> {

    private Context aContext;

    // Hace falta pasar un contexto para poder crear un dbManager
    public EnvioNotificaciones(Context aContext){
        this.aContext = aContext;
    }

    @Override
    protected Void doInBackground(String... params) {
        // Traer las notificaciones no enviadas de la base de datos
        DataBaseManager dbManager = new DataBaseManager(aContext);
        List<Notificacion> notificaciones = dbManager.getNotificationsForChild(params[0]);

        // Convertir las notificaciones a JSON
        JSONArray jsonNotificaciones = generarJson(notificaciones);

        // Enviar las notificaciones por Red
        List<String> configuraciones = dbManager.getConfiguration();
        boolean envioExitoso = enviarNotificaciones(jsonNotificaciones.toString(), configuraciones.get(0), configuraciones.get(1));

        // Verificar que devuelve OK
        if(envioExitoso){
            // Si devuelve OK, borrar esas notificaciones de la BD
            dbManager.deleteChildNotifications(params[0]);
        }

        return null;
    }

    private boolean enviarNotificaciones(String json, String ip, String puerto){
        try {
            //String url = "http://"+ip+":"+puerto+"/get";
            String url = "http://192.168.0.6:8765/get";
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setReadTimeout(5000);
            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            conn.addRequestProperty("User-Agent", "Mozilla");
            conn.addRequestProperty("Referer", "google.com");

            conn.setDoOutput(true);//HABILITAMOS EL ENVIO
            conn.setRequestMethod("POST");

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());//PARA PODER ESCRIBIR DATOS A ENVIAR
            out.write(json);
            out.close();

            //RESPUESTA SERVIDOR
            int status = conn.getResponseCode();
            return status == HttpURLConnection.HTTP_OK;

        } catch (IOException e) {
            Log.e("Hermes", "notification-sending-exception", e);
        }
        return false;
    }

    private JSONArray generarJson(List<Notificacion> list){
        JSONArray jArray = new JSONArray();

        JSONObject json;

        for (Notificacion notificacion : list){
            json = new JSONObject();
            json.put("child",	  notificacion.getNombreChico());
            json.put("pictogram", notificacion.getContenidoPictograma());
            json.put("category",  notificacion.getCategoriaPictograma());
            json.put("sent",      Calendar.getInstance().getTimeInMillis());
            json.put("context", "CEDICA");

            jArray.add(json);
        }

        return jArray;
    }
}