import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DesignPatternsExamples {
    private static int checks;

    // BEGIN STRATEGY
    interface ShippingCost {
        int fee(int subtotalCents);
    }

    static final class Checkout {
        private final ShippingCost shipping;
        Checkout(ShippingCost shipping) {
            this.shipping = Objects.requireNonNull(shipping);
        }
        int total(int subtotalCents) {
            if (subtotalCents < 0) {
                throw new IllegalArgumentException("Negative subtotal");
            }
            int fee = shipping.fee(subtotalCents);
            if (fee < 0) throw new IllegalArgumentException("Negative fee");
            return Math.addExact(subtotalCents, fee);
        }
    }
    // END STRATEGY

    // BEGIN FACTORY_METHOD
    static abstract class CheckoutCreator {
        protected abstract ShippingCost createShipping();

        Checkout createCheckout() {
            return new Checkout(createShipping());
        }
    }

    static final class PickupCheckoutCreator extends CheckoutCreator {
        @Override
        protected ShippingCost createShipping() {
            return subtotal -> 0;
        }
    }
    // END FACTORY_METHOD

    interface ReceiptSender {
        void send(String email, String text);
    }

    // A local fake, not an external mail service.
    static final class OldMailClient {
        private final List<String> deliveries = new ArrayList<>();
        void deliver(String body, String recipient) {
            deliveries.add(recipient + "|" + body);
        }
    }

    // BEGIN ADAPTER
    record MailAdapter(OldMailClient client) implements ReceiptSender {
        MailAdapter {
            Objects.requireNonNull(client);
        }
        @Override
        public void send(String email, String text) {
            client.deliver(text, email);
        }
    }
    // END ADAPTER

    // BEGIN DECORATOR
    record LoggedSender(ReceiptSender next, List<String> log)
            implements ReceiptSender {
        LoggedSender {
            Objects.requireNonNull(next);
            Objects.requireNonNull(log);
        }
        @Override
        public void send(String email, String text) {
            log.add("Sending");
            next.send(email, text);
            log.add("Sent");
        }
    }
    // END DECORATOR

    record Report(String title, boolean includeCharts) {
        Report {
            Objects.requireNonNull(title);
            if (title.isBlank()) throw new IllegalArgumentException("Blank title");
        }
        static final class Builder {
            private final String title;
            private boolean charts;
            Builder(String title) { this.title = title; }
            Builder withCharts() {
                charts = true;
                return this;
            }
            Report build() { return new Report(title, charts); }
        }
    }

    static void equal(Object expected, Object actual) {
        checks++;
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Expected " + expected + ", got " + actual);
        }
    }

    static void fails(Class<? extends Throwable> type, Runnable task) {
        checks++;
        try {
            task.run();
        } catch (Throwable error) {
            if (type.isInstance(error)) return;
            throw new AssertionError("Unexpected failure", error);
        }
        throw new AssertionError("Expected " + type.getSimpleName());
    }

    public static void main(String[] args) {
        // BEGIN STRATEGY_USAGE
        Checkout pickup = new Checkout(subtotal -> 0);
        Checkout delivery = new Checkout(subtotal -> 500);
        System.out.println(pickup.total(2000));   // 2000 cents
        System.out.println(delivery.total(2000)); // 2500 cents
        // END STRATEGY_USAGE
        equal(2000, pickup.total(2000));
        equal(2500, delivery.total(2000));
        equal(0, pickup.total(0));
        equal(500, delivery.total(0));
        equal(2000, new Checkout(subtotal -> subtotal / 10).total(1819));
        fails(IllegalArgumentException.class, () -> pickup.total(-1));
        fails(IllegalArgumentException.class, () -> new Checkout(x -> -1).total(10));
        fails(ArithmeticException.class, () -> delivery.total(Integer.MAX_VALUE));
        fails(NullPointerException.class, () -> new Checkout(null));

        CheckoutCreator creator = new PickupCheckoutCreator();
        equal(2000, creator.createCheckout().total(2000));
        equal(false, creator.createCheckout() == creator.createCheckout());

        OldMailClient oldClient = new OldMailClient();
        ReceiptSender adapter = new MailAdapter(oldClient);
        adapter.send("reader@example.com", "Order ready");
        equal(List.of("reader@example.com|Order ready"), oldClient.deliveries);

        List<String> log = new ArrayList<>();
        ReceiptSender decorated = new LoggedSender(adapter, log);
        decorated.send("reader@example.com", "Order confirmed");
        equal(List.of("Sending", "Sent"), log);
        equal(2, oldClient.deliveries.size());
        equal("reader@example.com|Order confirmed", oldClient.deliveries.get(1));

        List<String> failureLog = new ArrayList<>();
        ReceiptSender failure = (email, text) -> {
            throw new IllegalStateException("Local simulated failure");
        };
        fails(IllegalStateException.class,
                () -> new LoggedSender(failure, failureLog).send("a", "b"));
        equal(List.of("Sending"), failureLog);

        // BEGIN BUILDER_USAGE
        Report report = new Report.Builder("Daily sales")
                .withCharts()
                .build();
        // END BUILDER_USAGE
        equal("Daily sales", report.title());
        equal(true, report.includeCharts());
        Report.Builder builder = new Report.Builder("Snapshot");
        Report first = builder.build();
        Report second = builder.withCharts().build();
        equal(false, first.includeCharts());
        equal(true, second.includeCharts());
        fails(IllegalArgumentException.class, () -> new Report.Builder(" ").build());
        fails(NullPointerException.class, () -> new Report.Builder(null).build());
        System.out.println("Design patterns passed " + checks + " checks");
    }
}
