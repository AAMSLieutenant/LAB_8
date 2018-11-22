select (to_char(o.object_id, '9999999999999')) as id, o.object_name, p.text_info, p.number_info, p.date_info, (to_char(o.parent_object_id, '9999999999999')) as parent_object_id, a.attr_id, a.ATTR_NAME

from objects o

inner join attributes a on a.TYPE_ID=15

inner join parameters p on p.object_id = o.object_id 
and a.ATTR_ID=p.ATTR_ID
order by attr_id;