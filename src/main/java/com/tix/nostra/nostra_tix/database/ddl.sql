-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION pg_database_owner;

-- DROP SEQUENCE public.booking_id_seq;

CREATE SEQUENCE public.booking_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.booking_seat_id_seq;

CREATE SEQUENCE public.booking_seat_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.city_id_seq;

CREATE SEQUENCE public.city_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.movie_id_seq;

CREATE SEQUENCE public.movie_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.schedule_id_seq;

CREATE SEQUENCE public.schedule_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.seat_id_seq;

CREATE SEQUENCE public.seat_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.seat_type_id_seq;

CREATE SEQUENCE public.seat_type_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.studio_id_seq;

CREATE SEQUENCE public.studio_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.theater_id_seq;

CREATE SEQUENCE public.theater_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.user_id_seq;

CREATE SEQUENCE public.user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;-- public.city definition

-- Drop table

-- DROP TABLE public.city;

CREATE TABLE public.city (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" varchar NULL,
	created_date date DEFAULT CURRENT_DATE NULL,
	updated_date date NULL,
	CONSTRAINT city_pk PRIMARY KEY (id)
);


-- public.movie definition

-- Drop table

-- DROP TABLE public.movie;

CREATE TABLE public.movie (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" varchar NULL,
	showing_start_date date NULL,
	created_date date DEFAULT CURRENT_DATE NULL,
	updated_date date NULL,
	showing_end_date date NULL,
	CONSTRAINT movie_pk PRIMARY KEY (id)
);


-- public.seat_type definition

-- Drop table

-- DROP TABLE public.seat_type;

CREATE TABLE public.seat_type (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" varchar NULL,
	created_date date DEFAULT CURRENT_DATE NULL,
	updated_date date NULL,
	additional_price int4 NULL,
	CONSTRAINT seat_type_pk PRIMARY KEY (id)
);


-- public."user" definition

-- Drop table

-- DROP TABLE public."user";

CREATE TABLE public."user" (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" varchar NULL,
	email varchar NOT NULL,
	phone_no varchar NULL,
	"password" varchar NULL,
	created_date date DEFAULT CURRENT_DATE NOT NULL,
	updated_date date NULL,
	CONSTRAINT user_pk PRIMARY KEY (id)
);


-- public.theater definition

-- Drop table

-- DROP TABLE public.theater;

CREATE TABLE public.theater (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" varchar NULL,
	city_id int4 NULL,
	created_date date DEFAULT CURRENT_DATE NOT NULL,
	updated_date date NULL,
	base_price int4 NULL,
	CONSTRAINT theater_pk PRIMARY KEY (id),
	CONSTRAINT theater_city_fk FOREIGN KEY (city_id) REFERENCES public.city(id) ON DELETE SET NULL
);


-- public.studio definition

-- Drop table

-- DROP TABLE public.studio;

CREATE TABLE public.studio (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" varchar NULL,
	theater_id int4 NULL,
	created_date date DEFAULT CURRENT_DATE NULL,
	updated_date date NULL,
	base_price int4 NULL,
	CONSTRAINT studio_pk PRIMARY KEY (id),
	CONSTRAINT studio_theater_fk FOREIGN KEY (theater_id) REFERENCES public.theater(id) ON DELETE SET NULL
);


-- public.schedule definition

-- Drop table

-- DROP TABLE public.schedule;

CREATE TABLE public.schedule (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	movie_id int4 NULL,
	studio_id int4 NULL,
	show_time timestamptz NULL,
	created_date date DEFAULT CURRENT_DATE NOT NULL,
	updated_date date NULL,
	price int4 DEFAULT 0 NOT NULL,
	end_show_time timestamptz NULL,
	CONSTRAINT schedule_pk PRIMARY KEY (id),
	CONSTRAINT schedule_movie_fk FOREIGN KEY (movie_id) REFERENCES public.movie(id),
	CONSTRAINT schedule_studio_fk FOREIGN KEY (studio_id) REFERENCES public.studio(id) ON DELETE SET NULL
);


-- public.seat definition

-- Drop table

-- DROP TABLE public.seat;

CREATE TABLE public.seat (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"row" varchar NULL,
	"column" int4 NULL,
	seat_type_id int4 NULL,
	created_date date DEFAULT CURRENT_DATE NULL,
	updated_date date NULL,
	studio_id int4 NULL,
	is_available bool DEFAULT true NULL,
	CONSTRAINT seat_pk PRIMARY KEY (id),
	CONSTRAINT seat_seat_type_fk FOREIGN KEY (seat_type_id) REFERENCES public.seat_type(id) ON DELETE SET NULL,
	CONSTRAINT seat_studio_fk FOREIGN KEY (studio_id) REFERENCES public.studio(id) ON DELETE SET NULL
);


-- public.booking definition

-- Drop table

-- DROP TABLE public.booking;

CREATE TABLE public.booking (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	user_id int4 NULL,
	schedule_id int4 NULL,
	created_date date DEFAULT CURRENT_DATE NOT NULL,
	updated_date date NULL,
	ispaid bool DEFAULT false NOT NULL,
	total_price int4 NULL,
	CONSTRAINT booking_pk PRIMARY KEY (id),
	CONSTRAINT booking_schedule_fk FOREIGN KEY (schedule_id) REFERENCES public.schedule(id) ON DELETE SET NULL ON UPDATE SET NULL,
	CONSTRAINT booking_user_fk FOREIGN KEY (user_id) REFERENCES public."user"(id) ON DELETE SET NULL ON UPDATE SET NULL
);


-- public.booking_seat definition

-- Drop table

-- DROP TABLE public.booking_seat;

CREATE TABLE public.booking_seat (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	booking_id int4 NOT NULL,
	seat_id int4 NOT NULL,
	created_date date DEFAULT CURRENT_DATE NOT NULL,
	updated_date varchar NULL,
	CONSTRAINT booking_seat_pk PRIMARY KEY (id),
	CONSTRAINT booking_seat_booking_fk FOREIGN KEY (booking_id) REFERENCES public.booking(id) ON DELETE SET NULL,
	CONSTRAINT booking_seat_seat_fk FOREIGN KEY (seat_id) REFERENCES public.seat(id) ON DELETE SET NULL
);