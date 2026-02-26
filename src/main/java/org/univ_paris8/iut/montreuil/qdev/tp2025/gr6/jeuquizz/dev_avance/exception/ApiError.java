package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.exception;

import java.time.Instant;
import java.util.List;

public class ApiError {
    private int status;
    private String error;
    private List<String> messages;
    private Instant timestamp;

    public ApiError(int status, String error, List<String> messages) {
        this.status = status;
        this.error = error;
        this.messages = messages;
        this.timestamp = Instant.now();
    }

    public int getStatus() { return status; }
    public String getError() { return error; }
    public List<String> getMessages() { return messages; }
    public Instant getTimestamp() { return timestamp; }
}