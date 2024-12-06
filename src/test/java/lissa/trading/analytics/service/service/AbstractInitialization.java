package lissa.trading.analytics.service.service;

import lissa.trading.analytics.service.client.tinkoff.dto.CandlesDto;
import lissa.trading.analytics.service.client.tinkoff.dto.CompanyNamesDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffCandlesRequestDto;
import lissa.trading.analytics.service.dto.CandleDto;
import lissa.trading.analytics.service.dto.CandleInterval;
import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.dto.NewsDto;
import lissa.trading.analytics.service.dto.NewsResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.BrandInfoDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.BrandLinkDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.FullBrandInfoDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.PayloadBrandInfoDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.BrokerIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.FullIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.PayloadIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.StockIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.StockIdeaFromPulseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.TickerIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.FullNewsDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.NewsContentDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.NewsOwnerDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.NewsTickerDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.PayloadNewsDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.StockNewsDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractInitialization {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z",
            Locale.ENGLISH);
    protected static FullNewsDto fullNewsDto;
    protected static FullBrandInfoDto fullBrandInfoDto;
    protected static FullIdeaDto fullIdeaDto;
    protected static String finamRssFeed;
    protected static NewsResponseDto newsResponseDto;
    protected static CompanyNamesDto companyNamesDto;
    protected static NewsResponseDto filteredNewsByCompanies;
    protected static NewsResponseDto newsWithoutHtml;
    protected static CandlesDto candlesDto;
    protected static IndicatorsDto indicatorsDto;
    protected static TinkoffCandlesRequestDto tinkoffCandlesRequestDto;

    @BeforeAll
    public static void init() {
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
        fullNewsDto = new FullNewsDto(payload);

        BrandInfoDto sberBrand = new BrandInfoDto(
                List.of("SBER"),
                new BrandLinkDto("https://sberbank.ru"),
                "СБЕР",
                "Крупнейший российский банк",
                "Банковский сектор",
                "РФ"
        );
        BrandInfoDto ydexBrand = new BrandInfoDto(
                List.of("YDEX"),
                new BrandLinkDto("https://yandex.ru"),
                "Yandex",
                "Компания занимающаяся разработкой ПО",
                "Технологии",
                "РФ"
        );
        fullBrandInfoDto = new FullBrandInfoDto(new PayloadBrandInfoDto(List.of(ydexBrand, sberBrand)));

        StockIdeaFromPulseDto sberIdea = new StockIdeaFromPulseDto("1", "Idea 1", new BrokerIdeaDto("broker1", "Broker 1", 0.85),
                List.of(new TickerIdeaDto("SBER", "Sberbank", 250.0)), 10.0, "url1", "2024-01-01", "2024-12-31", 100.0,
                120.0, 0.2);
        StockIdeaFromPulseDto gazpIdea = new StockIdeaFromPulseDto("2", "Idea 2", new BrokerIdeaDto("broker2", "Broker 2", 0.9),
                List.of(new TickerIdeaDto("GAZP", "Gazprom", 300.0)), 15.0, "url2", "2024-01-01", "2024-12-31", 150.0,
                170.0, 0.15);
        fullIdeaDto = new FullIdeaDto(new PayloadIdeaDto(List.of(sberIdea, gazpIdea)));

        finamRssFeed = "<rss xmlns:a10=\"http://www.w3.org/2005/Atom\" version=\"2.0\">\n" +
                "  <channel>\n" +
                "    <title>Финам.RU© - Рынок и Аналитика : Новости компаний</title>\n" +
                "    <link>https://www.finam.ru/</link>\n" +
                "    <description>Финам.RU© - акции, облигации, фондовый рынок. Весь спектр оперативной финансовой информации</description>\n" +
                "    <language>ru</language>\n" +
                "    <copyright>Финам.RU©</copyright>\n" +
                "    <image>\n" +
                "      <url>https://www.finam.ru/i/N/logo-rss.gif</url>\n" +
                "      <title>Финам.RU© - Рынок и Аналитика : Новости компаний</title>\n" +
                "      <link>https://www.finam.ru/</link>\n" +
                "    </image>\n" +
                "    <item>\n" +
                "      <link>https://www.finam.ru/publications/1</link>\n" +
                "      <a10:author>\n" +
                "        <a10:name>Finam.ru</a10:name>\n" +
                "      </a10:author>\n" +
                "      <title>«Газпром» запланировал инвестпрограмму на 2025 год в размере 1,524 трлн рублей</title>\n" +
                "      <description>&lt;p&gt;«Газпром» запланировал инвестиционную программу на 2025 год " +
                "в размере 1,524 трлн рублей. Об этом говорится в сообщении компании из ее Telegram-канала." +
                "&lt;/p&gt;&lt;p&gt;«Правление одобрило проекты инвестиционной программы и бюджета " +
                "«Газпрома» на 2025 год.</description>\n" +
                "      <pubDate>Thu, 28 Nov 2024 16:08:46 +0300</pubDate>\n" +
                "    </item>\n" +
                "    <item>\n" +
                "      <link>https://www.finam.ru/publications/2</link>\n" +
                "      <a10:author>\n" +
                "        <a10:name>Finam.ru</a10:name>\n" +
                "      </a10:author>\n" +
                "      <title>«ЛУКОЙЛ» модернизировал завод смазочных материалов в Торжке</title>\n" +
                "      <description>&lt;p&gt;«ЛУКОЙЛ» модернизировал завод смазочных материалов в Торжке, " +
                "сообщила компания.&lt;/p&gt;&lt;p&gt;</description>\n" +
                "      <pubDate>Thu, 28 Nov 2024 14:14:30 +0300</pubDate>\n" +
                "    </item>\n" +
                "  </channel>\n" +
                "</rss>";

        NewsDto gazpNews = NewsDto.builder()
                .title("«Газпром» запланировал инвестпрограмму на 2025 год в размере 1,524 трлн рублей")
                .description("<p>«Газпром» запланировал инвестиционную программу на 2025 год в размере 1,524 трлн рублей." +
                        " Об этом говорится в сообщении компании из ее Telegram-канала.</p>" +
                        "<p>«Правление одобрило проекты инвестиционной программы и бюджета «Газпрома» на 2025 год.")
                .url("https://www.finam.ru/publications/1")
                .pubDate(LocalDateTime.parse("Thu, 28 Nov 2024 16:08:46 +0300", formatter))
                .build();

        NewsDto lukoilNews = NewsDto.builder()
                .title("«ЛУКОЙЛ» модернизировал завод смазочных материалов в Торжке")
                .description("<p>«ЛУКОЙЛ» модернизировал завод смазочных материалов в Торжке, сообщила компания.</p><p>")
                .url("https://www.finam.ru/publications/2")
                .pubDate(LocalDateTime.parse("Thu, 28 Nov 2024 14:14:30 +0300", formatter))
                .build();

        newsResponseDto = new NewsResponseDto(List.of(gazpNews, lukoilNews));

        NewsDto gazpNewsFiltered = NewsDto.builder()
                .title("«Газпром» запланировал инвестпрограмму на 2025 год в размере 1,524 трлн рублей")
                .description("<p>«Газпром» запланировал инвестиционную программу на 2025 год в размере 1,524 трлн рублей." +
                        " Об этом говорится в сообщении компании из ее Telegram-канала.</p>" +
                        "<p>«Правление одобрило проекты инвестиционной программы и бюджета «Газпрома» на 2025 год.")
                .url("https://www.finam.ru/publications/1")
                .pubDate(LocalDateTime.parse("Thu, 28 Nov 2024 16:08:46 +0300", formatter))
                .build();

        filteredNewsByCompanies = new NewsResponseDto(List.of(gazpNewsFiltered));

        NewsDto gazpNewsDeletedHtml = NewsDto.builder()
                .title("«Газпром» запланировал инвестпрограмму на 2025 год в размере 1,524 трлн рублей")
                .description("«Газпром» запланировал инвестиционную программу на 2025 год в размере 1,524 трлн рублей." +
                        " Об этом говорится в сообщении компании из ее Telegram-канала." +
                        "«Правление одобрило проекты инвестиционной программы и бюджета «Газпрома» на 2025 год.")
                .url("https://www.finam.ru/publications/1")
                .pubDate(LocalDateTime.parse("Thu, 28 Nov 2024 16:08:46 +0300", formatter))
                .build();

        newsWithoutHtml = new NewsResponseDto(List.of(gazpNewsDeletedHtml));

        companyNamesDto = new CompanyNamesDto(List.of("Cбербанк, " +
                "Газпром"));

        List<CandleDto> candles = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            double open = 100.0 + i;
            double close = open + 2.0;
            double high = close + 1.0;
            double low = open - 1.0;
            long volume = 1000 + i * 10;
            OffsetDateTime time = OffsetDateTime.now()
                    .minusDays(50 - i);
            boolean isComplete = i < 49;

            candles.add(new CandleDto(volume, open, close, high, low, time, isComplete));
        }

        candlesDto = new CandlesDto(candles);
        indicatorsDto = new IndicatorsDto(126.5, 126.5, 100.0);
        tinkoffCandlesRequestDto = new TinkoffCandlesRequestDto("uid", OffsetDateTime.now(), OffsetDateTime.now(),
                CandleInterval.CANDLE_INTERVAL_DAY);

    }
}
