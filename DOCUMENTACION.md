# Documentación: Cálculo de compras y descuentos

## 1. Descripción del problema

Este proyecto es una parte del backend de una tienda en línea. La clase `PurchaseCalculator` hace cuatro cálculos:

- Calcula el **subtotal** de una compra (precio por cantidad).
- Revisa si una compra es **válida** (precio y cantidad mayores a cero).
- Calcula el **descuento** que le toca al cliente.
- Calcula el **precio final** (subtotal menos descuento).

Es importante verificar estos cálculos porque un error afecta directamente el dinero: el cliente podría pagar de más o la tienda podría cobrar de menos. Por eso se usan pruebas unitarias con JUnit, para comprobar cada método antes de usarlo en el resto de la aplicación.

## 2. Reglas de negocio

- **Subtotal:** `precio * cantidad`.
- **Compra válida:** solo si el precio es mayor que 0 **y** la cantidad es mayor que 0.
- **Descuento:** si el subtotal es **igual o mayor a $1,000**, el descuento es el 10 % del subtotal. Si es menor a $1,000, el descuento es $0.
- **Precio final:** `subtotal - descuento` (el método reutiliza `calculateDiscount`).

Los importes están en pesos mexicanos y se manejan con `double`.

## 3. Casos de prueba

Para comparar números `double` se usó una tolerancia de `0.001`.

| # | Método | Entrada | Resultado esperado | Resultado obtenido | Propósito |
|---|--------|---------|--------------------|--------------------|-----------|
| 1 | `calculateSubtotal` | precio 100, cantidad 3 | 300 | 300 | Caso normal |
| 2 | `calculateSubtotal` | precio 50, cantidad 1 | 50 | 50 | Con cantidad 1 el subtotal es igual al precio |
| 3 | `calculateSubtotal` | precio 0, cantidad 5 | 0 | 0 | Un precio de 0 da subtotal de 0 |
| 4 | `isValidPurchase` | precio 100, cantidad 2 | `true` | `true` | Caso válido |
| 5 | `isValidPurchase` | precio 0, cantidad 2 | `false` | `false` | El precio cero no es válido |
| 6 | `isValidPurchase` | precio 100, cantidad 0 | `false` | `false` | La cantidad cero no es válida |
| 7 | `isValidPurchase` | precio -100, cantidad 2 | `false` | `false` | El precio negativo no es válido |
| 8 | `calculateDiscount` | subtotal 999.99 | 0 | 0 | Justo debajo del límite, no hay descuento |
| 9 | `calculateDiscount` | subtotal 1000 | 100 | 100 | **Caso límite:** debe haber descuento |
| 10 | `calculateDiscount` | subtotal 1200 | 120 | 120 | Por encima del límite, descuento del 10 % |
| 11 | `calculateFinalPrice` | subtotal 500 | 500 | 500 | Compra sin descuento |
| 12 | `calculateFinalPrice` | subtotal 1200 | 1080 | 1080 | Compra con descuento |
| 13 | `calculateFinalPrice` | subtotal 1000 | 900 | 900 | Límite exacto, también debe tener descuento |

**Por qué se eligieron estos casos:** no se probaron todas las combinaciones posibles. Se eligió un caso normal de cada regla y los casos límite (0 y 1000), que son donde más fácil se cometen errores.

## 4. Resultados de ejecución

Se ejecutaron las pruebas de la clase `PurchaseCalculatorTest` (4 métodos de prueba, uno por cada método de la calculadora):

- Pruebas que pasaron: **4**
- Pruebas que fallaron: **0**

## 5. Errores que se pueden prevenir

1. **Usar `>` en lugar de `>=` en el descuento.** Con `>`, una compra de exactamente $1,000 no recibiría descuento. La prueba del caso 9 lo detectaría porque esperaría 100 y obtendría 0.
2. **Aceptar compras con precio o cantidad inválidos.** Si `isValidPurchase` usara `>=` en lugar de `>`, aceptaría precio 0 o cantidad 0. Los casos 5 y 6 lo detectarían.
3. **Restar mal el descuento en el precio final.** Si por error se devolviera solo el descuento o se sumara, el caso 12 fallaría, porque esperaría 1080.

## 6. Conclusión

Las pruebas unitarias nos permiten comprobar que cada método funciona como dice la regla de negocio, sobre todo en los casos límite donde es más fácil equivocarse. En una tienda en línea esto es muy importante, porque un error en un cálculo se traduce en cobros incorrectos. Además, si más adelante se cambia el código, basta volver a ejecutar las pruebas para saber si algo dejó de funcionar.

## Reflexión final

**Si el descuento funciona con $1,200 pero falla con exactamente $1,000, ¿qué prueba lo detecta y qué consecuencias tendría?**

La prueba que lo detecta es la del caso 9 (`assertEquals(100, calculadora.calculateDiscount(1000), tolerancia)`), que revisa el límite exacto. Si el código usara `>` en lugar de `>=`, devolvería 0 y la prueba fallaría. Si ese error llegara a la aplicación, los clientes que compran exactamente $1,000 pagarían $100 de más, lo que generaría quejas, reclamaciones y pérdida de confianza en la tienda.
