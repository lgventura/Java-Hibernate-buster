package controller;
// Generated 17/11/2017 01:21:43 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * CorFio generated by hbm2java
 */
public class CorFio  implements java.io.Serializable {


     private Integer idCor;
     private String cor;
     private Set tblAssocCors = new HashSet(0);

    public CorFio() {
    }

	
    public CorFio(String cor) {
        this.cor = cor;
    }
    public CorFio(String cor, Set tblAssocCors) {
       this.cor = cor;
       this.tblAssocCors = tblAssocCors;
    }
   
    public Integer getIdCor() {
        return this.idCor;
    }
    
    public void setIdCor(Integer idCor) {
        this.idCor = idCor;
    }
    public String getCor() {
        return this.cor;
    }
    
    public void setCor(String cor) {
        this.cor = cor;
    }
    public Set getTblAssocCors() {
        return this.tblAssocCors;
    }
    
    public void setTblAssocCors(Set tblAssocCors) {
        this.tblAssocCors = tblAssocCors;
    }




}


