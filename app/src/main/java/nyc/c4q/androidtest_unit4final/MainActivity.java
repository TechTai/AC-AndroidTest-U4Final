package nyc.c4q.androidtest_unit4final;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private ColorAdapter adapter;
    protected HashMap<String, String> colorDict;
    protected List<String> colorsList;
    private Colors color;
    private String colorURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorDict = new HashMap<>();
        colorDict.put("indigo", "#4b0082");
        colorDict.put("green", "#00ff00");
        colorDict.put("blue", "#0000ff");
        colorDict.put("red", "#ff0000");
        // TODO: adding all the colors and their values would be tedious, instead fetch it from the url below
        //

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        colorDict = retrofit.create(Colors.class);



        colorsList = new ArrayList<>();
        String[] names = new String[] {"blue", "red", "purple", "indigo", "orange", "brown", "black", "green"};
        for(String n: names) colorsList.add(n);

        RecyclerView recyclerView = findViewById(R.id.rv);
        adapter = new ColorAdapter(colorsList, colorDict);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    // Link to creating options menu: https://github.com/C4Q/AC-Android/tree/v2/Android/Lecture-9-Menus-and-Navigation#anatomy-of-menu-xml

    // TODO: Add options menu with the item "Info" which is always visible
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;
    }

    // TODO: When "Info" menu item is clicked, display the fragment InfoFragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.info_fragment,fragment);
        fragmentTransaction.commit();

        // TODO: If InfoFragment is already visible and I click "Info", remove InfoFragment from the view.
        if(fragment != null) getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        return super.onOptionsItemSelected(item);


        }
    }
}
