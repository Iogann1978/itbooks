package ru.home.itbooks.model.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class ContentItem {
    @XmlElement
    private String title;
    @XmlAttribute
    private String num;
    @XmlAttribute
    private int page;
    @XmlElement
    private List<ContentItem> item;
}
