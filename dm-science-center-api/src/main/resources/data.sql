INSERT INTO SCIENTIFIC_AREA (ID, NAME) VALUES (1, 'Informacioni sistemi');
INSERT INTO SCIENTIFIC_AREA (ID, NAME) VALUES (2, 'Baze podataka');
INSERT INTO SCIENTIFIC_AREA (ID, NAME) VALUES (3, 'Veštačka inteligencija');
INSERT INTO SCIENTIFIC_AREA (ID, NAME) VALUES (4, 'Softverski inženjering');
INSERT INTO SCIENTIFIC_AREA (ID, NAME) VALUES (5, 'Računarske komunikacije');
INSERT INTO SCIENTIFIC_AREA (ID, NAME) VALUES (6, 'Informacione tehnologije');

INSERT INTO USER (ID, FIRST_NAME, LAST_NAME, CITY, COUNTRY, TITLE, EMAIL, USERNAME, PASSWORD, USER_ROLE,
                  ACTIVATED_USER, SCIENTIFIC_AREA_ID)
VALUES (1, 'Dusan', 'Marjanski', 'Melenci', 'Srbija', 'Bachelor', 'marjanskid@yahoo.com', 'admin',
        '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 1, true, 1);

INSERT INTO USER (ID, FIRST_NAME, LAST_NAME, CITY, COUNTRY, TITLE, EMAIL, USERNAME, PASSWORD, USER_ROLE,
                  ACTIVATED_USER, SCIENTIFIC_AREA_ID)
VALUES (2, 'Milos', 'Krstic', 'Ruma', 'Srbija', 'Bachelor', 'krsticm@gmail.com', 'krle',
        '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 1, true, 1);

INSERT INTO MAGAZINE (ID, ACTIVE, ISSN_NUMBER, MAGAZINE_SUBSCRIBER, NAME, CHIEF_EDITOR_ID)
VALUES (1, true, 1, 1, 'Losmicin magazin', 2);

INSERT INTO MAGAZINE (ID, ACTIVE, ISSN_NUMBER, MAGAZINE_SUBSCRIBER, NAME, CHIEF_EDITOR_ID)
VALUES (2, true, 55, 0, 'Losmicin magazin2', 2);