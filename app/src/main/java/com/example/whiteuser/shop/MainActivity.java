package com.example.whiteuser.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    public final static int REQUEST_CODE = 1;
    public final static String TAG_INFORMATION = "INF:";

    public final static int ID_CONTEXT_MENU_RM = 1;
    public final static int ID_CONTEXT_MENU_ALL = 2;

    private CardView btnAdd;
    private ListView lstList;
    private TextView tvPrice;

    private ArrayList<Data> arrayList;
    private Adapter adapter;

    private SQLmanager sqlManager;

    private RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btn_activity_main_add_goods);
        lstList = findViewById(R.id.lst_activity_main_list_of_goods);
        tvPrice = findViewById(R.id.tv_activity_main_summa);
        arrayList = new ArrayList<>();
        adapter = new Adapter(this,0,arrayList);
        sqlManager = new SQLmanager(this);

        mainLayout = findViewById(R.id.main_layout);

        btnAdd.setOnClickListener(new myListener());
        lstList.setAdapter(adapter);
        registerForContextMenu(lstList);

        new Handler().postDelayed(new startActivity(),2000);

        setDataToArray();
    }

    private void setDataToArray()
    {
        try{
            arrayList.addAll(sqlManager.getData());//Here I get ArrayList from SQLmanager. Those data are from SQL
            //If runs with Exception it means that SQL doesnt have Data and function getData return null
            Log.i(TAG_INFORMATION,"Data are loaded to array!");
        }catch (Exception error)
        {
            error.printStackTrace();
            Log.i(TAG_INFORMATION,"DataBase maybe has not data!");
        }
        refresh();
    }

    private void refresh()
    {

        int summa = 0;
        for(Data d : arrayList)
        {
            summa+=d.getPrice();
        }
        tvPrice.setText(String.valueOf(summa));
        adapter.notifyDataSetChanged();
        Log.i(TAG_INFORMATION,"Refreshed!!!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                String txtName = data.getStringExtra(ActivityAddGoods.KEY_EXTRA_NAME);
                int intPrice = data.getIntExtra(ActivityAddGoods.KEY_EXTRA_PRICE,-1);

                sqlManager.addGoodsDB(txtName,intPrice);
                arrayList.add(new Data(txtName,intPrice));
                refresh();
                Log.i(TAG_INFORMATION,"SET_RESUALTS: "+txtName+" & "+intPrice);
            }else
            {
                Log.i(TAG_INFORMATION,"[!] Error of resault activity!");
            }
        }else
        {
            Log.i(TAG_INFORMATION,"[!] Error with REQUEST_CODE!");
        }
    }

    private class myListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this,ActivityAddGoods.class);
            startActivityForResult(intent,REQUEST_CODE);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {

        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        int index = info.position;//Position in ArrayList
        Log.i(TAG_INFORMATION,"INDEX:--->"+index);
        Log.i(TAG_INFORMATION,arrayList.get(index).getName());
        switch (item.getItemId())
        {
            case ID_CONTEXT_MENU_RM:
                sqlManager.removeGoodsDB(arrayList.get(index).getName(),arrayList.get(index).getPrice());
                arrayList.remove(index);
                break;
                //Sometimes I will add some function....maybe
        }

        refresh();
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select The Action");
        menu.add(0,ID_CONTEXT_MENU_RM,0,"Delete");
        menu.add(0,ID_CONTEXT_MENU_ALL,0,"Show All");
    }

    private class startActivity implements Runnable
    {

        @Override
        public void run() {
            mainLayout.setVisibility(View.VISIBLE);
        }
    }
}
