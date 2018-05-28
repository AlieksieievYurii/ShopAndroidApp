package com.example.whiteuser.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityAddGoods extends AppCompatActivity
{
    public static final String KEY_EXTRA_NAME = "extra_name";
    public static final String KEY_EXTRA_PRICE = "extra_price";

    private CardView btnAdd;
    private EditText edtName,edtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);

        btnAdd = findViewById(R.id.cv_activity_goods_add);
        edtName = findViewById(R.id.edt_activity_goods_name);
        edtPrice = findViewById(R.id.edt_activity_goods_price);

        btnAdd.setOnClickListener(new myListenerTwo());
    }

    private class myListenerTwo implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            String txtName;
            int intPrice;
            Intent intentForResaults;

            Log.i(MainActivity.TAG_INFORMATION,"OnClick!");
            try
            {
               txtName = edtName.getText().toString();

               if(txtName.equals(""))
               {
                   Toast.makeText(ActivityAddGoods.this,"Enter name goods!",Toast.LENGTH_SHORT).show();
                   return;
               }

               intPrice = Integer.parseInt(edtPrice.getText().toString());

               Log.i(MainActivity.TAG_INFORMATION,"---- DATA_NAME:"+txtName);
               Log.i(MainActivity.TAG_INFORMATION,"---- DATA_PRICE:"+intPrice);
            }catch (Exception error)
            {
                Log.i(MainActivity.TAG_INFORMATION,"[!] Error of reading data from ActivityAddGoods!");
                Toast.makeText(ActivityAddGoods.this,"Enter price!",Toast.LENGTH_SHORT).show();
                return;
            }

            intentForResaults = new Intent();

            intentForResaults.putExtra(KEY_EXTRA_NAME,txtName);
            intentForResaults.putExtra(KEY_EXTRA_PRICE,intPrice);

            setResult(RESULT_OK,intentForResaults);
            finish();
        }
    }
}
