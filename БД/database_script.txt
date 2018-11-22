CREATE TABLE Types(
type_id Integer NOT NULL PRIMARY KEY,
type_name Varchar(10),
parent_type_id Integer REFERENCES Types(type_id) ON DELETE CASCADE,
aread Integer,
awrite Integer,
mread Integer,
mwrite Integer,
dread Integer,
dwrite Integer);

CREATE TABLE Attributes(
attr_id Integer NOT NULL PRIMARY KEY,
attr_name Varchar(15),
type_id Integer REFERENCES Types(type_id) ON DELETE CASCADE);

CREATE TABLE Objects(
object_id Integer NOT NULL PRIMARY KEY,
object_name Varchar(20),
type_id Integer  REFERENCES Types(type_id),
parent_object_id Integer REFERENCES Objects(object_id) ON DELETE CASCADE);


CREATE TABLE Parameters(
attr_id Integer REFERENCES Attributes(attr_id) ON DELETE CASCADE,
object_id Integer REFERENCES Objects(object_id) ON DELETE CASCADE,
text_info Varchar(30),
number_info Integer,
date_info date);