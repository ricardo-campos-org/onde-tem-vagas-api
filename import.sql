INSERT INTO state (name, acronym) VALUES ('Santa Catarina', 'SC');
INSERT INTO city (name, state_id) VALUES ('Joinville', (SELECT id FROM state WHERE acronym = 'SC'));

INSERT INTO portal (name, jobs_url, city_id)
VALUES ('Joinville Vagas', 'https://www.joinvillevagas.com.br/', (SELECT id FROM city WHERE name = 'Joinville'));

INSERT INTO portal (name, jobs_url, city_id)
VALUES ('Indeed', 'https://br.indeed.com/jobs?q=&l=Joinville%2C+SC&sort=date', (SELECT id FROM city WHERE name = 'Joinville'));

INSERT INTO portal (name, jobs_url, city_id)
VALUES ('Info Jobs', 'https://www.infojobs.com.br/vagas-de-emprego-joinville.aspx', (SELECT id FROM city WHERE name = 'Joinville'));

INSERT INTO portal (name, jobs_url, city_id)
VALUES ('Trabalha Brasil', 'https://www.trabalhabrasil.com.br/vagas-empregos-em-joinville-sc', (SELECT id FROM city WHERE name = 'Joinville'));

INSERT INTO portal (name, jobs_url, city_id)
VALUES ('BNE', 'https://www.bne.com.br/vagas-de-emprego-em-joinville-sc', (SELECT id FROM city WHERE name = 'Joinville'));

INSERT INTO portal (name, jobs_url, city_id)
VALUES ('Vagas', 'https://www.vagas.com.br/vagas-de-joinville,-santa-catarina', (SELECT id FROM city WHERE name = 'Joinville'));

INSERT INTO user (first_name, last_name, email, enabled, updated_at, terms)
  VALUES ('Jéssica', 'Schmoller', 'jeschmoller@gmail.com', TRUE, '2022-09-06 22:22:11', 'Recepcionista');
  
INSERT INTO user (first_name, last_name, email, enabled, updated_at, terms)
  VALUES ('Ricardo', 'Montania', 'ricardo.montania@gmail.com', TRUE, '2022-09-06 22:22:11', 'Gerente');

INSERT INTO user (first_name, last_name, email, enabled, updated_at, terms)
  VALUES ('Gabriel', 'Montania', 'gmontania@hotmail.com', TRUE, '2022-09-06 22:22:11', 'Gerente,Produto,Projeto');
