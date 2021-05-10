package br.com.guifactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.CustomAdapter;
import entity.JsonObject;
import network.ResourceService;
import network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    RecyclerView list;
    private CustomAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        getResourceRetrofit();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDataList();
                getResourceRetrofit();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.example_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    private void getResourceRetrofit() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        ResourceService service = RetrofitClient.getRetrofitInstance().create(ResourceService.class);
        Call<List<JsonObject>> call = service.getWeb();
        call.enqueue(new Callback<List<JsonObject>>() {
            @Override
            public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
                progressDialog.dismiss();
                swipeContainer.setRefreshing(false);
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<JsonObject>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshDataList() {
        List<JsonObject> empty = new ArrayList<>();
        list = findViewById(R.id.recyclerViewResource);
        adapter = new CustomAdapter(this, empty);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        list.setLayoutManager(layoutManager);
        list.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        list.setAdapter(adapter);

    }

    private void generateDataList(List<JsonObject> jsonObject) {
        list = findViewById(R.id.recyclerViewResource);
        adapter = new CustomAdapter(this, jsonObject);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        list.setLayoutManager(layoutManager);
        list.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        list.setAdapter(adapter);
    }

}