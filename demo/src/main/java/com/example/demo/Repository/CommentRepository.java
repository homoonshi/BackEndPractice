package com.example.demo.Repository;

import com.example.demo.Entity.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query("SELECT c FROM Comment c WHERE c.id <= :id and c.specificationId = :specificationId")
  Slice<Comment> findBySpecificationIdAndIdLessThanOrEqual(
          @Param("specificationId") String specificationId,
          @Param("id") long id,
          Pageable pageable);

  Comment findBySpecificationId(String specificationId);

  @Query("SELECT MAX(c.id) FROM Comment c WHERE c.specificationId = :specificationId")
  Long findMaxIdBySpecificationId(@Param("specificationId") String specificationId);

}
