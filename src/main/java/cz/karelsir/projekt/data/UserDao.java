package cz.karelsir.projekt.data;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void create(User user) {
        session().save(user);
    }

    public boolean exists(String username) {
        Criteria crit = session().createCriteria(User.class);
        crit.add(Restrictions.eq("username", username));
        User user = (User) crit.uniqueResult();
        return user != null;
    }

    public User getUser(int id_user) {
        Criteria crit = session().createCriteria(User.class);
        crit.add(Restrictions.idEq(id_user));

        return (User) crit.uniqueResult();
    }

    public User getUser(String username) {
        Criteria crit = session().createCriteria(User.class);
        crit.add(Restrictions.eq("username", username));

        return (User) crit.uniqueResult();
    }


    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Criteria crit = session().createCriteria(User.class);
        return crit.list();
    }

    public boolean deleteUser(int id_user) {
        Query query = session().createQuery("delete from User where id_user=:id_user");
        query.setLong("id_user", id_user);
        return query.executeUpdate() == 1;
    }

    public void deleteUsers() {
        session().createQuery("delete from User").executeUpdate();
    }
}
