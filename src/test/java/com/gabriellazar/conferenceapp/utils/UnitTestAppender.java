package com.gabriellazar.conferenceapp.utils;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class UnitTestAppender extends AppenderBase<LoggingEvent> {

    private static EvictingQueue<LoggingEvent> loggingEventsQueue = new EvictingQueue<>(100);

    @Override
    protected void append(LoggingEvent eventObject) {
        loggingEventsQueue.add(eventObject);
    }

    public static void clearLogging() {
        loggingEventsQueue.clear();
    }

    public static LoggingEvent findLoggingEvent(String searchString) {
        List<LoggingEvent> loggingEventList = new ArrayList<>(loggingEventsQueue);
        for (LoggingEvent loggingEvent : loggingEventList) {
            if (loggingEvent.getFormattedMessage().contains(searchString)) {
                return loggingEvent;
            }
        }
        return null;
    }

    public static List<LoggingEvent> findLoggingEvents(String searchString) {
        List<LoggingEvent> loggingEventList = new ArrayList<>(loggingEventsQueue);

        return loggingEventList.stream()
                .filter(loggingEvent -> loggingEvent.getFormattedMessage().contains(searchString))
                .collect(Collectors.toList());
    }

    private static class EvictingQueue<E> extends Vector<E> {

        int queueLimit;

        public EvictingQueue(int queueLimit) {
            super();
            this.queueLimit = queueLimit;
        }

        @Override
        public synchronized boolean add(E e) {
            if (elementCount > queueLimit) {
                super.remove(super.firstElement());
            }
            return super.add(e);
        }
    }
}
