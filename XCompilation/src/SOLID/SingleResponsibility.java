package SOLID;

public class SingleResponsibility {
//    A class should have only one reason to change


    class Marker {
        private String color;
        private int price;

        public Marker(String color, int price) {
            this.color = color;
            this.price = price;
        }
    }

//    Don't do
    class Invoice {
        private Marker marker;
        private int quantity;

        public Invoice(Marker marker, int quantity) {
            this.marker = marker;
            this.quantity = quantity;
        }

        public int calculateCost() {
            return this.quantity * marker.price;
        }

        public void printInvoice() {
            System.out.println("printing invoice");
        }

        public void saveToDB() {
            System.out.println("saving to database");
        }
    }

//    Do
    class Invoice {
        private Marker marker;
        private int quantity;

        public Invoice(Marker marker, int quantity) {
            this.marker = marker;
            this.quantity = quantity;
        }

        public int calculateCost() {
            return this.quantity * marker.price;
        }
    }

    class InvoicePrinter {
        private Invoice invoice;

        public void printInvoice() {
            System.out.println("printing invoice");
        }
    }

    class InvoiceDAO {
        private Invoice invoice;

        public void saveToDB() {
            System.out.println("saving to database");
        }
    }
}
