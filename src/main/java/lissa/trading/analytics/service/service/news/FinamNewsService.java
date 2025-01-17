package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.client.finam.FinamClient;
import lissa.trading.analytics.service.client.tinkoff.dto.CompanyNamesDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffTokenDto;
import lissa.trading.analytics.service.client.tinkoff.feign.StockServiceClient;
import lissa.trading.analytics.service.dto.NewsResponseDto;
import lissa.trading.lissa.auth.lib.dto.UserInfoDto;
import lissa.trading.lissa.auth.lib.security.EncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service("finamService")
@RequiredArgsConstructor
public class FinamNewsService implements NewsService {

    private final FinamClient finamClient;
    private final NewsXmlParser newsXmlParser;
    private final StockServiceClient stockServiceClient;

    @Override
    public NewsResponseDto getNews(List<String> tickers) {
        setTinkoffApiToken();
        log.info("Requesting to Tinkoff-service for company names by tickers");
        CompanyNamesDto keywords = stockServiceClient.getCompanyNamesByTickers(tickers);
        log.info("Company names: {}", keywords);

        if (keywords.getNames().isEmpty()) {
            log.info("There is no keywords in entry, method is closing");
            return new NewsResponseDto(List.of());
        }

        NewsResponseDto unfilteredNews = newsXmlParser.toNewsDto(finamClient.getFinamRssFeed());
        NewsResponseDto filteredNews = NewsDataProcessor.filterNewsByKeywords(unfilteredNews, keywords.getNames());

        if (CollectionUtils.isEmpty(filteredNews.getItems())){
            log.info("There are no news in Tinkoff-service for company names");
            return filteredNews;
        }

        return NewsDataProcessor.removeHtmlTagsFromText(filteredNews);
    }

    @Override
    public String getSourceName() {
        return "Finam";
    }

    private void setTinkoffApiToken() {
        TinkoffTokenDto tinkoffTokenDto = new TinkoffTokenDto();
        UserInfoDto userInfoDto = (UserInfoDto) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (userInfoDto.getTinkoffToken() != null) {
            String encodedToken = userInfoDto.getTinkoffToken();
            tinkoffTokenDto.setToken(EncryptionService.decrypt(encodedToken));
        } else {
            log.error("Tinkoff token invalid or does not exists for current user: {}, token: {}",
                    userInfoDto.getExternalId(), userInfoDto.getTinkoffToken());
            throw new SecurityException("Tinkoff API token not found");
        }
        stockServiceClient.setTinkoffToken(tinkoffTokenDto);
    }
}
