package dm.sciencecenter.dtos;

public class AdvancedSearchDto {
    private String magazineName;
    private String name;
    private String author;
    private String keyWords;
    private String articleFile;
    private String scientificField;

    private boolean magazineNameSelected;
    private boolean nameSelected;
    private boolean authorSelected;
    private boolean keyWordsSelected;
    private boolean articleFileSelected;

    private boolean phraseMagazinName;
    private boolean phraseName;
    private boolean phraseAuthor;
    private boolean phraseKeyWords;
    private boolean phraseArticleFile;

    public AdvancedSearchDto() {
    }

    public String getMagazineName() {
        return magazineName;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
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

    public String getArticleFile() {
        return articleFile;
    }

    public void setArticleFile(String articleFile) {
        this.articleFile = articleFile;
    }

    public String getScientificField() {
        return scientificField;
    }

    public void setScientificField(String scientificField) {
        this.scientificField = scientificField;
    }

    public boolean isMagazineNameSelected() {
        return magazineNameSelected;
    }

    public void setMagazineNameSelected(boolean magazineNameSelected) {
        this.magazineNameSelected = magazineNameSelected;
    }

    public boolean isNameSelected() {
        return nameSelected;
    }

    public void setNameSelected(boolean nameSelected) {
        this.nameSelected = nameSelected;
    }

    public boolean isAuthorSelected() {
        return authorSelected;
    }

    public void setAuthorSelected(boolean authorSelected) {
        this.authorSelected = authorSelected;
    }

    public boolean isKeyWordsSelected() {
        return keyWordsSelected;
    }

    public void setKeyWordsSelected(boolean keyWordsSelected) {
        this.keyWordsSelected = keyWordsSelected;
    }

    public boolean isArticleFileSelected() {
        return articleFileSelected;
    }

    public void setArticleFileSelected(boolean articleFileSelected) {
        this.articleFileSelected = articleFileSelected;
    }

    public boolean isPhraseMagazinName() {
        return phraseMagazinName;
    }

    public void setPhraseMagazinName(boolean phraseMagazinName) {
        this.phraseMagazinName = phraseMagazinName;
    }

    public boolean isPhraseName() {
        return phraseName;
    }

    public void setPhraseName(boolean phraseName) {
        this.phraseName = phraseName;
    }

    public boolean isPhraseAuthor() {
        return phraseAuthor;
    }

    public void setPhraseAuthor(boolean phraseAuthor) {
        this.phraseAuthor = phraseAuthor;
    }

    public boolean isPhraseKeyWords() {
        return phraseKeyWords;
    }

    public void setPhraseKeyWords(boolean phraseKeyWords) {
        this.phraseKeyWords = phraseKeyWords;
    }

    public boolean isPhraseArticleFile() {
        return phraseArticleFile;
    }

    public void setPhraseArticleFile(boolean phraseArticleFile) {
        this.phraseArticleFile = phraseArticleFile;
    }
}
