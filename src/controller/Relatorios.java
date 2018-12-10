package controller;

import static controller.Cliente.conectaBd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Relatorios {
    
    public static Connection conectaBd(){
            try{

                Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/projeto","root","");

                return conexao;
            }catch(SQLException erro){
                JOptionPane.showMessageDialog(null, erro);
                return null;
            }
    }
    
    public void RelatorioAtendimentoCliente(int mes, int id){
        
        HashMap filtro = new HashMap();
        filtro.put("mes", mes);
        filtro.put("id", id);
        
        JOptionPane.showMessageDialog(null, id+" | "+mes);
        JasperPrint print = null;
        try {
            print = JasperFillManager.fillReport("src/relatorios/relatorioAgenda.jasper",filtro,conectaBd());
       
            JasperViewer view = new JasperViewer(print, false);
        
            view.setVisible(true);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
    
    public void RelatorioAtendimentoColaborador(int mes, int id){
        
        HashMap filtro = new HashMap();
        filtro.put("mes", mes);
        filtro.put("id", id);
        
        JOptionPane.showMessageDialog(null, id+" | "+mes);
        JasperPrint print = null;
        try {
            print = JasperFillManager.fillReport("src/relatorios/relatorioAgendaColaborador.jasper",filtro,conectaBd());
       
            JasperViewer view = new JasperViewer(print, false);
        
            view.setVisible(true);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
    
    public void RelatorioAtendimentoServico(int mes, int id){
        
        HashMap filtro = new HashMap();
        filtro.put("mes", mes);
        filtro.put("id", id);
        
        JOptionPane.showMessageDialog(null, id+" | "+mes);
        JasperPrint print = null;
        try {
            print = JasperFillManager.fillReport("src/relatorios/relatorioAgendaServico.jasper",filtro,conectaBd());
       
            JasperViewer view = new JasperViewer(print, false);
        
            view.setVisible(true);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
    
    

}
