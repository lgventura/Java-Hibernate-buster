package view;

import controller.Colaborador;
import controller.Estoque;
import controller.Pessoa;
import controller.Produto;
import java.awt.Dimension;

import org.hibernate.Session;
import org.hibernate.Query;

import dao.DAO_PROJETO;
import controller.ServicoTerceiro;
import controller.Terceiro;
import controller.TipoServico;
import controller.Usuarios;
import java.awt.event.ActionEvent;

import java.text.ParseException;

import java.util.Iterator;
import java.util.List;
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
public class IFrmServicosTerceiros extends javax.swing.JInternalFrame {
    
    private DefaultListModel dlm ; //Usado p/Criação da lista com os dados vindo do BD
    private DefaultTableModel dtm; //Usado p/ Criação da Tabela com os dados vindo do BD
    private DefaultTableModel dtmT;
    
    private int operacao = 0;
    
    private void limparCampos(){//Limpa todos os campos após o cadastro

        txtBuscaServ.setText("");
        txtServico.setText("");
        cbxTipoServ.setSelectedIndex(0);
        cbxTempo.setSelectedIndex(0);
        
        spnValorServ.setValue(0);
        
        txtServico.requestFocus();
    }
    
    
    private void habilitarCampos(){
        txtServico.setEnabled(true);
        cbxTipoServ.setEnabled(true);
        cbxTempo.setEnabled(true);
        
        btnAddTipo.setEnabled(true);
        btnSelTerceiro.setEnabled(true);
        spnValorServ.setEnabled(true);
        
    }
    
    private void desabilitarCampos(){
        txtServico.setEnabled(false);
        cbxTipoServ.setEnabled(false);
        cbxTempo.setEnabled(false);
        
        btnAddTipo.setEnabled(false);
        btnSelTerceiro.setEnabled(false);
        
        spnValorServ.setEnabled(false);
    }
    
    private void preencherCampos(int linha, String valor){
        String sql = "from ServicoTerceiro order by nomeServico";
        int aux = 0;
        
        if(valor != null){
            sql = " from ServicoTerceiro where like '%"+valor+"%' order by nomeServico";
        }
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            ServicoTerceiro serv;
            
            while(i.hasNext()){
                serv = (ServicoTerceiro)i.next();
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
                    
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private int pegarIdPorLinha(int linha, String valor){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from ServicoTerceiro order by nomeServico ";
        int aux = 0;
        int id = 0;
        
        if(valor != null ){
            sql = " from ServicoTerceiro where nome like '%"+valor+"%' order by nomeServico";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            ServicoTerceiro serv;
            
            while(i.hasNext()){
                serv = (ServicoTerceiro)i.next();
                if(aux == linha){
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
    
    private void carregaTabelaServicoTerceiros(String valor){//carrega a tabela de ServicoTerceiros ordenando pelo nome
        String sql = "from ServicoTerceiro order by nomeServico ";
        
        if(valor != null){
            sql = " from ServicoTerceiro where nome like '%"+valor+"%' order by nomeServico";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtm.setNumRows(0);
            
            ServicoTerceiro serv;
            
            while(i.hasNext()){
                serv = (ServicoTerceiro)i.next();
                dtm.addRow(serv.getServicoTerceiro());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void carregaTabelaTerceiros(){//carrega a tabela de ServicoTerceiros ordenando pelo nome
        String sql = "from Terceiro as t order by t.pessoa.nome ";
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtmT.setNumRows(0);
            
            Terceiro t;
            
            while(i.hasNext()){
                t = (Terceiro)i.next();
                
                dtmT.addRow(t.getTerceiroAgenda());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
    }
    
    private void carregarListaNomes(String nome){
       
        String sql = "from ServicoTerceiro order by nomeServico";
        
        if(nome != null){
            sql = " from ServicoTerceiro where nomeServico like '%"+nome+"%'";
        }
        
        dlm.removeAllElements();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            ServicoTerceiro serv;
            
            while(i.hasNext()){
                serv = (ServicoTerceiro)i.next();
                dlm.add(dlm.getSize(), serv.getNomeServico());
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
    }
    
    public IFrmServicosTerceiros() {
        initComponents();
        
        listServicos.setModel(new DefaultListModel());
        dlm = (DefaultListModel) listServicos.getModel();
        dtm = (DefaultTableModel)tblServicos.getModel();
        dtmT = (DefaultTableModel)tblTerceiros.getModel();
        carregarListaNomes(null);
        carregaTabelaServicoTerceiros(null);
        carregaTabelaTerceiros();
        btnSalvar.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnDesbloquear.setEnabled(false);
        //btnSeleciona.setEnabled(false);
        btnAddTipo.setEnabled(false);
        
        preencherTipoServico();
        
        limparCampos();
        desabilitarCampos();
        
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
        frmTerceiros = new javax.swing.JFrame();
        btnEscolhe = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTerceiros = new javax.swing.JTable();
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
        jLabel22 = new javax.swing.JLabel();
        cbxTempo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cbxTipoServ = new javax.swing.JComboBox<>();
        btnAddTipo = new javax.swing.JButton();
        btnSelTerceiro = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtIdTerceiro = new javax.swing.JTextField();
        txtNomeTerceiro = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
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
                .addContainerGap(46, Short.MAX_VALUE))
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
                .addContainerGap(79, Short.MAX_VALUE))
        );

        frmTerceiros.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmTerceiros.setTitle("Produtos em Estoque");
        frmTerceiros.setMinimumSize(new java.awt.Dimension(800, 450));

        btnEscolhe.setText("Selecionar Terceiro");
        btnEscolhe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscolheActionPerformed(evt);
            }
        });

        jLabel11.setText("Lista de Terceiros");

        tblTerceiros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome do Terceiro", "CPF", "Telefone", "Especialidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblTerceiros);
        if (tblTerceiros.getColumnModel().getColumnCount() > 0) {
            tblTerceiros.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblTerceiros.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblTerceiros.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblTerceiros.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblTerceiros.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        javax.swing.GroupLayout frmTerceirosLayout = new javax.swing.GroupLayout(frmTerceiros.getContentPane());
        frmTerceiros.getContentPane().setLayout(frmTerceirosLayout);
        frmTerceirosLayout.setHorizontalGroup(
            frmTerceirosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmTerceirosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmTerceirosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(frmTerceirosLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEscolhe)))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        frmTerceirosLayout.setVerticalGroup(
            frmTerceirosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmTerceirosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmTerceirosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEscolhe)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setClosable(true);
        setIconifiable(true);
        setTitle("Serviços");
        setMaximumSize(new java.awt.Dimension(898, 522));
        setMinimumSize(new java.awt.Dimension(898, 522));
        getContentPane().setLayout(new java.awt.FlowLayout());

        pnCadastro.setBackground(new java.awt.Color(184, 168, 214));
        pnCadastro.setMaximumSize(new java.awt.Dimension(886, 558));
        pnCadastro.setMinimumSize(new java.awt.Dimension(886, 558));

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

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5739_Knob_Search.png"))); // NOI18N
        jLabel16.setToolTipText("Buscar Serviço cadastrado");

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
        btnAddTipo.setToolTipText("Adicionar novo tipo de Serviço");
        btnAddTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTipoActionPerformed(evt);
            }
        });

        btnSelTerceiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bt_up.png"))); // NOI18N
        btnSelTerceiro.setText("Selecionar Terceiro:");
        btnSelTerceiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelTerceiroActionPerformed(evt);
            }
        });

        jLabel8.setText("ID Terceiro:");

        jLabel13.setText("Terceiro:");

        jPanel1.setBackground(new java.awt.Color(166, 80, 189));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5113_add_48.png"))); // NOI18N
        btnNovo.setToolTipText("Cadastrar novo Serviço de Terceiro");
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSalvar)
                .addGap(18, 18, 18)
                .addComponent(btnAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDesbloquear)
                .addGap(6, 6, 6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDesbloquear, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnCadastroLayout = new javax.swing.GroupLayout(pnCadastro);
        pnCadastro.setLayout(pnCadastroLayout);
        pnCadastroLayout.setHorizontalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscaServ))
                    .addComponent(scrolLista, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSelTerceiro)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnCancelar)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCadastroLayout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(32, 32, 32)
                                .addComponent(spnValorServ, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(141, 141, 141)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtServico)
                                .addGap(44, 44, 44)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxTipoServ, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAddTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCadastroLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtIdTerceiro, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNomeTerceiro, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(106, 106, 106))
        );
        pnCadastroLayout.setVerticalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscaServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrolLista, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSelTerceiro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtIdTerceiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(txtNomeTerceiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(cbxTipoServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddTipo))
                        .addGap(18, 18, 18)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(spnValorServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(cbxTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar)))
                .addContainerGap(191, Short.MAX_VALUE))
        );

        tpnCadastrar.addTab("Cadastrar", pnCadastro);

        pnExibir.setBackground(new java.awt.Color(0, 204, 204));

        tblServicos.setBackground(new java.awt.Color(0, 153, 153));
        tblServicos.setForeground(new java.awt.Color(255, 255, 255));
        tblServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Serviço", "Terceiro", "Tipo de Serviço", "Tempo de Execução", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            tblServicos.getColumnModel().getColumn(4).setPreferredWidth(40);
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
            .addGroup(pnExibirLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnExibirLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(pnExibirLayout.createSequentialGroup()
                        .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnExibirLayout.createSequentialGroup()
                                .addComponent(btnAltera)
                                .addGap(24, 24, 24)
                                .addComponent(btnDeleta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnExibirLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(245, 245, 245)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(txtBServico, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnBusca)
                        .addGap(52, 52, 52))))
        );
        pnExibirLayout.setVerticalGroup(
            pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnExibirLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnExibirLayout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addGap(24, 24, 24))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnExibirLayout.createSequentialGroup()
                            .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                    .addGroup(pnExibirLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAltera)
                    .addComponent(btnDeleta))
                .addContainerGap())
        );

        tpnCadastrar.addTab("Exibir", pnExibir);

        getContentPane().add(tpnCadastrar);

        setBounds(200, 100, 997, 499);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadTipoActionPerformed
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        if(txtTipoServ.getText().equals("")){
            
            JOptionPane.showMessageDialog(IFrmServicosTerceiros.this, "Preencha o nome do Tipo de Serviço", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        }else {
            int opc = JOptionPane.showOptionDialog(IFrmServicosTerceiros.this,"Deseja Salvar Tipo de Serviço?: "+txtTipoServ.getText(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
            
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
                
                JOptionPane.showMessageDialog(IFrmServicosTerceiros.this, "Tipo de Serviço Salvo");

            }else if(JOptionPane.showOptionDialog(IFrmServicosTerceiros.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
                txtTipoServ.setText("");
                frmTipoServ.setVisible(false);
                frmTipoServ.toBack();
            }
        }
    }//GEN-LAST:event_btnCadTipoActionPerformed

    private void txtBServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBServicoActionPerformed
        
    }//GEN-LAST:event_txtBServicoActionPerformed

    private void btnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaActionPerformed
        if(!txtBuscaServ.getText().equals("")){
            carregaTabelaServicoTerceiros(txtBServico.getText());
        }else{
            carregaTabelaServicoTerceiros(null);
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
            int opc = JOptionPane.showOptionDialog(IFrmServicosTerceiros.this,"Deseja Confirmar a Exclusção do ServicoTerceiro: "+dtm.getValueAt(linha, 0).toString(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, op, op[1]);

            if(opc == 1){

                TipoServico tp = new TipoServico();
                tp.setIdTipo(1);
                
                Pessoa p = new Pessoa();
                Terceiro t = new Terceiro(p, "");
                t.setIdTerceiro(1);
                

                ServicoTerceiro serv = new ServicoTerceiro(t, tp, "", 0);
                serv.setIdServicoT(id);
                    
                    Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                try{
                    
                    s.beginTransaction();

                    s.delete(serv);

                    s.getTransaction().commit();

                    txtBServico.setText("");

                    txtBServico.setEnabled(false);

                    carregaTabelaServicoTerceiros(null);
                    carregarListaNomes(null);

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Não se pode excluir o Serviço pois tem referencia deste cadastro em outro tela");
                    s.close();
                }
            }

        }else {
            JOptionPane.showMessageDialog(IFrmServicosTerceiros.this, "Escolha um ServicoTerceiro para Excluir", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(IFrmServicosTerceiros.this, "Escolha um ServicoTerceiro para Alterar", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
            operacao = 0;
        }

    }//GEN-LAST:event_btnAlteraActionPerformed

    private void btnAddTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTipoActionPerformed
        frmTipoServ.setLocationRelativeTo(null);
        frmTipoServ.setVisible(true);
        frmTipoServ.toFront();
    }//GEN-LAST:event_btnAddTipoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        carregaTabelaServicoTerceiros(null);
        carregarListaNomes(null);

        Object[] op = { "Não", "Sim" };
        int opc = 0;

        if(operacao == 1){
            opc = JOptionPane.showOptionDialog(IFrmServicosTerceiros.this,"Deseja Cancelar a Operação:\nNovo Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

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
            opc = JOptionPane.showOptionDialog(IFrmServicosTerceiros.this,"Deseja Cancelar a Operação:\nAlterar Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

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
            JOptionPane.showMessageDialog(IFrmServicosTerceiros.this, "Nenhuma Operaçao foi iniciada");
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
    }//GEN-LAST:event_btnDesbloquearActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        int linha = 0;
        int id = 0;
               
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        int opc = JOptionPane.showOptionDialog(IFrmServicosTerceiros.this,"Deseja Confirmar as alterações no Pedido de Compra: ", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
        
        if(opc == 1){
            
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
                
                try{
                    TipoServico tp = new TipoServico();
                    tp.setIdTipo(pegarIdTipoServico(cbxTipoServ.getSelectedIndex()));

                    Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                    s.beginTransaction();
                    
                    Terceiro t = new Terceiro();
                    t.setIdTerceiro(Integer.parseInt(txtIdTerceiro.getText()));

                    ServicoTerceiro serv = new ServicoTerceiro(t, tp, txtServico.getText(), tempo);
                    
                    serv.setValorServ((double)spnValorServ.getValue());
                    

                    s.merge(serv);

                    s.getTransaction().commit();
                    
                    
                }catch(Exception erro){ erro.printStackTrace();}
                carregarListaNomes(null);
                carregaTabelaServicoTerceiros(null);
                limparCampos();
                desabilitarBotao(btnSalvar);
                desabilitarBotao(btnAddTipo);
                desabilitarBotao(btnAlterar);
                desabilitarCampos();
                habilitarBotao(btnNovo);
                listServicos.setEnabled(true);
                txtBuscaServ.setEnabled(true);
                operacao = 0;

                JOptionPane.showMessageDialog(IFrmServicosTerceiros.this, "Servico alterado com sucesso");
            
        }else if(JOptionPane.showOptionDialog(IFrmServicosTerceiros.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
            btnCancelarActionPerformed(evt);
        }
     }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        if(txtServico.getText().equals("") ){

            JOptionPane.showMessageDialog(IFrmServicosTerceiros.this, "Alguns Campos Obrigatórios Não foram Preenchidos", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        }else {
            int opc = JOptionPane.showOptionDialog(IFrmServicosTerceiros.this,"Deseja Salvar o ServicoTerceiro de Compra: ", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){
                if(operacao == 1){
            
                    int tempo = 0;

                    if(cbxTempo.getSelectedItem().toString().equals("00:30")){
                        tempo = 1;
                    }else if(cbxTempo.getSelectedItem().toString().equals("01:00")){
                        tempo = 2;
                    }else if(cbxTempo.getSelectedItem().toString().equals("01:30")){
                        tempo = 3;
                    }else if(cbxTempo.getSelectedItem().toString().equals("02:00")){
                        tempo = 4;
                    }

                    try{
                        TipoServico tp = new TipoServico();
                        tp.setIdTipo(pegarIdTipoServico(cbxTipoServ.getSelectedIndex()));

                        Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                        s.beginTransaction();

                        Terceiro t = new Terceiro();
                        t.setIdTerceiro(Integer.parseInt(txtIdTerceiro.getText()));

                        ServicoTerceiro serv = new ServicoTerceiro(t, tp, txtServico.getText(), tempo);

                        serv.setValorServ((double)spnValorServ.getValue());


                        s.save(serv);

                        s.getTransaction().commit();


                    }catch(Exception erro){ erro.printStackTrace();}
                    carregarListaNomes(null);
                    carregaTabelaServicoTerceiros(null);
                    limparCampos();
                    desabilitarBotao(btnSalvar);
                    desabilitarBotao(btnAddTipo);
                    desabilitarBotao(btnAlterar);
                    desabilitarCampos();
                    habilitarBotao(btnNovo);
                    listServicos.setEnabled(true);
                    txtBuscaServ.setEnabled(true);
                    operacao = 0;

                    JOptionPane.showMessageDialog(IFrmServicosTerceiros.this, "Servico alterado com sucesso");
                }
            }else if(JOptionPane.showOptionDialog(IFrmServicosTerceiros.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
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
        habilitarBotao(btnSelTerceiro);
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);
        preencherTipoServico();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtBuscaServKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscaServKeyReleased
        carregarListaNomes(txtBuscaServ.getText());
    }//GEN-LAST:event_txtBuscaServKeyReleased

    private void listServicosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listServicosKeyReleased
        if(operacao == 0){
            limparCampos();
            if(txtBuscaServ.getText().equals("")){
                preencherCampos(listServicos.getSelectedIndex(), null);
            }else {
                preencherCampos(listServicos.getSelectedIndex(), txtBuscaServ.getText());
            }

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
            
            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listServicosMouseClicked

    private void btnEscolheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscolheActionPerformed
        int linha = tblTerceiros.getSelectedRow();
        
        if(linha >= 0){
            txtIdTerceiro.setText(dtmT.getValueAt(linha, 0).toString());
            txtNomeTerceiro.setText(dtmT.getValueAt(linha, 1).toString());

            frmTerceiros.setVisible(false);
            frmTerceiros.toBack();
        }else {
            JOptionPane.showMessageDialog(frmTerceiros, "Por favor Selecione Um Terceiro");
        }
    }//GEN-LAST:event_btnEscolheActionPerformed

    private void btnSelTerceiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelTerceiroActionPerformed
        carregaTabelaTerceiros();
        frmTerceiros.setVisible(true);
        frmTerceiros.setLocationRelativeTo(null);
        frmTerceiros.toFront();
    }//GEN-LAST:event_btnSelTerceiroActionPerformed

    private void cbxTipoServFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbxTipoServFocusGained
        if(operacao == 2 || operacao == 3){
            preencherTipoServico();   
        }
    }//GEN-LAST:event_cbxTipoServFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTipo;
    private javax.swing.JButton btnAltera;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnBusca;
    private javax.swing.JButton btnCadTipo;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDeleta;
    private javax.swing.JButton btnDesbloquear;
    private javax.swing.JButton btnEscolhe;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSelTerceiro;
    private javax.swing.JComboBox<String> cbxTempo;
    private javax.swing.JComboBox<String> cbxTipoServ;
    private javax.swing.JFrame frmTerceiros;
    private javax.swing.JFrame frmTipoServ;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> listServicos;
    private javax.swing.JPanel pnCadastro;
    private javax.swing.JPanel pnExibir;
    private javax.swing.JScrollPane scrolLista;
    private javax.swing.JSpinner spnValorServ;
    private javax.swing.JTable tblServicos;
    private javax.swing.JTable tblTerceiros;
    private javax.swing.JTabbedPane tpnCadastrar;
    private javax.swing.JTextField txtBServico;
    private javax.swing.JTextField txtBuscaServ;
    private javax.swing.JTextField txtIdTerceiro;
    private javax.swing.JTextField txtNomeTerceiro;
    private javax.swing.JTextField txtServico;
    private javax.swing.JTextField txtTipoServ;
    // End of variables declaration//GEN-END:variables
}
