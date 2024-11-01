package lissa.trading.analytics.service.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lissa.trading.analytics.service.adapter.LocalDateTimeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {
    private String title;

    private String description;

    private LocalDateTime pubDate;

    private String url;

    @XmlElement(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "description")
    public void setDescription(String description) {
        this.description = description;
    }


    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @XmlElement(name = "pubDate")
    public void setPubDate(LocalDateTime pubDate) {
        this.pubDate = pubDate;
    }

    @XmlElement(name = "link")
    public void setUrl(String url) {
        this.url = url;
    }
}
