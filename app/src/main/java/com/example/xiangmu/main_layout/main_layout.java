package com.example.xiangmu.main_layout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.xiangmu.R;
import com.example.xiangmu.Shopping_Car.shopping_car;


public class main_layout extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout homepage_layout;
    private LinearLayout search_layout;
    private LinearLayout discount_layout;
    private LinearLayout person_layout;
    private LinearLayout shopping_car;
    public static int choose=1;
    private ImageView person;
    private ImageView home_page;
    private ImageView search;
    private ImageView shopping_cart;
    private ImageView discount;
    private TextView person1;
    private TextView home_page1;
    private TextView search1;
    private TextView shopping_cart1;
    private TextView discount1;
    public static boolean a;
    public static boolean b;
    public static boolean c;
    public static boolean d;
    public static boolean e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        initView();
        initListener();
        chooseFragment();
    }
/*
来改变下面布局的颜色
 */
    private void replacelayout() {
        if (a == false) {
            home_page.setImageResource(R.drawable.homepage);
            home_page1.setTextColor(getResources().getColor(R.color.gray));
        }if(b==false)
        {
            search.setImageResource(R.drawable.search);
            search1.setTextColor(getResources().getColor(R.color.gray));
        }if(d==false)
        {
            person.setImageResource(R.drawable.person);
            person1.setTextColor(getResources().getColor(R.color.gray));
        }if(c==false){
            discount.setImageResource(R.drawable.discount);
            discount1.setTextColor(getResources().getColor(R.color.gray));
        }if(e==false)
        {
            shopping_cart.setImageResource(R.drawable.shopping_cart);
            shopping_cart1.setTextColor(getResources().getColor(R.color.gray));
        }
    }

    public void chooseFragment(){
        switch (choose)
        {
            case 1:
                replaceFragment(new home_page());
                home_page1.setTextColor(getResources().getColor(R.color.orange1));
                home_page.setImageResource(R.drawable.homepage_1);
                break;
            case 2:
                replaceFragment(new search_page());
                search1.setTextColor(getResources().getColor(R.color.orange1));
                search.setImageResource(R.drawable.search_1);

                break;
            case 3:
                replaceFragment(new shopping_car());
                shopping_cart1.setTextColor(getResources().getColor(R.color.orange1));
                shopping_cart.setImageResource(R.drawable.shopping_cart_1);
                break;
            case 4:
                person.setImageResource(R.drawable.person_1);
                person1.setTextColor(getResources().getColor(R.color.orange1));
                replaceFragment(new person_page());
                break;
            case 5:
                discount.setImageResource(R.drawable.discount_1);
                discount1.setTextColor(getResources().getColor(R.color.orange1));
                replaceFragment(new discount_page());
                break;
        }
    }
    private void initListener() {
    homepage_layout.setOnClickListener(this);
    search_layout.setOnClickListener(this);
    discount_layout.setOnClickListener(this);
    person_layout.setOnClickListener(this);
    shopping_car.setOnClickListener(this);
    }

    private void initView() {
    homepage_layout=findViewById(R.id.homepage_layout);
    search_layout=findViewById(R.id.search_layout);
    discount_layout=findViewById(R.id.discount_layout);
    person_layout=findViewById(R.id.person_layout);
    shopping_car=findViewById(R.id.shopping_car_layout);
    person=findViewById(R.id.person);
    home_page=findViewById(R.id.homepage);
    search=findViewById(R.id.search);
    shopping_cart=findViewById(R.id.shopping_car);
    discount=findViewById(R.id.discount);
    home_page1=findViewById(R.id.homepage1);
    search1=findViewById(R.id.search1);
    shopping_cart1=findViewById(R.id.shopping_car1);
    discount1=findViewById(R.id.discount1);
    person1=findViewById(R.id.person1);
    }

    @Override
    public void onClick(View v) {
          switch (v.getId())
          {
              case R.id.homepage_layout:
                  a=true;
                  if(a==true) {
                      home_page1.setTextColor(getResources().getColor(R.color.orange1));
                      home_page.setImageResource(R.drawable.homepage_1);
                      replaceFragment(new home_page());
                  }
                  b=false;c=false;d=false;e=false;
                  replacelayout();
                  break;
              case R.id.search_layout:
                  b=true;
                  if(b==true)
                  {
                  search1.setTextColor(getResources().getColor(R.color.orange1));
                  search.setImageResource(R.drawable.search_1);
                  replaceFragment(new search_page());
                  }
                  a=false;c=false;d=false;e=false;
                  replacelayout();
                  break;
              case R.id.discount_layout:
                  c=true;
                  if(c==true) {
                      discount1.setTextColor(getResources().getColor(R.color.orange1));
                      discount.setImageResource(R.drawable.discount_1);
                      replaceFragment(new discount_page());
                  }
                  a=false;b=false;d=false;e=false;
                  replacelayout();
                  break;
              case R.id.person_layout:
                  d=true;
                  if(d==true) {
                      person.setImageResource(R.drawable.person_1);
                      person1.setTextColor(getResources().getColor(R.color.orange1));
                      replaceFragment(new person_page());
                  }
                  a=false;c=false;b=false;e=false;
                  replacelayout();
                  break;
              case R.id.shopping_car_layout:
                  e=true;
                  if(e==true) {
                      shopping_cart1.setTextColor(getResources().getColor(R.color.orange1));
                      shopping_cart.setImageResource(R.drawable.shopping_cart_1);
                      replaceFragment(new shopping_car());
                   a=false;b=false;c=false;d=false;
                   replacelayout();
                  }
                  break;
          }
    }
    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout,fragment);
        fragmentTransaction.commit();
    }
}
