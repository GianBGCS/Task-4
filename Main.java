import java.util.*;

public class Main {
    public static void main(String[] args) {

        Repository repo = new Repository();
        List<Hardware> hardwareList = repo.getAllHardware();

        System.out.println("           HARDWARE MASTERLIST                ");
        System.out.printf (" %-2s | %-16s | %-18s |%n", "ID", "Brand", "Specification");

        for (Hardware hw : hardwareList) {
            System.out.printf("| %-2d | %-16s | %-18s |%n",
                    hw.getId(), hw.getName(), hw.getSpecDescription());
        }

        Map<String, Integer> laptopCounts = new TreeMap<>();
        Map<String, Integer> phoneCounts  = new TreeMap<>();

        for (Hardware hw : hardwareList) {
            if (hw instanceof Laptop) {
                String key = hw.getSpecDescription();
                laptopCounts.put(key, laptopCounts.getOrDefault(key, 0) + 1);
            } else if (hw instanceof Phone) {
                String key = hw.getSpecDescription();
                phoneCounts.put(key, phoneCounts.getOrDefault(key, 0) + 1);
            }
        }

        System.out.println("\n         LAPTOP & PHONE INVENTORY             ");
        System.out.printf (" %-28s | %-13s |%n", "Specification", "Quantity");

        System.out.println(" -- LAPTOPS --                                ");
        for (Map.Entry<String, Integer> entry : laptopCounts.entrySet()) {
            System.out.printf("|   %-27s| %-13d |%n", entry.getKey(), entry.getValue());
        }

        System.out.println(" -- PHONES --                                 ");
        for (Map.Entry<String, Integer> entry : phoneCounts.entrySet()) {
            System.out.printf("   %-27s| %-13d |%n", entry.getKey(), entry.getValue());
        }


        repo.close();
    }
}
