package cz.karelsir.projekt.data;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Ronik on 7. 4. 2017.
 */

@Transactional
public class CommentDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    public List<Comment> getComments() {
        Criteria crit = session().createCriteria(Comment.class);
        return crit.list();
    }

    @Transactional
    public void update(Comment comment) {
        comment.setDateLastEdit(LocalDateTime.now());
        session().saveOrUpdate(comment);
    }

    @Transactional
    public void create(Comment comment) {
        Integer id = (Integer) session().save(comment);
        comment.setId(id);
    }

    @Transactional
    public void changeLikes(Comment comment, boolean like) {
        if (like){
            comment.setLikes(comment.getLikes()+1);
        }else{
            comment.setLikes(comment.getLikes()-1);
        }
        update(comment);
    }

    @Transactional
    public void create(List<Comment> comments) {
        for (Comment comment:comments) {
            create(comment);
        }
    }

    @Transactional
    public boolean deleteComment(int id_comment) {
        Query query = session().createQuery("delete from Comment where id_comment=:id_comment");
        query.setLong("id_comment", id_comment);
        return query.executeUpdate() == 1;
    }

    public Comment getComment(int id_comment) {

        Criteria crit = session().createCriteria(Comment.class);
        crit.add(Restrictions.idEq(id_comment));

        return (Comment) crit.uniqueResult();
    }

    public Comment getComment(String text) {

        Criteria crit = session().createCriteria(Comment.class);
        crit.add(Restrictions.eq("text", text));

        return (Comment) crit.uniqueResult();
    }

    @Transactional
    public void deleteComments() {
        session().createQuery("delete from Comment").executeUpdate();
    }


}