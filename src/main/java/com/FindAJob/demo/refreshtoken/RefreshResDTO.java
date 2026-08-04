package com.FindAJob.demo.refreshtoken;

public record RefreshResDTO(String token) {

    public static RefreshResDTO from(RefreshToken refreshT){
        return new RefreshResDTO(
                refreshT.getToken()
        );

    }

}
