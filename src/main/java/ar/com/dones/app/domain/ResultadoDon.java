package ar.com.dones.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResultadoDon.
 */
@Entity
@Table(name = "resultado_don")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResultadoDon implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @NotNull
  @Min(value = 0)
  @Column(name = "puntuacion_total", nullable = false)
  private Integer puntuacionTotal;

  @NotNull
  @DecimalMin(value = "0")
  @DecimalMax(value = "100")
  @Column(name = "porcentaje", precision = 21, scale = 2, nullable = false)
  private BigDecimal porcentaje;

  @NotNull
  @Min(value = 1)
  @Column(name = "ranking_posicion", nullable = false)
  private Integer rankingPosicion;

  @NotNull
  @Column(name = "es_don_principal", nullable = false)
  private Boolean esDonPrincipal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "donEspiritual" }, allowSetters = true)
  private Interpretacion interpretacion;

  @ManyToOne(optional = false)
  @NotNull
  @JsonIgnoreProperties(
    value = { "detalleRespuestas", "resultadoDones", "sesiones", "auditorias", "user", "cuestionario" },
    allowSetters = true
  )
  private RespuestaUsuario respuestaUsuario;

  @ManyToOne(optional = false)
  @NotNull
  @JsonIgnoreProperties(value = { "preguntaDones", "resultadoDones", "interpretaciones" }, allowSetters = true)
  private DonEspiritual donEspiritual;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public ResultadoDon id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getPuntuacionTotal() {
    return this.puntuacionTotal;
  }

  public ResultadoDon puntuacionTotal(Integer puntuacionTotal) {
    this.setPuntuacionTotal(puntuacionTotal);
    return this;
  }

  public void setPuntuacionTotal(Integer puntuacionTotal) {
    this.puntuacionTotal = puntuacionTotal;
  }

  public BigDecimal getPorcentaje() {
    return this.porcentaje;
  }

  public ResultadoDon porcentaje(BigDecimal porcentaje) {
    this.setPorcentaje(porcentaje);
    return this;
  }

  public void setPorcentaje(BigDecimal porcentaje) {
    this.porcentaje = porcentaje;
  }

  public Integer getRankingPosicion() {
    return this.rankingPosicion;
  }

  public ResultadoDon rankingPosicion(Integer rankingPosicion) {
    this.setRankingPosicion(rankingPosicion);
    return this;
  }

  public void setRankingPosicion(Integer rankingPosicion) {
    this.rankingPosicion = rankingPosicion;
  }

  public Boolean getEsDonPrincipal() {
    return this.esDonPrincipal;
  }

  public ResultadoDon esDonPrincipal(Boolean esDonPrincipal) {
    this.setEsDonPrincipal(esDonPrincipal);
    return this;
  }

  public void setEsDonPrincipal(Boolean esDonPrincipal) {
    this.esDonPrincipal = esDonPrincipal;
  }

  public Interpretacion getInterpretacion() {
    return this.interpretacion;
  }

  public void setInterpretacion(Interpretacion interpretacion) {
    this.interpretacion = interpretacion;
  }

  public ResultadoDon interpretacion(Interpretacion interpretacion) {
    this.setInterpretacion(interpretacion);
    return this;
  }

  public RespuestaUsuario getRespuestaUsuario() {
    return this.respuestaUsuario;
  }

  public void setRespuestaUsuario(RespuestaUsuario respuestaUsuario) {
    this.respuestaUsuario = respuestaUsuario;
  }

  public ResultadoDon respuestaUsuario(RespuestaUsuario respuestaUsuario) {
    this.setRespuestaUsuario(respuestaUsuario);
    return this;
  }

  public DonEspiritual getDonEspiritual() {
    return this.donEspiritual;
  }

  public void setDonEspiritual(DonEspiritual donEspiritual) {
    this.donEspiritual = donEspiritual;
  }

  public ResultadoDon donEspiritual(DonEspiritual donEspiritual) {
    this.setDonEspiritual(donEspiritual);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ResultadoDon)) {
      return false;
    }
    return getId() != null && getId().equals(((ResultadoDon) o).getId());
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ResultadoDon{" +
            "id=" + getId() +
            ", puntuacionTotal=" + getPuntuacionTotal() +
            ", porcentaje=" + getPorcentaje() +
            ", rankingPosicion=" + getRankingPosicion() +
            ", esDonPrincipal='" + getEsDonPrincipal() + "'" +
            "}";
    }
}
