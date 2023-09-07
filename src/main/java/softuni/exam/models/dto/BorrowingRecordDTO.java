package softuni.exam.models.dto;

import org.hibernate.validator.constraints.Length;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordDTO {

    @XmlElement(name = "borrow_date")
    private String borrowDate;

    @XmlElement(name = "return_date")
    private String returnDate;

    @XmlElement(name = "book")
    private BookTitle book;

    @XmlElement(name = "member")
    private LibraryMemberID member;

    @XmlElement(name = "remarks")
    @Length(min = 3, max = 100)
    private String remarks;

    public BorrowingRecordDTO() {
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public BorrowingRecordDTO setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
        return this;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public BorrowingRecordDTO setReturnDate(String returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public BookTitle getBook() {
        return book;
    }

    public BorrowingRecordDTO setBook(BookTitle book) {
        this.book = book;
        return this;
    }

    public LibraryMemberID getMember() {
        return member;
    }

    public BorrowingRecordDTO setMember(LibraryMemberID member) {
        this.member = member;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public BorrowingRecordDTO setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }
    //<borrow_date>2020-01-13</borrow_date>
//<return_date>2022-09-11</return_date>
//<book>
//    <title>The Lord of the Rings</title>
//</book>
//<member>
//<id>27</id>
//</member>
//    <remarks>Handle with care, fragile book.</remarks
}
