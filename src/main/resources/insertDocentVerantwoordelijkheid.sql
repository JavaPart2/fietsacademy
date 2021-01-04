insert into docentenverantwoordelijkheden(docentid, verantwoordelijkheidid)
values ((select id from docenten where voornaam = 'testM'),
        (select id from verantwoordelijkheden where naam = 'test'));