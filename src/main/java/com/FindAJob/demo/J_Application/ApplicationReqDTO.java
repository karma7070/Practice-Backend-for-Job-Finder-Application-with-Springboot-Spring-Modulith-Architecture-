package com.FindAJob.demo.J_Application;

import java.time.Instant;

public record ApplicationReqDTO(
        Long jobId,
        Long userId,
        String info,
        Instant applied_at,
        AppStatus status
) {
}
