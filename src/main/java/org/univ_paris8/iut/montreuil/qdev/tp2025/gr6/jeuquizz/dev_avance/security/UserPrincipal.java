package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.security;

import java.security.Principal;

public class UserPrincipal implements Principal {
    private final String name;
    public UserPrincipal(String name) { this.name = name; }
    @Override
    public String getName() { return name; }
}