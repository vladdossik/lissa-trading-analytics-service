package lissa.trading.analytics.service.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@XmlRootElement(name = "channel")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsResponseDto {
    private List<NewsDto> items;
}
