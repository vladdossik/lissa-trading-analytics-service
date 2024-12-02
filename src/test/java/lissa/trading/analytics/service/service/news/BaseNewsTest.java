package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.client.finam.FinamClient;
import lissa.trading.analytics.service.client.tinkoff.dto.CompanyNamesDto;
import lissa.trading.analytics.service.client.tinkoff.feign.StockServiceClient;
import lissa.trading.analytics.service.dto.NewsDto;
import lissa.trading.analytics.service.dto.NewsResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@ExtendWith(MockitoExtension.class)
class BaseNewsTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z",
            Locale.ENGLISH);

    @Mock
    protected FinamClient finamClientMock;

    @Mock
    protected StockServiceClient stockServiceClientMock;

    @Mock
    protected NewsXmlParser newsXmlParserMock;

    @Mock
    protected NewsDataProcessor newsDataProcessorMock;

    @InjectMocks
    protected FinamNewsService FinamNewsService;

    protected String finamRssFeed;
    protected CompanyNamesDto companyNamesDto;
    protected NewsResponseDto newsResponseDto;
    protected NewsResponseDto filteredNewsByCompanies;
    protected NewsResponseDto newsWithoutHtml;


    @BeforeEach
    void setUp() {
        finamRssFeed = getFinamRssFeed();
        companyNamesDto = new CompanyNamesDto(List.of("Яндекс, " +
                "Мосбиржа"));
        newsResponseDto = parsedFeed();
        filteredNewsByCompanies = getFilteredNewsByCompanies();
        newsWithoutHtml = getFilteredNewsWithoutHtml();
    }

    private String getFinamRssFeed() {
        return "<rss xmlns:a10=\"http://www.w3.org/2005/Atom\" version=\"2.0\">\n" +
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
    }



    private NewsResponseDto parsedFeed() {
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

        return new NewsResponseDto(List.of(gazpNews, lukoilNews));
    }


    private NewsResponseDto getFilteredNewsByCompanies() {
        NewsDto gazpNews = NewsDto.builder()
                .title("«Газпром» запланировал инвестпрограмму на 2025 год в размере 1,524 трлн рублей")
                .description("<p>«Газпром» запланировал инвестиционную программу на 2025 год в размере 1,524 трлн рублей." +
                        " Об этом говорится в сообщении компании из ее Telegram-канала.</p>" +
                        "<p>«Правление одобрило проекты инвестиционной программы и бюджета «Газпрома» на 2025 год.")
                .url("https://www.finam.ru/publications/1")
                .pubDate(LocalDateTime.parse("Thu, 28 Nov 2024 16:08:46 +0300", formatter))
                .build();

        return new NewsResponseDto(List.of(gazpNews));
    }

    private NewsResponseDto getFilteredNewsWithoutHtml() {
        NewsDto gazpNews = NewsDto.builder()
                .title("«Газпром» запланировал инвестпрограмму на 2025 год в размере 1,524 трлн рублей")
                .description("«Газпром» запланировал инвестиционную программу на 2025 год в размере 1,524 трлн рублей." +
                        " Об этом говорится в сообщении компании из ее Telegram-канала." +
                        "«Правление одобрило проекты инвестиционной программы и бюджета «Газпрома» на 2025 год.")
                .url("https://www.finam.ru/publications/1")
                .pubDate(LocalDateTime.parse("Thu, 28 Nov 2024 16:08:46 +0300", formatter))
                .build();

        return new NewsResponseDto(List.of(gazpNews));
    }
}
