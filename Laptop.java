public class Laptop extends Hardware {

    public Laptop(int id, String name, int spec) {
        super(id, name, spec);
    }

    @Override
    public String getSpecDescription() {
        return getSpec() + "GB RAM";
    }
}
