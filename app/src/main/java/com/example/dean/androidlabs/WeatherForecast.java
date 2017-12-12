package com.example.dean.androidlabs;

import com.example.dean.androidlabs.HttpUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecast extends AppCompatActivity {
    String url = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=e0d14b7a07a205c72890ed9d98bec93b&mode=xml&units=metric";
     ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("thing","happening" );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        ForecastQuery fq = new ForecastQuery();

        fq.execute(url);
    }
    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String min = "";
        String max = "";
        String currentTemp = "";
        String weatherIconString = "";
        Bitmap weatherIcon = null;
        HttpUtils HttpUtils = new HttpUtils();



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);
            ImageView weatherImageView = (ImageView) findViewById(R.id.weatherImageView);
            TextView minTextView = (TextView) findViewById(R.id.minTemperature);
            TextView maxTextView = (TextView) findViewById(R.id.maxTemperature);
            TextView currentTextView = (TextView) findViewById(R.id.currentTemperature);

            weatherImageView.setImageBitmap( weatherIcon);
            minTextView.setText("Today's low: " +min+"C");
            maxTextView.setText("Today's High: "+max+"C");
            currentTextView.setText("Current Temperature : "+currentTemp+"C");


        }


        @Override
        protected String doInBackground(String ...args){
            try {
                URL url = new URL(args[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                InputStream inStream = conn.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inStream, "UTF-8");
                List entries = new ArrayList();

                    ///////
                    while(xpp.getEventType() != XmlPullParser.END_DOCUMENT){
                        switch(xpp.getEventType())
                        {
                            case XmlPullParser.START_TAG:
                                String  parameter = xpp.getAttributeValue(null, "temperature");
                                if (xpp.getName().equals("temperature")) {
                                    currentTemp = xpp.getAttributeValue(null, "value");
                                    publishProgress(25);
                                    min = xpp.getAttributeValue(null, "min");
                                    publishProgress(50);
                                    max = xpp.getAttributeValue(null, "max");
                                    publishProgress(75);
                                    Log.i("degrees and stuff cur",currentTemp);
                                    Log.i("degrees and stuff min ",min);
                                    Log.i("degrees and stuff max",max);

                                }
                                if(xpp.getName().equals("weather")){
                                    weatherIconString = xpp.getAttributeValue(null, "icon");

                                }
                                Log.i("Opening tag", xpp.getName() + " message:" + parameter);
                                break;
                            case XmlPullParser.END_TAG:
                                Log.i("Closing tag", xpp.getName());
                                break;
                            case XmlPullParser.TEXT:

                                Log.i("text tag",xpp.getText());
                                break;

                        }
                        xpp.next();


                    }
                String imageURL = "http://openweathermap.org/img/w/" + weatherIconString + ".png";
                weatherIcon = pullImage(imageURL);

            }catch(Exception e){
                e.printStackTrace();
                return "Error establishing network";
            }
            return null;
        }

        public Bitmap pullImage(String imageURL) {
            try {
                if(fileExistance(weatherIconString+".png")){
                    Log.i("in pullImage","using file");
                    FileInputStream fis = null;
                    try {    fis = openFileInput(weatherIconString+".png");   }
                    catch (FileNotFoundException e) {    e.printStackTrace();  }
                    Bitmap bm = BitmapFactory.decodeStream(fis);
                    publishProgress(100);

                    return bm;

                }
                else {
                    Log.i("in pullimage", "reading from URL");
                    Bitmap image = HttpUtils.getImage(imageURL);
                    FileOutputStream outputStream = openFileOutput(weatherIconString + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    publishProgress(100);

                    return image;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("progressbar","updating");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);

        }
    }

}
