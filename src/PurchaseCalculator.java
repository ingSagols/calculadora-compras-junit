public class PurchaseCalculator {

    // Multiplica el precio de un producto por la cantidad que se quiere comprar
    public double calculateSubtotal(double price, int quantity){
        return price * quantity;
    }//calculateSubtotal

    // Una compra es valida solo si el precio y la cantidad son mayores que cero
    public boolean isValidPurchase(double price, int quantity){
        if(price > 0 && quantity > 0){
            return true;
        }//if

        return false;
    }//isValidPurchase

    // Si el subtotal es de $1,000 o mas, el descuento es el 10% del subtotal
    // Si es menor a $1,000, no hay descuento (se regresa 0)
    public double calculateDiscount(double subtotal){
        if(subtotal >= 1000){
            return subtotal * 0.10;
        }//if

        return 0;
    }//calculateDiscount

    // El precio final es el subtotal menos el descuento
    // Aqui se reutiliza calculateDiscount para no repetir la regla del descuento
    public double calculateFinalPrice(double subtotal){
        return subtotal - calculateDiscount(subtotal);
    }//calculateFinalPrice

}//class PurchaseCalculator