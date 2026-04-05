# Task Management System
Учебный проект — система управления задачами.

## Технологии
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven

## Что умеет
- CRUD операции с задачами
- Статусы: CREATED, IN_PROGRESS, DONE
- Приоритеты: LOW, MEDIUM, HIGH
- Ограничение: не более 3 задач в работе у одного исполнителя
- Пагинация и фильтрация по статусу, приоритету, исполнителю

## Запуск
```bash
git clone https://github.com/ewaw01/Task-management-system.git
cd Task-management-system
./mvnw spring-boot:run
```

## API (базовый URL: /task)
* GET /{id} - получить задачу
* GET / - список задач (пагинация, фильтры)
* POST / - создать задачу
* PUT /{id}- обновить задачу
* DELETE /{id} - удалить задачу
* POST /{id}/start - начать выполнение
* POST /{id}/done - завершить задачу
