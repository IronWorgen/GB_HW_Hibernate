package gb.db.hw.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lectors")
@NoArgsConstructor
@Getter
@Setter

public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "lector")
    private List<Course> courses;

    public Lector(String name) {
        this.name = name;
    }
}
