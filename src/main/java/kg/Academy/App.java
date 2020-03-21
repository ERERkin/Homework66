package kg.Academy;

import kg.Academy.entities.Auto;
import kg.Academy.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class App {
    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
            Auto auto = Auto.builder()
                    .id(1 + i)
                    .model("A" + i)
                    .price(2000)
                    .build();
            create(auto);
        }
        HibernateUtil.shutDown();
    }

    public static <T> void create(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
        session.close();
        System.out.println("Создали запись успешно");
    }

    public static List<Auto> getAllAuto(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Auto> list = session.createQuery("from Auto").list();
        session.close();
        return list;
    }

    public static void deleteAutoById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Auto auto = (Auto) session.load(Auto.class, id);
        session.delete(auto);
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully deleted");
    }

    public static void update(Auto a) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Auto auto = (Auto) session.load(Auto.class, a.getId());
        auto.setModel(a.getModel());
        auto.setPrice(a.getPrice());
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully updated: " + a.getModel());
    }

    public static void deleteAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from Auto");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully deleted");
    }
}
