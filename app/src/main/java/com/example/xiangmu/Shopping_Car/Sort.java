package com.example.xiangmu.Shopping_Car;


import com.example.xiangmu.middle_commodity.Spus;

import java.util.Comparator;

public class Sort implements Comparator<Spus>{

    @Override
    public int compare(Spus o1, Spus o2) {
        return o1.getPrice()>o2.getPrice()?1:-1;
    }
}
