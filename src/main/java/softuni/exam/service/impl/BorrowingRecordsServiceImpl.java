package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BorrowingRecordDTO;
import softuni.exam.models.dto.BorrowingRecordRootDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.models.entity.Genre.SCIENCE_FICTION;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {
    private static final String BORROWING_FILE_PATH="src/main/resources/files/xml/borrowing-records.xml";

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository,
                                       BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository,
                                       XmlParser xmlParser, ValidationUtil validationUtil,
                                       ModelMapper modelMapper) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return borrowingRecordRepository.count()>0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(Path.of(BORROWING_FILE_PATH));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();
        xmlParser
                .fromFile(BORROWING_FILE_PATH, BorrowingRecordRootDTO.class)
                .getBorrowing_records()
                .stream()
                .filter(borrowingRecordDTO -> {
                    boolean isValid = validationUtil.isValid(borrowingRecordDTO);

                    Optional<BorrowingRecord> isExist =
                            Optional.ofNullable(borrowingRecordRepository.
                                    findAllByBookTitle(String.valueOf(borrowingRecordDTO.getBook().getTitle())));

                    if (isExist.isPresent()) {
                        isValid = false;
                    }
                    Optional<BorrowingRecord> isHere =
                            Optional.ofNullable(borrowingRecordRepository.
                                    findAllByLibraryMemberId(borrowingRecordDTO.getMember().getId()));

                    if (isHere.isPresent()) {
                        isValid = false;
                    }


                    sb.append(isValid ?
                                    String.format("Successfully imported borrowing record %s - %s"
                                            ,borrowingRecordDTO.getBook().getTitle()
                                            , borrowingRecordDTO.getBorrowDate())
                                    : "Invalid borrowing record")
                            .append(System.lineSeparator());

                    return isValid;


                }).map(borrowingRecordDTO -> {
                    BorrowingRecord borrowingRecord = modelMapper.map(borrowingRecordDTO, BorrowingRecord.class);
                    borrowingRecord
                            .setBook(bookRepository.findAllBookByTitle
                                    (String.valueOf(borrowingRecordDTO.getBook().getTitle())));
                    borrowingRecord
                            .setLibraryMember(libraryMemberRepository
                                    .findLibraryMemberById(borrowingRecordDTO.getMember().getId()));

                    return borrowingRecord;
                })
                .forEach(borrowingRecordRepository::save);
        return sb.toString();
    }

    @Override
    public String exportBorrowingRecords() {

        StringBuilder sb = new StringBuilder();

        List<BorrowingRecord> records =
                borrowingRecordRepository
                        .findAllByBook_GenreOrderByBorrowDateDesc
                                (SCIENCE_FICTION);

        records
                .forEach(record -> {
                    sb.append(String.format("Book title: %s\n" +
                                            "*Book author: %s\n" +
                                            "**Date borrowed: %s\n" +
                                            "***Borrowed by: %s %s\n",
                                    record.getBook().getTitle(), record.getBook().getAuthor(), record.getBorrowDate(),
                                    record.getLibraryMember().getFirstName(), record.getLibraryMember().getLastName()))
                            .append(System.lineSeparator());
                });

        return sb.toString();


    }



}
