package com.yashen.eestudyapp.util;


import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class JsonContext {

    private static Jsonb jsonb;

    public static Jsonb getJsonb() {
        if (jsonb == null) {
            jsonb = JsonbBuilder.create();
        }
        return jsonb;
    }
}

