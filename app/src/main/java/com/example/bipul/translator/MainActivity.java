package com.example.bipul.translator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView transdataobject;
    EditText edittextobject;
    String tobetranslated="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittextobject = (EditText) findViewById(R.id.editText) ;

        Button btnHit =(Button) findViewById(R.id.button);
        transdataobject = (TextView) findViewById(R.id.transdata);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            tobetranslated= edittextobject.getText().toString();
                tobetranslated=tobetranslated.replace(" ","+");

                new JSONTask().execute("http://newjustin.com/translateit.php?action=translations&english_words="+tobetranslated);
            }
        });

    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;                                     //Declaring a http connection
            try {
                URL url = new URL(params[0]);                                //The Url that you intend to connect
                connection= (HttpURLConnection) url.openConnection(); //Initialising the connection
                connection.connect();                                 //Connecting to the Url
                InputStream stream = connection.getInputStream();     //Get the stream from the url
                reader = new BufferedReader(new InputStreamReader(stream)); //This reader will read the stream line by line
                StringBuffer buffer = new StringBuffer();             //To store the whole data
                String line = "";                                     //Temporary string variable to store the data after each line is read
                while((line= reader.readLine())!=null){
                    buffer.append(line);
                }
                String result = "";
                String finalJson= buffer.toString();
                JSONObject parentObject= new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("translations");
                StringBuffer finalJsonData= new StringBuffer();
                String[] languages = {"arabic", "chinese", "danish","dutch","french","german","italian","portuguese","russian","spanish"};
                for(int i=0;i<parentArray.length();i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    result = result+languages[i]+"  :  "+ finalObject.getString(languages[i])+"\n";
                }
                finalJsonData.append(result);
                return finalJsonData.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally{                                          //To close the reader and to disconnect
                if(connection!=null){
                    connection.disconnect();}
                try {
                    if(reader!=null){reader.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            transdataobject.setText(result);
        }
    }
}


