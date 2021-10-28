package com.alejandroarevalo.hilooss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TareaAsyncrona tareaAsyncrona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }



    public void llamarADormir(View view){
        Toast.makeText(this, "Iniciando sleep", Toast.LENGTH_LONG).show();
        for(int i=0; i<=10; i++){
            adormir();
        }
        Toast.makeText(this, "Fin sleep", Toast.LENGTH_LONG).show();
    }
    private void adormir() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    //SEGUNDO HILO
    public void segundohilo(View view){

        Toast.makeText(this, "Iniciando segundo hilo", Toast.LENGTH_LONG).show();
        new Thread(new Runnable(){
            @Override
            public void run(){
                for(int i=0; i<=10; i++){
                    adormir();
                }
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        Toast.makeText(getApplicationContext(),"Finalizo segundo hilo", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
        Toast.makeText(this, "Fin segundo hilo?", Toast.LENGTH_LONG).show();
    }


    //TERCER HILO
    public void tercerhilo(View view){
        Toast.makeText(this, "Iniciando tercer hilo", Toast.LENGTH_LONG).show();
        new Thread(new Runnable(){
            @Override
            public void run(){
                for(int i=0; i<=10; i++){
                    adormir();
                }
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        Toast.makeText(getApplicationContext(),"Finalizo tercer hilo", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
        Toast.makeText(this, "Fin tercer hilo?", Toast.LENGTH_LONG).show();
    }


    //Tarea Asyncrona

    public void tareaAsincrona(View view){
        if(tareaAsyncrona  == null){
            Toast.makeText(this, "Iniciando tarea asincrona", Toast.LENGTH_LONG).show();
            tareaAsyncrona = new TareaAsyncrona(1,10);
            tareaAsyncrona.execute();
            Toast.makeText(this, "Fin tarea asincrona", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Estado"+tareaAsyncrona.getStatus().name(), Toast.LENGTH_LONG).show();
            tareaAsyncrona.cancel(true);
            tareaAsyncrona = null;
        }


    }

    private class TareaAsyncrona extends AsyncTask<Void, Integer, Boolean> {
        private int desde, hasta;

        public TareaAsyncrona(int desde,int hasta) {
            super();
            this.desde = desde;
            this.hasta = hasta;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setProgress(0);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(getApplicationContext(), "Fin de tareas asincrona", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.incrementProgressBy(values[0].intValue());
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
            Log.i("Informacion", "onCancelled(Boolean aBoolean)");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i("Informacion", "onCancelled");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = desde; i <= hasta; i++) {
                adormir();
                publishProgress(1);
            }
            return null;
        }
        }
    }
