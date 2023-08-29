package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportBookDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOK_FILE_PATH = "src/main/resources/files/json/books.json";

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper,
                           ValidationUtil validationUtil, Gson gson) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return bookRepository.count()>0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files.readString(Path.of(BOOK_FILE_PATH));
    }

    @Override
    public String importBooks() throws IOException {

        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readBooksFromFile(), ImportBookDTO[].class))
                .filter(importBookDTO -> {
                    boolean isValid = validationUtil.isValid(importBookDTO);

                    boolean doesntExist = bookRepository
                            .findBookByTitle(importBookDTO.getTitle()).isEmpty();
                    if (!doesntExist) {
                        isValid = false;
                    }
                    Optional<Book> isExist =
                            Optional.ofNullable(bookRepository
                                    .findAllBookByTitle(importBookDTO.getTitle()));

                    if (isExist.isPresent()) {
                        isValid = false;
                    }

                    sb.append(isValid ?
                                    String.format("Successfully imported book %s - %s"
                                            , importBookDTO.getAuthor(), importBookDTO.getTitle())
                                    : "Invalid book")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(importBookDTO -> {
                    Book book = modelMapper.map(importBookDTO, Book.class);

                    return book;
                })
                .forEach(bookRepository::save);

        return sb.toString();

    }
}
