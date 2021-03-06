package controller;
// Generated 17/11/2017 01:21:43 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TipoServico generated by hbm2java
 */
public class TipoServico  implements java.io.Serializable {


     private Integer idTipo;
     private String nomeTipo;
     private Set tblAssosiativaServicoses = new HashSet(0);
     private Set servicoTerceiros = new HashSet(0);
     private Set servicos = new HashSet(0);

    public TipoServico() {
    }

    public TipoServico(String nomeTipo, Set tblAssosiativaServicoses, Set servicoTerceiros, Set servicos) {
       this.nomeTipo = nomeTipo;
       this.tblAssosiativaServicoses = tblAssosiativaServicoses;
       this.servicoTerceiros = servicoTerceiros;
       this.servicos = servicos;
    }
   
    public Integer getIdTipo() {
        return this.idTipo;
    }
    
    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }
    public String getNomeTipo() {
        return this.nomeTipo;
    }
    
    public void setNomeTipo(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }
    public Set getTblAssosiativaServicoses() {
        return this.tblAssosiativaServicoses;
    }
    
    public void setTblAssosiativaServicoses(Set tblAssosiativaServicoses) {
        this.tblAssosiativaServicoses = tblAssosiativaServicoses;
    }
    public Set getServicoTerceiros() {
        return this.servicoTerceiros;
    }
    
    public void setServicoTerceiros(Set servicoTerceiros) {
        this.servicoTerceiros = servicoTerceiros;
    }
    public Set getServicos() {
        return this.servicos;
    }
    
    public void setServicos(Set servicos) {
        this.servicos = servicos;
    }




}


