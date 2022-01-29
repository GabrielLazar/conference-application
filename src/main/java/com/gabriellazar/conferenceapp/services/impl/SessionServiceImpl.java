package com.gabriellazar.conferenceapp.services.impl;

import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Session;
import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.repositories.SessionRepository;
import com.gabriellazar.conferenceapp.services.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class SessionServiceImpl implements SessionService {

    private SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> getAllSessions() {
        List<Session> sessions = null;
        try {
            sessions = sessionRepository.findAll();
            log.info("Getting all sessions :: {}", sessions);
        } catch (Exception e) {
            log.error("Exception in getting all sessions :: {}", e);
            return Collections.emptyList();
        }
        return sessions;
    }

    @Override
    public Session getSessionById(final Long id) {
        return sessionRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Session not found with this id :: " + id));
    }

    @Override
    public Session createSession(final Session session) {
        Session savedSession = null;
        try {
            savedSession = sessionRepository.saveAndFlush(session);
            log.info("Successfully saved session :: {}", session);
        } catch (Exception e) {
            log.error("Exception in saving session :: {}, Exception :: {}", session, e);
        }
        return getSessionById(savedSession.getSession_id());
    }

    @Override
    public Session editSessionById(final Long id, Session session) {
        try {
            Session existingSession = getSessionById(id);
            BeanUtils.copyProperties(existingSession, session, "session_id");
            sessionRepository.saveAndFlush(existingSession);
            log.info("Updating session with id :: {} Session :: {}", id, existingSession);
        } catch (Exception e) {
            log.error("Exception in updating session id :: {}, session ::{}, Exception :: {}", id, session, e);
        }
        return getSessionById(id);
    }

    @Override
    public void deleteSessionById(final Long id) {
        try {
            sessionRepository.deleteById(id);
            log.info("Successfully deleted session with id :: {}", id);
        } catch (Exception e) {
            log.error("Exception in deleting session with id :: {}", id);
        }

    }
}
