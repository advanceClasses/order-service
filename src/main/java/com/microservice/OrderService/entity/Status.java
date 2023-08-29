package com.microservice.OrderService.entity;

public class Status {
    private static final String dikemas = "Pesanan sedang dikemas :)";
    private static final String diantar = "Pesanan sedang dikirim ke alamat tujuan ^_^";

    public static String getDikemas(){
        return dikemas;
    }

    public static String getDiantar(){
        return diantar;
    }
}
