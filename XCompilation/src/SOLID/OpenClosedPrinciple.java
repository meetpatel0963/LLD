package SOLID;

public class OpenClosedPrinciple {
//    Open for extension but closed for modification

    class InvoiceDAO {
        private SingleResponsibility.Invoice invoice;

        public void saveToDB() {
            System.out.println("saving to database");
        }
    }

//    Don't do
    class InvoiceDAO {
        private SingleResponsibility.Invoice invoice;

        public void saveToDB() {
            System.out.println("saving to database");
        }

        public void saveToFile() {
            System.out.println("saving to file");
        }
    }

//    Do
    interface InvoiceDAO {
        public void save(Invoice invoice);
    }

    class DatabaseInvoiceDAO implements InvoiceDAO {
        @Override
        public void save(Invoice invoice) {
            System.out.println("saving to database");
        }
    }

    class FileInvoiceDAO implements  InvoiceDAO {
        @Override
        public void save(Invoice invoice) {
            System.out.println("saving to file");
        }
    }
}
