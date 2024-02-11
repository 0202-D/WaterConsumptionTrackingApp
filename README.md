# WaterConsumptionTrackingApp
# Описание
Приложение для подачи показаний счетчиков отопления, горячей и холодной воды.

## Используемые технологии
- Java 17
- PostgreSQL
- Docker
- Liquibase

- # Запуск приложения на Tomcat

1. **Установите Apache Tomcat**:
   - Убедитесь, что Apache Tomcat установлен на вашем компьютере.

2. **Сборка проекта**:
   - Соберите проект с помощью Maven.

3. **Деплой приложения**:
   - Скопируйте .war файл приложения в директорию webapps в установленной директории Tomcat.

4. **Запуск Tomcat**:
   - Запустите сервер Tomcat, выполнив startup.sh (Unix) или startup.bat (Windows) в директории Tomcat.

5. **Проверьте ваше приложение**:
   - Откройте браузер и введите URL вашего приложения: http://localhost:8080/auth


## Детали приложения
Приложение доступно на порте 8080. Ниже приведены доступные эндпоинты:

- auth
- reg
- user
- user/current
- user/month
- user/add
- user/history

## Запуск приложения
Для запуска базы данных используется docker контейнер. Выполните следующую команду в корне проекта: docker-compose up.
Альтернативно, вы можете также запустить docker file с помощью Intellij IDEA.

## Пользователи и счетчики по умолчанию
При запуске приложения создаются два пользователя:
1. userName - user, password - user, роль USER
2. userName - admin, password - admin, роль ADMIN

Также создаются три счетчика:
1. Горячей воды
   - id: 1
   - name: HOT_WATER

2. Холодной воды
   - id: 2
   - name: COLD_WATER

3. Отопления
   - id: 3
   - name: HEATING
