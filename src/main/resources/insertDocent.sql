insert into docenten(voornaam, familienaam, wedde, emailadres, geslacht) values
('testM', 'testM', 1000, 'testM@test.be', 'MAN'),
('testV', 'testV', 1000, 'testV@test.be', 'VROUW');
insert into docentenbijnamen(docentid, bijnaam)
values ((select id from docenten where voornaam = 'testM'), 'test');