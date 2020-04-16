package com.example.client_wsdl.ui.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.client_wsdl.MainActivity;
import com.example.client_wsdl.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginFragment extends Fragment {

    Button enviar;
    EditText login, password;
    CardView checker;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        checker = (CardView) root.findViewById(R.id.cardView_Checker);
        enviar = (Button) root.findViewById(R.id.button_enviar);
        login = (EditText) root.findViewById(R.id.editText_login);
        password = (EditText) root.findViewById(R.id.editText_password);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                    new LoginFragment.LoginSoap(checker).execute(login.getText().toString(), password.getText().toString());
                }
            }
        });
        return root;
    }

    //AsyncTask --> Login
    private class LoginSoap extends AsyncTask<String,String,String> {

        static final String NAMESPACE = "http://Login/";
        static final String METHODNAME = "login";
        static final String URL = "http://"+ MainActivity.IP+":8080/Servidor_WebServices_mySQL/Login?wsdl";
        static final String SOAP_ACTION = NAMESPACE+METHODNAME;

        CardView respuesta;

        public LoginSoap(CardView respuesta){
            this.respuesta = respuesta;
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {

            SoapObject request = new SoapObject(NAMESPACE, METHODNAME);

            request.addProperty("login",params[0]);
            request.addProperty("password",params[1]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            try{
                transporte.call(SOAP_ACTION,envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                Log.e("response", "a" + response.toString());
                String respuesta_server = response.toString();

                if(respuesta_server.equals("Accepted")){
                    respuesta.setCardBackgroundColor(getResources().getColor(R.color.colorAccentLogin_correct));
                }else{
                    respuesta.setCardBackgroundColor(getResources().getColor(R.color.colorAccentLogin_bad));
                }
            }catch(Exception e){
                Log.e("eXXX", e.getMessage());
            }
            return null;
        }
    }

}