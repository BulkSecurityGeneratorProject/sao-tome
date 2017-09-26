package br.com.accesys.saotome.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private Instant data;

    @ManyToOne(optional = false)
    @NotNull
    private Comanda comanda;

    public Pedido data(Instant data) {
        this.data = data;
        return this;
    }

    public Pedido comanda(Comanda comanda) {
        this.comanda = comanda;
        return this;
    }
}
