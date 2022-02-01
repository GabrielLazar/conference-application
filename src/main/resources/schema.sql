CREATE TABLE attendees
(
    attendee_id  SERIAL PRIMARY KEY,
    first_name   varchar(30) NOT NULL,
    last_name    varchar(30) NOT NULL,
    title        varchar(40) NULL,
    company      varchar(50) NULL,
    email        varchar(80) NOT NULL,
    phone_number varchar(10) NULL,
    password     varchar(150) NOT NULL,
    role         varchar(40) NULL
);


CREATE TABLE time_slots
(
    time_slot_id         SERIAL PRIMARY KEY,
    time_slot_date       date                   NOT NULL,
    start_time           time without time zone NOT NULL,
    end_time             time without time zone NOT NULL,
    is_keynote_time_slot boolean default false  NOT NULL
);

CREATE TABLE sessions
(
    session_id          SERIAL PRIMARY KEY,
    session_name        varchar(80)   NOT NULL,
    session_description varchar(1024) NOT NULL,
    session_length      integer       NOT NULL
);

CREATE TABLE session_schedule
(
    schedule_id  SERIAL PRIMARY KEY,
    time_slot_id integer     NOT NULL REFERENCES time_slots (time_slot_id),
    session_id   integer     NOT NULL REFERENCES sessions (session_id),
    room         varchar(30) NOT NULL
);

CREATE TABLE tags
(
    tag_id      SERIAL PRIMARY KEY,
    description varchar(30) NOT NULL
);

CREATE TABLE session_tags
(
    session_id integer NOT NULL REFERENCES sessions (session_id),
    tag_id     integer NOT NULL REFERENCES tags (tag_id)
);

CREATE TABLE speakers
(
    speaker_id    SERIAL PRIMARY KEY,
    first_name    varchar(30)   NOT NULL,
    last_name     varchar(30)   NOT NULL,
    title         varchar(40)   NOT NULL,
    company       varchar(50)   NOT NULL,
    speaker_bio   varchar(2000) NOT NULL,
    speaker_photo BYTEA   NULL
);

CREATE TABLE session_speakers
(
    session_id integer NOT NULL REFERENCES sessions (session_id),
    speaker_id integer NOT NULL REFERENCES speakers (speaker_id)
);

CREATE TABLE workshops
(
    workshop_id   SERIAL PRIMARY KEY,
    workshop_name varchar(60)   NOT NULL,
    description   varchar(1024) NOT NULL,
    requirements  varchar(1024) NOT NULL,
    room          varchar(30)   NOT NULL,
    capacity      integer       NOT NULL
);

CREATE TABLE workshop_speakers
(
    workshop_id integer NOT NULL REFERENCES workshops (workshop_id),
    speaker_id  integer NOT NULL REFERENCES speakers (speaker_id)
);
