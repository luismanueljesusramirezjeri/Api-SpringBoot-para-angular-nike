package pe.idat.dsn.producto.dtos;

public class GetByPropertyPageableFilter {
    private int pageNumber;
    private int pageSize;
    private String code;
    private String name;
    private String category;

    public GetByPropertyPageableFilter(int pageNumber, int pageSize, String code, String name, String category) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.code = code;
        this.name = name;
        this.category = category;
    }

    // Getters
    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
