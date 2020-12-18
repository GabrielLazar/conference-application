package com.gabriellazar.conferenceapp.services.impl;

import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Session;
import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.repositories.SessionRepository;
import com.gabriellazar.conferenceapp.services.SessionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    private SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public Session getSessionById(Long id) {
        return sessionRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Session not found with this id :: " +  id));
    }

    @Override
    public Session createSession(Session session) {
       return sessionRepository.saveAndFlush(session);
    }

    @Override
    public Session editSessionById(Long id, Session session) {
        Session existingSession = getSessionById(id);
        BeanUtils.copyProperties(existingSession,session,"session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }

    @Override
    public void deleteSessionById(Long id) {
        sessionRepository.deleteById(id);
    }
}
