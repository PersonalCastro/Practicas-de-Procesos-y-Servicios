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

import com.example.client_wsdl.MainActivity;
import com.example.client_wsdl.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CalculadoraFragment extends Fragment {

    Button sumar, restar, multiplicar, dividir;
    EditText numero1_suma, numero2_suma, numero1_resta, numero2_resta, numero1_multiplicacion, numero2_multiplicacion, numero1_division, numero2_division;
    TextView respuestaSuma, respuestaResta, respuestaMultiplicacion, respuestaDivision;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calculadora, container, false);

        //Suma
        sumar = (Button) root.findViewById(R.id.buttonSuma);
        numero1_suma = (EditText) root.findViewById(R.id.editTextsuma_1);
        numero2_suma = (EditText) root.findViewById(R.id.editTextsuma_2);
        respuestaSuma = (TextView) root.findViewById(R.id.textViewResultadoSuma);

        sumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!numero1_suma.getText().toString().isEmpty() && !numero2_suma.getText().toString().isEmpty()){
                    new SumaSoap(respuestaSuma).execute(Integer.valueOf(numero1_suma.getText().toString()), Integer.valueOf(numero2_suma.getText().toString()));
                }
            }
        });


        //Resta
        restar = (Button) root.findViewById(R.id.buttonResta);
        numero1_resta = (EditText) root.findViewById(R.id.editTextresta_1);
        numero2_resta = (EditText) root.findViewById(R.id.editTextresta_2);
        respuestaResta = (TextView) root.findViewById(R.id.textViewResultadoResta);

        restar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!numero1_resta.getText().toString().isEmpty() && !numero2_resta.getText().toString().isEmpty()){
                    new RestaSoap(respuestaResta).execute(Integer.valueOf(numero1_resta.getText().toString()), Integer.valueOf(numero2_resta.getText().toString()));
                }
            }
        });


        //División
        dividir = (Button) root.findViewById(R.id.buttonDivision);
        numero1_division = (EditText) root.findViewById(R.id.editTextdivision_1);
        numero2_division = (EditText) root.findViewById(R.id.editTextdivision_2);
        respuestaDivision = (TextView) root.findViewById(R.id.textViewResultadoDivision);

        dividir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!numero1_division.getText().toString().isEmpty() && !numero2_division.getText().toString().isEmpty()){
                    new DivisionSoap(respuestaDivision).execute(Integer.valueOf(numero1_division.getText().toString()), Integer.valueOf(numero2_division.getText().toString()));
                }
            }
        });


        //Multiplicación
        multiplicar = (Button) root.findViewById(R.id.buttonMultiplicacion);
        numero1_multiplicacion = (EditText) root.findViewById(R.id.editTextmultiplicacion_1);
        numero2_multiplicacion = (EditText) root.findViewById(R.id.editTextmultiplicacion_2);
        respuestaMultiplicacion = (TextView) root.findViewById(R.id.textViewResultadoMultiplicacion);

        multiplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!numero1_multiplicacion.getText().toString().isEmpty() && !numero2_multiplicacion.getText().toString().isEmpty()){
                    new MultiplicacionSoap(respuestaMultiplicacion).execute(Integer.valueOf(numero1_multiplicacion.getText().toString()), Integer.valueOf(numero2_multiplicacion.getText().toString()));
                }
            }
        });


        return root;
    }

    //AsyncTask --> Suma
    private class SumaSoap extends AsyncTask<Integer,String,String>{

        static final String NAMESPACE = "http://Calculadora/";
        static final String METHODNAME = "Sumar";
        static final String URL = "http://"+MainActivity.IP+":8080/Servidor_WebServices_mySQL/Calculadora?wsdl";
        static final String SOAP_ACTION = NAMESPACE+METHODNAME;

        TextView respuesta;

        public SumaSoap(TextView respuesta){
            this.respuesta = respuesta;
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Integer... params) {

            SoapObject request = new SoapObject(NAMESPACE, METHODNAME);

            request.addProperty("firstNumber",params[0]);
            request.addProperty("secondNumber",params[1]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            try{
                transporte.call(SOAP_ACTION,envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                Log.e("response", "a" + response.toString());
                this.respuesta.setText("It equals to: " + response.toString());

            }catch(Exception e){
                Log.e("eXXX", e.getMessage());
            }
            return null;
        }
    }

    //AsyncTask --> Resta
    private class RestaSoap extends AsyncTask<Integer,String,String>{

        static final String NAMESPACE = "http://Calculadora/";
        static final String METHODNAME = "Restar";
        static final String URL = "http://"+MainActivity.IP+":8080/Servidor_WebServices_mySQL/Calculadora?wsdl";
        static final String SOAP_ACTION = NAMESPACE+METHODNAME;

        TextView respuesta;

        public RestaSoap(TextView respuesta){
            this.respuesta = respuesta;
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Integer... params) {

            SoapObject request = new SoapObject(NAMESPACE, METHODNAME);

            request.addProperty("firstNumber",params[0]);
            request.addProperty("secondNumber",params[1]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            try{
                transporte.call(SOAP_ACTION,envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                Log.e("response", "a" + response.toString());
                this.respuesta.setText("It equals to: " + response.toString());

            }catch(Exception e){
                Log.e("eXXX", e.getMessage());
            }

            return null;
        }
    }

    //AsyncTask --> División
    private class DivisionSoap extends AsyncTask<Integer,String,String>{

        static final String NAMESPACE = "http://Calculadora/";
        static final String METHODNAME = "Dividir";
        static final String URL = "http://"+MainActivity.IP+":8080/Servidor_WebServices_mySQL/Calculadora?wsdl";
        static final String SOAP_ACTION = NAMESPACE+METHODNAME;

        TextView respuesta;

        public DivisionSoap(TextView respuesta){
            this.respuesta = respuesta;
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Integer... params) {

            SoapObject request = new SoapObject(NAMESPACE, METHODNAME);

            request.addProperty("firstNumber",params[0]);
            request.addProperty("secondNumber",params[1]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            try{
                transporte.call(SOAP_ACTION,envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                Log.e("response", "a" + response.toString());
                this.respuesta.setText("It equals to: " + response.toString());

            }catch(Exception e){
                Log.e("eXXX", e.getMessage());
            }

            return null;
        }
    }

    //AsyncTask --> Multiplicación
    private class MultiplicacionSoap extends AsyncTask<Integer,String,String>{

        static final String NAMESPACE = "http://Calculadora/";
        static final String METHODNAME = "Multiplicar";
        static final String URL = "http://"+MainActivity.IP+":8080/Servidor_WebServices_mySQL/Calculadora?wsdl";
        static final String SOAP_ACTION = NAMESPACE+METHODNAME;

        TextView respuesta;

        public MultiplicacionSoap(TextView respuesta){
            this.respuesta = respuesta;
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Integer... params) {

            SoapObject request = new SoapObject(NAMESPACE, METHODNAME);

            request.addProperty("firstNumber",params[0]);
            request.addProperty("secondNumber",params[1]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            try{
                transporte.call(SOAP_ACTION,envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                Log.e("response", "a" + response.toString());
                this.respuesta.setText("It equals to: " + response.toString());

            }catch(Exception e){
                Log.e("eXXX", e.getMessage());
            }

            return null;
        }
    }

}
