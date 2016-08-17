package trendy.coloc.backgroundtask;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class DataTask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    String jsonString;
    String response = "";
    String line  = "";

   public DataTask(Context ctx)
    {
        this.ctx =ctx;
    }

    @Override
    protected void onPreExecute()
    {

    }

    @Override
    protected String doInBackground(String... params) {
        String signin_url = "http://192.168.1.3/slim3/public/index.php/api/signin";
        String signup_url = "http://192.168.1.3/slim3/public/index.php/api/signup";
        String users_url = "http://192.168.1.3/slim3/public/index.php/api/users";
        String user_url = "http://192.168.1.3/slim3/public/index.php/api/user";


        String method = params[0];
     if(method.equals("signin"))
        {
            String email = params[1];
            String password = params[2];
            try {
                URL url = new URL(signin_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(method.equals("signup"))
        {
            String id       = params[1];
            String email    = params[2];
            String password = params[3];
            String nom      = params[4];
            String prenom   = params[5];
            String adrs     = params[6];
            String tel      = params[7];

            try {
                URL url = new URL(signup_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data =   URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8")+"&"+
                                URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8")+"&"+
                                URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8")+"&"+
                                URLEncoder.encode("nom","UTF-8")+"="+ URLEncoder.encode(nom,"UTF-8")+"&"+
                                URLEncoder.encode("prenom","UTF-8")+"="+ URLEncoder.encode(prenom,"UTF-8")+"&"+
                                URLEncoder.encode("adresse","UTF-8")+"="+ URLEncoder.encode(adrs,"UTF-8")+"&"+
                                URLEncoder.encode("tel","UTF-8")+"="+ URLEncoder.encode(tel,"UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }






        return  response;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result)
    {


    }


}