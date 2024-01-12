## Test task for Gridnine

### Описание

Приложение представляет собой имитацию небольшого модуля в системе обработки авиаперелётов. 

Фильтрация осуществляется в сервисе `FlightServiceImpl`, который в свою очередь использует предоставленный класс `FlightBuilder` в качестве замены (имитации) стандартной базы данных. Это приводит к некоторым допущениям в коде и особенно в тестах.

Сервис взаимодействует с различными критериями фильтрации, представленными интерфейсом `FlightFiltrationCriterion`, а также реализациями этого интерфейса. Таким образом каждое новое правило фильтрации будет представлено классом, имплементирующим `FlightFiltrationCriterion` и, следовательно, переопределяющим метод `.getPredicate()`, то есть генерирующим необходимый предикат, исходя из своих потребностей и переданных данных (к примеру, класс-критерий, связанный со временем проводимом на земле между различными Сегментами, использует максимальное и минимальное время и другие поля).

Подобная реализация позволяет в случае появления на сайте системы нового фильтра, просто добавить новый класс-критерий и его логику. Изменять при этом класс-сервис и иной код не потребуется. При этом сервис работает и будет работать не только с одним критерием, но и с их списком, в том числе включающим новые, ранее неизвестные классы-критерии (у них один интерфейс).

При этом формирование списка, содержащего классы-критерии, для последующего направления в сервис можно будет осуществлять в отдельном классе, конкретная реализация которого будет зависеть от формата входных данных.

### Использованные технологии

1) Java 17
2) Gradle 8.4
3) JUnit

### Запуск

1) Скачайте данный репозиторий;
2) Перейдите в скачанную папку в терминале;
3) Введите следующую команду:

```zsh
./gradlew run
```