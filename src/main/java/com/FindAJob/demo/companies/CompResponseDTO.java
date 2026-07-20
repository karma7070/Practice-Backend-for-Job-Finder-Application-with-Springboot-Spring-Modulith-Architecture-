package com.FindAJob.demo.companies;

public record CompResponseDTO(String comp_name,
                              String location,
                              String comp_email) {

    public static CompResponseDTO from(Companies company){
        return new CompResponseDTO(
                company.getComp_name(),
                company.getLocation(),
                company.getComp_email()
        );

    }

}
