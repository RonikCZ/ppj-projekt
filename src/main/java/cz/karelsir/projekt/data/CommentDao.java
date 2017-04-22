package cz.karelsir.projekt.data;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void update(Comment comment) {
        session().saveOrUpdate(comment);
    }

    public void create(Comment comment) {
        session().save(comment);
    }

    public void changeLikes(Comment comment, boolean like) {
        if (like){
            comment.setComment_likes(comment.getComment_likes()+1);
        }else{
            comment.setComment_likes(comment.getComment_likes()-1);
        }
        update(comment);
    }

    public void create(List<Comment> comments) {
        for (Comment comment:comments) {
            create(comment);
        }
    }

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

    public void deleteComments() {
        session().createQuery("delete from Comment").executeUpdate();
    }


}