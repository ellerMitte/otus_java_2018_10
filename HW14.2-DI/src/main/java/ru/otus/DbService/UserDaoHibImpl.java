package ru.otus.DbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import ru.otus.domain.User;

import java.util.List;

/**
 * @author Igor on 15.02.19.
 */
@Repository
public class UserDaoHibImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoHibImpl() {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(User.class)
                .getMetadataBuilder()
                .build();
//
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    @Override
    public List<User> getUsers() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.createQuery(
                    "select u from User u", User.class)
                    .getResultList();
        }
    }

    @Override
    public User save(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public User findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.get(User.class, name);
        }
    }

    @Override
    public User findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.get(User.class, id);
        }
    }

    @Override
    public void deleteUser(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(findById(id));
            session.getTransaction().commit();
        }
    }
}
