package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class BookTitle {

    @XmlElement(name = "title")
    private String title;

    public String getTitle() {
        return title;
    }

    public BookTitle setTitle(String title) {
        this.title = title;
        return this;
    }
}
