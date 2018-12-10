package view;

import controller.Cidades;
import controller.Cliente;
import controller.Colaborador;
import controller.Endereco;
import controller.Estado;
import java.awt.Dimension;

import org.hibernate.Session;
import org.hibernate.Query;

import dao.DAO_PROJETO;
import controller.PedidoCompra;
import controller.Fornecedor;
import controller.Servico;
import java.awt.event.ActionEvent;
import static java.lang.Double.parseDouble;
import java.sql.Date;

import java.text.ParseException;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import controller.Relatorios;
/**
 *
 * @author lgustavo
 */


public class IFrmRelatorioAgendamento extends javax.swing.JInternalFrame {
    
    Relatorios relatorio;
    
    private void carregaCbxClientes(){
        String sql = "from Cliente as c order by c.pessoa.nome";
        
        cbxSeleciona.removeAllItems();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Cliente c;
            
            while(i.hasNext()){
                c = (Cliente)i.next();
                cbxSeleciona.addItem(c.getPessoa().getNome());
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
    }
    
    private void carregaCbxColaboradores(){
        String sql = "from Colaborador as c order by c.pessoa.nome";
        
        cbxSeleciona.removeAllItems();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Colaborador c;
            
            while(i.hasNext()){
                c = (Colaborador)i.next();
                cbxSeleciona.addItem(c.getPessoa().getNome());
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
    }
    
    private void carregaCbxServico(){
        String sql = "from Servico as s order by s.idServico";
        
        cbxSeleciona.removeAllItems();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Servico se;
            
            while(i.hasNext()){
                se = (Servico)i.next();
                cbxSeleciona.addItem(se.getNomeServico());
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
    }
    
    private int pegarIdPorLinhaCliente(int linha){
        String sql = "from Cliente as c order by c.pessoa.nome ";
        int aux = 0;
        int id = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Cliente c;
            
            while(i.hasNext()){
                c = (Cliente)i.next();
                if(aux == linha){
                    id = c.getIdCliente();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id; //Retorna um array com os ids de endereço, pessoa e PedidoCompra
    }
    
    private int pegarIdPorLinhaColaborador(int linha){
        String sql = "from Colaborador as c order by c.pessoa.nome ";
        int aux = 0;
        int id = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Colaborador c;
            
            while(i.hasNext()){
                c = (Colaborador)i.next();
                if(aux == linha){
                    id = c.getIdColaborador();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id; //Retorna um array com os ids de endereço, pessoa e PedidoCompra
    }
    
    private int pegarIdPorLinhaServicos(int linha){
        String sql = "from Servico as s order by s.idServico ";
        int aux = 0;
        int id = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Servico se;
            
            while(i.hasNext()){
                se = (Servico)i.next();
                if(aux == linha){
                    id = se.getIdServico();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id; //Retorna um array com os ids de endereço, pessoa e PedidoCompra
    }
    
    public IFrmRelatorioAgendamento() {
        initComponents();
        relatorio = new Relatorios();
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

        tpnCadastrar = new javax.swing.JTabbedPane();
        pnCadastro = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnGerar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cbxSeleciona = new javax.swing.JComboBox<>();
        mes = new com.toedter.calendar.JMonthChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxEscolha = new javax.swing.JComboBox<>();

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setClosable(true);
        setIconifiable(true);
        setTitle("Pedido de Compra");
        setMaximumSize(new java.awt.Dimension(482, 510));
        setMinimumSize(new java.awt.Dimension(482, 510));

        tpnCadastrar.setBackground(new java.awt.Color(153, 255, 153));

        pnCadastro.setBackground(new java.awt.Color(204, 255, 204));
        pnCadastro.setMaximumSize(new java.awt.Dimension(471, 455));
        pnCadastro.setMinimumSize(new java.awt.Dimension(471, 455));

        jPanel1.setBackground(new java.awt.Color(102, 255, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Gerar Relatório", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        btnGerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5105_table_48.png"))); // NOI18N
        btnGerar.setText("Gerar Relatório");
        btnGerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarActionPerformed(evt);
            }
        });

        jLabel5.setText("Selecione:");

        cbxSeleciona.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Clientes", "Colaboradores", "Serviços", "Terceiros" }));

        jLabel6.setText("Relatório do Mês de:");

        jLabel2.setText("Gerar Relaório Por:");

        cbxEscolha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Clientes", "Colaboradores", "Serviços" }));
        cbxEscolha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEscolhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(70, 70, 70)
                        .addComponent(mes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2))
                        .addGap(80, 80, 80)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxEscolha, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxSeleciona, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(btnGerar, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxEscolha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(cbxSeleciona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(btnGerar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout pnCadastroLayout = new javax.swing.GroupLayout(pnCadastro);
        pnCadastro.setLayout(pnCadastroLayout);
        pnCadastroLayout.setHorizontalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pnCadastroLayout.setVerticalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 221, Short.MAX_VALUE))
        );

        tpnCadastrar.addTab("Cadastrar", pnCadastro);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setBounds(200, 100, 482, 355);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarActionPerformed
        if(cbxEscolha.getSelectedItem().toString().equals("Clientes")){
            relatorio.RelatorioAtendimentoCliente(mes.getMonth()+1, pegarIdPorLinhaCliente(cbxSeleciona.getSelectedIndex()));
        }else if(cbxEscolha.getSelectedItem().toString().equals("Colaboradores")){
            relatorio.RelatorioAtendimentoColaborador(mes.getMonth()+1, pegarIdPorLinhaColaborador(cbxSeleciona.getSelectedIndex()));
        }else if(cbxEscolha.getSelectedItem().toString().equals("Serviços")){
            relatorio.RelatorioAtendimentoServico(mes.getMonth()+1, pegarIdPorLinhaServicos(cbxSeleciona.getSelectedIndex()));
        }
    }//GEN-LAST:event_btnGerarActionPerformed

    private void cbxEscolhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEscolhaActionPerformed
        if(cbxEscolha.getSelectedItem().toString().equals("Clientes")){
            carregaCbxClientes();
        }else if(cbxEscolha.getSelectedItem().toString().equals("Colaboradores")){
            carregaCbxColaboradores();
        }else if(cbxEscolha.getSelectedItem().toString().equals("Serviços")){
            carregaCbxServico();
        }
    }//GEN-LAST:event_cbxEscolhaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGerar;
    private javax.swing.JComboBox<String> cbxEscolha;
    private javax.swing.JComboBox<String> cbxSeleciona;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private com.toedter.calendar.JMonthChooser mes;
    private javax.swing.JPanel pnCadastro;
    private javax.swing.JTabbedPane tpnCadastrar;
    // End of variables declaration//GEN-END:variables
}
