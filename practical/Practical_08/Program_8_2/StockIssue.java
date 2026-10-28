class OutOfStockException extends Exception {
    private final int shortfall;

    public OutOfStockException(String item, int requested, int available) {
        super("Insufficient stock for '" + item + "' (Requested: " + requested + ", Available: " + available + ")");
        this.shortfall = requested - available;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    public InvalidQuantityException(int qty) {
        super("Invalid quantity requested: " + qty + " (Quantity must be > 0)");
    }
}

class Warehouse {
    private final String itemName;
    private int stock;

    public Warehouse(String itemName, int initialStock) {
        this.itemName = itemName;
        this.stock = initialStock;
    }

    public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException(qty);
        }
        if (qty > stock) {
            throw new OutOfStockException(item, qty, stock);
        }
        stock -= qty;
        System.out.println("[SUCCESS] Issued " + qty + " unit(s) of " + item + ". Remaining stock: " + stock);
    }

    public int getStock() {
        return stock;
    }
}

public class StockIssue {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse("SSD-1TB", 15);
        int[] requests = {5, -3, 20, 0, 8, 5};

        System.out.println("Initial Warehouse Stock for SSD-1TB: " + warehouse.getStock());
        for (int req : requests) {
            try {
                warehouse.issue("SSD-1TB", req);
            } catch (InvalidQuantityException e) {
                System.out.println("[REJECTED] " + e.getMessage());
            } catch (OutOfStockException e) {
                System.out.println("[OUT OF STOCK] " + e.getMessage() + " -> Shortfall: " + e.getShortfall());
            }
        }
        System.out.println("Final Warehouse Stock for SSD-1TB: " + warehouse.getStock());
    }
}
