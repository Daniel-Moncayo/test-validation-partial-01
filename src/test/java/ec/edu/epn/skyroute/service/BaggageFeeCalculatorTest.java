package ec.edu.epn.skyroute.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaggageFeeCalculatorTest {

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private BaggageFeeCalculator calculator;

    @Test
    @DisplayName("retorno $30 para 1 maleta de 20 kg y pasajero regular")
    void TestRetornoMaletaEstandar() {
        when(passengerService.isVip(1L)).thenReturn(false);

        double fee = calculator.calcularFee(20.0, 1, 1L);

        assertEquals(30.0, fee);
    }

    @Test
    @DisplayName("retorno $80 para 1 maleta de 25 kg y pasajero regular (exceso de peso)")
    void TestRetornoMaletaExcesiva() {
        when(passengerService.isVip(1L)).thenReturn(false);

        double fee = calculator.calcularFee(25.0, 1, 1L);

        assertEquals(80.0, fee);
    }

    @Test
    @DisplayName("retorno $0 para 1 maleta de 15 kg y pasajero VIP")
    void TestRetornoMaletaVIP() {
        when(passengerService.isVip(1L)).thenReturn(true);

        double fee = calculator.calcularFee(15.0, 1, 1L);

        assertEquals(0.0, fee);
    }

    @Test
    @DisplayName("retorno $30 para 2 maletas de 15 kg y pasajero VIP")
    void TestRetornoMaletasVIP() {
        when(passengerService.isVip(1L)).thenReturn(true);

        double fee = calculator.calcularFee(15.0, 2, 1L);

        assertEquals(30.0, fee);
    }

    @Test
    @DisplayName("IllegalArgumentException cuando el peso es 0 o negativo")
    void TestIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calcularFee(0.0, 1, 1L));
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calcularFee(-10.0, 1, 1L));
    }
}
