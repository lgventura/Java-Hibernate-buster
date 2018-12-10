package view;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.awt.Dimension;

import org.hibernate.Session;
import org.hibernate.Query;

import dao.DAO_PROJETO;
import controller.Agendamento;
import controller.Cliente;
import controller.Colaborador;
import controller.Pessoa;
import controller.Servico;
import controller.ServicoTerceiro;
import controller.TblAssocCor;
import controller.TblAssocProdCasa;
import controller.TblAssocSaude;
import controller.TblAssocTpCabelo;
import controller.TblAssosiativaServicos;
import controller.Terceiro;
import controller.Usuarios;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;

import java.text.ParseException;
import java.util.Date;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.xml.crypto.Data;

/**
 *
 * @author lgustavo
 */
public class IFrmAgendamento extends javax.swing.JInternalFrame {
    
    private DefaultListModel dlm ; //Usado p/Criação da lista com os dados vindo do BD
    private DefaultTableModel dtm, dtmCol, dtmCli, dtmA; //Usado p/ Criação da Tabela com os dados vindo do BD
    JButton btnHorario[] = new JButton[16];
    private int operacao = 0;
    
    JLabel fichaTpCabelo[] = new JLabel[24];
    JLabel fichaSaude[] = new JLabel[9];
    JLabel fichaProdCasa[] = new JLabel[9];
    JLabel fichaCor[] = new JLabel[11];
    
    
    private void criarFichaTpCabelo(int id){//metodo para criar a ficha de tipo de cabelo
        String sql = "from TblAssocTpCabelo";//consulta a tabela tipo cabelo
        int aux = 1, x = 10, y = 60, linha = 1; // Variaveis para controle de tamanho e espaço no form
        
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            TblAssocTpCabelo tbl;
            
            while(i.hasNext()){
                tbl = (TblAssocTpCabelo)i.next();
                if(tbl.getFicha().getCliente().getIdCliente() == id){
                    
                    fichaTpCabelo[aux].setText(tbl.getTipoCabelo().getTipoCabelo()); //pega o texto do banco
                    fichaTpCabelo[aux].setBounds(x, y, 150, 15); // inidica os tamanhos e posição

                    if(linha == 6){
                        y += 30;
                        x = 10;
                        linha = 1;
                    }else {
                        linha++;
                        x += 200;
                    }

                    frmFicha.add(fichaTpCabelo[aux]); // adiciona ao formulario
                    aux++;
                }
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private void criarFichaProdCasa(int id){//metodo para criar a ficha de tipo de cabelo
        String sql = "from TblAssocProdCasa";//consulta a tabela tipo cabelo
        int aux = 1, x = 10, y = 370, linha = 1;
        
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            TblAssocProdCasa tbl;
            
            while(i.hasNext()){
                tbl = (TblAssocProdCasa)i.next();
                if(tbl.getFicha().getCliente().getIdCliente() == id){
                    
                    fichaProdCasa[aux].setText(tbl.getProdutosCasa().getProdCasa()); //pega o texto do banco
                    fichaProdCasa[aux].setBounds(x, y, 150, 15); // inidica os tamanhos e posição

                    if(linha == 6){
                        y += 30;
                        x = 10;
                        linha = 1;
                    }else {
                        linha++;
                        x += 200;
                    }

                    frmFicha.add(fichaProdCasa[aux]); // adiciona ao formulario
                    aux++;
                }
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private void criarFichaSaude(int id){//metodo para criar a ficha de tipo de cabelo
        String sql = "from TblAssocSaude";//consulta a tabela tipo cabelo
        int aux = 1, x = 10, y = 250, linha = 1;
        
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            TblAssocSaude tbl;
            while(i.hasNext()){
                 tbl = (TblAssocSaude)i.next();
                if(tbl.getFicha().getCliente().getIdCliente() == id){
                    
                    fichaSaude[aux].setText(tbl.getProblemasSaude().getTipoProblema()); //pega o texto do banco
                    fichaSaude[aux].setBounds(x, y, 150, 15); // inidica os tamanhos e posição

                    if(linha == 6){
                        y += 30;
                        x = 10;
                        linha = 1;
                    }else {
                        linha++;
                        x += 200;
                    }

                    frmFicha.add(fichaSaude[aux]); // adiciona ao formulario
                    aux++;
                }
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private void criarFichaCor(int id){//metodo para criar a ficha de tipo de cabelo
        String sql = "from TblAssocCor";//consulta a tabela tipo cabelo
        int aux = 1, x = 10, y = 500, linha = 1;
        
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            TblAssocCor tbl;
            
            while(i.hasNext()){
                tbl = (TblAssocCor)i.next();
                if(tbl.getFicha().getCliente().getIdCliente() == id){
                    
                    fichaSaude[aux].setText(tbl.getCorFio().getCor()); //pega o texto do banco
                    fichaSaude[aux].setBounds(x, y, 150, 15); // inidica os tamanhos e posição

                    if(linha == 6){
                        y += 30;
                        x = 10;
                        linha = 1;
                    }else {
                        linha++;
                        x += 200;
                    }

                    frmFicha.add(fichaCor[aux]); // adiciona ao formulario
                    aux++;
                }
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
        
    
    private void preencherServicos(){
         String sql = "from Servico order by idServico ";
        
        cbxServico.removeAllItems();
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Servico serv;
            
            while(i.hasNext()){
                serv = (Servico)i.next();
                
                cbxServico.addItem(serv.getNomeServico());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void preencherServicosTerceiros(){
         String sql = "from ServicoTerceiro order by idServicoT ";
        
        cbxServico.removeAllItems();
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            ServicoTerceiro serv;
            
            while(i.hasNext()){
                serv = (ServicoTerceiro)i.next();
                
                cbxServico.addItem(serv.getNomeServico());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    
    private void carregaTabelaClientes(String valor, int teste){//carrega a tabela de clientes ordenando pelo nome
        String sql = "from Cliente as c order by c.pessoa.nome ";
        
        if(valor != null && teste == 1){
            sql = " from Cliente as c where c.pessoa.nome like '%"+valor+"%' order by nome";
        }else if(valor != null && teste == 2){
            sql = " from Cliente as c where c.pessoa.cpfCnpj like '%"+valor+"%' order by nome";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtmCli.setNumRows(0);
            
            Cliente c;
            
            while(i.hasNext()){
                c = (Cliente)i.next();
                dtmCli.addRow(c.getClienteAgenda());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private void carregaAgenda(String data2){
        String sql = " from Agendamento as a where a.data = '"+ data2 +"' order by a.hora";
        int auxH = 0;
        
        java.text.SimpleDateFormat formatoData = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String data = formatoData.format(dataAgenda.getDate());
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtmA.setNumRows(0);
            
            Agendamento a;
            
            while(i.hasNext()){
                a = (Agendamento)i.next();
                    int teste1 = 0;
                    int testeHora = 0, auxBtn = 0;
                    
                    while(teste1 != 1){
                        
                         btnHorario[auxH].setEnabled(true);
                         if(a.getHora().equals(btnHorario[auxH].getText())){
                             
                             auxBtn = auxH;
                             if(a.getOperacao() == 1){
                                while(testeHora < a.getServico().getTempoExec()){
                                    dtmA.addRow(a.getAgenda());
                                    testeHora++;
                                    btnHorario[auxH].setEnabled(false);
                                    auxH++;
                                }
                             }else if(a.getOperacao() == 2){
                                while(testeHora < a.getServicoTerceiro().getTempoExec()){
                                    dtmA.addRow(a.getAgenda());
                                    testeHora++;
                                    btnHorario[auxH].setEnabled(false);
                                    auxH++;
                                }
                             }
                             btnHorario[auxBtn].setEnabled(true);
                             
                             teste1 = 1;
                         }else{
                            dtmA.addRow(a.getAgendaVoid());
                            auxH++;
                         }         
                    }
            }
            Agendamento tbl = new Agendamento();
            while(auxH < 16){
                btnHorario[auxH].setEnabled(true);
                dtmA.addRow(tbl.getAgendaVoid());
                auxH++;
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
        private String[] confirmarAtendimento(int id){
        String sql = " from Agendamento as a where idAgendamento ="+id;
        String valores[] = new String[7];
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            
            Agendamento a;
            
            while(i.hasNext()){
                a = (Agendamento)i.next();
                valores[0] = String.valueOf(a.getCliente().getIdCliente());
                
                if(a.getOperacao() == 1){
                    valores[1] = String.valueOf(a.getColaborador().getIdColaborador());
                    valores[2] = String.valueOf(a.getServico().getIdServico());
                }else if(a.getOperacao() == 2){
                    valores[1] = String.valueOf(a.getServicoTerceiro().getIdServicoT());
                }
                
                valores[3] = String.valueOf(a.getData());
                valores[4] = String.valueOf(a.getHora());
                valores[5] = String.valueOf(a.getOperacao());
                valores[6] = String.valueOf(a.getValorTotal());
                
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return valores;
    }
    
    private int pegarIdAgenda(String data, String hora){
        java.text.SimpleDateFormat formatoData = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String testeData = formatoData.format(dataAgenda.getDate());
        
        String sql = " from Agendamento as a where a.data = '"+ testeData +"' and a.hora ='"+ hora +"'";
        int id = 0;
        
        
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Agendamento a;
            
            while(i.hasNext()){
                a = (Agendamento)i.next();
                id = a.getIdAgendamento();
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id;
    }
    
    private boolean testaAgenda(String data2, String hora){
        String sql = " from Agendamento as a where a.data = '"+ data2 +"' and a.hora ='"+ hora +"'";
        boolean teste = false;
        
        java.text.SimpleDateFormat formatoData = new java.text.SimpleDateFormat("dd/MM/yyyy");
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Agendamento a;
            
            while(i.hasNext()){
                a = (Agendamento)i.next();
                
                txtDataAgendado.setText(formatoData.format(a.getData()));
                txtHoraAgendado.setText(a.getHora());
                
                if(a.getOperacao() == 1){
                    txtServicoAgendado.setText(a.getServico().getNomeServico());
                    txtColAgendado.setText(a.getColaborador().getPessoa().getNome());
                }else if(a.getOperacao() == 2){
                    txtServicoAgendado.setText(a.getServicoTerceiro().getNomeServico());
                    txtColAgendado.setText(a.getServicoTerceiro().getTerceiro().getPessoa().getNome());
                }
                
                txtClienteAgendado.setText(a.getCliente().getPessoa().getNome());
                
                txtDataAgendado.setEditable(false);
                txtHoraAgendado.setEditable(false);
                txtServicoAgendado.setEditable(false);
                txtColAgendado.setEditable(false);
                txtClienteAgendado.setEditable(false);
                
                if(a.getStatus() == 0){
                    btnConfirmarAgendamento.setEnabled(true);
                    btnCancelarAgendamento.setEnabled(true);
                    btnConcluirAgendamento.setEnabled(false);
                    lblStatus.setText("Agendado");
                }else if(a.getStatus() == 1){
                    btnConfirmarAgendamento.setEnabled(false);
                    btnCancelarAgendamento.setEnabled(false);
                    btnConcluirAgendamento.setEnabled(true);
                    lblStatus.setText("Confirmado");
                }else if(a.getStatus() == 2){
                    btnConfirmarAgendamento.setEnabled(false);
                    btnCancelarAgendamento.setEnabled(false);
                    btnConcluirAgendamento.setEnabled(false);
                    lblStatus.setText("Concluido");
                }
                
                teste = true;
                
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return teste;
    }
    
    private boolean testaHora(String data2, String hora){
        String sql = " from Agendamento as a where a.data = '"+ data2 +"' and a.hora ='"+ hora +"'";
        boolean teste = false;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Agendamento a;
            
            while(i.hasNext()){
                a = (Agendamento)i.next();
       
                teste = true;
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return teste;
    }
    
    private int pegarTempoServico(int index){
        String sql = "from Servico order by idServico";
        int aux = 0;
        int valor = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().openSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Servico serv;
            
            while(i.hasNext()){
                serv = (Servico)i.next();
                if(aux == index){
                   valor = serv.getTempoExec();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return valor;
    }
    
    private int pegarTempoServicoTerceiro(int index){
        String sql = "from ServicoTerceiro order by idServicoT";
        int aux = 0;
        int valor = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().openSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            ServicoTerceiro serv;
            
            while(i.hasNext()){
                serv = (ServicoTerceiro)i.next();
                if(aux == index){
                   valor = serv.getTempoExec();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return valor;
    }
    
    private boolean verificarDisponibilidade(String hora, int tempoServico){
        boolean teste = false;
        java.text.SimpleDateFormat formatoData = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String data = formatoData.format(dataAgenda.getDate());
        
        if(tempoServico == 1){
            teste = true;
        }else {
            
            if(hora.equals("08:00")){
                if(tempoServico == 2){
                    if(testaHora(data, "08:30")){
                        teste = false;
                    }else if(testaHora(data, "09:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "08:30")){
                        teste = false;
                    }else if(testaHora(data, "09:00")){
                        teste = false;
                    }else if(testaHora(data, "09:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    if(testaHora(data, "08:30")){
                        teste = false;
                    }else if(testaHora(data, "09:00")){
                        teste = false;
                    }else if(testaHora(data, "09:30")){
                        teste = false;
                    }else if(testaHora(data, "10:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }
            }else if(hora.equals("08:30")){
                if(tempoServico == 2){
                    if(testaHora(data, "09:00")){
                        teste = false;
                    }else if(testaHora(data, "09:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "09:00")){
                        teste = false;
                    }else if(testaHora(data, "09:30")){
                        teste = false;
                    }else if(testaHora(data, "10:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    if(testaHora(data, "09:00")){
                        teste = false;
                    }else if(testaHora(data, "09:30")){
                        teste = false;
                    }else if(testaHora(data, "10:00")){
                        teste = false;
                    }else if(testaHora(data, "10:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }
            }else if(hora.equals("09:00")){
                if(tempoServico == 2){
                    if(testaHora(data, "09:30")){
                        teste = false;
                    }else if(testaHora(data, "10:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "09:30")){
                        teste = false;
                    }else if(testaHora(data, "10:00")){
                        teste = false;
                    }else if(testaHora(data, "10:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    if(testaHora(data, "09:30")){
                        teste = false;
                    }else if(testaHora(data, "10:00")){
                        teste = false;
                    }else if(testaHora(data, "10:30")){
                        teste = false;
                    }else if(testaHora(data, "11:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }
            }else if(hora.equals("09:30")){
                if(tempoServico == 2){
                    if(testaHora(data, "10:00")){
                        teste = false;
                    }else if(testaHora(data, "10:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "10:00")){
                        teste = false;
                    }else if(testaHora(data, "10:30")){
                        teste = false;
                    }else if(testaHora(data, "11:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    if(testaHora(data, "10:00")){
                        teste = false;
                    }else if(testaHora(data, "10:30")){
                        teste = false;
                    }else if(testaHora(data, "11:00")){
                        teste = false;
                    }else if(testaHora(data, "11:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }
            }else if(hora.equals("10:00")){
                if(tempoServico == 2){
                    if(testaHora(data, "10:30")){
                        teste = false;
                    }else if(testaHora(data, "11:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "10:30")){
                        teste = false;
                    }else if(testaHora(data, "11:00")){
                        teste = false;
                    }else if(testaHora(data, "11:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    if(testaHora(data, "10:30")){
                        teste = false;
                    }else if(testaHora(data, "11:00")){
                        teste = false;
                    }else if(testaHora(data, "11:30")){
                        teste = false;
                    }else if(testaHora(data, "13:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }
            }else if(hora.equals("10:30")){
                if(tempoServico == 2){
                    if(testaHora(data, "11:00")){
                        teste = false;
                    }else if(testaHora(data, "11:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "11:00")){
                        teste = false;
                    }else if(testaHora(data, "11:30")){
                        teste = false;
                    }else if(testaHora(data, "13:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    if(testaHora(data, "11:00")){
                        teste = false;
                    }else if(testaHora(data, "11:30")){
                        teste = false;
                    }else if(testaHora(data, "13:00")){
                        teste = false;
                    }else if(testaHora(data, "13:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }
            }else if(hora.equals("11:00")){
                if(tempoServico == 2){
                    if(testaHora(data, "11:30")){
                        teste = false;
                    }else if(testaHora(data, "13:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "11:30")){
                        teste = false;
                    }else if(testaHora(data, "13:00")){
                        teste = false;
                    }else if(testaHora(data, "13:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    if(testaHora(data, "11:30")){
                        teste = false;
                    }else if(testaHora(data, "13:00")){
                        teste = false;
                    }else if(testaHora(data, "13:30")){
                        teste = false;
                    }else if(testaHora(data, "14:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }
            }else if(hora.equals("11:30")){
                if(tempoServico == 2){
                    if(testaHora(data, "13:00")){
                        teste = false;
                    }else if(testaHora(data, "13:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "13:00")){
                        teste = false;
                    }else if(testaHora(data, "13:30")){
                        teste = false;
                    }else if(testaHora(data, "14:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    if(testaHora(data, "13:00")){
                        teste = false;
                    }else if(testaHora(data, "13:30")){
                        teste = false;
                    }else if(testaHora(data, "14:00")){
                        teste = false;
                    }else if(testaHora(data, "14:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }
            }else if(hora.equals("13:00")){
                if(tempoServico == 2){
                    if(testaHora(data, "13:30")){
                        teste = false;
                    }else if(testaHora(data, "14:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "13:30")){
                        teste = false;
                    }else if(testaHora(data, "14:00")){
                        teste = false;
                    }else if(testaHora(data, "14:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    if(testaHora(data, "13:30")){
                        teste = false;
                    }else if(testaHora(data, "14:00")){
                        teste = false;
                    }else if(testaHora(data, "14:30")){
                        teste = false;
                    }else if(testaHora(data, "15:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }
            }else if(hora.equals("13:30")){
                if(tempoServico == 2){
                    if(testaHora(data, "14:00")){
                        teste = false;
                    }else if(testaHora(data, "14:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "14:00")){
                        teste = false;
                    }else if(testaHora(data, "14:30")){
                        teste = false;
                    }else if(testaHora(data, "15:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    if(testaHora(data, "14:00")){
                        teste = false;
                    }else if(testaHora(data, "14:30")){
                        teste = false;
                    }else if(testaHora(data, "15:00")){
                        teste = false;
                    }else if(testaHora(data, "15:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }
            }else if(hora.equals("14:00")){
                if(tempoServico == 2){
                    if(testaHora(data, "14:30")){
                        teste = false;
                    }else if(testaHora(data, "15:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "14:30")){
                        teste = false;
                    }else if(testaHora(data, "15:00")){
                        teste = false;
                    }else if(testaHora(data, "15:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    if(testaHora(data, "14:30")){
                        teste = false;
                    }else if(testaHora(data, "15:00")){
                        teste = false;
                    }else if(testaHora(data, "15:30")){
                        teste = false;
                    }else if(testaHora(data, "16:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }
            }else if(hora.equals("14:30")){
                if(tempoServico == 2){
                    if(testaHora(data, "15:00")){
                        teste = false;
                    }else if(testaHora(data, "15:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "15:00")){
                        teste = false;
                    }else if(testaHora(data, "15:30")){
                        teste = false;
                    }else if(testaHora(data, "16:00")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    if(testaHora(data, "15:00")){
                        teste = false;
                    }else if(testaHora(data, "15:30")){
                        teste = false;
                    }else if(testaHora(data, "16:00")){
                        teste = false;
                    }else if(testaHora(data, "16:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }
            }else if(hora.equals("15:00")){
                if(tempoServico == 2){
                    if(testaHora(data, "15:30")){
                        teste = false;
                    }else if(testaHora(data, "16:00")){
                        teste = false;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "15:30")){
                        teste = false;
                    }else if(testaHora(data, "16:00")){
                        teste = false;
                    }else if(testaHora(data, "16:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    teste = false;
                }else {
                        teste = true;
                    }
            }else if(hora.equals("15:30")){
                if(tempoServico == 2){
                    if(testaHora(data, "16:00")){
                        teste = false;
                    }else if(testaHora(data, "16:30")){
                        teste = false;
                    }
                }else if(tempoServico == 3){
                    if(testaHora(data, "16:00")){
                        teste = false;
                    }else if(testaHora(data, "16:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                }else if(tempoServico == 4){
                    teste = false;
                }else {
                        teste = true;
                    }
            }else if(hora.equals("16:00")){
                if(tempoServico == 2){
                    if(testaHora(data, "16:30")){
                        teste = false;
                    }else {
                        teste = true;
                    }
                    
                }else if(tempoServico == 3){
                    teste = false;
                }else if(tempoServico == 4){
                    teste = false;
                }else {
                        teste = true;
                    }
            }else if(hora.equals("16:30")){
                if(tempoServico == 2){
                    teste = false;
                }else if(tempoServico == 3){
                    teste = false;
                }else if(tempoServico == 4){
                    teste = false;
                }else {
                        teste = true;
                    }
            }
            
        }
        
        return teste;
    }
    
    private int pegarIdServico(int index){
        String sql = "from Servico order by idServico";
        int aux = 0;
        int id = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().openSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Servico serv;
            
            while(i.hasNext()){
                serv = (Servico)i.next();
                if(aux == index){
                   id = serv.getIdServico();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id;
    }
    
    private double pegarValorServico(int index){
        String sql = "from Servico order by idServico";
        int aux = 0;
        double valor = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().openSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Servico serv;
            
            while(i.hasNext()){
                serv = (Servico)i.next();
                if(aux == index){
                   valor = serv.getValorTotal();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return valor;
    }
    
    private int pegarIdServicoTerceiro(int index){
        String sql = "from ServicoTerceiro order by idServicoT";
        int aux = 0;
        int id = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().openSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            ServicoTerceiro serv;
            
            while(i.hasNext()){
                serv = (ServicoTerceiro)i.next();
                if(aux == index){
                   id = serv.getIdServicoT();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id;
    }
    
    private double pegarValorServicoTerceiro(int index){
        String sql = "from ServicoTerceiro order by idServicoT";
        int aux = 0;
        double valor = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().openSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            ServicoTerceiro serv;
            
            while(i.hasNext()){
                serv = (ServicoTerceiro)i.next();
                if(aux == index){
                   valor = serv.getValorServ();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return valor;
    }
    
    private void carregaTabelaColaboradoresHabilitados(int idServ){
        String sql = "from TblAssosiativaServicos as tbl where tbl.servico.idServico = "+idServ;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().openSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtmCol.setNumRows(0);
            
            TblAssosiativaServicos tbl;
            
            while(i.hasNext()){
                tbl = (TblAssosiativaServicos)i.next();
                dtmCol.addRow(tbl.getColaborador().getColaboradorAgenda());
            }
            
            s.getTransaction().commit();
            s.close();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void carregaTerceiro(int idServTerceiro){
        String sql = "from ServicoTerceiro as serv where serv.idServicoT = "+idServTerceiro;
        try{
            Session s = DAO_PROJETO.getSessionFactory().openSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            ServicoTerceiro serv;
            
            while(i.hasNext()){
                serv = (ServicoTerceiro)i.next();
                
                txtIdCol.setText(String.valueOf(serv.getTerceiro().getIdTerceiro()));
                txtColaborador.setText(String.valueOf(serv.getTerceiro().getPessoa().getNome()));
                
                txtIdCol.setEditable(false);
                txtColaborador.setEditable(false);
                btnEscolhaCol.setEnabled(false);
            }
            
            s.getTransaction().commit();
            s.close();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public IFrmAgendamento() {
        initComponents();
        dtmCli = (DefaultTableModel)tblCliente.getModel();
        dtmCol = (DefaultTableModel)tblColaborador.getModel();
        dtmA = (DefaultTableModel)tblAgendamento.getModel();
        
        txtColAgendado.setEnabled(false);
        txtClienteAgendado.setEnabled(false);
        txtDataAgendado.setEnabled(false);
        txtHoraAgendado.setEnabled(false);
        txtServicoAgendado.setEnabled(false);
        
        btnCancelarAgendamento.setEnabled(false);
        btnConcluirAgendamento.setEnabled(false);
        btnConfirmarAgendamento.setEnabled(false);
        
        for(int aux=0; aux < 24; aux++){
            fichaTpCabelo[aux] = new JLabel();
        }
        for(int aux=0; aux < 9; aux++){
            fichaSaude[aux] = new JLabel();
        }
        for(int aux=0; aux < 9; aux++){
            fichaProdCasa[aux] = new JLabel();
        }
        for(int aux=0; aux < 11; aux++){
            fichaCor[aux] = new JLabel();
        }
                    
        
        int y = 30;
        for(int aux = 0; aux < 16; aux++){
            String hora = "";
            
            
            if(aux == 0){
                hora = "08:00";
            }else if(aux == 1){
                hora = "08:30";
            }else if(aux == 2){
                hora = "09:00";
            }else if(aux == 3){
                hora = "09:30";
            }else if(aux == 4){
                hora = "10:00";
            }else if(aux == 5){
                hora = "10:30";
            }else if(aux == 6){
                hora = "11:00";
            }else if(aux == 7){
                hora = "11:30";
            }else if(aux == 8){
                hora = "13:00";
            }else if(aux == 9){
                hora = "13:30";
            }else if(aux == 10){
                hora = "14:00";
            }else if(aux == 11){
                hora = "14:30";
            }else if(aux == 12){
                hora = "15:00";
            }else if(aux == 13){
                hora = "15:30";
            }else if(aux == 14){
                hora = "16:00";
            }else if(aux == 15){
                hora = "16:30";
            }
            String horario = hora;
            btnHorario[aux] = new JButton(hora);
            btnHorario[aux].setBounds(480, y, 80, 30);
            btnHorario[aux].addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt){
                java.text.SimpleDateFormat formatoData = new java.text.SimpleDateFormat("yyyy-MM-dd");
                String data = formatoData.format(dataAgenda.getDate());
                
                if(testaAgenda(data, horario)){
                    
                    txtColAgendado.setEnabled(true);
                    txtClienteAgendado.setEnabled(true);
                    txtDataAgendado.setEnabled(true);
                    txtHoraAgendado.setEnabled(true);
                    txtServicoAgendado.setEnabled(true);
                    
                }else {
                    java.text.SimpleDateFormat data2 = new java.text.SimpleDateFormat("dd/MM/yyyy");
                    
                    txtColAgendado.setEnabled(false);
                    txtClienteAgendado.setEnabled(false);
                    txtDataAgendado.setEnabled(false);
                    txtHoraAgendado.setEnabled(false);
                    txtServicoAgendado.setEnabled(false);
                    btnCancelarAgendamento.setEnabled(false);
                    btnConcluirAgendamento.setEnabled(false);
                    btnConfirmarAgendamento.setEnabled(false);

                    
                    txtColAgendado.setText("");
                    txtClienteAgendado.setText("");
                    txtDataAgendado.setText("");
                    txtHoraAgendado.setText("");
                    txtServicoAgendado.setText("");
                    
                    lblStatus.setText("Selecione um Atendimento Cadastrado");
                    
                    txtDataAgendamento.setText(formatoData.format(dataAgenda.getDate()));
                    txtHoraAgendamento.setText(horario);
                    txtDataAgendamento.setEditable(false);
                    txtHoraAgendamento.setEditable(false);

                    preencherServicos();
                    
                    


                    frmCadAgenda.setVisible(true);
                    frmCadAgenda.setLocationRelativeTo(null);
                    frmCadAgenda.toFront();
                }
            }
            });
            
            pnCadastro.add(btnHorario[aux]);
            
            y += 35;
        }
        
        java.text.SimpleDateFormat formatoData = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String data = formatoData.format(dataAgenda.getDate());
        
        carregaAgenda(data);
        
        
    }
    
    public void setPosicao() {
    Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gpBusca = new javax.swing.ButtonGroup();
        frmClientes = new javax.swing.JFrame();
        btnEscolheCli = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        frmColaboradores = new javax.swing.JFrame();
        btnEscolheCol = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblColaborador = new javax.swing.JTable();
        gpServicos = new javax.swing.ButtonGroup();
        frmCadAgenda = new javax.swing.JFrame();
        jLabel26 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnEscolhaCli = new javax.swing.JButton();
        txtDataAgendamento = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cbxServico = new javax.swing.JComboBox<>();
        txtHoraAgendamento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtColaborador = new javax.swing.JTextField();
        btnEscolhaCol = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtIdCol = new javax.swing.JTextField();
        txtCliente = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        rbServicoTerceiro = new javax.swing.JRadioButton();
        rbServico = new javax.swing.JRadioButton();
        frmFicha = new javax.swing.JFrame();
        jLabel21 = new javax.swing.JLabel();
        btnFinalizar = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        tpnCadastrar = new javax.swing.JTabbedPane();
        pnCadastro = new javax.swing.JPanel();
        dataAgenda = new com.toedter.calendar.JCalendar();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAgendamento = new javax.swing.JTable();
        pnAtendimento = new javax.swing.JPanel();
        btnCancelarAgendamento = new javax.swing.JButton();
        btnConfirmarAgendamento = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtDataAgendado = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnConcluirAgendamento = new javax.swing.JButton();
        txtHoraAgendado = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtClienteAgendado = new javax.swing.JTextField();
        txtColAgendado = new javax.swing.JTextField();
        txtServicoAgendado = new javax.swing.JTextField();

        frmClientes.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmClientes.setTitle("Produtos em Estoque");
        frmClientes.setMinimumSize(new java.awt.Dimension(800, 450));

        btnEscolheCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bt_up.png"))); // NOI18N
        btnEscolheCli.setText("Selecionar Cliente");
        btnEscolheCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscolheCliActionPerformed(evt);
            }
        });

        jLabel18.setText("Clientes:");

        tblCliente.setBackground(new java.awt.Color(153, 153, 255));
        tblCliente.setForeground(new java.awt.Color(255, 255, 255));
        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome do Cliente", "CPF", "Telefone", "Alergia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblCliente);

        javax.swing.GroupLayout frmClientesLayout = new javax.swing.GroupLayout(frmClientes.getContentPane());
        frmClientes.getContentPane().setLayout(frmClientesLayout);
        frmClientesLayout.setHorizontalGroup(
            frmClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmClientesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEscolheCli)
                .addContainerGap())
        );
        frmClientesLayout.setVerticalGroup(
            frmClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEscolheCli)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        frmColaboradores.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmColaboradores.setTitle("Produtos em Estoque");
        frmColaboradores.setMinimumSize(new java.awt.Dimension(773, 472));

        btnEscolheCol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bt_up.png"))); // NOI18N
        btnEscolheCol.setText("Selecionar Colaborador");
        btnEscolheCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscolheColActionPerformed(evt);
            }
        });

        jLabel20.setText("Colaboradores:");

        tblColaborador.setBackground(new java.awt.Color(153, 153, 255));
        tblColaborador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome do Colaborador", "CPF", "Telefone", "Especialidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblColaborador);

        javax.swing.GroupLayout frmColaboradoresLayout = new javax.swing.GroupLayout(frmColaboradores.getContentPane());
        frmColaboradores.getContentPane().setLayout(frmColaboradoresLayout);
        frmColaboradoresLayout.setHorizontalGroup(
            frmColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmColaboradoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, frmColaboradoresLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(475, 475, 475))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEscolheCol))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        frmColaboradoresLayout.setVerticalGroup(
            frmColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmColaboradoresLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel20)
                .addGap(25, 25, 25)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEscolheCol)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        frmCadAgenda.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmCadAgenda.setTitle("Agendamento");
        frmCadAgenda.setBackground(new java.awt.Color(153, 153, 255));
        frmCadAgenda.setMinimumSize(new java.awt.Dimension(687, 427));

        jLabel26.setText("Cadastrar Agendamento:");

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5745_Knob_Valid_Green.png"))); // NOI18N
        btnSalvar.setText("Adicionar na Agenda");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Detalhes Sobre o Agendamento"));

        btnEscolhaCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        btnEscolhaCli.setToolTipText("Selecionar Cliente");
        btnEscolhaCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscolhaCliActionPerformed(evt);
            }
        });

        jLabel7.setText("Serviço:");

        jLabel25.setText("Horário:");

        cbxServico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxServicoActionPerformed(evt);
            }
        });

        jLabel9.setText("ID:");

        jLabel12.setText("Colaborador:");

        btnEscolhaCol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        btnEscolhaCol.setToolTipText("Selecionar Colaborador");
        btnEscolhaCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscolhaColActionPerformed(evt);
            }
        });

        jLabel17.setText("ID:");

        jLabel4.setText("Cliente:");

        jLabel24.setText("DATA:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdCol, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtColaborador, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                            .addComponent(txtCliente))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEscolhaCli, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(btnEscolhaCol, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                        .addGap(28, 28, 28))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDataAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoraAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxServico, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtDataAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(txtHoraAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cbxServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEscolhaCli, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEscolhaCol, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtIdCol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Operação"));

        gpServicos.add(rbServicoTerceiro);
        rbServicoTerceiro.setText("Serviços de Terceiros");
        rbServicoTerceiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbServicoTerceiroActionPerformed(evt);
            }
        });

        gpServicos.add(rbServico);
        rbServico.setSelected(true);
        rbServico.setText("Serviços Do Salão");
        rbServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbServicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbServico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbServicoTerceiro)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbServico)
                    .addComponent(rbServicoTerceiro))
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout frmCadAgendaLayout = new javax.swing.GroupLayout(frmCadAgenda.getContentPane());
        frmCadAgenda.getContentPane().setLayout(frmCadAgendaLayout);
        frmCadAgendaLayout.setHorizontalGroup(
            frmCadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmCadAgendaLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(frmCadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(frmCadAgendaLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(frmCadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnSalvar)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        frmCadAgendaLayout.setVerticalGroup(
            frmCadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmCadAgendaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmCadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalvar)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        frmFicha.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmFicha.setTitle("Ficha de Anaminése");
        frmFicha.setBackground(new java.awt.Color(255, 204, 255));
        frmFicha.setMinimumSize(new java.awt.Dimension(936, 653));

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel21.setText("Detalhes sobre o Tipo de Cabelo:");

        btnFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5122_accepted_48.png"))); // NOI18N
        btnFinalizar.setText("Confimar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel22.setText("Detalhes sobre a Saude do Cliente:");

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel23.setText("Detalhes Sobre a Cor do Fio");

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel29.setText("Detalhes sobre Tratamento que o Cliente Faz em Casa:");

        javax.swing.GroupLayout frmFichaLayout = new javax.swing.GroupLayout(frmFicha.getContentPane());
        frmFicha.getContentPane().setLayout(frmFichaLayout);
        frmFichaLayout.setHorizontalGroup(
            frmFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmFichaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmFichaLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 498, Short.MAX_VALUE)
                        .addComponent(btnFinalizar))
                    .addGroup(frmFichaLayout.createSequentialGroup()
                        .addGroup(frmFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel29))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        frmFichaLayout.setVerticalGroup(
            frmFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmFichaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(164, 164, 164)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addGap(113, 113, 113)
                .addComponent(jLabel23)
                .addGap(138, 138, 138))
        );

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Agendamento");
        setMinimumSize(new java.awt.Dimension(5230, 5648));
        setPreferredSize(new java.awt.Dimension(5230, 5648));

        tpnCadastrar.setMaximumSize(new java.awt.Dimension(5224, 5620));
        tpnCadastrar.setMinimumSize(new java.awt.Dimension(1123, 580));
        tpnCadastrar.setPreferredSize(new java.awt.Dimension(5224, 5620));

        pnCadastro.setBackground(new java.awt.Color(204, 255, 204));
        pnCadastro.setMaximumSize(new java.awt.Dimension(1292, 628));
        pnCadastro.setMinimumSize(new java.awt.Dimension(1292, 628));

        dataAgenda.setMaximumSize(new java.awt.Dimension(244, 159));
        dataAgenda.setMinimumSize(new java.awt.Dimension(244, 159));
        dataAgenda.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dataAgendaPropertyChange(evt);
            }
        });

        tblAgendamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null},
                {"", null, null, null, null}
            },
            new String [] {
                "Cliente", "Colaborador", "Serviço", "Status", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAgendamento.setMaximumSize(new java.awt.Dimension(375, 576));
        tblAgendamento.setMinimumSize(new java.awt.Dimension(375, 576));
        tblAgendamento.setRowHeight(35);
        jScrollPane2.setViewportView(tblAgendamento);

        pnAtendimento.setBackground(new java.awt.Color(102, 204, 255));
        pnAtendimento.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Atendimento"));

        btnCancelarAgendamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5750_Knob_Cancel.png"))); // NOI18N
        btnCancelarAgendamento.setText("Cancelar");
        btnCancelarAgendamento.setToolTipText("");
        btnCancelarAgendamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarAgendamentoActionPerformed(evt);
            }
        });

        btnConfirmarAgendamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5746_Knob_Valid_Blue.png"))); // NOI18N
        btnConfirmarAgendamento.setText("Confirmar");
        btnConfirmarAgendamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarAgendamentoActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(102, 204, 255));
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Serviço:");

        jLabel28.setBackground(new java.awt.Color(102, 204, 255));
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Colaborador/Terceiro:");

        jLabel27.setBackground(new java.awt.Color(102, 204, 255));
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Cliente:");

        jLabel13.setBackground(new java.awt.Color(102, 204, 255));
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("DATA:");

        lblStatus.setBackground(new java.awt.Color(102, 204, 255));
        lblStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblStatus.setText("Selecione um Atendimento Cadastrado");

        jLabel1.setBackground(new java.awt.Color(102, 204, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Status:");

        btnConcluirAgendamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5745_Knob_Valid_Green.png"))); // NOI18N
        btnConcluirAgendamento.setText("Concluir Agendamento");
        btnConcluirAgendamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConcluirAgendamentoActionPerformed(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(102, 204, 255));
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Horário:");

        javax.swing.GroupLayout pnAtendimentoLayout = new javax.swing.GroupLayout(pnAtendimento);
        pnAtendimento.setLayout(pnAtendimentoLayout);
        pnAtendimentoLayout.setHorizontalGroup(
            pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAtendimentoLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(btnConfirmarAgendamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelarAgendamento)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAtendimentoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAtendimentoLayout.createSequentialGroup()
                        .addGroup(pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnAtendimentoLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(lblStatus))
                            .addGroup(pnAtendimentoLayout.createSequentialGroup()
                                .addGroup(pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addGroup(pnAtendimentoLayout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDataAgendado, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel27))
                                .addGap(38, 38, 38)
                                .addGroup(pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtServicoAgendado, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtClienteAgendado, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtColAgendado, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAtendimentoLayout.createSequentialGroup()
                                            .addComponent(jLabel14)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtHoraAgendado, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jLabel6))
                        .addGap(220, 220, 220))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAtendimentoLayout.createSequentialGroup()
                        .addComponent(btnConcluirAgendamento)
                        .addGap(184, 184, 184))))
        );
        pnAtendimentoLayout.setVerticalGroup(
            pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAtendimentoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnConcluirAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblStatus))
                .addGap(20, 20, 20)
                .addGroup(pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtDataAgendado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtHoraAgendado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtClienteAgendado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtColAgendado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtServicoAgendado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(pnAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmarAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout pnCadastroLayout = new javax.swing.GroupLayout(pnCadastro);
        pnCadastro.setLayout(pnCadastroLayout);
        pnCadastroLayout.setHorizontalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnAtendimento, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataAgenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(112, 112, 112)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        pnCadastroLayout.setVerticalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(dataAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnAtendimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tpnCadastrar.addTab("Agenda", pnCadastro);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnCadastrar, javax.swing.GroupLayout.DEFAULT_SIZE, 1297, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnCadastrar, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
        );

        setBounds(200, 100, 1303, 683);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEscolheCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscolheCliActionPerformed
        int linha = tblCliente.getSelectedRow();
        
        frmClientes.setVisible(false);
        frmClientes.toBack();
        
        criarFichaTpCabelo(Integer.parseInt(dtmCli.getValueAt(linha, 0).toString()));
        criarFichaSaude(Integer.parseInt(dtmCli.getValueAt(linha, 0).toString()));
        criarFichaProdCasa(Integer.parseInt(dtmCli.getValueAt(linha, 0).toString()));
        criarFichaCor(Integer.parseInt(dtmCli.getValueAt(linha, 0).toString()));
        
        
        
        
        frmFicha.setVisible(true);
        frmFicha.setLocationRelativeTo(null);
        frmFicha.toFront();
        
        
    }//GEN-LAST:event_btnEscolheCliActionPerformed

    private void btnEscolheColActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscolheColActionPerformed
        int linha = tblColaborador.getSelectedRow();
        
        txtIdCol.setText(dtmCol.getValueAt(linha, 0).toString());
        txtIdCol.setEditable(false);
        
        txtColaborador.setText(dtmCol.getValueAt(linha, 1).toString());
        txtColaborador.setEditable(false);
        
        frmColaboradores.setVisible(false);
        frmColaboradores.toBack();
        
        frmCadAgenda.toFront();
    }//GEN-LAST:event_btnEscolheColActionPerformed

    private void btnConfirmarAgendamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarAgendamentoActionPerformed
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };
        String [] valores = new String[7];
        
        
        
        int id = pegarIdAgenda(txtDataAgendado.getText(), txtHoraAgendado.getText());
        
        valores = confirmarAtendimento(id);
        
        int opc = JOptionPane.showOptionDialog(IFrmAgendamento.this,"Deseja Confirmar Este atendimento?: ", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
        if(opc == 1){

            try{
                Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                s.beginTransaction();
                
                Cliente c = new Cliente();
                c.setIdCliente(Integer.parseInt(valores[0]));
                
                
                
                Agendamento a = new Agendamento();
                a.setStatus(1);
                a.setIdAgendamento(id);
                
                a.setCliente(c);
                
                a.setTransacao(1);
                
                if(Byte.parseByte(valores[5]) == 1){
                    Colaborador col = new Colaborador();
                    col.setIdColaborador(Integer.parseInt(valores[1]));
                    
                    Servico serv = new Servico();
                    serv.setIdServico(Integer.parseInt(valores[2]));
                    
                    a.setColaborador(col);
                    a.setServico(serv);
                    
                }else if(Byte.parseByte(valores[5]) == 2){
                    
                    ServicoTerceiro serv = new ServicoTerceiro();
                    serv.setIdServicoT(Integer.parseInt(valores[1]));
                    
                    a.setServicoTerceiro(serv);
                }
                
                a.setData(new Date(dataAgenda.getDate().getTime()));
                a.setHora(valores[4]);
                a.setOperacao(Byte.parseByte(valores[5]));
                a.setValorTotal(Double.valueOf(valores[6]));
                
                s.merge(a);

                s.getTransaction().commit();

                java.text.SimpleDateFormat formatoData = new java.text.SimpleDateFormat("yyyy-MM-dd");
                String data = formatoData.format(dataAgenda.getDate());
                carregaAgenda(data);

                txtColAgendado.setEnabled(false);
                    txtClienteAgendado.setEnabled(false);
                    txtDataAgendado.setEnabled(false);
                    txtHoraAgendado.setEnabled(false);
                    txtServicoAgendado.setEnabled(false);
                    btnCancelarAgendamento.setEnabled(false);
                    btnConcluirAgendamento.setEnabled(false);
                    btnConfirmarAgendamento.setEnabled(false);

                    
                    txtColAgendado.setText("");
                    txtClienteAgendado.setText("");
                    txtDataAgendado.setText("");
                    txtHoraAgendado.setText("");
                    txtServicoAgendado.setText("");
                    
                    lblStatus.setText("Selecione um Atendimento Cadastrado");
                    
                JOptionPane.showMessageDialog(IFrmAgendamento.this, "Atendimento Confirmado");
            }catch(Exception erro){erro.printStackTrace();}

        }else if(JOptionPane.showOptionDialog(IFrmAgendamento.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
            txtColAgendado.setEnabled(false);
                    txtClienteAgendado.setEnabled(false);
                    txtDataAgendado.setEnabled(false);
                    txtHoraAgendado.setEnabled(false);
                    txtServicoAgendado.setEnabled(false);
                    btnCancelarAgendamento.setEnabled(false);
                    btnConcluirAgendamento.setEnabled(false);
                    btnConfirmarAgendamento.setEnabled(false);

                    
                    txtColAgendado.setText("");
                    txtClienteAgendado.setText("");
                    txtDataAgendado.setText("");
                    txtHoraAgendado.setText("");
                    txtServicoAgendado.setText("");
                    
                    lblStatus.setText("Selecione um Atendimento Cadastrado");
       }
    
    }//GEN-LAST:event_btnConfirmarAgendamentoActionPerformed

    private void btnEscolhaCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscolhaCliActionPerformed
        carregaTabelaClientes(null, 0);
        frmClientes.setVisible(true);
        frmClientes.setLocationRelativeTo(null);
        frmClientes.toFront();
    }//GEN-LAST:event_btnEscolhaCliActionPerformed

    private void cbxServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxServicoActionPerformed
        if(rbServico.isSelected()){
            carregaTabelaColaboradoresHabilitados(pegarIdServico(cbxServico.getSelectedIndex()));
        }else if(rbServicoTerceiro.isSelected()){
            carregaTerceiro(pegarIdServico(cbxServico.getSelectedIndex()));
        }
    }//GEN-LAST:event_cbxServicoActionPerformed

    private void btnEscolhaColActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscolhaColActionPerformed
        frmColaboradores.setVisible(true);
        frmColaboradores.setLocationRelativeTo(null);
        frmColaboradores.toFront();
    }//GEN-LAST:event_btnEscolhaColActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        byte operacao = 0;
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        if(txtIdCliente.getText().equals("") || txtIdCol.getText().equals("")){

            JOptionPane.showMessageDialog(IFrmAgendamento.this, "Alguns Campos Obrigatórios Não foram Preenchidos", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        }else {
            int opc = JOptionPane.showOptionDialog(IFrmAgendamento.this,"Deseja o novo Atendimento: ", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
            if(opc == 1){
                int tempoServico = 0;
                
                if(rbServico.isSelected()){
                    tempoServico = pegarTempoServico(cbxServico.getSelectedIndex());
                }else if(rbServicoTerceiro.isSelected()){
                    tempoServico = pegarTempoServicoTerceiro(cbxServico.getSelectedIndex());
                }
                
                if(verificarDisponibilidade(txtHoraAgendamento.getText(), tempoServico)){

                    try{
                        Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                        s.beginTransaction();


                        Cliente c = new Cliente();
                        c.setIdCliente(Integer.parseInt(txtIdCliente.getText()));

                        Agendamento a = new Agendamento();

                        if(rbServico.isSelected()){
                            Servico serv = new Servico();
                            serv.setIdServico(pegarIdServico(cbxServico.getSelectedIndex()));

                            Colaborador col = new Colaborador();
                            col.setIdColaborador(Integer.parseInt(txtIdCol.getText()));

                            operacao = 1;

                            a.setCliente(c);
                            a.setHora(txtHoraAgendamento.getText());
                            a.setData(new Date(dataAgenda.getDate().getTime()));
                            a.setTransacao(1);
                            a.setStatus(0);
                            a.setOperacao(operacao);
                            a.setServico(serv);
                            a.setColaborador(col);
                            a.setValorTotal(pegarValorServico(cbxServico.getSelectedIndex()));

                        }else if(rbServicoTerceiro.isSelected()){
                            ServicoTerceiro serv = new ServicoTerceiro();
                            serv.setIdServicoT(pegarIdServicoTerceiro(cbxServico.getSelectedIndex()));

                            Terceiro t = new Terceiro();
                            t.setIdTerceiro(Integer.parseInt(txtIdCol.getText()));

                            operacao = 2;

                            a.setCliente(c);
                            a.setHora(txtHoraAgendamento.getText());
                            a.setData(new Date(dataAgenda.getDate().getTime()));
                            a.setTransacao(1);
                            a.setStatus(0);
                            a.setOperacao(operacao);
                            a.setServicoTerceiro(serv);
                            a.setValorTotal(pegarValorServicoTerceiro(cbxServico.getSelectedIndex()));
                        }

                        s.save(a);

                        s.getTransaction().commit();

                        java.text.SimpleDateFormat formatoData = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        String data = formatoData.format(dataAgenda.getDate());
                        carregaAgenda(data);

                        frmCadAgenda.setVisible(false);
                        frmCadAgenda.toBack();
                        rbServico.setSelected(true);
                        cbxServico.setSelectedIndex(0);
                        txtIdCliente.setText("");
                        txtIdCol.setText("");
                        txtCliente.setText("");
                        txtColaborador.setText("");
                        btnEscolhaCol.setEnabled(true);

                        JOptionPane.showMessageDialog(IFrmAgendamento.this, "Novo Atendimento Adicionado");
                    }catch(Exception erro){erro.printStackTrace();}
                    
                }else{
                    JOptionPane.showMessageDialog(IFrmAgendamento.this, "Sem Horario disponivel para esse serviço", "Atenção", JOptionPane.WARNING_MESSAGE, frameIcon);
                }

            }else if(JOptionPane.showOptionDialog(IFrmAgendamento.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
                frmCadAgenda.setVisible(false);
                frmCadAgenda.toBack();
                rbServico.setSelected(true);
                cbxServico.setSelectedIndex(0);
                txtIdCliente.setText("");
                txtIdCol.setText("");
                txtCliente.setText("");
                txtColaborador.setText("");
                btnEscolhaCol.setEnabled(true);
           }
        }
        
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void rbServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbServicoActionPerformed
        preencherServicos();
        txtIdCol.setEditable(true);
        txtColaborador.setEditable(true);
        btnEscolhaCol.setEnabled(true);
        txtIdCol.setText("");
        txtColaborador.setText("");
    }//GEN-LAST:event_rbServicoActionPerformed

    private void rbServicoTerceiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbServicoTerceiroActionPerformed
        preencherServicosTerceiros();
    }//GEN-LAST:event_rbServicoTerceiroActionPerformed

    private void btnConcluirAgendamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConcluirAgendamentoActionPerformed
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };
        String [] valores = new String[7];
        
        
        
        int id = pegarIdAgenda(txtDataAgendado.getText(), txtHoraAgendado.getText());
        
        valores = confirmarAtendimento(id);
        
        int opc = JOptionPane.showOptionDialog(IFrmAgendamento.this,"Deseja Concluir Este atendimento?: ", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
        if(opc == 1){

            try{
                Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                s.beginTransaction();

                Cliente c = new Cliente();
                c.setIdCliente(Integer.parseInt(valores[0]));
                
                
                
                Agendamento a = new Agendamento();
                a.setStatus(2);
                a.setIdAgendamento(id);
                
                a.setCliente(c);
                
                a.setTransacao(1);
                
                if(Byte.parseByte(valores[5]) == 1){
                    Colaborador col = new Colaborador();
                    col.setIdColaborador(Integer.parseInt(valores[1]));
                    
                    Servico serv = new Servico();
                    serv.setIdServico(Integer.parseInt(valores[2]));
                    
                    a.setColaborador(col);
                    a.setServico(serv);
                    
                }else if(Byte.parseByte(valores[5]) == 2){
                    
                    ServicoTerceiro serv = new ServicoTerceiro();
                    serv.setIdServicoT(Integer.parseInt(valores[1]));
                    
                    a.setServicoTerceiro(serv);
                }
                
                a.setData(new Date(dataAgenda.getDate().getTime()));
                a.setHora(valores[4]);
                a.setOperacao(Byte.parseByte(valores[5]));
                a.setValorTotal(Double.valueOf(valores[6]));
                
                s.merge(a);

                s.getTransaction().commit();

                java.text.SimpleDateFormat formatoData = new java.text.SimpleDateFormat("yyyy-MM-dd");
                String data = formatoData.format(dataAgenda.getDate());
                carregaAgenda(data);

                txtColAgendado.setEnabled(false);
                    txtClienteAgendado.setEnabled(false);
                    txtDataAgendado.setEnabled(false);
                    txtHoraAgendado.setEnabled(false);
                    txtServicoAgendado.setEnabled(false);
                    btnCancelarAgendamento.setEnabled(false);
                    btnConcluirAgendamento.setEnabled(false);
                    btnConfirmarAgendamento.setEnabled(false);

                    
                    txtColAgendado.setText("");
                    txtClienteAgendado.setText("");
                    txtDataAgendado.setText("");
                    txtHoraAgendado.setText("");
                    txtServicoAgendado.setText("");
                    
                    lblStatus.setText("Selecione um Atendimento Cadastrado");

                JOptionPane.showMessageDialog(IFrmAgendamento.this, "Atendimento Concluido");
            }catch(Exception erro){erro.printStackTrace();}

        }else if(JOptionPane.showOptionDialog(IFrmAgendamento.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
txtColAgendado.setEnabled(false);
                    txtClienteAgendado.setEnabled(false);
                    txtDataAgendado.setEnabled(false);
                    txtHoraAgendado.setEnabled(false);
                    txtServicoAgendado.setEnabled(false);
                    btnCancelarAgendamento.setEnabled(false);
                    btnConcluirAgendamento.setEnabled(false);
                    btnConfirmarAgendamento.setEnabled(false);

                    
                    txtColAgendado.setText("");
                    txtClienteAgendado.setText("");
                    txtDataAgendado.setText("");
                    txtHoraAgendado.setText("");
                    txtServicoAgendado.setText("");
                    
                    lblStatus.setText("Selecione um Atendimento Cadastrado");
       }
    }//GEN-LAST:event_btnConcluirAgendamentoActionPerformed

    private void btnCancelarAgendamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarAgendamentoActionPerformed
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };
        
        
        
        int id = pegarIdAgenda(txtDataAgendado.getText(), txtHoraAgendado.getText());
        
        
        int opc = JOptionPane.showOptionDialog(IFrmAgendamento.this,"Deseja Cancelar Este atendimento?: ", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
        if(opc == 1){

            try{
                byte b = 0;
                
                Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                s.beginTransaction();
                
                Pessoa p = new Pessoa();
                        
                Cliente c = new Cliente(p, 1, b);
                c.setIdCliente(1);

                Agendamento a = new Agendamento();
                a.setCliente(c);
                a.setIdAgendamento(id);
                
                s.delete(a);

                s.getTransaction().commit();

                java.text.SimpleDateFormat formatoData = new java.text.SimpleDateFormat("yyyy-MM-dd");
                String data = formatoData.format(dataAgenda.getDate());
                carregaAgenda(data);

                txtColAgendado.setEnabled(false);
                    txtClienteAgendado.setEnabled(false);
                    txtDataAgendado.setEnabled(false);
                    txtHoraAgendado.setEnabled(false);
                    txtServicoAgendado.setEnabled(false);
                    btnCancelarAgendamento.setEnabled(false);
                    btnConcluirAgendamento.setEnabled(false);
                    btnConfirmarAgendamento.setEnabled(false);

                    
                    txtColAgendado.setText("");
                    txtClienteAgendado.setText("");
                    txtDataAgendado.setText("");
                    txtHoraAgendado.setText("");
                    txtServicoAgendado.setText("");
                    
                    lblStatus.setText("Selecione um Atendimento Cadastrado");

                JOptionPane.showMessageDialog(IFrmAgendamento.this, "Atendimento Cancelado");
            }catch(Exception erro){erro.printStackTrace();}

        }else if(JOptionPane.showOptionDialog(IFrmAgendamento.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
            txtColAgendado.setEnabled(false);
                    txtClienteAgendado.setEnabled(false);
                    txtDataAgendado.setEnabled(false);
                    txtHoraAgendado.setEnabled(false);
                    txtServicoAgendado.setEnabled(false);
                    btnCancelarAgendamento.setEnabled(false);
                    btnConcluirAgendamento.setEnabled(false);
                    btnConfirmarAgendamento.setEnabled(false);

                    
                    txtColAgendado.setText("");
                    txtClienteAgendado.setText("");
                    txtDataAgendado.setText("");
                    txtHoraAgendado.setText("");
                    txtServicoAgendado.setText("");
                    
                    lblStatus.setText("Selecione um Atendimento Cadastrado");
       }
    }//GEN-LAST:event_btnCancelarAgendamentoActionPerformed

    private void dataAgendaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dataAgendaPropertyChange
        java.text.SimpleDateFormat formatoData = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String data = formatoData.format(dataAgenda.getDate());
        carregaAgenda(data);
    }//GEN-LAST:event_dataAgendaPropertyChange

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        int linha = tblCliente.getSelectedRow();
        
        txtIdCliente.setText(dtmCli.getValueAt(linha, 0).toString());
        txtIdCliente.setEditable(false);
        
        
        
        txtCliente.setText(dtmCli.getValueAt(linha, 1).toString());
        txtCliente.setEditable(false);
        
        frmFicha.setVisible(false);
        frmFicha.toBack();
        
        frmCadAgenda.toFront();
    }//GEN-LAST:event_btnFinalizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarAgendamento;
    private javax.swing.JButton btnConcluirAgendamento;
    private javax.swing.JButton btnConfirmarAgendamento;
    private javax.swing.JButton btnEscolhaCli;
    private javax.swing.JButton btnEscolhaCol;
    private javax.swing.JButton btnEscolheCli;
    private javax.swing.JButton btnEscolheCol;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbxServico;
    private com.toedter.calendar.JCalendar dataAgenda;
    private javax.swing.JFrame frmCadAgenda;
    private javax.swing.JFrame frmClientes;
    private javax.swing.JFrame frmColaboradores;
    private javax.swing.JFrame frmFicha;
    private javax.swing.ButtonGroup gpBusca;
    private javax.swing.ButtonGroup gpServicos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel pnAtendimento;
    private javax.swing.JPanel pnCadastro;
    private javax.swing.JRadioButton rbServico;
    private javax.swing.JRadioButton rbServicoTerceiro;
    private javax.swing.JTable tblAgendamento;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTable tblColaborador;
    private javax.swing.JTabbedPane tpnCadastrar;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtClienteAgendado;
    private javax.swing.JTextField txtColAgendado;
    private javax.swing.JTextField txtColaborador;
    private javax.swing.JTextField txtDataAgendado;
    private javax.swing.JTextField txtDataAgendamento;
    private javax.swing.JTextField txtHoraAgendado;
    private javax.swing.JTextField txtHoraAgendamento;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdCol;
    private javax.swing.JTextField txtServicoAgendado;
    // End of variables declaration//GEN-END:variables
}
