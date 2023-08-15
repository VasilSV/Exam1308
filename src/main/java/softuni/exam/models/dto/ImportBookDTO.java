package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import softuni.exam.models.entity.Genre;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ImportBookDTO {
    @Expose
    @Size(min = 3 , max = 40)
    @NotNull
    private String author;
    @Expose
    @NotNull
    private Boolean available;
    @Expose
    @Size(min = 5)
    @NotNull
    private String description;
    @Expose
    @Enumerated(EnumType.STRING)
    @NotNull
    private Genre genre;
    @Expose
    @Size(min = 3 , max = 40)
    @NotNull
    private String title;
    @Expose
    @Positive
    @NotNull
    private Double rating;

    public ImportBookDTO() {
    }

    public String getAuthor() {
        return author;
    }

    public ImportBookDTO setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Boolean getAvailable() {
        return available;
    }

    public ImportBookDTO setAvailable(Boolean available) {
        this.available = available;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ImportBookDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public ImportBookDTO setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ImportBookDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public ImportBookDTO setRating(Double rating) {
        this.rating = rating;
        return this;
    }
}
