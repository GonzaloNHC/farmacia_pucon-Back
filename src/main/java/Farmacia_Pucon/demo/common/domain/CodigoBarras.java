package Farmacia_Pucon.demo.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CodigoBarras {

    @Column(name = "codigo_barras", length = 255)
    private String valor;

    protected CodigoBarras() {
        // requerido por JPA
    }

    public CodigoBarras(String valor) {
        // ❌ Sin validaciones que lancen excepciones
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}
