insert into docenten(voornaam,familienaam,wedde,emailadres,geslacht,campusid)
values('testM', 'testM', 1000, 'testM@test.be', 'MAN', (select id from campussen where naam = 'test')),
    ('testV', 'testV', 1000, 'testV@test.be', 'VROUW', (select id from campussen where naam = 'test'));
insert into docentenbijnamen(docentid, bijnaam)
values ((select id from docenten where voornaam = 'testM'), 'test');