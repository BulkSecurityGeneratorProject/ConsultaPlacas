package org.seguritech.cp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import org.seguritech.cp.domain.enumeration.Permiso;

/**
 * A DTO for the Radio entity.
 */
public class RadioDTO implements Serializable {

    private Long id;

    @NotNull
    private String descripcion;

    private Permiso permiso;

    @NotNull
    private String idRadio;

    private Long marcaId;

    private String marcaDescripcion;

    private Long municipioId;

    private String municipioDescripcion;

    private Long corporacionId;

    private String corporacionDescripcion;

    private Long tipoRadioId;

    private String tipoRadioDescripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public String getIdRadio() {
        return idRadio;
    }

    public void setIdRadio(String idRadio) {
        this.idRadio = idRadio;
    }

    public Long getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Long marcaId) {
        this.marcaId = marcaId;
    }

    public String getMarcaDescripcion() {
        return marcaDescripcion;
    }

    public void setMarcaDescripcion(String marcaDescripcion) {
        this.marcaDescripcion = marcaDescripcion;
    }

    public Long getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Long municipioId) {
        this.municipioId = municipioId;
    }

    public String getMunicipioDescripcion() {
        return municipioDescripcion;
    }

    public void setMunicipioDescripcion(String municipioDescripcion) {
        this.municipioDescripcion = municipioDescripcion;
    }

    public Long getCorporacionId() {
        return corporacionId;
    }

    public void setCorporacionId(Long corporacionId) {
        this.corporacionId = corporacionId;
    }

    public String getCorporacionDescripcion() {
        return corporacionDescripcion;
    }

    public void setCorporacionDescripcion(String corporacionDescripcion) {
        this.corporacionDescripcion = corporacionDescripcion;
    }

    public Long getTipoRadioId() {
        return tipoRadioId;
    }

    public void setTipoRadioId(Long tipoRadioId) {
        this.tipoRadioId = tipoRadioId;
    }

    public String getTipoRadioDescripcion() {
        return tipoRadioDescripcion;
    }

    public void setTipoRadioDescripcion(String tipoRadioDescripcion) {
        this.tipoRadioDescripcion = tipoRadioDescripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RadioDTO radioDTO = (RadioDTO) o;
        if(radioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), radioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RadioDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", permiso='" + getPermiso() + "'" +
            ", idRadio='" + getIdRadio() + "'" +
            "}";
    }
}
