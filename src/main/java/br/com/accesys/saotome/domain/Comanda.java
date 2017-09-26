package br.com.accesys.saotome.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
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

    public Comanda identificador(String identificador) {
        this.identificador = identificador;
        return this;
    }

    public Comanda disponivel(Boolean disponivel) {
        this.disponivel = disponivel;
        return this;
    }
}
