package com.FindAJob.demo.reg_users;

public record Reg_UserResponseDTO(
        String name,
        Integer age,
        Gen_Type gender,
        String profession,
        String email
) {

    public static Reg_UserResponseDTO from(Reg_Users user){
        return new Reg_UserResponseDTO(
              user.getName(),
              user.getAge(),
              user.getGender(),
              user.getProfession(),
                user.getEmail()
        );
    }

}
