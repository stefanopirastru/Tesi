package com.example.mirco.civichacking;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Created by mirco on 23/05/2018.
 */

public class MyTask extends AsyncTask<Void, Integer, Void> {

    ProgressDialog progressDialog;
    Context context;

    Fragment fragment;



    MyTask(Context context, Fragment fragment){
        this.context = context;
        this.fragment = fragment;

    }

    @Override
    protected Void doInBackground(Void... voids) {

        int i =0;
        synchronized (this){
            while (i<10){
                try{
                    wait(200);
                    i++;
                    publishProgress(i);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            FragmentTransaction fragmentTransaction = fragment.getFragmentManager().beginTransaction();
            fragmentTransaction.detach(fragment).attach(fragment).commit();

        }


        return null;
    }

    @Override
    protected void onPreExecute(){

      progressDialog = new ProgressDialog(context);
      progressDialog.setTitle("Aggiornamento Eventi");
      progressDialog.setMax(10);
      progressDialog.setProgress(0);
      progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
      progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(context, "Eventi Aggiornati", Toast.LENGTH_SHORT).show();
        progressDialog.hide();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        progressDialog.setProgress(progress);



    }


}
