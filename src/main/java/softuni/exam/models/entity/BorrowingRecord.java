package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private LocalDate borrowDate;
    @Column(nullable = false)
    private LocalDate returnDate;
    @Column
    private String remarks;


    @ManyToOne
    private Book book;
    @ManyToOne
    private LibraryMember libraryMember;


    public BorrowingRecord() {
    }

    public long getId() {
        return id;
    }

    public BorrowingRecord setId(long id) {
        this.id = id;
        return this;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public BorrowingRecord setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
        return this;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public BorrowingRecord setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public BorrowingRecord setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public BorrowingRecord setBook(Book book) {
        this.book = book;
        return this;
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public BorrowingRecord setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
        return this;
    }
}