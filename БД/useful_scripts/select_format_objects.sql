select (to_char(object_id, '999999999999')) as object_id, object_name, type_id, (to_char(parent_object_id, '999999999999')) as parent_object_id from objects
order by object_id;
commit work;