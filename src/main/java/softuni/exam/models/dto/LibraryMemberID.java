package softuni.exam.models.dto;

import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LibraryMemberID {

    @XmlElement(name = "id")
    private long id;

    public long getId() {
        return id;
    }

    public LibraryMemberID setId(long id) {
        this.id = id;
        return this;
    }
}
