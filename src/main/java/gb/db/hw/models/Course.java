package gb.db.hw.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "title")
    private String title;
    @Column(name = "duration")
    private int duration;
    @ManyToOne
    @JoinColumn(name = "id_lector")
    private Lector lector;
    private static List<String> coursesNames = List.of("Математика", "Физика", "История");

    public static Course getRandomCourse() {
        Random random = new Random();
        return new Course(coursesNames.get(random.nextInt(3)), random.nextInt(100, 200));
    }


    public Course(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

}
