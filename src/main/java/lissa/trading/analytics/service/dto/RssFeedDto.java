package lissa.trading.analytics.service.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@XmlRootElement(name = "rss")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RssFeedDto {

    private Channel channel;

    @XmlElement(name = "channel")
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Channel {
        private List<NewsDto> items;

        @XmlElement(name = "item")
        public void setItems(List<NewsDto> items) {
            this.items = items;
        }
    }
}
