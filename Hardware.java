public abstract class Hardware {
    private int id;
    private String name;
    private int spec;

    public Hardware(int id, String name, int spec) {
        this.id = id;
        this.name = name;
        this.spec = spec;
    }

    public int getId()       { return id; }
    public String getName()  { return name; }
    public int getSpec()     { return spec; }

    public abstract String getSpecDescription();
}
