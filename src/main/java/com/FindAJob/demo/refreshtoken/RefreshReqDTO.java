package com.FindAJob.demo.refreshtoken;

import java.time.Instant;

public record RefreshReqDTO(String token,
                            String email) {
}
