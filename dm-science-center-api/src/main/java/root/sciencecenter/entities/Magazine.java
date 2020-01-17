package root.sciencecenter.entities;

import root.sciencecenter.enums.MagazineSubscriber;

import javax.persistence.*;

@Entity
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private Long issnNumber;

    private String scientificAreas;

    @Column(nullable = false)
    private MagazineSubscriber magazineSubscriber;

    @ManyToOne
    private User chiefEditor;

    @ManyToOne
    private User reviewer1;

    @ManyToOne
    private User reviewer2;

    @ManyToOne
    private User editor1;

    @ManyToOne
    private User editor2;

    @Column(nullable = false)
    private boolean active;

    public Magazine() { }

    public Magazine(String name, Long issnNumber, String scientificAreas, MagazineSubscriber magazineSubscriber, User chiefEditor) {
        this.name = name;
        this.issnNumber = issnNumber;
        this.scientificAreas = scientificAreas;
        this.magazineSubscriber = magazineSubscriber;
        this.chiefEditor = chiefEditor;
        this.active = false;
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

    public String getScientificAreas() {
        return scientificAreas;
    }

    public void setScientificAreas(String scientificAreas) {
        this.scientificAreas = scientificAreas;
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

    public User getReviewer1() {
        return reviewer1;
    }

    public void setReviewer1(User reviewer1) {
        this.reviewer1 = reviewer1;
    }

    public User getReviewer2() {
        return reviewer2;
    }

    public void setReviewer2(User reviewer2) {
        this.reviewer2 = reviewer2;
    }

    public User getEditor1() {
        return editor1;
    }

    public void setEditor1(User editor1) {
        this.editor1 = editor1;
    }

    public User getEditor2() {
        return editor2;
    }

    public void setEditor2(User editor2) {
        this.editor2 = editor2;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
