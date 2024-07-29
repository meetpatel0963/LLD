package DesignPatterns;

import java.util.ArrayList;
import java.util.List;

public class ObserverPattern {
    /*
    * Mainly used to implement pub-sub type designs where several users subscribe to receive notifications upon
    * some event.
    * */

    interface NotificationAlertObserver {
        void update();
    }

    interface StockObservable {
        void add(NotificationAlertObserver observer);
        void remove(NotificationAlertObserver observer);
        void notifySubscribers();
        void setStockCount(int add);
        int getStockCount();
    }

    static class IPhoneObservable implements StockObservable {
        private final List<NotificationAlertObserver> observerList;
        private int stockCount;

        IPhoneObservable() {
            this.observerList = new ArrayList<>();
            this.stockCount = 0;
        }

        @Override
        public void add(NotificationAlertObserver observer) {
            observerList.add(observer);
        }

        @Override
        public void remove(NotificationAlertObserver observer) {
            observerList.remove(observer);
        }

        @Override
        public void notifySubscribers() {
            for (NotificationAlertObserver observer : observerList) {
                observer.update();
            }
        }

        @Override
        public void setStockCount(int add) {
            if (stockCount == 0) {
                notifySubscribers();
            }
            stockCount = stockCount + add;
        }

        @Override
        public int getStockCount() {
            return stockCount;
        }
    }

    static class EmailAlertObserver implements NotificationAlertObserver {
        private String emailId;
        private StockObservable observable;

        public EmailAlertObserver(String emailId, StockObservable stockObservable) {
            this.emailId = emailId;
            this.observable = stockObservable;
        }

        @Override
        public void update() {
            sendEmail("some message");
        }

        private void sendEmail(String message) {
            System.out.println("sending email to: " + emailId);
        }
    }

    static class MobileAlertObserver implements NotificationAlertObserver {
        private String contact;
        private StockObservable observable;

        public MobileAlertObserver(String contact, StockObservable stockObservable) {
            this.contact = contact;
            this.observable = stockObservable;
        }

        @Override
        public void update() {
            sendMessage("some message");
        }

        private void sendMessage(String message) {
            System.out.println("sending message to: " + contact);
        }
    }

    public static void main(String[] args) {
        IPhoneObservable iPhoneObservable = new IPhoneObservable();
        NotificationAlertObserver observer1 = new EmailAlertObserver("observer1@gmail.com", iPhoneObservable);
        NotificationAlertObserver observer2 = new MobileAlertObserver("1234567890", iPhoneObservable);
        NotificationAlertObserver observer3 = new EmailAlertObserver("observer3@yahoo.com", iPhoneObservable);

        iPhoneObservable.add(observer1);
        iPhoneObservable.add(observer2);
        iPhoneObservable.add(observer3);

        iPhoneObservable.setStockCount(10);
        iPhoneObservable.setStockCount(-10);

        iPhoneObservable.remove(observer3);

        iPhoneObservable.setStockCount(5);

    }
}
