Simple web-application for library

1. Queries for a genre and a list of all books with all authors in that genre

(Запросы на получение названия жанра и списка всех книг со всеми авторами у этого жанра).

![image](https://github.com/Bloody-Mary/library-project/assets/37978402/84e4ac27-1ec1-42d2-aeeb-55ab6bc63765)


![image](https://github.com/Bloody-Mary/library-project/assets/37978402/f91b9216-2d23-415f-8145-33be1232a71b)


![image](https://github.com/Bloody-Mary/library-project/assets/37978402/c0e93578-1d12-4ad7-ae26-f222c090f0e1)


2. Queries to find an author by his name with a list of his books

(Запросы на нахождение автора по его имени со списком его книг).

a. Using Spring Data JPA

(С помощью автогенерации запроса).

![image](https://github.com/Bloody-Mary/library-project/assets/37978402/99321c84-a52c-4a2e-845f-a4b70c72949c)

![image](https://github.com/Bloody-Mary/library-project/assets/37978402/eeef2ff7-7983-4e82-a4d8-ebd2c4f68215)



b. Using @Query

(С помощью аннотации @Query).

![image](https://github.com/Bloody-Mary/library-project/assets/37978402/8ce90269-1578-4ead-a19a-554da8fec468)


![image](https://github.com/Bloody-Mary/library-project/assets/37978402/55f1c8f0-a4a4-4ae7-a2b0-ef558bc7cea9)


c. Using JPA Criteria Queries

(С помощью JPA Criteria Queries).

![image](https://github.com/Bloody-Mary/library-project/assets/37978402/6372b37b-50bd-4e40-8381-0312e52f3291)

![image](https://github.com/Bloody-Mary/library-project/assets/37978402/09796032-469a-4314-934c-3cdad015161d)


3. Queries to Create book, Update book and Delete book using HTTP methods and CRUD operations

(Запросы на создание книги, изменение информации о книге и удаление книги).

a. Create a book - @PostMapping
(Создание книги)

![Screenshot from 2023-11-03 15-44-15](https://github.com/Bloody-Mary/library-project/assets/37978402/f0cc1cb3-03e5-46df-a331-a584ddcb252f)


b. Update a book - PutMapping
(Изменение информации о книге)

![Screenshot from 2023-11-03 17-23-27](https://github.com/Bloody-Mary/library-project/assets/37978402/18dbf017-3408-4cd1-b3a7-9d324d8b626c)


c. Delete a book - @DeleteMapping
(Удаление книги)

![Screenshot from 2023-11-03 17-45-17](https://github.com/Bloody-Mary/library-project/assets/37978402/72cacb38-6b87-462f-b974-76176795aa7b)


4. Creating a controller with html pages using the FreeMarker

(Создание контроллера с html страничками с помощью шаблонизатора FreeMarker).

a. Book data table implemented with FreeMarker and with different parameter settings in css and ftml files

(Таблица данных о книгах, реализованная с помощью FreeMarker и с различными настройками параметров в файлах css и ftml).


![Screenshot from 2023-11-09 19-23-09](https://github.com/Bloody-Mary/library-project/assets/37978402/4672b76f-71ec-4f39-bdc2-a36d05b38a6d)

![Screenshot from 2023-11-09 19-23-19](https://github.com/Bloody-Mary/library-project/assets/37978402/c24262f0-8f94-4a26-9b9b-0526ce637b2d)

![Screenshot from 2023-11-09 19-23-27](https://github.com/Bloody-Mary/library-project/assets/37978402/a823747e-5b8f-4eb6-984f-3cfb9a6cf1af)


b. Author data table implemented with FreeMarker and with different parameter settings in css and ftml files

(Таблица данных об авторах,реализованная с помощью FreeMarker и с различными настройками параметров в файлах css и ftml).


![Screenshot from 2023-11-09 20-00-08](https://github.com/Bloody-Mary/library-project/assets/37978402/b6ad7785-0248-4c15-9565-006ac3a76ab6)

![Screenshot from 2023-11-09 20-00-50](https://github.com/Bloody-Mary/library-project/assets/37978402/382b82ca-3411-4f4a-bf42-5355383e7e39)
