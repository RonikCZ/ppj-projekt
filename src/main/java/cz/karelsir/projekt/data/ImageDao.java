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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Transactional
    public void update(Image image) {
        image.setDateLastEdit(LocalDateTime.now());
        session().saveOrUpdate(image);
    }

    public void changeLikes(Image image, boolean like) {
        if (like){
            image.setLikes(image.getLikes()+1);
        }else{
            image.setLikes(image.getLikes()-1);
        }
        update(image);
    }

    @Transactional
    public void create(Image image) {
        Integer id = (Integer) session().save(image);
        image.setId(id);
    }

    @Transactional
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

    @Transactional
    public boolean deleteImage(int id_image) {
        Query query = session().createQuery("delete from Image where id_image=:id_image");
        query.setLong("id_image", id_image);
        return query.executeUpdate() == 1;
    }

    @Transactional
    public void deleteImages() {
        session().createQuery("delete from Image").executeUpdate();
    }
}
