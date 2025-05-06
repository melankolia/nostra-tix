--
-- PostgreSQL database dump
--

-- Dumped from database version 16.8 (Ubuntu 16.8-0ubuntu0.24.04.1)
-- Dumped by pg_dump version 16.8 (Ubuntu 16.8-0ubuntu0.24.04.1)

-- Started on 2025-05-06 13:40:10 WIB

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3539 (class 1262 OID 16588)
-- Name: tix; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE tix WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.UTF-8';


ALTER DATABASE tix OWNER TO postgres;

\connect tix

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 230 (class 1259 OID 16718)
-- Name: booking; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.booking (
    id integer NOT NULL,
    user_id integer,
    schedule_id integer,
    created_date date DEFAULT CURRENT_DATE NOT NULL,
    updated_date date,
    ispaid boolean DEFAULT false NOT NULL,
    total_price integer
);


ALTER TABLE public.booking OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16717)
-- Name: booking_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.booking ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.booking_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 234 (class 1259 OID 16748)
-- Name: booking_seat; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.booking_seat (
    id integer NOT NULL,
    booking_id integer NOT NULL,
    seat_id integer NOT NULL,
    created_date date DEFAULT CURRENT_DATE NOT NULL,
    updated_date character varying
);


ALTER TABLE public.booking_seat OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16747)
-- Name: booking_seat_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.booking_seat ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.booking_seat_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 216 (class 1259 OID 16645)
-- Name: city; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.city (
    id integer NOT NULL,
    name character varying,
    created_date date DEFAULT CURRENT_DATE,
    updated_date date
);


ALTER TABLE public.city OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16644)
-- Name: city_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.city ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.city_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 226 (class 1259 OID 16693)
-- Name: movie; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movie (
    id integer NOT NULL,
    name character varying,
    showing_start_date date,
    created_date date DEFAULT CURRENT_DATE,
    updated_date date,
    showing_end_date date
);


ALTER TABLE public.movie OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16692)
-- Name: movie_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.movie ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.movie_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 228 (class 1259 OID 16713)
-- Name: schedule; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.schedule (
    id integer NOT NULL,
    movie_id integer,
    studio_id integer,
    show_time timestamp with time zone,
    created_date date DEFAULT CURRENT_DATE NOT NULL,
    updated_date date,
    price integer DEFAULT 0 NOT NULL,
    end_show_time timestamp with time zone
);


ALTER TABLE public.schedule OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16712)
-- Name: schedule_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.schedule ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.schedule_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 222 (class 1259 OID 16679)
-- Name: seat; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.seat (
    id integer NOT NULL,
    "row" character varying,
    "column" integer,
    seat_type_id integer,
    created_date date DEFAULT CURRENT_DATE,
    updated_date date,
    studio_id integer,
    is_available boolean DEFAULT true
);


ALTER TABLE public.seat OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16678)
-- Name: seat_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.seat ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.seat_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 224 (class 1259 OID 16685)
-- Name: seat_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.seat_type (
    id integer NOT NULL,
    name character varying,
    created_date date DEFAULT CURRENT_DATE,
    updated_date date,
    additional_price integer
);


ALTER TABLE public.seat_type OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16684)
-- Name: seat_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.seat_type ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.seat_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 220 (class 1259 OID 16666)
-- Name: studio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.studio (
    id integer NOT NULL,
    name character varying,
    theater_id integer,
    created_date date DEFAULT CURRENT_DATE,
    updated_date date,
    base_price integer
);


ALTER TABLE public.studio OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16665)
-- Name: studio_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.studio ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.studio_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 218 (class 1259 OID 16653)
-- Name: theater; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.theater (
    id integer NOT NULL,
    name character varying,
    city_id integer,
    created_date date DEFAULT CURRENT_DATE NOT NULL,
    updated_date date,
    base_price integer
);


ALTER TABLE public.theater OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16652)
-- Name: theater_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.theater ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.theater_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 232 (class 1259 OID 16727)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    id integer NOT NULL,
    name character varying,
    email character varying NOT NULL,
    phone_no character varying,
    password character varying,
    created_date date DEFAULT CURRENT_DATE NOT NULL,
    updated_date date
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16726)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."user" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3529 (class 0 OID 16718)
-- Dependencies: 230
-- Data for Name: booking; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.booking OVERRIDING SYSTEM VALUE VALUES (1, 1, 4, '2025-05-05', NULL, false, 70000);
INSERT INTO public.booking OVERRIDING SYSTEM VALUE VALUES (2, 2, 4, '2025-05-05', NULL, false, 60000);


--
-- TOC entry 3533 (class 0 OID 16748)
-- Dependencies: 234
-- Data for Name: booking_seat; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.booking_seat OVERRIDING SYSTEM VALUE VALUES (1, 1, 23, '2025-05-05', NULL);
INSERT INTO public.booking_seat OVERRIDING SYSTEM VALUE VALUES (2, 1, 24, '2025-05-05', NULL);
INSERT INTO public.booking_seat OVERRIDING SYSTEM VALUE VALUES (3, 2, 38, '2025-05-05', NULL);


--
-- TOC entry 3515 (class 0 OID 16645)
-- Dependencies: 216
-- Data for Name: city; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.city OVERRIDING SYSTEM VALUE VALUES (1, 'Palembang', NULL, NULL);
INSERT INTO public.city OVERRIDING SYSTEM VALUE VALUES (2, 'Depok', NULL, NULL);
INSERT INTO public.city OVERRIDING SYSTEM VALUE VALUES (3, 'Jakarta', NULL, NULL);


--
-- TOC entry 3525 (class 0 OID 16693)
-- Dependencies: 226
-- Data for Name: movie; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.movie OVERRIDING SYSTEM VALUE VALUES (1, 'The Witcher 3', '2025-05-05', '2025-05-05', NULL, '2025-06-05');
INSERT INTO public.movie OVERRIDING SYSTEM VALUE VALUES (2, 'The Last of Us', '2025-05-05', '2025-05-05', NULL, '2025-06-05');
INSERT INTO public.movie OVERRIDING SYSTEM VALUE VALUES (3, 'Saving Private Ryan', '2025-05-05', '2025-05-05', NULL, '2025-06-05');
INSERT INTO public.movie OVERRIDING SYSTEM VALUE VALUES (4, 'Devil May Cry 5', '2025-05-05', '2025-05-05', NULL, '2025-06-05');


--
-- TOC entry 3527 (class 0 OID 16713)
-- Dependencies: 228
-- Data for Name: schedule; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.schedule OVERRIDING SYSTEM VALUE VALUES (4, 1, 1, '2025-05-05 18:25:38.420246+07', '2025-05-05', NULL, 0, NULL);


--
-- TOC entry 3521 (class 0 OID 16679)
-- Dependencies: 222
-- Data for Name: seat; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (7, 'A', 7, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (8, 'B', 1, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (9, 'B', 2, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (10, 'B', 3, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (11, 'B', 4, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (12, 'B', 5, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (13, 'B', 6, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (14, 'B', 7, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (15, 'C', 1, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (16, 'C', 2, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (17, 'C', 3, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (18, 'C', 4, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (19, 'C', 5, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (20, 'C', 6, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (21, 'C', 7, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (22, 'D', 1, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (23, 'D', 2, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (24, 'D', 3, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (25, 'D', 4, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (26, 'D', 5, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (27, 'D', 6, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (28, 'D', 7, 1, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (29, 'E', 1, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (30, 'E', 2, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (31, 'E', 3, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (32, 'E', 4, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (33, 'E', 5, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (34, 'E', 6, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (35, 'E', 7, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (1, 'A', 1, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (2, 'A', 2, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (3, 'A', 3, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (4, 'A', 4, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (5, 'A', 5, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (6, 'A', 6, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (37, 'F', 1, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (38, 'F', 2, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (39, 'F', 3, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (40, 'F', 4, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (41, 'F', 5, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (42, 'F', 6, 2, '2025-05-05', NULL, 1, true);
INSERT INTO public.seat OVERRIDING SYSTEM VALUE VALUES (43, 'F', 7, 2, '2025-05-05', NULL, 1, true);


--
-- TOC entry 3523 (class 0 OID 16685)
-- Dependencies: 224
-- Data for Name: seat_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.seat_type OVERRIDING SYSTEM VALUE VALUES (1, 'Regular', '2025-05-05', NULL, NULL);
INSERT INTO public.seat_type OVERRIDING SYSTEM VALUE VALUES (2, 'Satin', '2025-05-05', NULL, 25000);


--
-- TOC entry 3519 (class 0 OID 16666)
-- Dependencies: 220
-- Data for Name: studio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.studio OVERRIDING SYSTEM VALUE VALUES (1, 'Studio 1', 1, '2025-05-05', NULL, NULL);
INSERT INTO public.studio OVERRIDING SYSTEM VALUE VALUES (2, 'Studio 2', 1, '2025-05-05', NULL, NULL);
INSERT INTO public.studio OVERRIDING SYSTEM VALUE VALUES (3, 'Studio 3', 1, '2025-05-05', NULL, NULL);
INSERT INTO public.studio OVERRIDING SYSTEM VALUE VALUES (4, 'Studio 4', 1, '2025-05-05', NULL, NULL);
INSERT INTO public.studio OVERRIDING SYSTEM VALUE VALUES (5, 'Studio 1', 2, '2025-05-05', NULL, NULL);
INSERT INTO public.studio OVERRIDING SYSTEM VALUE VALUES (6, 'Studio 2', 2, '2025-05-05', NULL, NULL);
INSERT INTO public.studio OVERRIDING SYSTEM VALUE VALUES (7, 'Studio 3', 2, '2025-05-05', NULL, NULL);
INSERT INTO public.studio OVERRIDING SYSTEM VALUE VALUES (8, 'Studio 4', 2, '2025-05-05', NULL, NULL);
INSERT INTO public.studio OVERRIDING SYSTEM VALUE VALUES (9, 'Studio 1', 3, '2025-05-05', NULL, NULL);
INSERT INTO public.studio OVERRIDING SYSTEM VALUE VALUES (10, 'Studio 2', 3, '2025-05-05', NULL, NULL);
INSERT INTO public.studio OVERRIDING SYSTEM VALUE VALUES (11, 'Studio 3', 3, '2025-05-05', NULL, NULL);
INSERT INTO public.studio OVERRIDING SYSTEM VALUE VALUES (12, 'Studio 4', 3, '2025-05-05', NULL, NULL);


--
-- TOC entry 3517 (class 0 OID 16653)
-- Dependencies: 218
-- Data for Name: theater; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.theater OVERRIDING SYSTEM VALUE VALUES (1, 'CGV DTC', 2, '2025-05-05', NULL, NULL);
INSERT INTO public.theater OVERRIDING SYSTEM VALUE VALUES (3, 'CGV Kenari Mas', 3, '2025-05-05', NULL, NULL);
INSERT INTO public.theater OVERRIDING SYSTEM VALUE VALUES (2, 'CGV Palembang Square', 1, '2025-05-05', NULL, 35000);


--
-- TOC entry 3531 (class 0 OID 16727)
-- Dependencies: 232
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."user" OVERRIDING SYSTEM VALUE VALUES (1, 'Ageng', 'ageng@gmail.com', '0812222', '123456', '2025-05-05', NULL);
INSERT INTO public."user" OVERRIDING SYSTEM VALUE VALUES (2, 'Aldo', 'aldo@gmail.com', '0812222', '123456', '2025-05-05', NULL);


--
-- TOC entry 3540 (class 0 OID 0)
-- Dependencies: 229
-- Name: booking_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.booking_id_seq', 2, true);


--
-- TOC entry 3541 (class 0 OID 0)
-- Dependencies: 233
-- Name: booking_seat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.booking_seat_id_seq', 3, true);


--
-- TOC entry 3542 (class 0 OID 0)
-- Dependencies: 215
-- Name: city_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.city_id_seq', 3, true);


--
-- TOC entry 3543 (class 0 OID 0)
-- Dependencies: 225
-- Name: movie_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.movie_id_seq', 4, true);


--
-- TOC entry 3544 (class 0 OID 0)
-- Dependencies: 227
-- Name: schedule_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.schedule_id_seq', 4, true);


--
-- TOC entry 3545 (class 0 OID 0)
-- Dependencies: 221
-- Name: seat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seat_id_seq', 43, true);


--
-- TOC entry 3546 (class 0 OID 0)
-- Dependencies: 223
-- Name: seat_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seat_type_id_seq', 2, true);


--
-- TOC entry 3547 (class 0 OID 0)
-- Dependencies: 219
-- Name: studio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.studio_id_seq', 12, true);


--
-- TOC entry 3548 (class 0 OID 0)
-- Dependencies: 217
-- Name: theater_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.theater_id_seq', 3, true);


--
-- TOC entry 3549 (class 0 OID 0)
-- Dependencies: 231
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 2, true);


--
-- TOC entry 3356 (class 2606 OID 16741)
-- Name: booking booking_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_pk PRIMARY KEY (id);


--
-- TOC entry 3360 (class 2606 OID 16752)
-- Name: booking_seat booking_seat_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking_seat
    ADD CONSTRAINT booking_seat_pk PRIMARY KEY (id);


--
-- TOC entry 3342 (class 2606 OID 16651)
-- Name: city city_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.city
    ADD CONSTRAINT city_pk PRIMARY KEY (id);


--
-- TOC entry 3352 (class 2606 OID 16699)
-- Name: movie movie_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movie
    ADD CONSTRAINT movie_pk PRIMARY KEY (id);


--
-- TOC entry 3354 (class 2606 OID 16769)
-- Name: schedule schedule_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_pk PRIMARY KEY (id);


--
-- TOC entry 3348 (class 2606 OID 16691)
-- Name: seat seat_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seat
    ADD CONSTRAINT seat_pk PRIMARY KEY (id);


--
-- TOC entry 3350 (class 2606 OID 16689)
-- Name: seat_type seat_type_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seat_type
    ADD CONSTRAINT seat_type_pk PRIMARY KEY (id);


--
-- TOC entry 3346 (class 2606 OID 16670)
-- Name: studio studio_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studio
    ADD CONSTRAINT studio_pk PRIMARY KEY (id);


--
-- TOC entry 3344 (class 2606 OID 16659)
-- Name: theater theater_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.theater
    ADD CONSTRAINT theater_pk PRIMARY KEY (id);


--
-- TOC entry 3358 (class 2606 OID 16733)
-- Name: user user_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pk PRIMARY KEY (id);


--
-- TOC entry 3367 (class 2606 OID 16840)
-- Name: booking booking_schedule_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_schedule_fk FOREIGN KEY (schedule_id) REFERENCES public.schedule(id) ON UPDATE SET NULL ON DELETE SET NULL;


--
-- TOC entry 3369 (class 2606 OID 16753)
-- Name: booking_seat booking_seat_booking_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking_seat
    ADD CONSTRAINT booking_seat_booking_fk FOREIGN KEY (booking_id) REFERENCES public.booking(id) ON DELETE SET NULL;


--
-- TOC entry 3370 (class 2606 OID 16758)
-- Name: booking_seat booking_seat_seat_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking_seat
    ADD CONSTRAINT booking_seat_seat_fk FOREIGN KEY (seat_id) REFERENCES public.seat(id) ON DELETE SET NULL;


--
-- TOC entry 3368 (class 2606 OID 16820)
-- Name: booking booking_user_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_user_fk FOREIGN KEY (user_id) REFERENCES public."user"(id) ON UPDATE SET NULL ON DELETE SET NULL;


--
-- TOC entry 3365 (class 2606 OID 16770)
-- Name: schedule schedule_movie_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_movie_fk FOREIGN KEY (movie_id) REFERENCES public.movie(id);


--
-- TOC entry 3366 (class 2606 OID 16775)
-- Name: schedule schedule_studio_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_studio_fk FOREIGN KEY (studio_id) REFERENCES public.studio(id) ON DELETE SET NULL;


--
-- TOC entry 3363 (class 2606 OID 16707)
-- Name: seat seat_seat_type_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seat
    ADD CONSTRAINT seat_seat_type_fk FOREIGN KEY (seat_type_id) REFERENCES public.seat_type(id) ON DELETE SET NULL;


--
-- TOC entry 3364 (class 2606 OID 16763)
-- Name: seat seat_studio_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seat
    ADD CONSTRAINT seat_studio_fk FOREIGN KEY (studio_id) REFERENCES public.studio(id) ON DELETE SET NULL;


--
-- TOC entry 3362 (class 2606 OID 16673)
-- Name: studio studio_theater_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studio
    ADD CONSTRAINT studio_theater_fk FOREIGN KEY (theater_id) REFERENCES public.theater(id) ON DELETE SET NULL;


--
-- TOC entry 3361 (class 2606 OID 16660)
-- Name: theater theater_city_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.theater
    ADD CONSTRAINT theater_city_fk FOREIGN KEY (city_id) REFERENCES public.city(id) ON DELETE SET NULL;


-- Completed on 2025-05-06 13:40:10 WIB

--
-- PostgreSQL database dump complete
--

