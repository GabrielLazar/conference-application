package com.gabriellazar.conferenceapp.services;

import com.gabriellazar.conferenceapp.models.Session;


import java.util.List;

public interface SessionService {

    List<Session> getAllSessions();
    Session getSessionById(Long id);
    Session createSession(Session session);
    Session editSessionById(Long id,Session session);
    void deleteSessionById(Long id);
}
