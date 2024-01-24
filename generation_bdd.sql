
CREATE SEQUENCE public.role_role_id_seq;

CREATE TABLE public.role (
                role_id INTEGER NOT NULL DEFAULT nextval('public.role_role_id_seq'),
                role_nom VARCHAR(32) NOT NULL,
                CONSTRAINT role_pk PRIMARY KEY (role_id)
);


ALTER SEQUENCE public.role_role_id_seq OWNED BY public.role.role_id;

CREATE SEQUENCE public.personne_personne_id_seq;

CREATE TABLE public.personne (
                personne_id INTEGER NOT NULL DEFAULT nextval('public.personne_personne_id_seq'),
                personne_nom VARCHAR(256) NOT NULL,
                personne_prenom VARCHAR(256) NOT NULL,
                personne_login VARCHAR(256),
                personne_password VARCHAR(3000),
                role_id INTEGER,
                CONSTRAINT personne_pk PRIMARY KEY (personne_id)
);


ALTER SEQUENCE public.personne_personne_id_seq OWNED BY public.personne.personne_id;

CREATE TABLE public.connexion (
                connection_id VARCHAR NOT NULL,
                expiration TIMESTAMP NOT NULL,
                personne_id INTEGER NOT NULL,
                CONSTRAINT connexion_pk PRIMARY KEY (connection_id)
);


CREATE TABLE public.commune (
                code_commune INTEGER NOT NULL,
                nom_commune VARCHAR NOT NULL,
                code_postal INTEGER NOT NULL,
                latitude REAL NOT NULL,
                longitude REAL NOT NULL,
                dans_metropole_nantes BOOLEAN NOT NULL,
                CONSTRAINT commune_pk PRIMARY KEY (code_commune)
);


CREATE TABLE public.type_appart (
                type_appart_nom VARCHAR NOT NULL,
                CONSTRAINT type_appart_pk PRIMARY KEY (type_appart_nom)
);


CREATE TABLE public.logement (
                logement_numero VARCHAR NOT NULL,
                logement_genre_requis VARCHAR,
                type_appart_nom VARCHAR NOT NULL,
                logement_places_dispo INTEGER NOT NULL,
                CONSTRAINT logement_pk PRIMARY KEY (logement_numero)
);


CREATE TABLE public.souhait (
                type_souhait VARCHAR NOT NULL,
                CONSTRAINT souhait_pk PRIMARY KEY (type_souhait)
);


CREATE SEQUENCE public.eleve_eleve_id_seq;

CREATE TABLE public.eleve (
                eleve_id INTEGER NOT NULL DEFAULT nextval('public.eleve_eleve_id_seq'),
                personne_id INTEGER NOT NULL,
                eleve_numscei INTEGER NOT NULL,
                eleve_date_naissance DATE,
                genre VARCHAR,
                eleve_payshab VARCHAR,
                eleve_villehab VARCHAR,
                eleve_codepostal INTEGER,
                eleve_mail VARCHAR,
                eleve_numtel VARCHAR,
                eleve_boursier BOOLEAN,
                eleve_infosup VARCHAR,
                type_souhait VARCHAR,
                logement_numero VARCHAR,
                code_commune INTEGER,
                CONSTRAINT eleve_pk PRIMARY KEY (eleve_id)
);


ALTER SEQUENCE public.eleve_eleve_id_seq OWNED BY public.eleve.eleve_id;

ALTER TABLE public.personne ADD CONSTRAINT role_personne_fk
FOREIGN KEY (role_id)
REFERENCES public.role (role_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.connexion ADD CONSTRAINT personne_connexion_fk
FOREIGN KEY (personne_id)
REFERENCES public.personne (personne_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.eleve ADD CONSTRAINT personne_eleve_fk
FOREIGN KEY (personne_id)
REFERENCES public.personne (personne_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.eleve ADD CONSTRAINT communesfrance_eleve_fk
FOREIGN KEY (code_commune)
REFERENCES public.commune (code_commune)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.logement ADD CONSTRAINT apparttype_appartrez_fk
FOREIGN KEY (type_appart_nom)
REFERENCES public.type_appart (type_appart_nom)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.eleve ADD CONSTRAINT appartrez_eleve_fk
FOREIGN KEY (logement_numero)
REFERENCES public.logement (logement_numero)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.eleve ADD CONSTRAINT elevesouhait_eleve_fk
FOREIGN KEY (type_souhait)
REFERENCES public.souhait (type_souhait)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
