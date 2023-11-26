
CREATE TABLE public.connexion (
                connection_id VARCHAR NOT NULL,
                expiration TIMESTAMP NOT NULL,
                CONSTRAINT connexion_pk PRIMARY KEY (connection_id)
);


CREATE TABLE public.communes (
                code_commune INTEGER NOT NULL,
                nom_commune VARCHAR NOT NULL,
                code_postal INTEGER NOT NULL,
                latitude REAL NOT NULL,
                longitude REAL NOT NULL,
                dans_metropole_nantes BOOLEAN NOT NULL,
                CONSTRAINT communes_pk PRIMARY KEY (code_commune)
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
                eleve_nom VARCHAR NOT NULL,
                eleve_prenom VARCHAR NOT NULL,
                eleve_date_naissance DATE NOT NULL,
                genre VARCHAR NOT NULL,
                eleve_payshab VARCHAR NOT NULL,
                eleve_villehab VARCHAR NOT NULL,
                eleve_codepostal INTEGER NOT NULL,
                eleve_mail VARCHAR,
                eleve_numtel VARCHAR,
                eleve_boursier BOOLEAN,
                eleve_infosup VARCHAR,
                type_souhait VARCHAR,
                logement_numero VARCHAR,
                code_commune INTEGER,
                connection_id VARCHAR,
                CONSTRAINT eleve_pk PRIMARY KEY (eleve_id)
);


ALTER SEQUENCE public.eleve_eleve_id_seq OWNED BY public.eleve.eleve_id;

ALTER TABLE public.eleve ADD CONSTRAINT connexion_eleve_fk
FOREIGN KEY (connection_id)
REFERENCES public.connexion (connection_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.eleve ADD CONSTRAINT communesfrance_eleve_fk
FOREIGN KEY (code_commune)
REFERENCES public.communes (code_commune)
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
