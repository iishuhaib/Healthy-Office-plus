package com.healthyofficeplus.healthyoffice.healthyofficeplus;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class dailyOffersActivity extends AppCompatActivity {
    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    private List<Product> productList;
    private int currentViewMode = 0;

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_offers);

        stubList = (ViewStub) findViewById(R.id.stub_list);
        stubGrid = (ViewStub) findViewById(R.id.stub_grid);

        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView) findViewById(R.id.mylistview);
        gridView = (GridView) findViewById(R.id.mygridview);

        //get list of product
        getProductList();

        //Get current view mode in share reference
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        //Register item lick
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();
    }

    private void switchView() {

        if(VIEW_MODE_LISTVIEW == currentViewMode) {
            //Display listview
            stubList.setVisibility(View.VISIBLE);
            //Hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            //Hide listview
            stubList.setVisibility(View.GONE);
            //Display gridview
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }

    private void setAdapters() {
        if(VIEW_MODE_LISTVIEW == currentViewMode) {
            listViewAdapter = new ListViewAdapter(this, R.layout.list_item, productList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, productList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    public List<Product> getProductList() {
        //pseudo code to get product, replace your code to get real product here
        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.icon_1, "SUBWAY", "Craving for some SUBWAY® goodness? Come enjoy the BUY-1-GET-1 free offer at SUBWAY"));
        productList.add(new Product(R.drawable.icon_2, "PIZZA HUT", "Buy a Large Pan Pizza and Get a Medium Pan Pizza Free"));
        productList.add(new Product(R.drawable.icon_3, "KFC", "A Hot Deal not just for one day but for THREE DAYS!!"));
        productList.add(new Product(R.drawable.icon_4, "SINGER", "15% discount on Dell Notebook Inspiron 7566 i7"));
        productList.add(new Product(R.drawable.icon_5, "SOFTLOGIC", "A Big-screen TV for just Rs. 2,083/- upwards per month"));
        productList.add(new Product(R.drawable.icon_6, "Dress Factory", "Buy 3 and Get 1 Free Dress Factory Anniversary Week"));
        productList.add(new Product(R.drawable.icon_7, "KEELS SUPER", "25% for nexus mobile members on hot sellers"));
        productList.add(new Product(R.drawable.icon_8, "Domino's", "Domino's Latest Offer - Get Rs 101 OFF On Rs 400 - All Users"));
        productList.add(new Product(R.drawable.icon_9, "Odel", "Enjoy 20% Savings at Odel | American Express"));
        productList.add(new Product(R.drawable.icon_10, "fashionbug", "Get 10% OFF while you spend for more than Rs.5000/"));

        return productList;
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item
            String selectedValue;
            Toast.makeText(getApplicationContext(), productList.get(position).getTitle() + " - " + productList.get(position).getDescription(), Toast.LENGTH_SHORT).show();
            selectedValue = productList.get(position).getTitle();
            switch (selectedValue){
                case "SUBWAY":
                    goToUrl ( "http://subway.com/");
                    break;
                case "PIZZA HUT":
                    goToUrl ( "http://pizzahut.lk/");
                    break;
                case "KFC":
                    goToUrl ( "http://cargillsceylon.com/OurBusinesses/KFCPromo/kfcpromo.html");
                    break;
                case "SINGER":
                    goToUrl ( "http://singersl.com/");
                    break;
                case "SOFTLOGIC":
                    goToUrl ( "http://mysoftlogic.lk/mysoftlogic_home-0.html/");
                    break;
                case "Dress Factory":
                    goToUrl ( "http://dressfactory.lk/");
                    break;
                case "KEELS SUPER":
                    goToUrl ( "http://keellssuper.com/");
                    break;
                case "Domino's":
                    goToUrl ( "http://pizzaonline.dominoslk.com/");
                    break;
                case "Odel":
                    goToUrl ( "http://odel.lk/");
                    break;
                case "fashionbug":
                    goToUrl ( "http://fashionbug.lk/");
                    break;
            }
        }
    };

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_1:
                if(VIEW_MODE_LISTVIEW == currentViewMode) {
                    currentViewMode = VIEW_MODE_GRIDVIEW;
                } else {
                    currentViewMode = VIEW_MODE_LISTVIEW;
                }
                //Switch view
                switchView();
                //Save view mode in share reference
                SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentViewMode", currentViewMode);
                editor.commit();

                break;
        }
        return true;
    }
}
