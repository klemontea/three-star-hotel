package com.miniproject.threestarhotel.ui.model.response;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -7826290605479411807L;
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getJwttoken() {
        return jwttoken;
    }
}
