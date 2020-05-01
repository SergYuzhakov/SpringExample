package testgroup.filmography.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import testgroup.filmography.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
/*
имитируем хранилище в памяти, создадим список с несколькими фильмами.
Для хранения списка будем использовать не List, а Map,
чтобы было удобно получать конкретный фильм по его id,
не перебирая для этого весь список. Для генерации id используем AtomicInteger.
 */

/*
Чтобы отдать наши классы под управление Spring'а, нужно обозначить, что они являются компонентами.
 */
@Repository
public class FilmDAOImpl implements FilmDAO {

    /*
  Т.к. мы настроили доступ к данным через БД удаляем пробный список, он нам больше не нужен
    private static final AtomicInteger AUTO_ID = new AtomicInteger(0);
    private static Map<Integer,Film> films = new HashMap<>();

    static {
        Film film1 = new Film();
        film1.setId(AUTO_ID.getAndIncrement());
        film1.setTitle("Inception");
        film1.setYear(2010);
        film1.setGenre("sci-fi");
        film1.setWatched(true);
        films.put(film1.getId(), film1);

        Film film2 = new Film();
        film2.setId(AUTO_ID.getAndIncrement());
        film2.setTitle("Predator");
        film2.setYear(1990);
        film2.setGenre("sci-fi");
        film2.setWatched(true);
        films.put(film2.getId(), film2);

        Film film3 = new Film();
        film3.setId(AUTO_ID.getAndIncrement());
        film3.setTitle("Aliens");
        film3.setYear(1982);
        film3.setGenre("sci-fi");
        film3.setWatched(true);
        films.put(film3.getId(), film3);
    }

     */

 // Добавляем фабрику сессий и будем работать через нее.

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Film> allFilms(int page) {
        //List<Film> list = new ArrayList<>(films.values());
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("from Film").setFirstResult(10 * (page - 1)).setMaxResults(10).list();
    }

    @Override
    public int filmsCount() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(*) from Film", Number.class).getSingleResult().intValue();

    }

    @Override
    public void add(Film film) {
        //film.setId(AUTO_ID.getAndIncrement());
        //films.put(film.getId(), film);
        Session session = sessionFactory.getCurrentSession();
        session.persist(film);

    }

    @Override
    public void delete(Film film) {
       // films.remove(film.getId());
        Session session = sessionFactory.getCurrentSession();
        session.delete(film);
    }

    @Override
    public void edit(Film film) {
        //films.put(film.getId(), film);
        Session session = sessionFactory.getCurrentSession();
        session.update(film);
    }

    @Override
    public Film getById(int id) {
        //return films.get(id);
        Session session = sessionFactory.getCurrentSession();
        return session.get(Film.class, id);
    }

    @Override
    public boolean checkTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        Query query;
        query = session.createQuery("from Film where title = :title");
        query.setParameter("title", title);
        return query.list().isEmpty();

    }
}
