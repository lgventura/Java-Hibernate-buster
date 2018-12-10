package view;

import controller.Colaborador;
import controller.Estoque;
import controller.Produto;
import java.awt.Dimension;

import org.hibernate.Session;
import org.hibernate.Query;

import dao.DAO_PROJETO;
import controller.Servico;
import controller.TblAssocProdServ;
import controller.TblAssosiativaServicos;
import controller.TipoCabelo;
import controller.TipoServico;
import java.awt.event.ActionEvent;

import java.text.ParseException;
import java.sql.Date;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author lgustavo
 */
public class IFrmServicos extends javax.swing.JInternalFrame {
    
    private DefaultListModel dlm ; //Usado p/Criação da lista com os dados vindo do BD
    private DefaultTableModel dtm; //Usado p/ Criação da Tabela com os dados vindo do BD
    private DefaultTableModel dtmE;
    private DefaultTableModel dtmP;
    javax.swing.JCheckBox chColaboradores[] = new JCheckBox[contarColaboradores()];;
    
    private int operacao = 0;
    
    private void limparCampos(){//Limpa todos os campos após o cadastro

        txtBuscaServ.setText("");
        txtServico.setText("");
        cbxTipoServ.setSelectedIndex(0);
        cbxTempo.setSelectedIndex(0);
        
        spnValorServ.setValue(0);
        spnValorProd.setValue(0);
        spnValorTotal.setValue(0);
        chbUsaProduto.setSelected(false);
        
        txtServico.requestFocus();
    }
    
    
    private void habilitarCampos(){
        txtServico.setEnabled(true);
        cbxTipoServ.setEnabled(true);
        cbxTempo.setEnabled(true);
        
        btnAddTipo.setEnabled(true);
        btnSeleciona.setEnabled(true);
        btnSelProduto.setEnabled(true);
        btnVisualizar.setEnabled(true);
        chbUsaProduto.setEnabled(true);
        spnValorServ.setEnabled(true);
        spnValorProd.setEnabled(true);
        spnValorTotal.setEnabled(true);
        
    }
    
    private void desabilitarCampos(){
        txtServico.setEnabled(false);
        cbxTipoServ.setEnabled(false);
        cbxTempo.setEnabled(false);
        
        btnAddTipo.setEnabled(false);
        btnSelProduto.setEnabled(false);
        btnVisualizar.setEnabled(false);
        
        chbUsaProduto.setEnabled(false);
        spnValorServ.setEnabled(false);
        spnValorProd.setEnabled(false);
        spnValorTotal.setEnabled(false);
    }
    
    private int contarColaboradores(){
        String sql = "from Colaborador";
        int aux = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Colaborador c;
            
            while(i.hasNext()){
                c = (Colaborador)i.next();
                aux++;
                
            }
            
            s.getTransaction().commit();
            
         
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return aux;
    }
    
    private void criarFrmColaboradores(){
        String sql = "from Colaborador as c order by c.idColaborador";
        int aux = 0, x = 10, y = 60, linha = 1;
        
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Colaborador c;
            
            while(i.hasNext()){
                c = (Colaborador)i.next();
                
                chColaboradores[aux].setText(c.getPessoa().getNome());
                chColaboradores[aux].setBounds(x, y, 150, 15);
                chColaboradores[aux].setSelected(false);
                
                if(linha == 3){
                    y += 30;
                    x = 10;
                    linha = 1;
                }else {
                    linha++;
                    x += 250;
                }
                
                frmColaboradores.add(chColaboradores[aux]);
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void preencheFrmColaboradores(int id){
        String sql = "from TblAssosiativaServicos as tbl where tbl.servico.idServico = "+id;
        int cont = contarColaboradores();
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            TblAssosiativaServicos tbl;
            
            while(i.hasNext()){
                tbl = (TblAssosiativaServicos)i.next();
                
                for(int aux = 0; aux < cont ; aux++){
                    
                    if(tbl.getColaborador().getPessoa().getNome().equals(chColaboradores[aux].getText()) && tbl.getServico().getIdServico() == id){
                        chColaboradores[aux].setSelected(true);
                    }
                }
                
                
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private int pegarIdColaborador(int linha){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from Colaborador as c order by c.idColaborador ";
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
        
        return id;
    }
    
    private void preencherTabelaProdutos(int idServico){
        String sql = "from TblAssocProdServ as tbl where tbl.servico.idServico ="+idServico;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtmP.setNumRows(0);
            
            TblAssocProdServ tbl;
            
            while(i.hasNext()){
                tbl = (TblAssocProdServ)i.next();
                dtmP.addRow(tbl.getTblAssocProdServ());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        double valorProdutos = 0;
        
        for(int aux = 0; aux < dtmP.getRowCount(); aux++){
            valorProdutos += Double.valueOf(dtmP.getValueAt(aux, 3).toString());
        }
        
        spnValorProd.setValue(valorProdutos);
    }
    
    
    private void preencherCampos(int linha, String valor){
        String sql = "from Servico order by nomeServico";
        int aux = 0;
        
        if(valor != null){
            sql = " from Servico where like '%"+valor+"%' order by nomeServico";
        }
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Servico serv;
            
            while(i.hasNext()){
                serv = (Servico)i.next();
                if(aux == linha){
                    txtServico.setText(serv.getNomeServico());
                    cbxTipoServ.removeAllItems();
                    cbxTipoServ.addItem(serv.getTipoServico().getNomeTipo());
                    
                    if(serv.getTempoExec() == 1){
                        cbxTempo.setSelectedIndex(0);
                    }else if(serv.getTempoExec() == 2){
                        cbxTempo.setSelectedIndex(1);
                    }else if(serv.getTempoExec() == 3){
                        cbxTempo.setSelectedIndex(2);
                    }else if(serv.getTempoExec() == 4){
                        cbxTempo.setSelectedIndex(3);
                    }
                    
                    spnValorServ.setValue(serv.getValorServ());
                    spnValorTotal.setValue(serv.getValorTotal());
                    
                    if(serv.getSemProduto() == 1){
                        chbUsaProduto.setSelected(true);
                    }
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private int pegarIdPorLinha(int linha, String valor){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from Servico order by nomeServico ";
        int aux = 0;
        int id = 0;
        
        if(valor != null ){
            sql = " from Servico where nome like '%"+valor+"%' order by nomeServico";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Servico serv;
            
            while(i.hasNext()){
                serv = (Servico)i.next();
                if(aux == linha){
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
    
    private int pegarIdTipoServico(int index){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from TipoServico order by idTipo ";
        int aux = 0;
        int id = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            TipoServico t;
            
            while(i.hasNext()){
                t = (TipoServico)i.next();
                if(aux == index){
                    id = t.getIdTipo();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id;
    }
    
    private void salvar(Object o){
        Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        
        s.save(o);
        
        s.getTransaction().commit();
    }
    
    private void alterar(Object o){
        Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        
        s.merge(o);
        
        s.getTransaction().commit();
    }
    
    private void preencherTipoServico(){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from TipoServico order by idTipo ";
        
        cbxTipoServ.removeAllItems();
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            TipoServico t;
            
            while(i.hasNext()){
                t = (TipoServico)i.next();
                
                cbxTipoServ.addItem(t.getNomeTipo());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    
    private void habilitarBotao(JButton btn){
        btn.setEnabled(true);
    }
    
    private void desabilitarBotao(JButton btn){
        btn.setEnabled(false);
    }
    
    private void carregaTabelaServicos(String valor){//carrega a tabela de Servicos ordenando pelo nome
        String sql = "from Servico order by nomeServico ";
        
        if(valor != null){
            sql = " from Servico where nome like '%"+valor+"%' order by nomeServico";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtm.setNumRows(0);
            
            Servico serv;
            
            while(i.hasNext()){
                serv = (Servico)i.next();
                dtm.addRow(serv.getServico());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void carregaTabelaEstoque(String valor){//carrega a tabela de Servicos ordenando pelo nome
        String sql = "from Estoque as e order by e.produto.nome ";
        int testeId = 0;
        
        if(valor != null){
            sql = " from Estoque as e where e.produto.nome like '%"+valor+"%' order by e.produto.nome";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtmE.setNumRows(0);
            
            Estoque e;
            
            while(i.hasNext()){
                e = (Estoque)i.next();
                
                for(int aux = 0; aux < dtmP.getRowCount(); aux++){

                    if(Integer.valueOf(dtmP.getValueAt(aux, 0).toString()) == e.getIdEstoque()){
                        testeId = 1;
                    }else if(testeId != 1){
                        testeId = 0;
                    }
                }
                
                if(testeId == 0){
                    dtmE.addRow(e.getEstoqueComId());
                }
                testeId = 0;
                
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
    }
    
    private void preencheTabelaProd(String[] campos){
        dtmP.addRow(campos);
    }
    
    private void carregarListaNomes(String nome){
       
        String sql = "from Servico order by nomeServico";
        
        if(nome != null){
            sql = " from Servico where nomeServico like '%"+nome+"%'";
        }
        
        dlm.removeAllElements();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Servico serv;
            
            while(i.hasNext()){
                serv = (Servico)i.next();
                dlm.add(dlm.getSize(), serv.getNomeServico());
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
    }
    
    public IFrmServicos() {
        initComponents();
        
        listServicos.setModel(new DefaultListModel());
        dlm = (DefaultListModel) listServicos.getModel();
        dtm = (DefaultTableModel)tblServicos.getModel();
        dtmE = (DefaultTableModel)tblEstoque.getModel();
        dtmP = (DefaultTableModel)tblPedido.getModel();
        dtmP.setNumRows(0);
        carregarListaNomes(null);
        carregaTabelaServicos(null);
        carregaTabelaEstoque(null);
        btnSalvar.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnDesbloquear.setEnabled(false);
        //btnSeleciona.setEnabled(false);
        btnAddTipo.setEnabled(false);
        
        preencherTipoServico();
        
        limparCampos();
        desabilitarCampos();
        
        for(int aux=0; aux < contarColaboradores(); aux++){
            chColaboradores[aux] = new JCheckBox();
        }
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

        frmTipoServ = new javax.swing.JFrame();
        jLabel21 = new javax.swing.JLabel();
        btnCadTipo = new javax.swing.JButton();
        txtTipoServ = new javax.swing.JTextField();
        frmProdutos = new javax.swing.JFrame();
        btnSeleciona = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPedido = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        btnFinalizar = new javax.swing.JButton();
        frmEstoque = new javax.swing.JFrame();
        btnEscolhe = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblEstoque = new javax.swing.JTable();
        frmColaboradores = new javax.swing.JFrame();
        jLabel24 = new javax.swing.JLabel();
        btnFinalizarCadastro = new javax.swing.JButton();
        frmPropProdutos = new javax.swing.JFrame();
        btnConfirmarProdutos = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtIdDoEstoque = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNomeDoEstoque = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtUnidadeDoEstoque = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtValorGasto = new javax.swing.JTextField();
        spnQuantidadeUtilizada = new javax.swing.JSpinner();
        lblUnidade = new javax.swing.JLabel();
        tpnCadastrar = new javax.swing.JTabbedPane();
        pnCadastro = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtServico = new javax.swing.JTextField();
        scrolLista = new javax.swing.JScrollPane();
        listServicos = new javax.swing.JList<>();
        txtBuscaServ = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        spnValorServ = new javax.swing.JSpinner();
        spnValorProd = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        cbxTempo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cbxTipoServ = new javax.swing.JComboBox<>();
        btnAddTipo = new javax.swing.JButton();
        spnValorTotal = new javax.swing.JSpinner();
        snpValorTotal = new javax.swing.JLabel();
        btnSelProduto = new javax.swing.JButton();
        chbUsaProduto = new javax.swing.JCheckBox();
        btnVisualizar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnDesbloquear = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        pnExibir = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblServicos = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        btnAltera = new javax.swing.JButton();
        btnDeleta = new javax.swing.JButton();
        btnBusca = new javax.swing.JButton();
        txtBServico = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();

        frmTipoServ.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmTipoServ.setTitle("Tipo de Serviço");
        frmTipoServ.setMinimumSize(new java.awt.Dimension(366, 153));

        jLabel21.setText("Cadastrar Novo Tipo de Serviço:");

        btnCadTipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/accept.png"))); // NOI18N
        btnCadTipo.setText("Adicionar");
        btnCadTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadTipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmTipoServLayout = new javax.swing.GroupLayout(frmTipoServ.getContentPane());
        frmTipoServ.getContentPane().setLayout(frmTipoServLayout);
        frmTipoServLayout.setHorizontalGroup(
            frmTipoServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmTipoServLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(frmTipoServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addGroup(frmTipoServLayout.createSequentialGroup()
                        .addComponent(txtTipoServ, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCadTipo)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        frmTipoServLayout.setVerticalGroup(
            frmTipoServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmTipoServLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmTipoServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipoServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadTipo))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        frmProdutos.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmProdutos.setTitle("Produtos Adicionados");
        frmProdutos.setMinimumSize(new java.awt.Dimension(850, 450));

        btnSeleciona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_upload.png"))); // NOI18N
        btnSeleciona.setText("Selecionar mais Produtos");
        btnSeleciona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaActionPerformed(evt);
            }
        });

        tblPedido.setBackground(new java.awt.Color(0, 153, 153));
        tblPedido.setForeground(new java.awt.Color(255, 255, 255));
        tblPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Estoque", "Produto", "Quantidade Utilizada", "Total Gasto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPedido.setMaximumSize(new java.awt.Dimension(366, 64));
        tblPedido.setMinimumSize(new java.awt.Dimension(366, 64));
        jScrollPane2.setViewportView(tblPedido);
        if (tblPedido.getColumnModel().getColumnCount() > 0) {
            tblPedido.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        jLabel10.setText("Produtos Selecionados:");

        btnFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5746_Knob_Valid_Blue.png"))); // NOI18N
        btnFinalizar.setText("Finalizar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmProdutosLayout = new javax.swing.GroupLayout(frmProdutos.getContentPane());
        frmProdutos.getContentPane().setLayout(frmProdutosLayout);
        frmProdutosLayout.setHorizontalGroup(
            frmProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmProdutosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmProdutosLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmProdutosLayout.createSequentialGroup()
                        .addGroup(frmProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(frmProdutosLayout.createSequentialGroup()
                                .addComponent(btnSeleciona)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnFinalizar))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE))
                        .addGap(21, 21, 21))))
        );
        frmProdutosLayout.setVerticalGroup(
            frmProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmProdutosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFinalizar)
                    .addComponent(btnSeleciona))
                .addContainerGap())
        );

        frmEstoque.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmEstoque.setTitle("Produtos em Estoque");
        frmEstoque.setMinimumSize(new java.awt.Dimension(800, 450));

        btnEscolhe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_upload.png"))); // NOI18N
        btnEscolhe.setText("Selecionar Produto");
        btnEscolhe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscolheActionPerformed(evt);
            }
        });

        jLabel11.setText("Estoque");

        tblEstoque.setBackground(new java.awt.Color(0, 153, 153));
        tblEstoque.setForeground(new java.awt.Color(255, 255, 255));
        tblEstoque.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome do Produto", "Unidade de Medida", "Quantidade", "Quantidade por Medida", "Valor Unitário"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblEstoque);
        if (tblEstoque.getColumnModel().getColumnCount() > 0) {
            tblEstoque.getColumnModel().getColumn(0).setResizable(false);
            tblEstoque.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblEstoque.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblEstoque.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblEstoque.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblEstoque.getColumnModel().getColumn(4).setResizable(false);
            tblEstoque.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblEstoque.getColumnModel().getColumn(5).setPreferredWidth(60);
        }

        javax.swing.GroupLayout frmEstoqueLayout = new javax.swing.GroupLayout(frmEstoque.getContentPane());
        frmEstoque.getContentPane().setLayout(frmEstoqueLayout);
        frmEstoqueLayout.setHorizontalGroup(
            frmEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmEstoqueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(frmEstoqueLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(503, 503, 503)
                        .addComponent(btnEscolhe)))
                .addGap(0, 7, Short.MAX_VALUE))
        );
        frmEstoqueLayout.setVerticalGroup(
            frmEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmEstoqueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEscolhe)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        frmColaboradores.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmColaboradores.setTitle("Selecionar Colaboradores Habilitados para Realizar Este Serviço");
        frmColaboradores.setMinimumSize(new java.awt.Dimension(650, 450));

        jLabel24.setText("Selecione os Colaboradores  Habilitados:");

        btnFinalizarCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5745_Knob_Valid_Green.png"))); // NOI18N
        btnFinalizarCadastro.setText("Finalizar Cadastro");
        btnFinalizarCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarCadastroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmColaboradoresLayout = new javax.swing.GroupLayout(frmColaboradores.getContentPane());
        frmColaboradores.getContentPane().setLayout(frmColaboradoresLayout);
        frmColaboradoresLayout.setHorizontalGroup(
            frmColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmColaboradoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(btnFinalizarCadastro)
                .addContainerGap())
        );
        frmColaboradoresLayout.setVerticalGroup(
            frmColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmColaboradoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(btnFinalizarCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(421, Short.MAX_VALUE))
        );

        frmPropProdutos.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmPropProdutos.setTitle("Propriedades do produto Escolhido");
        frmPropProdutos.setMinimumSize(new java.awt.Dimension(376, 349));

        btnConfirmarProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5745_Knob_Valid_Green.png"))); // NOI18N
        btnConfirmarProdutos.setText("Confirmar");
        btnConfirmarProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarProdutosActionPerformed(evt);
            }
        });

        jLabel12.setText("Propriedades do Produto:");

        jLabel1.setText("ID:");

        jLabel2.setText("Nome do Produto:");

        jLabel4.setText("Unidade de Medida:");

        jLabel5.setText("Quantidade Utilizada:");

        jLabel9.setText("Valor Gasto");

        txtValorGasto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorGastoFocusGained(evt);
            }
        });

        spnQuantidadeUtilizada.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 100.0d));

        lblUnidade.setText("ml");

        javax.swing.GroupLayout frmPropProdutosLayout = new javax.swing.GroupLayout(frmPropProdutos.getContentPane());
        frmPropProdutos.getContentPane().setLayout(frmPropProdutosLayout);
        frmPropProdutosLayout.setHorizontalGroup(
            frmPropProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmPropProdutosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmPropProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmPropProdutosLayout.createSequentialGroup()
                        .addGroup(frmPropProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(frmPropProdutosLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdDoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUnidadeDoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12)
                            .addComponent(btnConfirmarProdutos))
                        .addContainerGap(48, Short.MAX_VALUE))
                    .addGroup(frmPropProdutosLayout.createSequentialGroup()
                        .addGroup(frmPropProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(frmPropProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNomeDoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(frmPropProdutosLayout.createSequentialGroup()
                                .addComponent(spnQuantidadeUtilizada, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblUnidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(46, 46, 46))))
        );
        frmPropProdutosLayout.setVerticalGroup(
            frmPropProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmPropProdutosLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel12)
                .addGap(28, 28, 28)
                .addGroup(frmPropProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdDoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(txtUnidadeDoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(frmPropProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNomeDoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(frmPropProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(spnQuantidadeUtilizada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUnidade))
                .addGap(26, 26, 26)
                .addGroup(frmPropProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addComponent(btnConfirmarProdutos)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setClosable(true);
        setIconifiable(true);
        setTitle("Serviços");
        setMaximumSize(new java.awt.Dimension(941, 562));
        setMinimumSize(new java.awt.Dimension(941, 562));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnCadastro.setBackground(new java.awt.Color(184, 168, 214));
        pnCadastro.setMaximumSize(new java.awt.Dimension(928, 502));
        pnCadastro.setMinimumSize(new java.awt.Dimension(928, 502));

        jLabel3.setText("Serviço:");

        listServicos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listServicos.setMinimumSize(new java.awt.Dimension(45, 80));
        listServicos.setPreferredSize(new java.awt.Dimension(45, 83));
        listServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listServicosMouseClicked(evt);
            }
        });
        listServicos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                listServicosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                listServicosKeyReleased(evt);
            }
        });
        scrolLista.setViewportView(listServicos);

        txtBuscaServ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscaServKeyReleased(evt);
            }
        });

        jLabel16.setText("Buscar:");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5750_Knob_Cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar Operação");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel19.setText("Valor do Serviço:");

        spnValorServ.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 0.1d));
        spnValorServ.setToolTipText("");

        spnValorProd.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 0.1d));
        spnValorProd.setToolTipText("");

        jLabel20.setText("Valor Gasto com Produtos:");

        jLabel22.setText("Tempo de execução:");

        cbxTempo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00:30", "01:00", "01:30", "02:00" }));
        cbxTempo.setToolTipText("");

        jLabel6.setText("Tipo de Serviço:");

        cbxTipoServ.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxTipoServ.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbxTipoServFocusGained(evt);
            }
        });

        btnAddTipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        btnAddTipo.setToolTipText("Adicionar Novo tipo de Serviço");
        btnAddTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTipoActionPerformed(evt);
            }
        });

        spnValorTotal.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 0.1d));
        spnValorTotal.setToolTipText("");

        snpValorTotal.setText("Valor total do Serviço:");

        btnSelProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bt_up.png"))); // NOI18N
        btnSelProduto.setText("Selecionar Produto(s)");
        btnSelProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelProdutoActionPerformed(evt);
            }
        });

        chbUsaProduto.setText("Serviço não utiliza Produtos?");
        chbUsaProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbUsaProdutoActionPerformed(evt);
            }
        });

        btnVisualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_search.png"))); // NOI18N
        btnVisualizar.setText("Visualizar Produtos");
        btnVisualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(166, 80, 189));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5113_add_48.png"))); // NOI18N
        btnNovo.setToolTipText("Adicionar Novo Serviço");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnDesbloquear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5051_lock_open_48.png"))); // NOI18N
        btnDesbloquear.setToolTipText("Desbloquear Campos");
        btnDesbloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesbloquearActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/file_edit.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5117_floppy_disk_48.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSalvar)
                .addGap(18, 18, 18)
                .addComponent(btnAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDesbloquear)
                .addGap(6, 6, 6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDesbloquear, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnCadastroLayout = new javax.swing.GroupLayout(pnCadastro);
        pnCadastro.setLayout(pnCadastroLayout);
        pnCadastroLayout.setHorizontalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscaServ))
                    .addComponent(scrolLista, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(spnValorProd, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(snpValorTotal)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(spnValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addComponent(chbUsaProduto)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnSelProduto))
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnCadastroLayout.createSequentialGroup()
                                                .addComponent(jLabel19)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(spnValorServ, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnCadastroLayout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtServico, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(30, 30, 30)
                                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCadastroLayout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cbxTipoServ, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnAddTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCadastroLayout.createSequentialGroup()
                                                .addComponent(jLabel22)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cbxTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(0, 75, Short.MAX_VALUE))
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addComponent(btnVisualizar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancelar)))))
                .addContainerGap())
        );
        pnCadastroLayout.setVerticalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(txtServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnAddTipo)
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(cbxTipoServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(cbxTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(spnValorServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chbUsaProduto)
                            .addComponent(btnSelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(spnValorProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(snpValorTotal)
                            .addComponent(spnValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnVisualizar)
                            .addComponent(btnCancelar)))
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscaServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addComponent(scrolLista, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(62, 62, 62))
        );

        tpnCadastrar.addTab("Cadastrar", pnCadastro);

        pnExibir.setBackground(new java.awt.Color(0, 204, 204));
        pnExibir.setForeground(new java.awt.Color(255, 255, 255));

        tblServicos.setBackground(new java.awt.Color(0, 153, 153));
        tblServicos.setForeground(new java.awt.Color(255, 255, 255));
        tblServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Serviço", "Tipo de Serviço", "Tempo de Execução", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblServicos);
        if (tblServicos.getColumnModel().getColumnCount() > 0) {
            tblServicos.getColumnModel().getColumn(0).setPreferredWidth(110);
            tblServicos.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblServicos.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblServicos.getColumnModel().getColumn(3).setPreferredWidth(40);
        }

        jLabel7.setText("Serviços Cadastrados");

        btnAltera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_edit.png"))); // NOI18N
        btnAltera.setText("Alterar Serviços");
        btnAltera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlteraActionPerformed(evt);
            }
        });

        btnDeleta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_delete.png"))); // NOI18N
        btnDeleta.setText("Excluir Serviços");
        btnDeleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletaActionPerformed(evt);
            }
        });

        btnBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5739_Knob_Search.png"))); // NOI18N
        btnBusca.setText("Buscar");
        btnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaActionPerformed(evt);
            }
        });

        txtBServico.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtBServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBServicoActionPerformed(evt);
            }
        });

        jLabel17.setText("Buscar Serviços:");

        javax.swing.GroupLayout pnExibirLayout = new javax.swing.GroupLayout(pnExibir);
        pnExibir.setLayout(pnExibirLayout);
        pnExibirLayout.setHorizontalGroup(
            pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnExibirLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(pnExibirLayout.createSequentialGroup()
                        .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnExibirLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(210, 210, 210)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBServico, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnExibirLayout.createSequentialGroup()
                                .addComponent(btnAltera)
                                .addGap(35, 35, 35)
                                .addComponent(btnDeleta)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(btnBusca)))
                .addContainerGap())
        );
        pnExibirLayout.setVerticalGroup(
            pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnExibirLayout.createSequentialGroup()
                .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBusca)
                        .addComponent(jLabel17))
                    .addGroup(pnExibirLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAltera)
                    .addComponent(btnDeleta))
                .addGap(18, 18, 18))
        );

        tpnCadastrar.addTab("Exibir", pnExibir);

        getContentPane().add(tpnCadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 5, 880, -1));

        setBounds(200, 100, 888, 550);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadTipoActionPerformed
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        if(txtTipoServ.getText().equals("")){
            
            JOptionPane.showMessageDialog(IFrmServicos.this, "Preencha o nome do Tipo de Serviço", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        }else {
            int opc = JOptionPane.showOptionDialog(IFrmServicos.this,"Deseja Salvar Tipo de Serviço?: "+txtTipoServ.getText(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
            
            if(opc == 1){
                
                try{
                    Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                    s.beginTransaction();
                    
                    TipoServico t = new TipoServico();
                    t.setNomeTipo(txtTipoServ.getText());
                    
                    s.save(t);

                    s.getTransaction().commit();

                }catch(Exception erro){ erro.printStackTrace();}
                
                txtTipoServ.setText("");
                frmTipoServ.setVisible(false);
                frmTipoServ.toBack();
                preencherTipoServico();
                
                JOptionPane.showMessageDialog(IFrmServicos.this, "Tipo de Serviço Salvo");

            }else if(JOptionPane.showOptionDialog(IFrmServicos.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
                txtTipoServ.setText("");
                frmTipoServ.setVisible(false);
                frmTipoServ.toBack();
            }
        }
    }//GEN-LAST:event_btnCadTipoActionPerformed

    private void btnSelecionaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaActionPerformed
        carregaTabelaEstoque(null);
        frmEstoque.setVisible(true);
        frmEstoque.setLocationRelativeTo(null);
        frmEstoque.toFront();
    }//GEN-LAST:event_btnSelecionaActionPerformed

    private void txtBServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBServicoActionPerformed
        
    }//GEN-LAST:event_txtBServicoActionPerformed

    private void btnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaActionPerformed
        if(!txtBuscaServ.getText().equals("")){
            carregaTabelaServicos(txtBServico.getText());
        }else{
            carregaTabelaServicos(null);
        }
    }//GEN-LAST:event_btnBuscaActionPerformed

    private void btnDeletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletaActionPerformed
        int linha = tblServicos.getSelectedRow();
        int id = 0;

        if(!txtBServico.getText().equals("")){
            id = pegarIdPorLinha(linha,txtBServico.getText());
        }else{
            id = pegarIdPorLinha(linha,null);
        }

        Object[] op = { "Cancelar", "Confirmar" };
        if(linha >= 0){
            int opc = JOptionPane.showOptionDialog(IFrmServicos.this,"Deseja Confirmar a Exclusção do Servico: "+dtm.getValueAt(linha, 0).toString(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, op, op[1]);

            if(opc == 1){

                TipoServico tp = new TipoServico();
                tp.setIdTipo(1);

                Servico serv = new Servico(tp, "", 0);
                serv.setIdServico(id);
                
                TblAssosiativaServicos tblAExcluir = new TblAssosiativaServicos();
                    
                tblAExcluir.excluir(serv.getIdServico());
                
                TblAssocProdServ tblExcluir = new TblAssocProdServ();
                
                tblExcluir.excluir(serv.getIdServico());
                Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                try{
                    
                    s.beginTransaction();

                    s.delete(serv);

                    s.getTransaction().commit();

                    txtBServico.setText("");

                    txtBServico.setEnabled(false);

                    carregaTabelaServicos(null);
                    carregarListaNomes(null);

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Não se pode excluir o Serviço pois tem referencia deste cadastro em outro tela");
                    s.close();
                }
            }

        }else {
            JOptionPane.showMessageDialog(IFrmServicos.this, "Escolha um Servico para Excluir", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeletaActionPerformed

    private void btnAlteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlteraActionPerformed
        limparCampos();

        listServicos.setEnabled(false);
        listServicos.enable(false);
        habilitarBotao(btnNovo);
        desabilitarBotao(btnSalvar);

        txtBuscaServ.setEnabled(false);

        if(tblServicos.getSelectedRow() >=0 && txtBServico.getText().equals("") ){
            preencherCampos(tblServicos.getSelectedRow(), null);
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else if(tblServicos.getSelectedRow() >=0 && !txtBServico.getText().equals("") ){
            preencherCampos(tblServicos.getSelectedRow(), txtBServico.getText());
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else {
            JOptionPane.showMessageDialog(IFrmServicos.this, "Escolha um Servico para Alterar", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
            operacao = 0;
        }

    }//GEN-LAST:event_btnAlteraActionPerformed

    private void btnAddTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTipoActionPerformed
        frmTipoServ.setLocationRelativeTo(null);
        frmTipoServ.setVisible(true);
        frmTipoServ.toFront();
    }//GEN-LAST:event_btnAddTipoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        carregaTabelaServicos(null);
        carregarListaNomes(null);

        Object[] op = { "Não", "Sim" };
        int opc = 0;

        if(operacao == 1){
            opc = JOptionPane.showOptionDialog(IFrmServicos.this,"Deseja Cancelar a Operação:\nNovo Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){

                limparCampos();
                desabilitarCampos();
                listServicos.setEnabled(true);
                txtBuscaServ.setEnabled(true);
                desabilitarBotao(btnSalvar);
                desabilitarBotao(btnAddTipo);
                habilitarBotao(btnNovo);
                operacao = 0;

            }

        }else if(operacao == 2 || operacao == 3){
            opc = JOptionPane.showOptionDialog(IFrmServicos.this,"Deseja Cancelar a Operação:\nAlterar Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){
                limparCampos();
                desabilitarCampos();
                listServicos.setEnabled(true);
                txtBuscaServ.setEnabled(true);
                desabilitarBotao(btnAlterar);
                desabilitarBotao(btnAddTipo);
                desabilitarBotao(btnDesbloquear);
                habilitarBotao(btnNovo);

                operacao = 0;
            }
        }else if(operacao == 0){
            JOptionPane.showMessageDialog(IFrmServicos.this, "Nenhuma Operaçao foi iniciada");
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnDesbloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesbloquearActionPerformed
        habilitarCampos();
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);
        listServicos.setEnabled(false);
        txtBuscaServ.setEnabled(false);
        habilitarBotao(btnAlterar);
        habilitarBotao(btnAddTipo);
        cbxTempo.setEnabled(false);
        if(operacao != 3){
            operacao = 2;
        }
        
        
        if(chbUsaProduto.isSelected()){
            btnSelProduto.setEnabled(false);
            btnVisualizar.setEnabled(false);
            spnValorProd.setEnabled(false);
            spnValorTotal.setValue((double)spnValorServ.getValue());
            
            dtmP.setNumRows(0);
        }
    }//GEN-LAST:event_btnDesbloquearActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        int linha = 0;
        int id = 0;
               
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        int opc = JOptionPane.showOptionDialog(IFrmServicos.this,"Deseja Confirmar as alterações no Pedido de Compra: ", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
        
        if(opc == 1){
            
            criarFrmColaboradores();
            
            if(operacao == 2){
                linha = listServicos.getSelectedIndex();

                if(txtBuscaServ.getText().equals("")){
                    preencheFrmColaboradores(pegarIdPorLinha(linha,null));
                }else{
                    preencheFrmColaboradores(pegarIdPorLinha(linha,txtBuscaServ.getText()));
                }

            }else if(operacao == 3){
                linha = tblServicos.getSelectedRow();

                if(!txtBServico.getText().equals("")){
                    preencheFrmColaboradores(pegarIdPorLinha(linha,txtBServico.getText()));
                }else{
                    preencheFrmColaboradores(pegarIdPorLinha(linha,null));
                }
            }
            
            
            frmColaboradores.setLocationRelativeTo(null);
            frmColaboradores.setVisible(true);
            frmColaboradores.setEnabled(true);
            frmColaboradores.toFront();
            
        }else if(JOptionPane.showOptionDialog(IFrmServicos.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
            btnCancelarActionPerformed(evt);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        if(txtServico.getText().equals("") ){

            JOptionPane.showMessageDialog(IFrmServicos.this, "Alguns Campos Obrigatórios Não foram Preenchidos", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        }else {
            int opc = JOptionPane.showOptionDialog(IFrmServicos.this,"Deseja Salvar o Servico de Compra: ", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){

                criarFrmColaboradores();

                frmColaboradores.setLocationRelativeTo(null);
                frmColaboradores.setVisible(true);
                frmColaboradores.setEnabled(true);
                frmColaboradores.toFront();

        }else if(JOptionPane.showOptionDialog(IFrmServicos.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
                btnCancelarActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        operacao = 1;
        habilitarCampos();
        limparCampos();
        listServicos.setEnabled(false);
        txtBuscaServ.setEnabled(false);
        habilitarBotao(btnSalvar);
        habilitarBotao(btnAddTipo);
        habilitarBotao(btnSelProduto);
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);
        dtmP.setNumRows(0);
        preencherTipoServico();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtBuscaServKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscaServKeyReleased
        //        carregarListaNomes(txtBuscaServico.getText());
    }//GEN-LAST:event_txtBuscaServKeyReleased

    private void listServicosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listServicosKeyReleased
        if(operacao == 0){
            limparCampos();
            if(txtBuscaServ.getText().equals("")){
                preencherCampos(listServicos.getSelectedIndex(), null);
            }else {
                preencherCampos(listServicos.getSelectedIndex(), txtBuscaServ.getText());
            }
            
            preencherTabelaProdutos(pegarIdPorLinha(listServicos.getSelectedIndex(), null));

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listServicosKeyReleased

    private void listServicosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listServicosKeyPressed
        if(operacao == 0){
            limparCampos();
            if(txtBuscaServ.getText().equals("")){
                preencherCampos(listServicos.getSelectedIndex(), null);
            }else {
                preencherCampos(listServicos.getSelectedIndex(), txtBuscaServ.getText());
            }
            
            preencherTabelaProdutos(pegarIdPorLinha(listServicos.getSelectedIndex(), null));

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listServicosKeyPressed

    private void listServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listServicosMouseClicked
        if(operacao == 0){
            limparCampos();
            if(txtBuscaServ.getText().equals("")){
                preencherCampos(listServicos.getSelectedIndex(), null);
            }else {
                preencherCampos(listServicos.getSelectedIndex(), txtBuscaServ.getText());
            }
            
            preencherTabelaProdutos(pegarIdPorLinha(listServicos.getSelectedIndex(), null));

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listServicosMouseClicked

    private void btnEscolheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscolheActionPerformed
        int linha = tblEstoque.getSelectedRow();
        
        if(linha >= 0){
            txtIdDoEstoque.setText(dtmE.getValueAt(linha, 0).toString());
            txtNomeDoEstoque.setText(dtmE.getValueAt(linha, 1).toString());
            txtUnidadeDoEstoque.setText(dtmE.getValueAt(linha, 2).toString());
            lblUnidade.setText(txtUnidadeDoEstoque.getText());

            frmPropProdutos.setVisible(true);
            frmPropProdutos.setLocationRelativeTo(null);
            frmPropProdutos.toFront();
        }else {
            JOptionPane.showMessageDialog(frmEstoque, "Por favor Selecione Um produto");
        }
    }//GEN-LAST:event_btnEscolheActionPerformed

    private void btnFinalizarCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarCadastroActionPerformed
        int linha = 0, id = 0;
        if(operacao == 1){
            int tempo = 0;
            int sProduto = 0;

            if(cbxTempo.getSelectedItem().toString().equals("00:30")){
                tempo = 1;
            }else if(cbxTempo.getSelectedItem().toString().equals("01:00")){
                tempo = 2;
            }else if(cbxTempo.getSelectedItem().toString().equals("01:30")){
                tempo = 3;
            }else if(cbxTempo.getSelectedItem().toString().equals("02:00")){
                tempo = 4;
            }

            if(chbUsaProduto.isSelected()){
                sProduto = 1;
            }else {
                sProduto = 0;
            }

            try{
                TipoServico tp = new TipoServico();
                tp.setIdTipo(pegarIdTipoServico(cbxTipoServ.getSelectedIndex()));

                Session s = DAO_PROJETO.getSessionFactory().openSession();
                s.beginTransaction();

                Servico serv = new Servico(tp, txtServico.getText(), tempo);
                serv.setValorServ((double)spnValorServ.getValue());
                serv.setValorTotal((double)spnValorTotal.getValue());
                serv.setSemProduto(sProduto);

                s.save(serv);

                s.getTransaction().commit();

                Colaborador col = new Colaborador();
                TblAssosiativaServicos tblA = new TblAssosiativaServicos();

                for(int aux = 0; aux < contarColaboradores() ; aux++){

                    if(chColaboradores[aux].isSelected()){
                        col = new Colaborador();
                        col.setIdColaborador(pegarIdColaborador(aux));

                        tblA = new TblAssosiativaServicos(col, serv, tp);

                        salvar(tblA);
                    }
                }

                for(int aux=0; aux < dtmP.getRowCount(); aux++){
                    Estoque e = new Estoque();
                    e.setIdEstoque(Integer.valueOf(dtmP.getValueAt(aux, 0).toString()));
                    TblAssocProdServ tbl = new TblAssocProdServ(e, serv,Double.valueOf(dtmP.getValueAt(aux, 2).toString()),Double.valueOf(dtmP.getValueAt(aux, 3).toString()));

                    salvar(tbl);
                }

                carregarListaNomes(null);
                carregaTabelaServicos(null);
                carregaTabelaEstoque(null);
                limparCampos();
                desabilitarBotao(btnSalvar);
                desabilitarBotao(btnAddTipo);
                desabilitarCampos();
                habilitarBotao(btnNovo);
                listServicos.setEnabled(true);
                txtBuscaServ.setEnabled(true);
                frmColaboradores.setVisible(false);
                frmColaboradores.toBack();
                operacao = 0;

                JOptionPane.showMessageDialog(IFrmServicos.this, "Serviço Salvo com sucesso");

            }catch(Exception erro){ erro.printStackTrace();}

        }else if(operacao == 2 || operacao == 3){
            
            if(operacao == 2){
                linha = listServicos.getSelectedIndex();

                if(txtBuscaServ.getText().equals("")){
                    id = pegarIdPorLinha(linha,null);
                }else{
                    id = pegarIdPorLinha(linha,txtBuscaServ.getText());
                }

            }else if(operacao == 3){
                linha = tblServicos.getSelectedRow();

                if(!txtBServico.getText().equals("")){
                    id = pegarIdPorLinha(linha,txtBServico.getText());
                }else{
                    id = pegarIdPorLinha(linha,null);
                }
            }
            
            if(linha >= 0){
                int tempo = 0;
                int sProduto = 0;
                
                if(cbxTempo.getSelectedItem().toString().equals("00:30")){
                    tempo = 1;
                }else if(cbxTempo.getSelectedItem().toString().equals("01:00")){
                    tempo = 2;
                }else if(cbxTempo.getSelectedItem().toString().equals("01:30")){
                    tempo = 3;
                }else if(cbxTempo.getSelectedItem().toString().equals("02:00")){
                    tempo = 4;
                }
                
                if(chbUsaProduto.isSelected()){
                    sProduto = 1;
                }else {
                    sProduto = 0;
                }


                try{
                    TipoServico tp = new TipoServico();
                    tp.setIdTipo(pegarIdTipoServico(cbxTipoServ.getSelectedIndex()));

                    Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                    s.beginTransaction();

                    Servico serv = new Servico(tp, txtServico.getText(), tempo);
                    serv.setValorServ((double)spnValorServ.getValue());
                    serv.setValorTotal((double)spnValorTotal.getValue());
                    serv.setSemProduto(sProduto);
                    serv.setIdServico(id);

                    s.merge(serv);

                    s.getTransaction().commit();
                    
                    Colaborador col = new Colaborador();
                    TblAssosiativaServicos tblAExcluir = new TblAssosiativaServicos();
                    
                    tblAExcluir.excluir(serv.getIdServico());
                    for(int aux = 0; aux < contarColaboradores() ; aux++){

                        if(chColaboradores[aux].isSelected()){
                            col = new Colaborador();
                            col.setIdColaborador(pegarIdColaborador(aux));

                            TblAssosiativaServicos  tblA = new TblAssosiativaServicos(col, serv, tp);

                            alterar(tblA);
                        }
                    }
                    
                    TblAssocProdServ tblExcluir = new TblAssocProdServ();
                    tblExcluir.excluir(serv.getIdServico());
                            
                    for(int aux=0; aux < dtmP.getRowCount(); aux++){
                        Estoque e = new Estoque();
                        e.setIdEstoque(Integer.valueOf(dtmP.getValueAt(aux, 0).toString()));
                        TblAssocProdServ tbl = new TblAssocProdServ(e, serv,Double.valueOf(dtmP.getValueAt(aux, 2).toString()),Double.valueOf(dtmP.getValueAt(aux, 3).toString()));
                        
                        alterar(tbl);
                    }

                }catch(Exception erro){ erro.printStackTrace();}
                carregarListaNomes(null);
                carregaTabelaServicos(null);
                limparCampos();
                desabilitarBotao(btnSalvar);
                desabilitarBotao(btnAddTipo);
                desabilitarBotao(btnAlterar);
                desabilitarCampos();
                habilitarBotao(btnNovo);
                listServicos.setEnabled(true);
                txtBuscaServ.setEnabled(true);
                frmColaboradores.setVisible(false);
                frmColaboradores.toBack();
                operacao = 0;

                JOptionPane.showMessageDialog(IFrmServicos.this, "Servico alterado com sucesso");
            }
        }

            
    }//GEN-LAST:event_btnFinalizarCadastroActionPerformed

    private void btnSelProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelProdutoActionPerformed
        carregaTabelaEstoque(null);
        frmEstoque.setVisible(true);
        frmEstoque.setLocationRelativeTo(null);
        frmEstoque.toFront();
    }//GEN-LAST:event_btnSelProdutoActionPerformed

    private void btnConfirmarProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarProdutosActionPerformed
        int linha = tblEstoque.getSelectedRow();
        double qntM = Double.valueOf(dtmE.getValueAt(linha, 4).toString());
        double resultado = 0;
        double valorUni = Double.valueOf(dtmE.getValueAt(linha, 5).toString());
        
        if(lblUnidade.getText().equals("ml") || lblUnidade.getText().equals("kg")){
            resultado = ((double)spnQuantidadeUtilizada.getValue()*valorUni)/qntM;
        }else if(lblUnidade.getText().equals("un")){
            resultado = valorUni*(double)spnQuantidadeUtilizada.getValue();
        }

        if((double)spnQuantidadeUtilizada.getValue() <= 0){
            JOptionPane.showMessageDialog(frmPropProdutos, "Por favor digite uma Quantidade");
        }else{
            txtValorGasto.setText(String.valueOf(resultado));
            String[] preencherTabela = new String[]{
                txtIdDoEstoque.getText(),
                txtNomeDoEstoque.getText(),
                String.valueOf(spnQuantidadeUtilizada.getValue()),
                txtValorGasto.getText(),
        };
         
         preencheTabelaProd(preencherTabela);
         txtIdDoEstoque.setText("");
         txtNomeDoEstoque.setText("");
         spnQuantidadeUtilizada.setValue(0);
         txtValorGasto.setText("");
         
         frmPropProdutos.setVisible(false);
         frmPropProdutos.toBack();
         
         frmEstoque.setVisible(false);
         frmEstoque.toBack();
         
         frmProdutos.setVisible(true);
         frmProdutos.setLocationRelativeTo(null);
         frmProdutos.toFront();
        }
        
        
    }//GEN-LAST:event_btnConfirmarProdutosActionPerformed

    private void txtValorGastoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorGastoFocusGained
        int linha = tblEstoque.getSelectedRow();
        double qntM = Double.valueOf(dtmE.getValueAt(linha, 4).toString());
        double valorUni = Double.valueOf(dtmE.getValueAt(linha, 5).toString());
        
        double resultado = ((double)spnQuantidadeUtilizada.getValue()*valorUni)/qntM;
                
        txtValorGasto.setText(String.valueOf(resultado));
    }//GEN-LAST:event_txtValorGastoFocusGained

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        double valorProdutos = 0, valorTotal1 = 0, valorTotal2 = 0;
        
        for(int aux = 0; aux < dtmP.getRowCount(); aux++){
            valorProdutos += Double.valueOf(dtmP.getValueAt(aux, 3).toString());
        }
        
        frmProdutos.setVisible(false);
        frmProdutos.toBack();
        
        spnValorProd.setValue(valorProdutos);
        valorTotal1 = Double.parseDouble(spnValorServ.getValue().toString());
        valorTotal2 = Double.parseDouble(spnValorProd.getValue().toString());
        
        spnValorTotal.setValue((valorTotal1*1)+(valorTotal2*1));
       
        
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void btnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarActionPerformed
        frmProdutos.setVisible(true);
        frmProdutos.setLocationRelativeTo(null);
        frmProdutos.toFront();
    }//GEN-LAST:event_btnVisualizarActionPerformed

    private void cbxTipoServFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbxTipoServFocusGained
        if(operacao == 2 || operacao == 3){
            preencherTipoServico();   
        }
    }//GEN-LAST:event_cbxTipoServFocusGained

    private void chbUsaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbUsaProdutoActionPerformed
        if(chbUsaProduto.isSelected()){
            btnSelProduto.setEnabled(false);
            btnVisualizar.setEnabled(false);
            spnValorProd.setEnabled(false);
            spnValorTotal.setValue(Double.valueOf((double)spnValorServ.getValue()));
            
            dtmP.setNumRows(0);
        }else {
            btnSelProduto.setEnabled(true);
            btnVisualizar.setEnabled(true);
            spnValorProd.setEnabled(true);
            spnValorTotal.setValue(0);
        }
    }//GEN-LAST:event_chbUsaProdutoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTipo;
    private javax.swing.JButton btnAltera;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnBusca;
    private javax.swing.JButton btnCadTipo;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmarProdutos;
    private javax.swing.JButton btnDeleta;
    private javax.swing.JButton btnDesbloquear;
    private javax.swing.JButton btnEscolhe;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnFinalizarCadastro;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSelProduto;
    private javax.swing.JButton btnSeleciona;
    private javax.swing.JButton btnVisualizar;
    private javax.swing.JComboBox<String> cbxTempo;
    private javax.swing.JComboBox<String> cbxTipoServ;
    private javax.swing.JCheckBox chbUsaProduto;
    private javax.swing.JFrame frmColaboradores;
    private javax.swing.JFrame frmEstoque;
    private javax.swing.JFrame frmProdutos;
    private javax.swing.JFrame frmPropProdutos;
    private javax.swing.JFrame frmTipoServ;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblUnidade;
    private javax.swing.JList<String> listServicos;
    private javax.swing.JPanel pnCadastro;
    private javax.swing.JPanel pnExibir;
    private javax.swing.JScrollPane scrolLista;
    private javax.swing.JLabel snpValorTotal;
    private javax.swing.JSpinner spnQuantidadeUtilizada;
    private javax.swing.JSpinner spnValorProd;
    private javax.swing.JSpinner spnValorServ;
    private javax.swing.JSpinner spnValorTotal;
    private javax.swing.JTable tblEstoque;
    private javax.swing.JTable tblPedido;
    private javax.swing.JTable tblServicos;
    private javax.swing.JTabbedPane tpnCadastrar;
    private javax.swing.JTextField txtBServico;
    private javax.swing.JTextField txtBuscaServ;
    private javax.swing.JTextField txtIdDoEstoque;
    private javax.swing.JTextField txtNomeDoEstoque;
    private javax.swing.JTextField txtServico;
    private javax.swing.JTextField txtTipoServ;
    private javax.swing.JTextField txtUnidadeDoEstoque;
    private javax.swing.JTextField txtValorGasto;
    // End of variables declaration//GEN-END:variables
}
