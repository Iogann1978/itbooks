select NORMALIZED_NAME, count(1)
from AUTHOR
group by NORMALIZED_NAME
having count(1) > 1;