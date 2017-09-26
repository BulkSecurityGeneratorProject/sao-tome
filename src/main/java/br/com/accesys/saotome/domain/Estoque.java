package br.com.accesys.saotome.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "estoque")
public class Estoque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantidade", nullable = false)
    private Long quantidade;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Produto produto;

    public Estoque quantidade(Long quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public Estoque produto(Produto produto) {
        this.produto = produto;
        return this;
    }
}
