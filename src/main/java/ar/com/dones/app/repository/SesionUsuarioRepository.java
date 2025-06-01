package ar.com.dones.app.repository;

import ar.com.dones.app.domain.SesionUsuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SesionUsuario entity.
 */
@Repository
public interface SesionUsuarioRepository extends JpaRepository<SesionUsuario, Long> {
  @Query("select sesionUsuario from SesionUsuario sesionUsuario where sesionUsuario.user.login = ?#{authentication.name}")
  List<SesionUsuario> findByUserIsCurrentUser();

  default Optional<SesionUsuario> findOneWithEagerRelationships(Long id) {
    return this.findOneWithToOneRelationships(id);
  }

  default List<SesionUsuario> findAllWithEagerRelationships() {
    return this.findAllWithToOneRelationships();
  }

  default Page<SesionUsuario> findAllWithEagerRelationships(Pageable pageable) {
    return this.findAllWithToOneRelationships(pageable);
  }

  @Query(
    value = "select sesionUsuario from SesionUsuario sesionUsuario left join fetch sesionUsuario.user",
    countQuery = "select count(sesionUsuario) from SesionUsuario sesionUsuario"
  )
  Page<SesionUsuario> findAllWithToOneRelationships(Pageable pageable);

  @Query("select sesionUsuario from SesionUsuario sesionUsuario left join fetch sesionUsuario.user")
  List<SesionUsuario> findAllWithToOneRelationships();

  @Query("select sesionUsuario from SesionUsuario sesionUsuario left join fetch sesionUsuario.user where sesionUsuario.id =:id")
  Optional<SesionUsuario> findOneWithToOneRelationships(@Param("id") Long id);
}
