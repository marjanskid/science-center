package dm.sciencecenter.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dm.sciencecenter.enums.MagazineSubscriber;

import javax.persistence.*;
import java.util.List;

@Entity
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private Long issnNumber;

    @ManyToOne
    private ScientificArea scientificArea;

    @Column(nullable = false)
    private MagazineSubscriber magazineSubscriber;

    @ManyToOne
    private User chiefEditor;

    @OneToMany
    private List<User> reviewers;

    @OneToMany
    private List<User> editors;

    @OneToMany
    @JsonManagedReference
    private List<Article> articles;

    public Magazine() { }

    public Magazine(String name, Long issnNumber, MagazineSubscriber magazineSubscriber, User chiefEditor) {
        this.name = name;
        this.issnNumber = issnNumber;
        this.magazineSubscriber = magazineSubscriber;
        this.chiefEditor = chiefEditor;
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

    public Long getIssnNumber() {
        return issnNumber;
    }

    public void setIssnNumber(Long issnNumber) {
        this.issnNumber = issnNumber;
    }

    public MagazineSubscriber getMagazineSubscriber() {
        return magazineSubscriber;
    }

    public void setMagazineSubscriber(MagazineSubscriber magazineSubscriber) {
        this.magazineSubscriber = magazineSubscriber;
    }

    public User getChiefEditor() {
        return chiefEditor;
    }

    public void setChiefEditor(User chiefEditor) {
        this.chiefEditor = chiefEditor;
    }

    public ScientificArea getScientificArea() {
        return scientificArea;
    }

    public void setScientificArea(ScientificArea scientificArea) {
        this.scientificArea = scientificArea;
    }

    public List<User> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<User> reviewers) {
        this.reviewers = reviewers;
    }

    public List<User> getEditors() {
        return editors;
    }

    public void setEditors(List<User> editors) {
        this.editors = editors;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}