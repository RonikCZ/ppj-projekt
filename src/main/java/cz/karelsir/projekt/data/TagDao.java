package cz.karelsir.projekt.data;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ronik on 8. 4. 2017.
 */
@Transactional
public class TagDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    public List<Tag> getTags() {
        Criteria crit = session().createCriteria(Tag.class);
        return crit.list();
    }

    public void create(Tag tag) {
        session().save(tag);
    }

    public void create(List<Tag> tags) {
        for (Tag tag:tags) {
            create(tag);
        }
    }

    public boolean delete(Tag tag) {
        Query query = session().createQuery("delete from Tag where tag_title=:tag_title and id_image=:id_image");
        query.setString("tag_title", tag.getTag_title());
        query.setLong("id_image", tag.getImage().getId_image());
        return query.executeUpdate() == 1;
    }

    public Tag getTag(int id_image, String tag_title) {

        Criteria crit = session().createCriteria(Tag.class);
        crit.add(Restrictions.eq("tag_title", tag_title));

        crit.createAlias("image", "i");
        crit.add(Restrictions.eq("i.id_image", id_image));

        return (Tag) crit.uniqueResult();
    }


    public void deleteTags() {
        session().createQuery("delete from Tag").executeUpdate();
    }


}