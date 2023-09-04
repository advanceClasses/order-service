package com.microservice.OrderService.entity;

public class Status {
    private static final String dikemas = "Pesanan sedang dikemas :)";
    private static final String diantar = "Pesanan sedang dikirim ke alamat tujuan ^_^";
    private static final String gagal = "Pesanan gagal, bayar dulu ya ^_^";

    public static String getDikemas(){
        return dikemas;
    }

    public static String getDiantar(){
        return diantar;
    }

    public static String getGagal() {
        return gagal;
    }
}
