package com.example.client_wsdl.ui.main;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.client_wsdl.CorreoActivity;
import com.example.client_wsdl.MainActivity;
import com.example.client_wsdl.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class OrdenarFragment  extends Fragment {

    Button send;
    EditText numbers;
    TextView response;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_ordenar, container, false);

        response = (TextView) root.findViewById(R.id.textView_respuesta);
        numbers = (EditText) root.findViewById(R.id.editText_numbers);
        send = (Button) root.findViewById(R.id.button_send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!numbers.getText().toString().isEmpty()){
                    String numbers_split = numbers.getText().toString();
                    String n_1 = numbers_split.substring(0,numbers_split.indexOf(" "));
                    numbers_split = numbers_split.substring(numbers_split.indexOf(" ") + 1, numbers_split.length());
                    String n_2 = numbers_split.substring(0,numbers_split.indexOf(" "));
                    numbers_split = numbers_split.substring(numbers_split.indexOf(" ") + 1, numbers_split.length());
                    String n_3 = numbers_split.substring(0,numbers_split.indexOf(" "));
                    numbers_split = numbers_split.substring(numbers_split.indexOf(" ") + 1, numbers_split.length());
                    String n_4 = numbers_split.substring(0,numbers_split.indexOf(" "));
                    numbers_split = numbers_split.substring(numbers_split.indexOf(" ") + 1, numbers_split.length());
                    String n_5 = numbers_split;

                    new OrdenarFragment.ShortSoap(response).execute(n_1,n_2,n_3,n_4,n_5);
                }
            }
        });

        return root;
    }


    //AsyncTask --> Ordenar
    private class ShortSoap extends AsyncTask<String,String,String> {

        static final String NAMESPACE = "http://Short/";
        static final String METHODNAME = "ordenar";
        static final String URL = "http://"+ MainActivity.IP+":8080/Servidor_WebServices_mySQL/Short?wsdl";
        static final String SOAP_ACTION = NAMESPACE+METHODNAME;

        TextView respuesta;

        public ShortSoap(TextView respuesta){
            this.respuesta = respuesta;
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {

            SoapObject request = new SoapObject(NAMESPACE, METHODNAME);

            request.addProperty("n_1",params[0]);
            request.addProperty("n_2",params[1]);
            request.addProperty("n_3",params[2]);
            request.addProperty("n_4",params[3]);
            request.addProperty("n_5",params[4]);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            try{
                transporte.call(SOAP_ACTION,envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                Log.e("response", "a" + response.toString());
                this.respuesta.setText("Vector Ordenado: " + response.toString());

            }catch(Exception e){
                Log.e("eXXX", e.getMessage());
            }

            return null;
        }
    }

}
