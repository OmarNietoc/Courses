package com.edutech.courses.config;

import com.edutech.courses.model.Category;
import com.edutech.courses.model.Course;
import com.edutech.courses.model.Level;
import com.edutech.courses.repository.CategoryRepository;
import com.edutech.courses.repository.CourseRepository;
import com.edutech.courses.repository.LevelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final LevelRepository levelRepository;

    public DataLoader(CourseRepository courseRepository, CategoryRepository categoryRepository, LevelRepository levelRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.levelRepository = levelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear categorías
        Category dev = categoryRepository.save(new Category("Desarrollo"));
        Category legal = categoryRepository.save(new Category("Legal"));
        Category seguridad = categoryRepository.save(new Category("Seguridad y Vigilancia"));
        Category negocios = categoryRepository.save(new Category("Negocios y Estrategia"));
        Category ciencia = categoryRepository.save(new Category("Ciencia e Ingeniería"));

        // Crear niveles
        Level beginner = levelRepository.save(new Level("Principiante"));
        Level intermediate = levelRepository.save(new Level("Intermedio"));
        Level advanced = levelRepository.save(new Level("Avanzado"));

        // Crear cursos
        Course curso1 = Course.builder()
                .title("Java para principiantes con ejemplos legales")
                .description("Aprende Java desde cero con casos legales prácticos. Ideal para quienes quieren defender su código.")
                .price(49.99)
                .category(legal)
                .level(beginner)
                .instructorId(12L) // Por ejemplo, instructor ID de Saul Goodman
                .tags(Arrays.asList("java", "legal", "poo"))
                .build();

        Course curso2 = Course.builder()
                .title("Seguridad avanzada para instalaciones de pollos")
                .description("Curso enfocado en la seguridad y vigilancia de instalaciones de producción alimentaria altamente secretas.")
                .price(79.99)
                .category(seguridad)
                .level(advanced)
                .instructorId(14L) // Por ejemplo, instructor ID de Mike Ehrmantraut
                .tags(Arrays.asList("seguridad", "vigilancia", "estrategia"))
                .build();

        Course curso3 = Course.builder()
                .title("Negociación Estratégica y Persuasión")
                .description("Aprende técnicas avanzadas de negociación inspiradas en grandes personajes de la industria legal y comercial.")
                .price(59.99)
                .category(negocios)
                .level(intermediate)
                .instructorId(13L)
                .tags(Arrays.asList("negociación", "persuasión", "estrategia"))
                .build();

        Course curso4 = Course.builder()
                .title("Ciencia Interdimensional e Inventiva")
                .description("Explora teorías y experimentos poco convencionales que desafían la física moderna.")
                .price(99.99)
                .category(ciencia)
                .level(advanced)
                .instructorId(15L) // Rick
                .tags(Arrays.asList("ciencia", "inventos", "realidades alternas"))
                .build();

        Course curso5 = Course.builder()
                .title("Hacking Ético para Instructores con Pasado Turbio")
                .description("Aprende técnicas de hacking ético con casos basados en experiencias poco tradicionales.")
                .price(69.99)
                .category(seguridad)
                .level(intermediate)
                .instructorId(16L) // Gus
                .tags(Arrays.asList("hacking", "ético", "seguridad"))
                .build();

        courseRepository.saveAll(Arrays.asList(curso1, curso2, curso3, curso4, curso5));
    }
}
