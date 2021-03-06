package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Entity
@Table(name = "typedata")
@NamedQueries(
        @NamedQuery(
                name = "getAllTypesOfData",
                query = "SELECT t FROM TypeOfData t ORDER BY t.descType"
        )
)
public class TypeOfData
{
    @Id
    @GeneratedValue
    private int code;
    @NotNull
    private String descType;
    @NotNull
    private int valorMinimo;
    @NotNull
    private int valorMaximo;
    @NotNull
    private String unidadeValorQuantitativo;
    @ElementCollection
    private Map<Integer, String> listaValoresQualitativos;

    public TypeOfData() {
    }

    public TypeOfData(int code, String descType, int valorMinimo, int valorMaximo, String unidadeValorQuantitativo, Map<Integer, String> listaValoresQualitativos) {
        this.code = code;
        this.descType = descType;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        this.unidadeValorQuantitativo = unidadeValorQuantitativo;
        this.listaValoresQualitativos = listaValoresQualitativos;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescType() {
        return descType;
    }

    public void setDescType(String descType) {
        this.descType = descType;
    }

    public int getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(int valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public int getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(int valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public String getUnidadeValorQuantitativo() {
        return unidadeValorQuantitativo;
    }

    public void setUnidadeValorQuantitativo(String unidadeValorQuantitativo) {
        this.unidadeValorQuantitativo = unidadeValorQuantitativo;
    }

    public Map<Integer, String> getListaValoresQualitativos() {
        return listaValoresQualitativos;
    }

    public void setListaValoresQualitativos(Map<Integer, String> listaValoresQualitativos) {
        this.listaValoresQualitativos = listaValoresQualitativos;
    }
}
