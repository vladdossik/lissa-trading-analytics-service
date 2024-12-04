package lissa.trading.analytics.service.service.pulse;

import lissa.trading.analytics.service.client.pulse.TinkoffPulseClient;
import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.BrandInfoResponseDto;
import lissa.trading.analytics.service.service.AbstractInitialization;
import lissa.trading.analytics.service.service.tinkoffPulse.TinkoffPulseBrandInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TinkoffPulseBrandInfoServiceTest extends AbstractInitialization {
    @InjectMocks
    private TinkoffPulseBrandInfoService tinkoffPulseBrandInfoService;

    @Mock
    private TinkoffPulseClient tinkoffPulseClientMock;

    @Test
    void getData_Correct() {
        when(tinkoffPulseClientMock.getBrandsInfo()).thenReturn(fullBrandInfoDto);

        List<String> tickers = List.of("YDEX", "SBER");

        List<ResponseDto> response = tinkoffPulseBrandInfoService.getData(tickers);

        assertNotNull(response);
        assertEquals(2, response.size());

        BrandInfoResponseDto responseDtoYdex = (BrandInfoResponseDto) response.get(0);
        assertEquals("Yandex", responseDtoYdex.getBrandInfo().getName());
        assertEquals("https://yandex.ru", responseDtoYdex.getBrandInfo().getExternalLinks().getMain());

        BrandInfoResponseDto responseDtoSber = (BrandInfoResponseDto) response.get(1);
        assertEquals("СБЕР", responseDtoSber.getBrandInfo().getName());
        assertEquals("https://sberbank.ru", responseDtoSber.getBrandInfo().getExternalLinks().getMain());
    }

    @Test
    void getData_TickerNotFound() {
        when(tinkoffPulseClientMock.getBrandsInfo()).thenReturn(fullBrandInfoDto);

        List<String> tickers = List.of("LISSA-TRADING");

        List<ResponseDto> response = tinkoffPulseBrandInfoService.getData(tickers);

        assertNotNull(response);
        assertEquals(1, response.size());

        BrandInfoResponseDto responseDto = (BrandInfoResponseDto) response.get(0);
        assertNull(responseDto.getBrandInfo());
    }

    @Test
    void getData_TickersNotCaseSensitive() {
        when(tinkoffPulseClientMock.getBrandsInfo()).thenReturn(fullBrandInfoDto);

        List<String> tickers = List.of("ydex", "sber");

        List<ResponseDto> response = tinkoffPulseBrandInfoService.getData(tickers);

        assertNotNull(response);
        assertEquals(2, response.size());

        BrandInfoResponseDto responseDtoYdex = (BrandInfoResponseDto) response.get(0);
        assertEquals("Yandex", responseDtoYdex.getBrandInfo().getName());
        assertEquals("https://yandex.ru", responseDtoYdex.getBrandInfo().getExternalLinks().getMain());

        BrandInfoResponseDto responseDtoSber = (BrandInfoResponseDto) response.get(1);
        assertEquals("СБЕР", responseDtoSber.getBrandInfo().getName());
        assertEquals("https://sberbank.ru", responseDtoSber.getBrandInfo().getExternalLinks().getMain());
    }

}
