package controller;
// Generated 17/11/2017 01:21:43 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Estoque generated by hbm2java
 */
public class Estoque  implements java.io.Serializable {


     private Integer idEstoque;
     private Produto produto;
     private double quantidade;
     private int unidadeDeMedida;
     private double quantidadeMedida;
     private Set tblAssocProdServs = new HashSet(0);

    public Estoque() {
    }
    
    public String[] getEstoque(){
        String unidade = "";
        
        if(unidadeDeMedida == 1){
            unidade = "kg";
        }else if(unidadeDeMedida == 2){
            unidade = "ml";
        }else if(unidadeDeMedida == 3){
            unidade = "un";
        }

        
        return new String[]{
            
                produto.getNome(),
                unidade,
                String.valueOf(quantidade),
                String.valueOf(quantidadeMedida),
        };
    }
    
    public String[] getEstoqueComId(){
        String unidade = "";
        
        if(unidadeDeMedida == 1){
            unidade = "kg";
        }else if(unidadeDeMedida == 2){
            unidade = "ml";
        }else if(unidadeDeMedida == 3){
            unidade = "un";
        }

        
        return new String[]{
                
                String.valueOf(idEstoque),
                produto.getNome(),
                unidade,
                String.valueOf(quantidade),
                String.valueOf(quantidadeMedida),
                String.valueOf(produto.getValorCompra()),
        };
    }


	
    public Estoque(Produto produto, double quantidade, int unidadeDeMedida, double quantidadeMedida) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.unidadeDeMedida = unidadeDeMedida;
        this.quantidadeMedida = quantidadeMedida;
    }
    public Estoque(Produto produto, double quantidade, int unidadeDeMedida, double quantidadeMedida, Set tblAssocProdServs) {
       this.produto = produto;
       this.quantidade = quantidade;
       this.unidadeDeMedida = unidadeDeMedida;
       this.quantidadeMedida = quantidadeMedida;
       this.tblAssocProdServs = tblAssocProdServs;
    }
   
    public Integer getIdEstoque() {
        return this.idEstoque;
    }
    
    public void setIdEstoque(Integer idEstoque) {
        this.idEstoque = idEstoque;
    }
    public Produto getProduto() {
        return this.produto;
    }
    
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public double getQuantidade() {
        return this.quantidade;
    }
    
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
    public int getUnidadeDeMedida() {
        return this.unidadeDeMedida;
    }
    
    public void setUnidadeDeMedida(int unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }
    public double getQuantidadeMedida() {
        return this.quantidadeMedida;
    }
    
    public void setQuantidadeMedida(double quantidadeMedida) {
        this.quantidadeMedida = quantidadeMedida;
    }
    public Set getTblAssocProdServs() {
        return this.tblAssocProdServs;
    }
    
    public void setTblAssocProdServs(Set tblAssocProdServs) {
        this.tblAssocProdServs = tblAssocProdServs;
    }




}


