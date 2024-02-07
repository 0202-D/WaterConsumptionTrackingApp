# WaterConsumptionTrackingApp
Консольное приложение для подачи показаний счетчиков отопления, горячей и холодной воды
Используемые технологии : Java  17, PostgreSQL, Docker
Для запуска базы данных используется docker контейнер, 
для этого выполните в корне проекта команду docker-compose up 
или запустите docker file с помощью intellij idea
По умолчанию при запуске приложения создаются два пользователя : 
userName - user,
password - user,
с ролью USER 
и 
userName - admin,
password - admin,
с ролью ADMIN

Так же создаются три счетчика :
Горячей воды 
id=1;
name="HOT_WATER

Холодной воды
id=2;
name="COLD_WATER";

Отопления 
id=3
name="HEATING"

Взаимодействие с приложением производиться вводом через консоль

