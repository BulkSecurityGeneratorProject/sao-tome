package br.com.accesys.saotome.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantidade", nullable = false)
    private Long quantidade;

    @ManyToOne(optional = false)
    @NotNull
    private Pedido pedido;

    @ManyToOne(optional = false)
    @NotNull
    private Produto produto;

    public ItemPedido quantidade(Long quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public ItemPedido pedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public ItemPedido produto(Produto produto) {
        this.produto = produto;
        return this;
    }
}
