package testgroup.filmography.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import testgroup.filmography.model.Film;
import testgroup.filmography.service.FilmService;
import testgroup.filmography.service.FilmServiceImpl;

import java.util.List;

@Controller
public class FilmController {
 // т.к. class FilmDAOImpl  и class FilmServiceImpl мы отметили специальными аннотациями:
//  И теперь нам больше не нужно самим создавать конкретные объекты этих классов:
//   private FilmService filmService = new FilmServiceImpl()
    // Вместо этого можно пометить поле специальной аннотацией и Spring сам подберет подходящую реализацию:

//@Autowired  // что использовать автосвязывание на поле не рекомендуется, лучше использовать конструктор или сеттер.
       private FilmService filmService;
       private  int page;
    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    // метод для отображения главной страницы со списком фильмов:
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView allFilms(@RequestParam(defaultValue = "1" ) int page){
        /*
        Здесь появилась новая аннотация @RequestParam,
         которая указывает что данное значение мы получаем из параметра запроса.
         Теперь, если мы перейдем по адресу с параметром, например http://localhost:8080/?page=4,
         то получим, соответственно, 4-ю страницу.
         */
        this.page = page;
        List<Film> films = filmService.allFilms(page);
        int filmsCount = filmService.filmsCount();
        int pagesCount = (filmsCount + 9) / 10;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("films");
        modelAndView.addObject("page", page);
        modelAndView.addObject("filmsList", films);
        modelAndView.addObject("filmsCount", filmsCount);
        modelAndView.addObject("pagesCount", pagesCount);

        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") int id) {
        // аннотация @PathVariable указывает на то, что данный параметр (int id) получается из адресной строки.
        Film film = filmService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        modelAndView.addObject("film", film);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editFilm(@ModelAttribute("film") Film film) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        filmService.edit(film);
        return modelAndView;
    }
// Метод для получения страницы:
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        return modelAndView;
    }
// метод для добавления страницы:
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addFilm(@ModelAttribute("film") Film film) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        filmService.add(film);
        return modelAndView;
    }

    //метод контроллера для удаления фильма из списка:
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteFilm(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        Film film = filmService.getById(id);
        filmService.delete(film);
        return modelAndView;
    }


}
