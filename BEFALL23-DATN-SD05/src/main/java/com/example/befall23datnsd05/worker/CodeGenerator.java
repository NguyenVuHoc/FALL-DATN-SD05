package com.example.befall23datnsd05.worker;

import java.util.UUID;

public class CodeGenerator  {
    public static String genUUID(){
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
