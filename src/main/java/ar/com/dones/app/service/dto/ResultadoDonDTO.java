package ar.com.dones.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.dones.app.domain.ResultadoDon} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResultadoDonDTO implements Serializable {

  private Long id;

  @NotNull
  @Min(value = 0)
  private Integer puntuacionTotal;

  @NotNull
  @DecimalMin(value = "0")
  @DecimalMax(value = "100")
  private BigDecimal porcentaje;

  @NotNull
  @Min(value = 1)
  private Integer rankingPosicion;

  @NotNull
  private Boolean esDonPrincipal;

  private InterpretacionDTO interpretacion;

  @NotNull
  private RespuestaUsuarioDTO respuestaUsuario;

  @NotNull
  private DonEspiritualDTO donEspiritual;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getPuntuacionTotal() {
    return puntuacionTotal;
  }

  public void setPuntuacionTotal(Integer puntuacionTotal) {
    this.puntuacionTotal = puntuacionTotal;
  }

  public BigDecimal getPorcentaje() {
    return porcentaje;
  }

  public void setPorcentaje(BigDecimal porcentaje) {
    this.porcentaje = porcentaje;
  }

  public Integer getRankingPosicion() {
    return rankingPosicion;
  }

  public void setRankingPosicion(Integer rankingPosicion) {
    this.rankingPosicion = rankingPosicion;
  }

  public Boolean getEsDonPrincipal() {
    return esDonPrincipal;
  }

  public void setEsDonPrincipal(Boolean esDonPrincipal) {
    this.esDonPrincipal = esDonPrincipal;
  }

  public InterpretacionDTO getInterpretacion() {
    return interpretacion;
  }

  public void setInterpretacion(InterpretacionDTO interpretacion) {
    this.interpretacion = interpretacion;
  }

  public RespuestaUsuarioDTO getRespuestaUsuario() {
    return respuestaUsuario;
  }

  public void setRespuestaUsuario(RespuestaUsuarioDTO respuestaUsuario) {
    this.respuestaUsuario = respuestaUsuario;
  }

  public DonEspiritualDTO getDonEspiritual() {
    return donEspiritual;
  }

  public void setDonEspiritual(DonEspiritualDTO donEspiritual) {
    this.donEspiritual = donEspiritual;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ResultadoDonDTO)) {
      return false;
    }

    ResultadoDonDTO resultadoDonDTO = (ResultadoDonDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, resultadoDonDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ResultadoDonDTO{" +
            "id=" + getId() +
            ", puntuacionTotal=" + getPuntuacionTotal() +
            ", porcentaje=" + getPorcentaje() +
            ", rankingPosicion=" + getRankingPosicion() +
            ", esDonPrincipal='" + getEsDonPrincipal() + "'" +
            ", interpretacion=" + getInterpretacion() +
            ", respuestaUsuario=" + getRespuestaUsuario() +
            ", donEspiritual=" + getDonEspiritual() +
            "}";
    }
}
