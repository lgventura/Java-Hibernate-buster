package controller;
// Generated 17/11/2017 01:21:43 by Hibernate Tools 4.3.1



/**
 * TblAssocProdCasaId generated by hbm2java
 */
public class TblAssocProdCasaId  implements java.io.Serializable {


     private int produtosCasaIdProdCasa;
     private int fichaIdFicha;

    public TblAssocProdCasaId() {
    }

    public TblAssocProdCasaId(int produtosCasaIdProdCasa, int fichaIdFicha) {
       this.produtosCasaIdProdCasa = produtosCasaIdProdCasa;
       this.fichaIdFicha = fichaIdFicha;
    }
   
    public int getProdutosCasaIdProdCasa() {
        return this.produtosCasaIdProdCasa;
    }
    
    public void setProdutosCasaIdProdCasa(int produtosCasaIdProdCasa) {
        this.produtosCasaIdProdCasa = produtosCasaIdProdCasa;
    }
    public int getFichaIdFicha() {
        return this.fichaIdFicha;
    }
    
    public void setFichaIdFicha(int fichaIdFicha) {
        this.fichaIdFicha = fichaIdFicha;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TblAssocProdCasaId) ) return false;
		 TblAssocProdCasaId castOther = ( TblAssocProdCasaId ) other; 
         
		 return (this.getProdutosCasaIdProdCasa()==castOther.getProdutosCasaIdProdCasa())
 && (this.getFichaIdFicha()==castOther.getFichaIdFicha());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getProdutosCasaIdProdCasa();
         result = 37 * result + this.getFichaIdFicha();
         return result;
   }   


}


