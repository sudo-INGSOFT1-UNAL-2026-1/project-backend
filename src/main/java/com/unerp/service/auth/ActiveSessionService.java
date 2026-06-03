package com.unerp.service.auth;

import org.springframework.stereotype.Service;

@Service
public class ActiveSessionService {


    private String activeToken = null;

    public boolean hasActiveSession() {
        return activeToken != null;
    }

    public void setActiveToken(String token) {
        this.activeToken = token;
    }

    public String getActiveToken() {
        if(!hasActiveSession()) {
            throw new IllegalArgumentException("No hay una sesión activa.");
        }
        return activeToken;
    }

    public void clearSession() {
        this.activeToken = null;
    }

    public void validateNoActiveSession() {
        if (hasActiveSession()) {
            throw new IllegalArgumentException("Ya existe una sesión activa. Por favor, cierre la sesión actual antes de iniciar una nueva.");
        }
    }


}
