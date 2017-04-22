package cz.karelsir.projekt.data;

/**
 * Created by akasa on 20.1.2015.
 */

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

@Transactional
public class ImageDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public List<Image> getImages() {
        Criteria crit = session().createCriteria(Image.class);
        return crit.list();
    }

    public void update(Image image) {
        session().saveOrUpdate(image);
    }

    public void changeLikes(Image image, boolean like) {
        if (like){
            image.setImage_likes(image.getImage_likes()+1);
        }else{
            image.setImage_likes(image.getImage_likes()-1);
        }
        update(image);
    }

    public void create(Image image) {
        session().save(image);
    }

    public void create(List<Image> images) {
        for (Image image:images) {
            create(image);
        }
    }

    public Image getImage(int id_image) {
        Criteria crit = session().createCriteria(Image.class);
        crit.add(Restrictions.idEq(id_image));

        return (Image) crit.uniqueResult();
    }

    public Image getImage(String url) {
        Criteria crit = session().createCriteria(Image.class);
        crit.add(Restrictions.eq("url", url));

        return (Image) crit.uniqueResult();
    }

    public boolean deleteImage(int id_image) {
        Query query = session().createQuery("delete from Image where id_image=:id_image");
        query.setLong("id_image", id_image);
        return query.executeUpdate() == 1;
    }

    public void deleteImages() {
        session().createQuery("delete from Image").executeUpdate();
    }
}
