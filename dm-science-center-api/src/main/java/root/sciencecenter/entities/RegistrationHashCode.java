package root.sciencecenter.entities;

import javax.persistence.*;

@Entity
public class RegistrationHashCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hashCode;

    @Column(nullable = false, unique = true)
    private String username;

    public RegistrationHashCode() { }

    public RegistrationHashCode(String hashCode, String username) {
        this.hashCode = hashCode;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
