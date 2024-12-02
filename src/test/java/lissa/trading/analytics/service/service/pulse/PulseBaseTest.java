package lissa.trading.analytics.service.service.pulse;

import lissa.trading.analytics.service.client.pulse.TinkoffPulseClient;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.BrandInfoDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.BrandLinkDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.FullBrandInfoDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.PayloadBrandInfoDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.BrokerIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.FullIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.PayloadIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.StockIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.TickerIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.FullNewsDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.NewsContentDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.NewsOwnerDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.NewsTickerDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.PayloadNewsDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.StockNewsDto;
import lissa.trading.analytics.service.service.tinkoffPulse.TinkoffPulseBrandInfoService;
import lissa.trading.analytics.service.service.tinkoffPulse.TinkoffPulseIdeasService;
import lissa.trading.analytics.service.service.tinkoffPulse.TinkoffPulseNewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PulseBaseTest {
    @Mock
    TinkoffPulseClient tinkoffPulseClientMock;

    @InjectMocks
    TinkoffPulseNewsService tinkoffPulseNewsService;

    @InjectMocks
    TinkoffPulseIdeasService tinkoffPulseIdeasService;

    @InjectMocks
    TinkoffPulseBrandInfoService tinkoffPulseBrandInfoService;

    protected FullNewsDto fullNewsDto;
    protected FullBrandInfoDto fullBrandInfoDto;
    protected FullIdeaDto fullIdeaDto;

    @BeforeEach
    void setUp() {
        fullNewsDto = getFullNewsDto();
        fullBrandInfoDto = getFullBrandInfoDto();
        fullIdeaDto = getFullIdeaDto();
    }

    private FullNewsDto getFullNewsDto() {
        NewsOwnerDto owner = new NewsOwnerDto("1234", "Owner");
        NewsContentDto content = new NewsContentDto("simple",
                List.of(new NewsTickerDto("SHARE", "SBER", "RUB", 1500.0)),
                "test content");
        StockNewsDto stockNews = new StockNewsDto("newsId1",
                "2024-12-01T06:15:32.362Z",
                owner,
                content,
                null);

        PayloadNewsDto payload = new PayloadNewsDto(List.of(stockNews));
        return new FullNewsDto(payload);
    }

    private FullBrandInfoDto getFullBrandInfoDto() {
        BrandInfoDto sber = new BrandInfoDto(
                List.of("SBER"),
                new BrandLinkDto("https://sberbank.ru"),
                "СБЕР",
                "Крупнейший российский банк",
                "Банковский сектор",
                "РФ"
        );
        BrandInfoDto ydex = new BrandInfoDto(
                List.of("YDEX"),
                new BrandLinkDto("https://yandex.ru"),
                "Yandex",
                "Компания занимающаяся разработкой ПО",
                "Технологии",
                "РФ"
        );
        return new FullBrandInfoDto(new PayloadBrandInfoDto(List.of(ydex, sber)));
    }

    FullIdeaDto getFullIdeaDto() {
        StockIdeaDto sber = new StockIdeaDto("1", "Idea 1", new BrokerIdeaDto("broker1", "Broker 1", 0.85),
                List.of(new TickerIdeaDto("SBER", "Sberbank", 250.0)), 10.0, "url1", "2024-01-01", "2024-12-31", 100.0,
                120.0, 0.2);
        StockIdeaDto gazp = new StockIdeaDto("2", "Idea 2", new BrokerIdeaDto("broker2", "Broker 2", 0.9),
                List.of(new TickerIdeaDto("GAZP", "Gazprom", 300.0)), 15.0, "url2", "2024-01-01", "2024-12-31", 150.0,
                170.0, 0.15);
        return new FullIdeaDto(new PayloadIdeaDto(List.of(sber, gazp)));
    }
}
