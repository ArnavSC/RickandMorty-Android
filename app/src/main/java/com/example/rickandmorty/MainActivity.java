package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;


import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    //String sub="";
    String respFromApi;
    List<CharacterClass> characterNames=new ArrayList<CharacterClass>();
    ArrayList<String> onlyNames=new ArrayList<String>();

    int  pageNum=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.Ch);
        searchView = findViewById(R.id.search);
        loadData(pageNum);
    }
    void loadData(int pageNum) {
                Request request = new Request.Builder().url("https://rickandmortyapi.com/api/character/?page="+pageNum).get().build();
                OkHttpClient client = new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_LONG).show();
                            return;
                        }
                        respFromApi = response.body().string();
                        try {
                            /*String temp="";
                            for(int i=0 ;i<respFromApi.length()-1;i++){
                                temp+=respFromApi.charAt(i);
                            }
                            respFromApi =temp;
                            respFromApi+="]";*/
                            Log.d("RawData", " k");
                            extractData(respFromApi);

                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Not Json", Toast.LENGTH_SHORT).show();
                        }


                    }

                });
            //for(int i=0;i<characterNames.size();i++)
            //{
               // Log.d("AllCharacters", characterNames.get(i).characters + " " + characterNames.get(i).id);
            //}

    }
    void extractData(String respFromApi) throws Exception{
            JSONObject Jobject = new JSONObject(respFromApi);
            JSONArray jsonArray=Jobject.getJSONArray("results");
            JSONObject newobj = Jobject.getJSONObject("info");
            String newstr=newobj.getString("next");
            for (int i=0;i<jsonArray.length();i++)
            {   Log.d("extract2.0","reached  "+i);
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String characterName = jsonObject.getString("name") ;
                int id = jsonObject.getInt("id");
                //if(characterName.contains(sub)){
                CharacterClass singleCharacter = new CharacterClass(characterName, id);
                characterNames.add(singleCharacter);
                //characterName=characterName;
                onlyNames.add(characterName);
                Log.d("AllCharacters", characterName + " " + id);//}
            }


        if (pageNum!=34)
        {   //pageNum+=2;
            loadData(++pageNum);
        }
        else
        {
            Asynchronous asynk  = new Asynchronous();
            asynk.execute();
        }

    }
    private class Asynchronous extends AsyncTask<String,Void,ArrayList<String>>{
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            return onlyNames;
        }

        @Override
        protected void onPostExecute(ArrayList<String> characterClasses) {
            final CharacterNamesArrayAdapter characterAdapter = new CharacterNamesArrayAdapter(MainActivity.this, characterClasses);
            listView.setAdapter(characterAdapter);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                @Override
                public boolean onQueryTextSubmit(String b) {
                    /*sub=b;
                    pageNum=1;
                    characterNames.clear();
                    loadData(pageNum);*/
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String supersub) {

                    characterAdapter.getFilter().filter(supersub);
                    Log.d("SearchView",supersub);

                    return false;
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String character = (String) characterAdapter.getItem(i);
                    CharacterClass extractID= (CharacterClass) characterNames.get(i);
                    Log.d("SelectedCharacter",character);//+" "+character.id);
                    Intent intent= new Intent(MainActivity.this,CharacterDetails.class);
                    intent.putExtra("characterName" , character);
                    int x=onlyNames.indexOf(character);
                    intent.putExtra("characterId" , characterNames.get(x).id);
                    /*for(int x=0;x<characterNames.size();x++)
                        {
                            if(characterNames.get(x).characters==character)
                            {
                                intent.putExtra("characterId" , characterNames.get(x).id);
                                break;
                            }
                        }*/
                    startActivity(intent);
                }
            });


        }
    }
}