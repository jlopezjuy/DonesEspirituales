package ar.com.dones.app.repository;

import ar.com.dones.app.domain.RespuestaUsuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RespuestaUsuario entity.
 */
@Repository
public interface RespuestaUsuarioRepository extends JpaRepository<RespuestaUsuario, Long> {
  @Query("select respuestaUsuario from RespuestaUsuario respuestaUsuario where respuestaUsuario.user.login = ?#{authentication.name}")
  List<RespuestaUsuario> findByUserIsCurrentUser();

  default Optional<RespuestaUsuario> findOneWithEagerRelationships(Long id) {
    return this.findOneWithToOneRelationships(id);
  }

  default List<RespuestaUsuario> findAllWithEagerRelationships() {
    return this.findAllWithToOneRelationships();
  }

  default Page<RespuestaUsuario> findAllWithEagerRelationships(Pageable pageable) {
    return this.findAllWithToOneRelationships(pageable);
  }

  @Query(
    value = "select respuestaUsuario from RespuestaUsuario respuestaUsuario left join fetch respuestaUsuario.user",
    countQuery = "select count(respuestaUsuario) from RespuestaUsuario respuestaUsuario"
  )
  Page<RespuestaUsuario> findAllWithToOneRelationships(Pageable pageable);

  @Query("select respuestaUsuario from RespuestaUsuario respuestaUsuario left join fetch respuestaUsuario.user")
  List<RespuestaUsuario> findAllWithToOneRelationships();

  @Query(
    "select respuestaUsuario from RespuestaUsuario respuestaUsuario left join fetch respuestaUsuario.user where respuestaUsuario.id =:id"
  )
  Optional<RespuestaUsuario> findOneWithToOneRelationships(@Param("id") Long id);
}
