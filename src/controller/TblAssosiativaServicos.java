package controller;
// Generated 17/11/2017 01:21:43 by Hibernate Tools 4.3.1

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;




/**
 * TblAssosiativaServicos generated by hbm2java
 */
public class TblAssosiativaServicos  implements java.io.Serializable {


     private Integer id;
     private Colaborador colaborador;
     private Servico servico;
     private TipoServico tipoServico;
     PreparedStatement pst;
    public TblAssosiativaServicos() {
    }
    
    public static Connection conectaBd(){
            try{

                Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/projeto","root","");

                return conexao;
            }catch(SQLException erro){
                JOptionPane.showMessageDialog(null, erro);
                return null;
            }
    }
    
    public void excluir(int id_Servico){
        String sql =" DELETE FROM `tbl_assosiativa_servicos` WHERE servico_id_servico = ?";
        
        try {
            pst = conectaBd().prepareStatement(sql);
            
            pst.setInt(1, id_Servico);
            
            pst.execute();
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, erro);
        }
    }


    public TblAssosiativaServicos(Colaborador colaborador, Servico servico, TipoServico tipoServico) {
       this.colaborador = colaborador;
       this.servico = servico;
       this.tipoServico = tipoServico;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Colaborador getColaborador() {
        return this.colaborador;
    }
    
    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
    public Servico getServico() {
        return this.servico;
    }
    
    public void setServico(Servico servico) {
        this.servico = servico;
    }
    public TipoServico getTipoServico() {
        return this.tipoServico;
    }
    
    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }




}


