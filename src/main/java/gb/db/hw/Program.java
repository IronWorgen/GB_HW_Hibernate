package gb.db.hw;

import gb.db.hw.models.Course;
import gb.db.hw.models.Lector;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.util.List;
import java.util.Random;

public class Program {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("HibernateConfig.cfg.xml")
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Lector.class)
                .buildSessionFactory();

        // заполнение таблицы лекторов
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction addLectorsTransaction = session.beginTransaction();

            List<Lector> lectors = session.createQuery("from Lector", Lector.class).list();
            if (lectors.size() == 0) {
                Lector lector1 = new Lector("Anton");
                Lector lector2 = new Lector("Vova");
                session.save(lector1);
                session.save(lector2);
            }
            addLectorsTransaction.commit();
        }

        // заполнение таблицы курсов
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction addCourcesTransaction = session.beginTransaction();
            List<Lector> lectors = session.createQuery("from Lector", Lector.class).list();
            if (lectors.size() > 0) {
                for (int i = 0; i < 5; i++) {
                    Course course = Course.getRandomCourse();
                    course.setLector(lectors.get(new Random().nextInt(lectors.size())));
                    session.save(course);
                }
            }

            addCourcesTransaction.commit();
        }

        // показать, список курсов каждого лектора
        showLectors(sessionFactory);

        // изменить лектора на курсе
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Course course = (Course) session.createQuery("from Course").list().get(0);

            List<Lector> lectors = session.createQuery("from Lector").list();
            for (Lector lector : lectors) {
                if (lector.getId() != course.getLector().getId()) {
                    course.setLector(lector);
                    break;
                }
            }
            session.update(course);

            transaction.commit();
        }

        // показать, список курсов каждого лектора
        showLectors(sessionFactory);

    }

    /**
     * отобразить список курсов у каждого лектора
     *
     * @param sessionFactory
     */
    private static void showLectors(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction addCourcesTransaction = session.beginTransaction();
            List<Lector> lectors = session.createQuery("from Lector", Lector.class).list();
            System.out.println();
            for (Lector lector : lectors) {
                System.out.println(lector.getName() + ": ");
                for (Course course : lector.getCourses()) {
                    System.out.println("\t" + course);
                }
                System.out.println();
            }
            addCourcesTransaction.commit();
        }
    }
}
