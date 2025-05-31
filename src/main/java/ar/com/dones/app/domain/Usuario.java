package ar.com.dones.app.domain;

import ar.com.dones.app.domain.enumeration.Genero;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 100)
    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Size(max = 20)
    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private Genero genero;

    @Size(max = 200)
    @Column(name = "iglesia", length = 200)
    private String iglesia;

    @Size(max = 100)
    @Column(name = "denominacion", length = 100)
    private String denominacion;

    @NotNull
    @Column(name = "fecha_registro", nullable = false)
    private Instant fechaRegistro;

    @Column(name = "ultima_actividad")
    private Instant ultimaActividad;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "detalleRespuestas", "resultadoDones", "sesiones", "auditorias", "usuario", "cuestionario" },
        allowSetters = true
    )
    private Set<RespuestaUsuario> respuestas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuario", "respuestaUsuario" }, allowSetters = true)
    private Set<SesionUsuario> sesiones = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Usuario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Usuario nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public Usuario apellido(String apellido) {
        this.setApellido(apellido);
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return this.email;
    }

    public Usuario email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Usuario telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public Usuario fechaNacimiento(LocalDate fechaNacimiento) {
        this.setFechaNacimiento(fechaNacimiento);
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Genero getGenero() {
        return this.genero;
    }

    public Usuario genero(Genero genero) {
        this.setGenero(genero);
        return this;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getIglesia() {
        return this.iglesia;
    }

    public Usuario iglesia(String iglesia) {
        this.setIglesia(iglesia);
        return this;
    }

    public void setIglesia(String iglesia) {
        this.iglesia = iglesia;
    }

    public String getDenominacion() {
        return this.denominacion;
    }

    public Usuario denominacion(String denominacion) {
        this.setDenominacion(denominacion);
        return this;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Instant getFechaRegistro() {
        return this.fechaRegistro;
    }

    public Usuario fechaRegistro(Instant fechaRegistro) {
        this.setFechaRegistro(fechaRegistro);
        return this;
    }

    public void setFechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Instant getUltimaActividad() {
        return this.ultimaActividad;
    }

    public Usuario ultimaActividad(Instant ultimaActividad) {
        this.setUltimaActividad(ultimaActividad);
        return this;
    }

    public void setUltimaActividad(Instant ultimaActividad) {
        this.ultimaActividad = ultimaActividad;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Usuario activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<RespuestaUsuario> getRespuestas() {
        return this.respuestas;
    }

    public void setRespuestas(Set<RespuestaUsuario> respuestaUsuarios) {
        if (this.respuestas != null) {
            this.respuestas.forEach(i -> i.setUsuario(null));
        }
        if (respuestaUsuarios != null) {
            respuestaUsuarios.forEach(i -> i.setUsuario(this));
        }
        this.respuestas = respuestaUsuarios;
    }

    public Usuario respuestas(Set<RespuestaUsuario> respuestaUsuarios) {
        this.setRespuestas(respuestaUsuarios);
        return this;
    }

    public Usuario addRespuestas(RespuestaUsuario respuestaUsuario) {
        this.respuestas.add(respuestaUsuario);
        respuestaUsuario.setUsuario(this);
        return this;
    }

    public Usuario removeRespuestas(RespuestaUsuario respuestaUsuario) {
        this.respuestas.remove(respuestaUsuario);
        respuestaUsuario.setUsuario(null);
        return this;
    }

    public Set<SesionUsuario> getSesiones() {
        return this.sesiones;
    }

    public void setSesiones(Set<SesionUsuario> sesionUsuarios) {
        if (this.sesiones != null) {
            this.sesiones.forEach(i -> i.setUsuario(null));
        }
        if (sesionUsuarios != null) {
            sesionUsuarios.forEach(i -> i.setUsuario(this));
        }
        this.sesiones = sesionUsuarios;
    }

    public Usuario sesiones(Set<SesionUsuario> sesionUsuarios) {
        this.setSesiones(sesionUsuarios);
        return this;
    }

    public Usuario addSesiones(SesionUsuario sesionUsuario) {
        this.sesiones.add(sesionUsuario);
        sesionUsuario.setUsuario(this);
        return this;
    }

    public Usuario removeSesiones(SesionUsuario sesionUsuario) {
        this.sesiones.remove(sesionUsuario);
        sesionUsuario.setUsuario(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        return getId() != null && getId().equals(((Usuario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", genero='" + getGenero() + "'" +
            ", iglesia='" + getIglesia() + "'" +
            ", denominacion='" + getDenominacion() + "'" +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", ultimaActividad='" + getUltimaActividad() + "'" +
            ", activo='" + getActivo() + "'" +
            "}";
    }
}
