package com.example.rickandmorty;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CharacterDetails extends AppCompatActivity {
    String characterName;
    int characterId;
    /*List<CharacterDetails> myList= new ArrayList<>();
    ListView listView;
    TextView textView;*/
    TextView cname,cstatus,cgender,ctype,cspecies;
    ImageView image;
    //ye hein?
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character);

        Intent intent=getIntent();
        characterName=intent.getStringExtra("characterName");
        characterId=intent.getIntExtra("characterId",2347567);
        cname=findViewById(R.id.name);
        cstatus=findViewById(R.id.status);
        cgender=findViewById(R.id.gender);
        cspecies=findViewById(R.id.species);
        ctype=findViewById(R.id.type);
        image=findViewById(R.id.imageView);

        LoadData();
        //displayoncharacter();


    }
    void LoadData(){
        Request request=new Request.Builder().url("https://rickandmortyapi.com/api/character/"+characterId).get().build();
        Log.d("RequestUrl", "https://rickandmortyapi.com/api/character/");
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                Toast.makeText(CharacterDetails.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    return;
                }
                String respFromApi = response.body().string();

                //Now we will Log Raw to console in order to undestand the response from server
                Log.d("CharacterDetailsRawData", respFromApi);
                try {
                    extractData(respFromApi);
                } catch (Exception e) {

                }
            }
        });
    }
    void extractData(String respFromApi) throws Exception{
        JSONObject jsonObject= new JSONObject(respFromApi);
         na=jsonObject.getString("name");
         status=jsonObject.getString("status");
         species=jsonObject.getString("species");
         gender=jsonObject.getString("gender");
         type=jsonObject.getString("type");
         data=jsonObject.getString("image");
         Log.d("Assigned",status+" "+species+" "+data);
         URL url=new URL(data);
        Asynchronous asynk = new Asynchronous();
        asynk.execute();

    }
    String na , species , gender , status, type,data;
    private class Asynchronous extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            return "";
        }

        @Override
        protected void onPostExecute(String none) {
            cname.setText(na);
            cspecies.setText("Species: "+species);
            cgender.setText("Gender: "+gender);
            cstatus.setText("Status: "+status);
            ctype.setText("Type: "+type);
            Log.d("new",data);
            Picasso.get().load(data).into(image);

        }
    }


}
