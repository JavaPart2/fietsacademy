insert into campussen(naam, straat, huisNr, postCode, gemeente)
values('test', 'test', 'test', 'test', 'test');
insert into campussentelefoonnrs(campusid, nummer, fax, opmerking)
values((select id from campussen where naam = 'test'), '052334625', false, 'test');