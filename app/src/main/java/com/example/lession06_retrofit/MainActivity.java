package com.example.lession06_retrofit;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{
    public static final String BASE_URL = "http://192.168.102.2/doan-laravel/public/api/";
    private static final String TAG = "Main Activity";
    public List<Category> categories;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listViewBooks);

        getCategories();
    }


    public void getCategories() {

        final ProgressDialog loading = ProgressDialog.show(MainActivity.this,"Fetching Data","Please wait...",false,false);
        Retrofit adapter = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        CategoryAPI api = adapter.create(CategoryAPI.class);
        api.getResponse().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                loading.dismiss();
                categories = response.body();
                Log.d(TAG, "onResponse: " +response.body());
                showList();
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Opps", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showList(){
        //String array to store all the book namesz
        String[] items = new String[categories.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<categories.size(); i++){
            //Storing names to string array
            items[i] = categories.get(i).getName();
        }

        //Creating an array adapter for list view
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.simple_list,items);

        //Setting adapter to listview
        listView.setAdapter(adapter);
    }
}