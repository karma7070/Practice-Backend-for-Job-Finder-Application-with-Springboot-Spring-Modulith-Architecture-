package com.FindAJob.demo.J_Application;

import java.time.Instant;

public record ApplicationResDTO(
        Long jobId,
        Long userId,
        String info,
        Instant applied_at,
        AppStatus status
) {
    public static ApplicationResDTO from(Application app){
      ApplicationResDTO response = new ApplicationResDTO(
              app.getJob().getId(),
              app.getUser().getId(),
              app.getInfo(),
              app.getApplied_at(),
              app.getStatus()
      )  ;

      return response;
    }

}
