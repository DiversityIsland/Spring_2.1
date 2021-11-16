package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      return sessionFactory.getCurrentSession()
              .createQuery("from User").getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      return sessionFactory.getCurrentSession()
              .createQuery("FROM Car c LEFT OUTER JOIN FETCH c.user WHERE c.model = :mod AND c.series = :ser", Car.class)
              .setParameter("mod", model).setParameter("ser", series).uniqueResult().getUser();
   }

}
