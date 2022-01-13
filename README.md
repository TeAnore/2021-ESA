# 2021-ESA
2021-ESA: Лабораторные задания по предмету "Архитектура корпоративных систем"

# Описание
В этом репозитории содержатся выполненные задания по курсу "Архитектура корпоративных систем".

Задания выполняли студенты: Юлия Пчелкина (6131-010402D) и Алексей Чаплыгин (6131-010402D).

# Компоненты
*Lab1 - Каталог с заданием:

    *Practical Work #1. Application with common JavaEE architecture.

*Lab2 - Каталог с заданиями:

    *Practical Work #2. Application using Spring Framework
    *Practical Work #3. RESTful web-service
    *Practical Work #4. Java Message Service

*webtemplate-ee - web interface для Lab1.

*webtemplate-spring - web interface для Lab2.

Пример отображения web interface для EE стэка
![GUI EE](/Lab1/others/proof_img.PNG "GUI EE")

Пример отображения web interface для Spting стэка
![GUI SPRING](/Lab2/others/proof_img.PNG "GUI SPRING")

Web interface реализован на yarn, для просмтра требуется запустить yarn serve -port 8081 в каталогах webtemplate_ee для JavaEE или webtemplate_spring для spring соответсвенно и перейти по http://localhost:8081 для просмотра веб интерфейса.

Примеры ответа в зависимости от заголовка:
![XML respoce](/Lab2/others/Lab3_XML_Response.PNG "XML response")
![Json response](/Lab2/others/Lab3_Json_Response.PNG "Json response")

Примеры логирования событий и email уведомлений в таблицах:
![Event](/Lab2/others/Lab4_Log_Event.PNG "Event")
![Email Notifications](/Lab2/others/Lab4_Log_Email_Notification.PNG "Email Notifications")