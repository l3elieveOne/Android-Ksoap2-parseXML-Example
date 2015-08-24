package example.app.com.ksoap2xmlparseexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    String TAG = "Response";
    SoapPrimitive resultString;

    List<DataAccess> PTT_DS;
    ListView ListXML;

    ArrayAdapter<DataAccess> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListXML = (ListView) findViewById(R.id.listXML);
        PTT_DS = null;

        AsyncCallWS task = new AsyncCallWS();
        task.execute();

    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            feedData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            Listing();
        }

    }

    public void feedData() {

        String SOAP_ACTION = "http://www.pttplc.com/ptt_webservice/CurrentOilPrice";
        String METHOD_NAME = "CurrentOilPrice";
        String NAMESPACE = "http://www.pttplc.com/ptt_webservice/";
        String URL = "http://www.pttplc.com/webservice/pttinfo.asmx";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo info = new PropertyInfo();
            info.setName("Language");
            info.setValue("EN");
            info.setType(String.class);

            Request.addProperty(info);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();


            Log.i(TAG, "Result: " + resultString);
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }


    public void Listing() {

        Runnable run = new Runnable() {
            @Override
            public void run() {

                XMLPullParserHandler parser = new XMLPullParserHandler();
                PTT_DS = parser.parse(resultString.toString());
                adapter = new ArrayAdapter<DataAccess>(MainActivity.this, R.layout.list_item, PTT_DS);
                ListXML.setAdapter(adapter);
            }
        };

        this.runOnUiThread(run);

    }

}