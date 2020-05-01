package testgroup.filmography.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testgroup.filmography.dao.FilmDAO;
import testgroup.filmography.dao.FilmDAOImpl;
import testgroup.filmography.model.Film;

import java.util.List;

/*
Чтобы отдать наши классы под управление Spring'а, нужно обозначить, что они являются компонентами.
 */

@Service
public class FilmServiceImpl implements FilmService {

    private FilmDAO filmDAO;  // (автосвязывание) сообщает Spring о том, что он должен покопаться у себя в контексте и подставить сюда подходящий бин.
    @Autowired
    public FilmServiceImpl(FilmDAO filmDAO) {
        this.filmDAO = filmDAO;
    }

    @Override
    @Transactional
    public List<Film> allFilms(int page) {
        return filmDAO.allFilms(page);
    }

    @Override
    @Transactional
    public void add(Film film) {
        filmDAO.add(film);
    }

    @Override
    @Transactional
    public void delete(Film film) {
        filmDAO.delete(film);
    }

    @Override
    @Transactional
    public void edit(Film film) {
         filmDAO.edit(film);
    }

    @Override
    @Transactional
    public Film getById(int id) {
        return filmDAO.getById(id);
    }

    @Override
    @Transactional
    public int filmsCount() {
        return filmDAO.filmsCount();
    }

    @Override
    @Transactional
    public boolean checkTitle(String title) {
        return filmDAO.checkTitle(title);
    }
}
