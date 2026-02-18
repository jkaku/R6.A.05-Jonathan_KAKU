package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.security;

import java.security.Principal;

public class RolePrincipal implements Principal {
    private final String role;

    public RolePrincipal(String role) { this.role = role; }

    @Override
    public String getName() { return role; }
}