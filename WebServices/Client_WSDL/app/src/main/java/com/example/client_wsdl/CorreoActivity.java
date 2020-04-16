package com.example.client_wsdl;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CorreoActivity extends AppCompatActivity {

    EditText correo, asunto, mensaje;
    Button enviar;
    TextView respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correo_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        correo = (EditText) findViewById(R.id.editText_correo);
        asunto = (EditText) findViewById(R.id.editText_asunto);
        mensaje = (EditText) findViewById(R.id.textArea_mensage);

        respuesta = (TextView) findViewById(R.id.textView_respuesta);

        enviar = (Button) findViewById(R.id.button_send);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!correo.getText().toString().isEmpty() && !asunto.getText().toString().isEmpty() && !mensaje.getText().toString().isEmpty()){
                    new CorreoActivity.MensajeSoap(respuesta).execute(correo.getText().toString(), asunto.getText().toString(), mensaje.getText().toString());
                }
            }
        });


    }


    //AsyncTask --> Correo
    private class MensajeSoap extends AsyncTask<String,String,String> {

        static final String NAMESPACE = "http://Mensaje/";
        static final String METHODNAME = "mensaje";
        static final String URL = "http://"+ MainActivity.IP+":8080/Servidor_WebServices_mySQL/Mensaje?wsdl";
        static final String SOAP_ACTION = NAMESPACE+METHODNAME;

        TextView respuesta;

        public MensajeSoap(TextView respuesta){
            this.respuesta = respuesta;
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {

            SoapObject request = new SoapObject(NAMESPACE, METHODNAME);

            request.addProperty("correo",params[0]);
            request.addProperty("asunto",params[1]);
            request.addProperty("mensaje",params[2]);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            try{
                transporte.call(SOAP_ACTION,envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                Log.e("response", "a" + response.toString());
                String respuesta_server = response.toString();

                if(respuesta_server.equals("Sended")){
                    respuesta.setText("Enviado correctamente");
                }else{
                    respuesta.setText("Error al enviar");
                }
            }catch(Exception e){
                Log.e("eXXX", e.getMessage());
            }
            return null;
        }
    }


}
