#Описание программы
###Что нужно сделать
Нужно реализовать перепривязку файла. Файл может быть ошибочно привязан не к той книге.\
Переделать все формы добавления на bootstrap
###Изменение файла книги
При добавлении книги выдается только список непривязанных файлов. При редактировании книги выдается список непривязанных файлов плюс текущий файл книги. Таким образом удается избежать ошибки при привязке одного файла к двум разным книгам.
##Запуск
```shell script
>java -Dfile.encoding=UTF-8 -jar target/itbooks-0.0.1-SNAPSHOT.jar
```