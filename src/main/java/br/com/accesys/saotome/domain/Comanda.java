package br.com.accesys.saotome.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Comanda.
 */
@Entity
@Table(name = "comanda")
public class Comanda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "identificador", length = 200, nullable = false)
    private String identificador;

    @Column(name = "disponivel")
    private Boolean disponivel;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public Comanda identificador(String identificador) {
        this.identificador = identificador;
        return this;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Boolean isDisponivel() {
        return disponivel;
    }

    public Comanda disponivel(Boolean disponivel) {
        this.disponivel = disponivel;
        return this;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comanda comanda = (Comanda) o;
        if (comanda.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comanda.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comanda{" +
            "id=" + getId() +
            ", identificador='" + getIdentificador() + "'" +
            ", disponivel='" + isDisponivel() + "'" +
            "}";
    }
}
