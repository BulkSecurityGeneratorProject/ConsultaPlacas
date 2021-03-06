package org.seguritech.cp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * A ConsultaPlaca.
 */
@Entity
@Table(name = "consulta_placa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConsultaPlaca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @NotNull
    @Column(name = "consulta", nullable = false)
    private String consulta;

    @NotNull
    @Column(name = "metodo", nullable = false)
    private String metodo;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @NotNull
    @Column(name = "resultado", nullable = false)
    private String resultado;

    @NotNull
    @Column(name = "coordenadas", nullable = false)
    private String coordenadas;

    @ManyToOne(optional = false)
    private Radio radio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public ConsultaPlaca fecha(LocalDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getConsulta() {
        return consulta;
    }

    public ConsultaPlaca consulta(String consulta) {
        this.consulta = consulta;
        return this;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getMetodo() {
        return metodo;
    }

    public ConsultaPlaca metodo(String metodo) {
        this.metodo = metodo;
        return this;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Boolean isEstado() {
        return estado;
    }

    public ConsultaPlaca estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getResultado() {
        return resultado;
    }

    public ConsultaPlaca resultado(String resultado) {
        this.resultado = resultado;
        return this;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public ConsultaPlaca coordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
        return this;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Radio getRadio() {
        return radio;
    }

    public ConsultaPlaca radio(Radio radio) {
        this.radio = radio;
        return this;
    }

    public void setRadio(Radio radio) {
        this.radio = radio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConsultaPlaca consultaPlaca = (ConsultaPlaca) o;
        if (consultaPlaca.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultaPlaca.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultaPlaca{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", consulta='" + getConsulta() + "'" +
            ", metodo='" + getMetodo() + "'" +
            ", estado='" + isEstado() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", coordenadas='" + getCoordenadas() + "'" +
            "}";
    }
}
