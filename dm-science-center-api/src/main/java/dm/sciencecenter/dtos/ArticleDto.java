package dm.sciencecenter.dtos;

public class ArticleDto {

    private Long id;
    private String name;
    private String author;
    private String keyWords;
    private String articleAbstract;
    private String magazine;
    private String articleFile;
    private String fileName;
    private boolean openAccess;

    public ArticleDto() { }

    public ArticleDto(Long id, String name, String author, String keyWords, String articleAbstract, String magazine, String articleFile, String fileName, boolean openAccess) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.keyWords = keyWords;
        this.articleAbstract = articleAbstract;
        this.magazine = magazine;
        this.articleFile = articleFile;
        this.fileName = fileName;
        this.openAccess = openAccess;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public String getMagazine() {
        return magazine;
    }

    public void setMagazine(String magazine) {
        this.magazine = magazine;
    }

    public String getArticleFile() {
        return articleFile;
    }

    public void setArticleFile(String articleFile) {
        this.articleFile = articleFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isOpenAccess() {
        return openAccess;
    }

    public void setOpenAccess(boolean openAccess) {
        this.openAccess = openAccess;
    }
}
