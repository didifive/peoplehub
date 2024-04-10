--POPULATE PERSON
INSERT INTO PERSON (ID, NAME, BIRTH_DATE) values ('d82b113e-afcc-49c3-8d43-cc75bbe46c7f', 'Luis Zancanela', '1982-04-03');
INSERT INTO PERSON (ID, NAME, BIRTH_DATE) values ('219ba73b-8fca-409b-9e58-88eb38a4156c', 'João Neves', '1969-09-03');

--POPULATE ADDRESS
INSERT INTO ADDRESS (ID, PUBLIC_PLACE, NUMBER, CITY, STATE, ZIP_CODE, ADDRESS_TYPE, MAIN, PERSON_ID) values ('e117ca30-0ad5-4963-93ef-4601f94140ed', 'Larry Avenue', 1673, 'São Paulo', 'SP', '01065-087', 'HOME', true, 'd82b113e-afcc-49c3-8d43-cc75bbe46c7f');
INSERT INTO ADDRESS (ID, PUBLIC_PLACE, NUMBER, CITY, STATE, ZIP_CODE, ADDRESS_TYPE, MAIN, PERSON_ID) values ('d14f52b0-f53b-41fa-bd2f-957570ccc76a', 'Washington Avenue', 9680, 'São Paulo', 'SP', '01065-087', 'COMMERCIAL', false, 'd82b113e-afcc-49c3-8d43-cc75bbe46c7f');
INSERT INTO ADDRESS (ID, PUBLIC_PLACE, NUMBER, CITY, STATE, ZIP_CODE, ADDRESS_TYPE, MAIN, PERSON_ID) values ('843d577c-edd6-4fce-80a2-ff30efd33a5a', 'Larry Avenue', 1673, 'Ribeirão Preto', 'SP', '11684-258', 'HOME', true, '219ba73b-8fca-409b-9e58-88eb38a4156c');
INSERT INTO ADDRESS (ID, PUBLIC_PLACE, NUMBER, CITY, STATE, ZIP_CODE, ADDRESS_TYPE, MAIN, PERSON_ID) values ('160a4daa-07cc-4bcd-8394-ae13ff52cc25', 'Washington Avenue', 9680, 'Ribeirão Preto', 'SP', '11684-258', 'MAILING', false, '219ba73b-8fca-409b-9e58-88eb38a4156c');