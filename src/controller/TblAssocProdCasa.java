package controller;
// Generated 17/11/2017 01:21:43 by Hibernate Tools 4.3.1



/**
 * TblAssocProdCasa generated by hbm2java
 */
public class TblAssocProdCasa  implements java.io.Serializable {


     private TblAssocProdCasaId id;
     private Ficha ficha;
     private ProdutosCasa produtosCasa;

    public TblAssocProdCasa() {
    }

    public TblAssocProdCasa(TblAssocProdCasaId id, Ficha ficha, ProdutosCasa produtosCasa) {
       this.id = id;
       this.ficha = ficha;
       this.produtosCasa = produtosCasa;
    }
   
    public TblAssocProdCasaId getId() {
        return this.id;
    }
    
    public void setId(TblAssocProdCasaId id) {
        this.id = id;
    }
    public Ficha getFicha() {
        return this.ficha;
    }
    
    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }
    public ProdutosCasa getProdutosCasa() {
        return this.produtosCasa;
    }
    
    public void setProdutosCasa(ProdutosCasa produtosCasa) {
        this.produtosCasa = produtosCasa;
    }




}


