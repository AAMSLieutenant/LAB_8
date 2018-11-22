insert into test (id)
select to_number( '11'||(to_char(sysdate, 'DDMMYYHH24MI'))) from dual;
select * from test;