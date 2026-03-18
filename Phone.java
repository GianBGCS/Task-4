public class Phone extends Hardware {

    public Phone(int id, String name, int spec) {
        super(id, name, spec);
    }

    @Override
    public String getSpecDescription() {
        return getSpec() + " Megapixels";
    }
}
