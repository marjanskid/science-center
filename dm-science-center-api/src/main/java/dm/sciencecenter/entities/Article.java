package dm.sciencecenter.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dm.sciencecenter.dtos.ArticleDto;

import javax.persistence.*;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String keyWords;

    @Column(nullable = false)
    private String articleAbstract;

    @OneToOne
    @JsonBackReference
    private ScientificArea scientificArea;

    @OneToOne
    @JsonBackReference
    private Magazine magazine;

    public Article() {
    }

    public Article(Long id, String name, String author, String keyWords, String articleAbstract, ScientificArea scientificArea, Magazine magazine) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.keyWords = keyWords;
        this.articleAbstract = articleAbstract;
        this.scientificArea = scientificArea;
        this.magazine = magazine;
    }

    public Article(ArticleDto articleDto) {
        this.name = articleDto.getName();
        this.author = articleDto.getAuthor();
        this.keyWords = articleDto.getKeyWords();
        this.articleAbstract = articleDto.getArticleAbstract();
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

    public ScientificArea getScientificArea() {
        return scientificArea;
    }

    public void setScientificArea(ScientificArea scientificArea) {
        this.scientificArea = scientificArea;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }
}
