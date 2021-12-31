package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;

import java.util.Map;

public class TypeOfDataDTO {
    private int code;
    private String descType;
    private int valorMinimo;
    private int valorMaximo;
    private String unidadeValorQuantitativo;
    private Map<Integer, String> listaValoresQualitativos;

    public TypeOfDataDTO() {
    }

    public TypeOfDataDTO(int code, String descType, int valorMinimo, int valorMaximo, String unidadeValorQuantitativo, Map<Integer, String> listaValoresQualitativos) {
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