package com.FindAJob.demo.refreshtoken.internal;

import com.FindAJob.demo.SecurityPackage.AuthResDTO;
import com.FindAJob.demo.SecurityPackage.JWTService;
import com.FindAJob.demo.refreshtoken.RefreshResDTO;
import com.FindAJob.demo.refreshtoken.RefreshToken;
import com.FindAJob.demo.reg_users.Reg_Users;
import com.FindAJob.demo.reg_users.internal.Reg_UsersRepository;
import com.FindAJob.demo.reg_users.internal.Reg_UsersService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class RefreshService {

    private final RefreshRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwt;
    private final Reg_UsersRepository userRepo;


    public RefreshService(RefreshRepository repo,
                          PasswordEncoder passwordEncoder,
                          JWTService jwt,
                          Reg_UsersRepository userRepo) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
        this.userRepo = userRepo;
    }

    public String createRefreshT(String email){

       Reg_Users user = userRepo.findByEmail(email)
               .orElseThrow(()->new UsernameNotFoundException("User not found"));

        String token = getRefToken();

        RefreshToken refreshT = new RefreshToken(passwordEncoder.encode(token),
                Instant.now(),
                Instant.now().plus(Duration.ofDays(1/2)),
                user);

        repo.save(refreshT);

        RefreshResDTO refRes = RefreshResDTO.from(refreshT);

        return token;

    }

    public AuthResDTO refreshAccessToken(String email) {

        Reg_Users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found: Refresh Access Service"));

        Optional<RefreshToken> refToken = Optional.of(repo.findByUserEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Refresh Token not found: Refresh Access Service")));

        RefreshToken rToken = refToken.get();

        long num = this.checkUserTokens(email);

//checks if number of tokens a user currently has is greater than 1
// deletes the rest and creates a new one if it is, solving the multiple log in problem
// but creating a single session per email problem

        if (num != 1) {
            repo.deleteAllByUserEmail(email);

            createRefreshT(email);

            if (validateRefToken(rToken)) {

                String accToken = jwt.generateToken(user.getEmail(),
                        user.getName(),
                        user.getRole());

                return new AuthResDTO(user.getName(),
                        user.getEmail(),
                        accToken);

            }
        }else {

                if (validateRefToken(rToken)) {

                    String accToken = jwt.generateToken(user.getEmail(),
                            user.getName(),
                            user.getRole());

                    return new AuthResDTO(user.getName(),
                            user.getEmail(),
                            accToken);

                } else {
                    throw new
                            UsernameNotFoundException("Token has either expired or been revoked");
                }


        }

        throw new UsernameNotFoundException("Failed!");
    }


    public boolean validateRefToken(RefreshToken rToken){

        RefreshToken refToken = repo.getReferenceById(rToken.getId());

        //this approach checks if the expiration is
        // after the current time then returns a true else a false
      if(Instant.now().isAfter(refToken.getExpiresAt())){
          return true;
      } else {
          return false;
      }



        //  if(rToken.getExpiresAt() >= Instant.now()) this didn't work
        //Instant time1 = Instant.now()

    }

    public AuthResDTO revokeOrSuspendRefToken(String email){
        Optional<RefreshToken> refT = Optional.of(repo.findByUserEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Refresh token doesn't exist")));

        RefreshToken rT = refT.get();

        repo.deleteById(rT.getId());

        return new AuthResDTO(
                rT.getUser().getName(),
                rT.getUser().getEmail(),
                "Unauthorized"
        );

    }


















////////////////   Functions used in the service      //////////////////

public String getRefToken(){
    SecureRandom random = new SecureRandom();//declaring a "Secure random" variable which allows u to use
    // methods for generating and managing a random value in bytes

    byte[] bytes = new byte[32]; //declaration of an array of
    //bytes, 32, to be precise as specified in the square brackets

    random.nextBytes(bytes);// generates random of number
    // of bytes equivalent to the array

    String token = Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(bytes);

    return token;
}

    public Long checkUserTokens(String email) {
        Reg_Users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));

        List<RefreshToken> refTokens = repo.findAllByUserEmail(email);

        long num = 0L;

        for (int i = 0; i < refTokens.size(); i++) {
            if (refTokens.get(i) != null) {
                num += 1;
            }
        }

        return num;
    }

}
