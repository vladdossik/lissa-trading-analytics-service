package lissa.trading.analytics.service.service.pulse;

import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.BrandInfoResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class TinkoffPulseBrandInfoServiceTest extends PulseBaseTest {
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
