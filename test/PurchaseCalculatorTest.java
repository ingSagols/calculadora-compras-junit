import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseCalculatorTest {

    PurchaseCalculator calculadora = new PurchaseCalculator();

    // Tolerancia para comparar numeros double
    double tolerancia = 0.001;

    @Test
    @DisplayName("Prueba método calculateSubtotal()")
    void pruebaCalculateSubtotal() {

        // (valorEsperado, llamarFuncion, tolerancia)
        // Un producto con precio de $100 y cantidad de 3.
        assertEquals(300, calculadora.calculateSubtotal(100, 3), tolerancia);
        // Un producto con cantidad de 1.
        assertEquals(50, calculadora.calculateSubtotal(50, 1), tolerancia);
        // Un producto con precio de $0.
        assertEquals(0, calculadora.calculateSubtotal(0, 5), tolerancia);

    }//pruebaCalculateSubtotal

    @Test
    @DisplayName("Prueba método isValidPurchase()")
    void pruebaIsValidPurchase() {

        // Precio y cantidad positivos.
        assertTrue(calculadora.isValidPurchase(100, 2));
        // Precio igual a 0.
        assertFalse(calculadora.isValidPurchase(0, 2));
        // Cantidad igual a 0.
        assertFalse(calculadora.isValidPurchase(100, 0));
        // Precio negativo.
        assertFalse(calculadora.isValidPurchase(-100, 2));

    }//pruebaIsValidPurchase

    @Test
    @DisplayName("Prueba método calculateDiscount()")
    void pruebaCalculateDiscount() {

        // Subtotal inferior a $1,000.
        assertEquals(0, calculadora.calculateDiscount(999.99), tolerancia);
        // Subtotal exactamente igual a $1,000.
        assertEquals(100, calculadora.calculateDiscount(1000), tolerancia);
        // Subtotal superior a $1,000.
        assertEquals(120, calculadora.calculateDiscount(1200), tolerancia);

    }//pruebaCalculateDiscount

    @Test
    @DisplayName("Prueba método calculateFinalPrice()")
    void pruebaCalculateFinalPrice() {

        // Compra sin descuento.
        assertEquals(500, calculadora.calculateFinalPrice(500), tolerancia);
        // Compra con descuento.
        assertEquals(1080, calculadora.calculateFinalPrice(1200), tolerancia);
        // Compra cuyo subtotal es exactamente $1,000.
        assertEquals(900, calculadora.calculateFinalPrice(1000), tolerancia);

    }//pruebaCalculateFinalPrice

}//class PurchaseCalculatorTest