package com.freedom.services.repos;

import com.freedom.services.dommain.Category;
import com.freedom.services.dommain.Publish;
import com.freedom.services.dommain.User;
import com.freedom.services.dommain.dto.PublishDto;
import com.freedom.services.dommain.dto.PublishesFilterDto;
import com.freedom.services.dommain.enums.PublishesCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Filter;

/**
 * Publish controller
 */
@Repository
public interface PublicationRepos extends CrudRepository<Publish, Long> {
    @Query("select new com.freedom.services.dommain.dto.PublishDto(" +
            "   p, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Publish p left join p.likes ml " +
            "where p.type='CUSTOM' and p.active=true " +
            "group by p")
    Page<PublishDto> findAllForUser(
            Pageable pageable ,
            @Param("user")User user );

    @Query("select new com.freedom.services.dommain.dto.PublishDto(" +
            "   p, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Publish p left join p.likes ml " +
            "where p.type='CUSTOM' and p.active=true " +
            "group by p")
    List<PublishDto> findAllForUser(@Param("user")User user );

    @Query("select new com.freedom.services.dommain.dto.PublishDto(" +
            "   p, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Publish p left join p.likes ml " +
            "where p.type='CUSTOM' and p.active=true and p.category=:category " +
            "group by p")
    Page<PublishDto> findAllByCategoryForUser(
            Pageable pageable ,
            @Param("user")User user ,@Param("category") Category category);

    @Query("select new com.freedom.services.dommain.dto.PublishDto(" +
            "   p, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Publish p left join p.likes ml " +
            "where p.type='CUSTOM' and p.category=:category and p.author=:user" +
            " " +
            "group by p")
    Page<PublishDto> findAllByCategoryAndByAuthorForUser(
            Pageable pageable ,
            @Param("user")User user ,@Param("category") Category category);


    @Query(value = "SELECT * FROM publishes " +
            "WHERE type = 'CUSTOM' and id IN " +
            "      (SELECT publish_id " +
            "          FROM tags " +
            "          WHERE tags.tags IN (:tags) " +
            "          GROUP BY  publish_id " +
            "          HAVING COUNT(*) = :count) ",nativeQuery = true)
    Page<Publish> findByFilter(Pageable pageable,
                               @Param("tags")List<String> tags,
                               @Param("count")int count);

    Publish findById(long id);

    @Query("select new com.freedom.services.dommain.dto.PublishDto(" +
            "   p, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Publish p left join p.likes ml " +
            "where p.id=:id " +
            "group by p")
    PublishDto findById(@Param("id")long id,@Param("user") User user);

    @Query("select new com.freedom.services.dommain.dto.PublishDto(" +
            "   p, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Publish p left join p.likes ml " +
            "where p.author = :user " +
            "group by p")
    Page<PublishDto> findAllByAuthor(@Param("user")User user,Pageable pageable);

    @Query("select new com.freedom.services.dommain.dto.PublishDto(" +
            "   m, " +
            "   count(ml), " +
            "  sum(case when ml = :author then 1 else 0 end) > 0 " +
            ") " +
            "from Publish m left join m.likes ml " +
            "where m.author = :author " +
            "group by m")
    List<PublishDto> findAllByAuthor(@Param("author") User author);

    @Modifying
    @Transactional
    @Query(value = "update publishes set author_id = 3 where  author_id=:author_id ;",nativeQuery = true)
    void replaceAllByAuthor_Id( @Param("author_id")Long authorId);
    List<Publish> findAll();

}
