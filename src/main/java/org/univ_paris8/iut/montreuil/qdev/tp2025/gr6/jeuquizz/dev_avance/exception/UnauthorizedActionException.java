package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.exception;

public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String message) { super(message); }
}