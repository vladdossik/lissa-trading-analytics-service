package lissa.trading.analytics.service.dto.TinkoffPulse.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsOwnerDto {
    private String id;
    private String nickname;
}
