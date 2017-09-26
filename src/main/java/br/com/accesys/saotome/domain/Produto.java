package br.com.accesys.saotome.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @NotNull
    @Column(name = "valor", precision=10, scale=2, nullable = false)
    private BigDecimal valor;

    public Produto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public Produto valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }
}
