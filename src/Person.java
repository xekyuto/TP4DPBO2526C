public class Person {
    private String id;
    private String nama;
    private int tahunLahir;
    private String kategori; // Divisi usia TCG: Junior, Senior, Master
    private String fokus;    // Bonus atribut: Kolektor / Kompetitif

    public Person(String id, String nama, int tahunLahir, String kategori, String fokus) {
        this.id = id;
        this.nama = nama;
        this.tahunLahir = tahunLahir;
        this.kategori = kategori;
        this.fokus = fokus;
    }

    // --- GETTER ---
    public String getId() { return id; }
    public String getNama() { return nama; }
    public int getTahunLahir() { return tahunLahir; }
    public String getKategori() { return kategori; }
    public String getFokus() { return fokus; }

    // --- SETTER ---
    public void setId(String id) { this.id = id; }
    public void setNama(String nama) { this.nama = nama; }
    public void setTahunLahir(int tahunLahir) { this.tahunLahir = tahunLahir; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public void setFokus(String fokus) { this.fokus = fokus; }
}