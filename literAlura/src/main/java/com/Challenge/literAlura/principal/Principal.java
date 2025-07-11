package com.Challenge.literAlura.principal;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Challenge.literAlura.model.Autor;
import com.Challenge.literAlura.model.Libro;
import com.Challenge.literAlura.repository.AutorRepository;
import com.Challenge.literAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode; // Para parsear JSON
import java.util.Scanner;
import java.util.List;
import java.util.Optional;

@Component // Indica a Spring que esta clase es un componente que puede ser inyectado
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/";
    private RestTemplate restTemplate = new RestTemplate(); // Para hacer peticiones HTTP
    private ObjectMapper objectMapper = new ObjectMapper(); // Para mapear JSON

    @Autowired
    private LibroRepository libroRepository; // Inyectamos el repositorio de libros

    @Autowired
    private AutorRepository autorRepository; // Inyectamos el repositorio de autores

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ----------------------------------------
                    LiterAlura - Catálogo de Libros
                    ----------------------------------------
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    ----------------------------------------
                    Elija una opción:
                    """;
            System.out.println(menu);
            try {
                opcion = Integer.valueOf(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Por favor, ingrese un número.");
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación LiterAlura. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el título del libro a buscar:");
        var tituloBusqueda = teclado.nextLine();

        // Construir la URL de la API de Gutendex
        String url = URL_BASE + "?search=" + tituloBusqueda.replace(" ", "%20");
        System.out.println("Buscando en la API: " + url);

        try {
            // Realizar la petición HTTP y obtener la respuesta JSON
            String json = restTemplate.getForObject(url, String.class);

            // Parsear el JSON
            JsonNode root = objectMapper.readTree(json);
            JsonNode results = root.get("results");

            if (results != null && results.isArray() && results.size() > 0) {
                // Tomamos el primer resultado como el más relevante
                JsonNode libroJson = results.get(0);

                String titulo = libroJson.get("title").asText();
                String idioma = libroJson.get("languages").get(0).asText();
                Integer numeroDescargas = libroJson.has("download_count") ? libroJson.get("download_count").asInt() : 0;

                JsonNode autoresJson = libroJson.get("authors");
                Autor autorEncontrado = null;

                if (autoresJson != null && autoresJson.isArray() && autoresJson.size() > 0) {
                    // Solo tomamos el primer autor para simplificar
                    JsonNode autorJson = autoresJson.get(0);
                    String nombreAutor = autorJson.get("name").asText();
                    Integer fechaNacimiento = autorJson.has("birth_year") && !autorJson.get("birth_year").isNull() ? autorJson.get("birth_year").asInt() : null;
                    Integer fechaFallecimiento = autorJson.has("death_year") && !autorJson.get("death_year").isNull() ? autorJson.get("death_year").asInt() : null;

                    // Buscar si el autor ya existe en la BD
                    Optional<Autor> autorExistente = autorRepository.findByNombreContainingIgnoreCase(nombreAutor);

                    if (autorExistente.isPresent()) {
                        autorEncontrado = autorExistente.get();
                        System.out.println("Autor existente encontrado en la base de datos.");
                    } else {
                        autorEncontrado = new Autor(nombreAutor, fechaNacimiento, fechaFallecimiento);
                        autorRepository.save(autorEncontrado);
                        System.out.println("Nuevo autor guardado en la base de datos.");
                    }
                } else {
                    System.out.println("No se encontró información del autor para este libro.");
                    return; // No podemos guardar el libro sin un autor
                }

                // Verificar si el libro ya existe en la BD
                Optional<Libro> libroExistente = libroRepository.findByTituloContainingIgnoreCase(titulo);

                if (libroExistente.isPresent()) {
                    System.out.println("Este libro ya está registrado en la base de datos.");
                    System.out.println(libroExistente.get()); // Mostrar el libro existente
                } else {
                    // Crear y guardar el nuevo libro
                    Libro nuevoLibro = new Libro(titulo, autorEncontrado, idioma, numeroDescargas);
                    libroRepository.save(nuevoLibro);
                    System.out.println("Libro guardado exitosamente:");
                    System.out.println(nuevoLibro);
                }

            } else {
                System.out.println("No se encontraron resultados para el título: " + tituloBusqueda);
            }

        } catch (JsonProcessingException e) {
            System.out.println("Error al procesar el JSON de la API: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al buscar el libro: " + e.getMessage());
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados aún.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados aún.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese el año para buscar autores vivos:");
        Integer anio = null;
        try {
            anio = Integer.valueOf(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Año inválido. Por favor, ingrese un número entero.");
            return;
        }

        List<Autor> autoresVivos = autorRepository.findAutoresVivosEnAnio(anio);
        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio + " en la base de datos.");
        } else {
            autoresVivos.forEach(System.out::println);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar libros:
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                (o cualquier otro código de idioma ISO 639-1 de 2 letras)
                """);
        var idiomaBusqueda = teclado.nextLine().toLowerCase();

        List<Libro> librosPorIdioma = libroRepository.findByIdioma(idiomaBusqueda);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma '" + idiomaBusqueda + "' registrados.");
        } else {
            librosPorIdioma.forEach(System.out::println);
        }
    }
}
