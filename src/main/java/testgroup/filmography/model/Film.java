package testgroup.filmography.model;
/*
Сделаем наш класс Film сущностью (Entity) при помощи JPA аннотаций:
т.к.мы хотим, чтобы объекты класса Film могли быть сохранены в базе данных
 */

import javax.persistence.*;

@Entity

@Table(name = "films")  //указывает на конкретную таблицу для отображения этой сущности.
public class Film {

    @Id  // указывает, что данное поле является первичным ключом, т.е. это свойство будет использоваться для идентификации каждой уникальной записи.
    @Column(name = "id") // связывает поле со столбцом таблицы. Если имена поля и столбца таблицы совпадают, можно не указывать.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // свойство будет генерироваться автоматически, в скобках можно указать каким образом.
    private int id;
/*
Можно для каждого свойства еще дополнительно указать много чего еще,
например, что должно быть не нулевое, или уникальное,
указать значение по-умолчанию, максимальный размер и т.д.
Это пригодится если нужно сгенерировать таблицу на основании этого класса,
с Hibernate есть такая возможность.
Но мы таблицу уже сами создали и все свойства настроили, так что обойдемся без этого.
  Небольшое замечание:
В документации Hibernate применять аннотации рекомендуется не к полям, а к геттерам.
 */
    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private int year;

    @Column(name = "genre")
    private String genre;

    @Column(name = "watched")
    private boolean watched;
// + Getters and setters

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    @Override
    public String toString() {
        return  id +
                " " + title +
                " " + year +
                " " + genre +
                " " + watched;
    }
}
