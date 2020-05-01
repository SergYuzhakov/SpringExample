package testgroup.filmography.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration  //сообщает Spring что данный класс является конфигурационным и содержит определения и зависимости bean-компонентов
@EnableWebMvc  // позволяет импортировать конфигурацию Spring MVC из класса WebMvcConfigurationSupport
@ComponentScan(basePackages = "testgroup.filmography")  // сообщает Spring где искать компоненты, которыми он должен управлять,
           // т.е. классы, помеченные аннотацией @Component или ее производными,
         // такими как @Controller, @Repository, @Service. Эти аннотации автоматически определяют бин класса.

public class WebConfig implements WebMvcConfigurer {
/*
Реализуем некоторые методы интерфейса WebMvcConfigurer
В данном случае нам нужен метод addResourceHandlers,
с помощью которого укажем расположение статических веб-ресурсов.
 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/res/**").addResourceLocations("/res/");
    }

    @Bean // Бины (bean) — это объекты, которые управляются Spring'ом. Для определения бина используется аннотация @Bean.
    public ViewResolver viewResolver(){
  // В методе viewResolver() мы создаем реализацию   и определяем где именно искать представления в webapp.
        // Поэтому когда в методе контроллера мы устанавливали имя "films" представление найдется как "/pages/films.jsp"
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;  //  интерфейс, необходимый для нахождения представления по имени
    }
}
