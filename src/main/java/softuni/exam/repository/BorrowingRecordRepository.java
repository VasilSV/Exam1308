package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.Genre;

import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    BorrowingRecord findAllByBookTitle(String title);

    BorrowingRecord findAllByLibraryMemberId(long id);

    List<BorrowingRecord> findAllByBook_GenreOrderByBorrowDateDesc(Genre SCIENCE_FICTION);

}


