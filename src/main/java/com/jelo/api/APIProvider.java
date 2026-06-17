package com.jelo.api;

import com.jelo.api.exception.APINotLoadedException;

public class APIProvider {

    private static JeloAPI API = null;

    public static JeloAPI get() {
        JeloAPI instance = APIProvider.API;
        if (instance == null) {
            throw new APINotLoadedException();
        }
        return instance;
    }

    public static void set(JeloAPI API) {
        APIProvider.API = API;
    }
}
