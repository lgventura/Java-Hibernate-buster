package controller;
// Generated 17/11/2017 01:21:43 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Cidades generated by hbm2java
 */
public class Cidades  implements java.io.Serializable {


     private int idCidade;
     private Estado estado;
     private String nomeCidade;
     private Set enderecos = new HashSet(0);

    public Cidades() {
    }

	
    public Cidades(int idCidade, Estado estado, String nomeCidade) {
        this.idCidade = idCidade;
        this.estado = estado;
        this.nomeCidade = nomeCidade;
    }
    public Cidades(int idCidade, Estado estado, String nomeCidade, Set enderecos) {
       this.idCidade = idCidade;
       this.estado = estado;
       this.nomeCidade = nomeCidade;
       this.enderecos = enderecos;
    }
   
    public int getIdCidade() {
        return this.idCidade;
    }
    
    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }
    public Estado getEstado() {
        return this.estado;
    }
    
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public String getNomeCidade() {
        return this.nomeCidade;
    }
    
    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }
    public Set getEnderecos() {
        return this.enderecos;
    }
    
    public void setEnderecos(Set enderecos) {
        this.enderecos = enderecos;
    }




}


