package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordRootDTO {

    @XmlElement(name = "borrowing_record")
    List<BorrowingRecordDTO> borrowing_records;

    public List<BorrowingRecordDTO> getBorrowing_records() {
        return borrowing_records;
    }
}
