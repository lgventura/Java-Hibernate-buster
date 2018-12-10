package view;

import controller.Acesso;
import controller.Agendamento;
import controller.Colaborador;
import controller.Usuarios;
import dao.DAO_PROJETO;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 *
 * @author lgustavo
 */
public class Tela_Principal extends javax.swing.JFrame {

    /**
     * Creates new form Tela_Principal
     */
    private IFrmClientes clientes;
    private IFrmColaboradores colaborador;
    private IFrmFornecedores fornecedor;
    private IFrmPedido pedido;
    private IFrmProdutos produto;
    private IFrmServicos servico;
    private IFrmAgendamento agenda;
    private IFrmTerceiros terceiro;
    private IFrmServicosTerceiros servTer;
    private IFrmRelatorioAgendamento rel;
    
    
    private DefaultTableModel dtm; //Usado p/ Criação da Tabela com os dados vindo do BD
    
    public void carregaAgenda(){
        java.text.SimpleDateFormat formatoData = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String data = formatoData.format(dataAgenda.getDate());
        
        String sql = " from Agendamento as a where a.data = '"+ data +"' order by a.hora";
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtm.setNumRows(0);
                    
            Agendamento a;
            
            while(i.hasNext()){
                a = (Agendamento)i.next();
                dtm.addRow(a.getAgenda());
                   
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void addAcesso(int id, String acesso){
        Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        
        
        Colaborador c = new Colaborador();
        c.setIdColaborador(id);
        Acesso a = new Acesso(c, acesso);
        
        s.save(a);
        
        s.getTransaction().commit();
    }
    
    private boolean verificaLogin(String user, String senha){
        String sql = " from Colaborador";
        int idCol = 0;
        String nivelAcesso = "";
        boolean teste = false;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
                    
            Colaborador c;
            
            while(i.hasNext()){
                c = (Colaborador)i.next();
                
                if(c.getUsuarios().getUsuario().equals(user) && c.getUsuarios().getSenha().equals(senha)){
                    teste = true;
                    
                    idCol = c.getIdColaborador();
                    nivelAcesso = c.getUsuarios().getTipoDeAcesso();
                    int tamanho = c.getUsuarios().getTipoDeAcesso().length();
                    String acesso = c.getUsuarios().getTipoDeAcesso().substring(0,3);
                    String acessoAux = c.getUsuarios().getTipoDeAcesso();
                    
                    lblUser.setText(c.getPessoa().getNome());
                    
                    if(acesso.equals("NRM")){
                        lblTpAcesso.setText("Normal");
                    }else if(acesso.equals("ADM")){
                        lblTpAcesso.setText("Administrador");
                    }else if(acesso.equals("PSL")){
                        lblTpAcesso.setText("Personalizado");
                    }
                    
                    lblControle.setText(c.getUsuarios().getTipoDeAcesso());
                }
                   
            }
            s.getTransaction().commit();
            
            addAcesso(idCol, nivelAcesso);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return teste;
    }
    
    public Tela_Principal() {
        initComponents();
        dtm = (DefaultTableModel)tblAgendamento.getModel();
        tblAgendamento.setVisible(false);
        spTbl.setVisible(false);
        this.setVisible(false);
        this.setEnabled(false);
        dskPrincipal.setVisible(false);
        dskPrincipal.setEnabled(false);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        txtUsuario = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        btnLogar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        acesso = new javax.swing.JFrame();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblControle = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblTpAcesso = new javax.swing.JLabel();
        dskPrincipal = new javax.swing.JDesktopPane();
        dataAgenda = new com.toedter.calendar.JCalendar();
        btnOAgenda = new javax.swing.JButton();
        btnViAgenda = new javax.swing.JButton();
        spTbl = new javax.swing.JScrollPane();
        tblAgendamento = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnCadCli = new javax.swing.JButton();
        btnCadProd = new javax.swing.JButton();
        btnCadServ = new javax.swing.JButton();
        btnAgenda = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuCadastro = new javax.swing.JMenu();
        mniClientes = new javax.swing.JMenuItem();
        mniColaboradores = new javax.swing.JMenuItem();
        mniTerceiros = new javax.swing.JMenuItem();
        mniFornecedores = new javax.swing.JMenuItem();
        mniProduto = new javax.swing.JMenuItem();
        mniPedido = new javax.swing.JMenuItem();
        mniServicos = new javax.swing.JMenuItem();
        mniServicosTerc = new javax.swing.JMenuItem();
        mniAgenda = new javax.swing.JMenuItem();
        menuAcesso = new javax.swing.JMenu();
        mniSessao = new javax.swing.JMenuItem();
        menuRelatorios = new javax.swing.JMenu();
        mniRelClientes = new javax.swing.JMenuItem();

        login.setMinimumSize(new java.awt.Dimension(400, 455));
        login.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Login"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        try {
            txtUsuario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsuario.setToolTipText("Difite Apenas Numeros");
        txtUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtUsuario.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusLost(evt);
            }
        });
        jPanel2.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 180, -1));

        jLabel3.setText("CPF:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, -1));

        jLabel4.setText("Senha:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, -1));

        txtSenha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 180, -1));

        btnLogar.setText("Logar");
        btnLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogarActionPerformed(evt);
            }
        });
        jPanel2.add(btnLogar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, -1, -1));

        login.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 398, 166));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logoe1.png"))); // NOI18N
        login.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 398, -1));

        acesso.setMinimumSize(new java.awt.Dimension(398, 220));
        acesso.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Acesso"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setText("Usuário Logado:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLabel9.setText("Tipo de Acesso:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5112_user_48.png"))); // NOI18N
        jLabel10.setToolTipText("");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        lblControle.setText("jLabel5");
        jPanel3.add(lblControle, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, -1, -1));

        lblUser.setText("jLabel6");
        jPanel3.add(lblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, -1, -1));

        lblTpAcesso.setText("jLabel7");
        jPanel3.add(lblTpAcesso, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, -1, -1));

        acesso.getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 398, 166));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Beauty Buster");
        setExtendedState(6);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        dataAgenda.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dataAgendaPropertyChange(evt);
            }
        });
        dskPrincipal.add(dataAgenda);
        dataAgenda.setBounds(884, 20, 430, 220);

        btnOAgenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_download.png"))); // NOI18N
        btnOAgenda.setText("Ocultar Agenda");
        btnOAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOAgendaActionPerformed(evt);
            }
        });
        dskPrincipal.add(btnOAgenda);
        btnOAgenda.setBounds(1120, 270, 200, 42);

        btnViAgenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_upload.png"))); // NOI18N
        btnViAgenda.setText("Visualizar Agenda");
        btnViAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViAgendaActionPerformed(evt);
            }
        });
        dskPrincipal.add(btnViAgenda);
        btnViAgenda.setBounds(890, 270, 200, 42);

        tblAgendamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
        spTbl.setViewportView(tblAgendamento);

        dskPrincipal.add(spTbl);
        spTbl.setBounds(-10, 0, 870, 403);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "MENU"));

        btnCadCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5110_user_add_48.png"))); // NOI18N
        btnCadCli.setText("Cadastrar Cliente");
        btnCadCli.setMaximumSize(new java.awt.Dimension(246, 58));
        btnCadCli.setMinimumSize(new java.awt.Dimension(246, 58));
        btnCadCli.setPreferredSize(new java.awt.Dimension(246, 58));
        btnCadCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadCliActionPerformed(evt);
            }
        });
        jPanel1.add(btnCadCli);

        btnCadProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/file.png"))); // NOI18N
        btnCadProd.setText("Cadastrar Produto");
        btnCadProd.setMaximumSize(new java.awt.Dimension(246, 58));
        btnCadProd.setMinimumSize(new java.awt.Dimension(246, 58));
        btnCadProd.setPreferredSize(new java.awt.Dimension(246, 58));
        btnCadProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdActionPerformed(evt);
            }
        });
        jPanel1.add(btnCadProd);

        btnCadServ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5121_paperpencil_48.png"))); // NOI18N
        btnCadServ.setText("Cadastrar Serviço");
        btnCadServ.setMaximumSize(new java.awt.Dimension(246, 58));
        btnCadServ.setMinimumSize(new java.awt.Dimension(246, 58));
        btnCadServ.setPreferredSize(new java.awt.Dimension(246, 58));
        btnCadServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadServActionPerformed(evt);
            }
        });
        jPanel1.add(btnCadServ);

        btnAgenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5105_table_48.png"))); // NOI18N
        btnAgenda.setText("Cadastrar Atendimento");
        btnAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendaActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgenda);

        dskPrincipal.add(jPanel1);
        jPanel1.setBounds(1010, 350, 310, 290);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo1.png"))); // NOI18N
        jLabel1.setFocusable(false);
        dskPrincipal.add(jLabel1);
        jLabel1.setBounds(0, 0, 1408, 704);

        getContentPane().add(dskPrincipal);
        dskPrincipal.setBounds(0, 0, 1378, 735);

        menuCadastro.setMnemonic('h');
        menuCadastro.setText("Cadastros");

        mniClientes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mniClientes.setMnemonic('c');
        mniClientes.setText("Clientes");
        mniClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniClientesActionPerformed(evt);
            }
        });
        menuCadastro.add(mniClientes);

        mniColaboradores.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mniColaboradores.setMnemonic('a');
        mniColaboradores.setText("Colaboradores");
        mniColaboradores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniColaboradoresActionPerformed(evt);
            }
        });
        menuCadastro.add(mniColaboradores);

        mniTerceiros.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mniTerceiros.setMnemonic('a');
        mniTerceiros.setText("Terceiros");
        mniTerceiros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTerceirosActionPerformed(evt);
            }
        });
        menuCadastro.add(mniTerceiros);

        mniFornecedores.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        mniFornecedores.setMnemonic('a');
        mniFornecedores.setText("Fornecedores");
        mniFornecedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFornecedoresActionPerformed(evt);
            }
        });
        menuCadastro.add(mniFornecedores);

        mniProduto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        mniProduto.setMnemonic('a');
        mniProduto.setText("Produtos");
        mniProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniProdutoActionPerformed(evt);
            }
        });
        menuCadastro.add(mniProduto);

        mniPedido.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mniPedido.setMnemonic('a');
        mniPedido.setText("Pedido de Compra");
        mniPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniPedidoActionPerformed(evt);
            }
        });
        menuCadastro.add(mniPedido);

        mniServicos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mniServicos.setMnemonic('a');
        mniServicos.setText("Serviços");
        mniServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniServicosActionPerformed(evt);
            }
        });
        menuCadastro.add(mniServicos);

        mniServicosTerc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mniServicosTerc.setMnemonic('a');
        mniServicosTerc.setText("Serviços de Terceiros");
        mniServicosTerc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniServicosTercActionPerformed(evt);
            }
        });
        menuCadastro.add(mniServicosTerc);

        mniAgenda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mniAgenda.setMnemonic('a');
        mniAgenda.setText("Agenda");
        mniAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAgendaActionPerformed(evt);
            }
        });
        menuCadastro.add(mniAgenda);

        menuBar.add(menuCadastro);

        menuAcesso.setMnemonic('h');
        menuAcesso.setText("Acesso");

        mniSessao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mniSessao.setMnemonic('c');
        mniSessao.setText("Verificar Sessão");
        mniSessao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSessaoActionPerformed(evt);
            }
        });
        menuAcesso.add(mniSessao);

        menuBar.add(menuAcesso);

        menuRelatorios.setMnemonic('h');
        menuRelatorios.setText("Relatórios");

        mniRelClientes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mniRelClientes.setMnemonic('c');
        mniRelClientes.setText("Relatório de Clientes");
        mniRelClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniRelClientesActionPerformed(evt);
            }
        });
        menuRelatorios.add(mniRelClientes);

        menuBar.add(menuRelatorios);

        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mniClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniClientesActionPerformed
        if(clientes == null){
            
           
            clientes = new IFrmClientes();
            
        }
        
        if(!clientes.isVisible()){
            
            dskPrincipal.add(this.clientes);
            clientes.setPosicao();
            clientes.show();
            
        }
        
        try {
            
            clientes.toFront();
        
            clientes.setSelected(true);
        } catch (Exception error) {
            error.printStackTrace();
        }
        
    }//GEN-LAST:event_mniClientesActionPerformed

    private void mniColaboradoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniColaboradoresActionPerformed
        
        
        if(colaborador == null){
           
            colaborador = new IFrmColaboradores();
            
        }
        
        if(!colaborador.isVisible()){
            
            dskPrincipal.add(this.colaborador);
            colaborador.setPosicao();
            colaborador.show();
            
        }
        
        try {
            
            colaborador.toFront();
        
            colaborador.setSelected(true);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }//GEN-LAST:event_mniColaboradoresActionPerformed

    private void mniFornecedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniFornecedoresActionPerformed
        
        
        if(fornecedor == null){
           
            fornecedor = new IFrmFornecedores();
            
        }
        
        if(!fornecedor.isVisible()){
            
            dskPrincipal.add(this.fornecedor);
            fornecedor.setPosicao();
            fornecedor.show();
            
        }
        
        try {
            
            fornecedor.toFront();
        
            fornecedor.setSelected(true);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }//GEN-LAST:event_mniFornecedoresActionPerformed

    private void mniPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniPedidoActionPerformed
        
        
        if(pedido == null){
           
            pedido = new IFrmPedido();
            
        }
        
        if(!pedido.isVisible()){
            
            dskPrincipal.add(this.pedido);
            pedido.setPosicao();
            pedido.show();
            
        }
        
        try {
            
            pedido.toFront();
        
            pedido.setSelected(true);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }//GEN-LAST:event_mniPedidoActionPerformed

    private void mniProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniProdutoActionPerformed
        
        
        if(produto == null){
           
            produto = new IFrmProdutos();
            
        }
        
        if(!produto.isVisible()){
            
            dskPrincipal.add(this.produto);
            produto.setPosicao();
            produto.show();
            
        }
        
        try {
            
            produto.toFront();
        
            produto.setSelected(true);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }//GEN-LAST:event_mniProdutoActionPerformed

    private void mniServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniServicosActionPerformed
         if(servico == null){
           
            servico = new IFrmServicos();
            
        }
        
        if(!servico.isVisible()){
            
            dskPrincipal.add(this.servico);
            servico.setPosicao();
            servico.show();
            
        }
        
        try {
            
            servico.toFront();
        
            servico.setSelected(true);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }//GEN-LAST:event_mniServicosActionPerformed

    private void mniAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAgendaActionPerformed
        if(agenda == null){
            
           
                agenda = new IFrmAgendamento();

                }

                if(!agenda.isVisible()){

                    dskPrincipal.add(this.agenda);
                    agenda.setPosicao();
                    agenda.show();

                }

                try {

                    agenda.toFront();

                    agenda.setSelected(true);
                } catch (Exception error) {
                    error.printStackTrace();
                }
    }//GEN-LAST:event_mniAgendaActionPerformed

    private void mniTerceirosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTerceirosActionPerformed
        
        
        if(terceiro == null){
           
            terceiro = new IFrmTerceiros();
            
        }
        
        if(!terceiro.isVisible()){
            
            dskPrincipal.add(this.terceiro);
            terceiro.setPosicao();
            terceiro.show();
            
        }
        
        try {
            
            terceiro.toFront();
        
            terceiro.setSelected(true);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }//GEN-LAST:event_mniTerceirosActionPerformed

    private void mniServicosTercActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniServicosTercActionPerformed
        if(servTer == null){
           
            servTer = new IFrmServicosTerceiros();
            
        }
        
        if(!servTer.isVisible()){
            
            dskPrincipal.add(this.servTer);
            servTer.setPosicao();
            servTer.show();
            
        }
        
        try {
            
            servTer.toFront();
        
            servTer.setSelected(true);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }//GEN-LAST:event_mniServicosTercActionPerformed

    private void dataAgendaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dataAgendaPropertyChange
        
    }//GEN-LAST:event_dataAgendaPropertyChange

    private void btnOAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOAgendaActionPerformed
        
        spTbl.setVisible(false);
        tblAgendamento.setVisible(false);
    }//GEN-LAST:event_btnOAgendaActionPerformed

    private void btnViAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViAgendaActionPerformed
        carregaAgenda();
        spTbl.setVisible(true);
        tblAgendamento.setVisible(true);
    }//GEN-LAST:event_btnViAgendaActionPerformed

    private void btnCadCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadCliActionPerformed
         if(clientes == null){
            
           
            clientes = new IFrmClientes();
            
        }
        
        if(!clientes.isVisible()){
            
            dskPrincipal.add(this.clientes);
            clientes.setPosicao();
            clientes.show();
            
        }
        
        try {
            
            clientes.toFront();
        
            clientes.setSelected(true);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }//GEN-LAST:event_btnCadCliActionPerformed

    private void btnCadProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdActionPerformed
        
        if(produto == null){
           
            produto = new IFrmProdutos();
            
        }
        
        if(!produto.isVisible()){
            
            dskPrincipal.add(this.produto);
            produto.setPosicao();
            produto.show();
            
        }
        
        try {
            
            produto.toFront();
        
            produto.setSelected(true);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }//GEN-LAST:event_btnCadProdActionPerformed

    private void btnCadServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadServActionPerformed
         if(servico == null){
           
            servico = new IFrmServicos();
            
        }
        
        if(!servico.isVisible()){
            
            dskPrincipal.add(this.servico);
            servico.setPosicao();
            servico.show();
            
        }
        
        try {
            
            servico.toFront();
        
            servico.setSelected(true);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }//GEN-LAST:event_btnCadServActionPerformed

    private void btnAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendaActionPerformed
        if(agenda == null){
            
           
                agenda = new IFrmAgendamento();

                }

                if(!agenda.isVisible()){

                    dskPrincipal.add(this.agenda);
                    agenda.setPosicao();
                    agenda.show();

                }

                try {

                    agenda.toFront();

                    agenda.setSelected(true);
                } catch (Exception error) {
                    error.printStackTrace();
                }
    }//GEN-LAST:event_btnAgendaActionPerformed

    private void txtUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusGained
        txtUsuario.grabFocus();
    }//GEN-LAST:event_txtUsuarioFocusGained

    private void txtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusLost
        
    }//GEN-LAST:event_txtUsuarioFocusLost

    private void btnLogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogarActionPerformed
        if(verificaLogin(txtUsuario.getText(), txtSenha.getText())){
            
            JOptionPane.showMessageDialog(this, "Login Realizado com sucesso");
            this.setVisible(true);
            this.setEnabled(true);
            dskPrincipal.setVisible(true);
            dskPrincipal.setEnabled(true);
            
            login.dispose();
            
            String acessoAux = lblControle.getText();
        int tamanho = acessoAux.length();
        String acesso = acessoAux.substring(0,3);
        
                    if(acesso.equals("NRM")){
                        mniAgenda.setEnabled(false);
                        mniClientes.setEnabled(false);
                        mniColaboradores.setEnabled(false);
                        mniFornecedores.setEnabled(false);
                        mniPedido.setEnabled(false);
                        mniProduto.setEnabled(false);
                        mniServicos.setEnabled(false);
                        mniTerceiros.setEnabled(false);
                        
                        btnAgenda.setEnabled(false);
                        btnCadCli.setEnabled(false);
                        btnCadProd.setEnabled(false);
                        btnCadServ.setEnabled(false);
                        
                    }else if(acesso.equals("PSL")){
                        mniAgenda.setEnabled(false);
                        mniClientes.setEnabled(false);
                        mniColaboradores.setEnabled(false);
                        mniFornecedores.setEnabled(false);
                        mniPedido.setEnabled(false);
                        mniProduto.setEnabled(false);
                        mniServicos.setEnabled(false);
                        mniTerceiros.setEnabled(false);
                        
                        btnAgenda.setEnabled(false);
                        btnCadCli.setEnabled(false);
                        btnCadProd.setEnabled(false);
                        btnCadServ.setEnabled(false);
                        
                        for(int auxAc = 3; auxAc < tamanho; auxAc+=3 ){
                            if(acessoAux.substring(auxAc, auxAc+3).equals("CAG")){
                                btnAgenda.setEnabled(true);
                                mniAgenda.setEnabled(true);
                            }
                        }
                        
                        for(int auxAc = 3; auxAc < tamanho; auxAc+=3 ){
                            if(acessoAux.substring(auxAc, auxAc+3).equals("CCL")){
                                btnCadCli.setEnabled(true);
                                mniClientes.setEnabled(true);
                            }
                        }
                        
                        for(int auxAc = 3; auxAc < tamanho; auxAc+=3 ){
                            if(acessoAux.substring(auxAc, auxAc+3).equals("CCO")){
                                
                                mniColaboradores.setEnabled(true);
                            }
                        }
                        
                        
                        for(int auxAc = 3; auxAc < tamanho; auxAc+=3 ){
                            if(acessoAux.substring(auxAc, auxAc+3).equals("CPD")){
                                btnCadProd.setEnabled(true);
                                mniProduto.setEnabled(true);
                                
                            }
                        }
                        
                        
                        for(int auxAc = 3; auxAc < tamanho; auxAc+=3 ){
                            if(acessoAux.substring(auxAc, auxAc+3).equals("CSV")){
                                btnCadServ.setEnabled(true);
                                mniServicos.setEnabled(true);
                            }
                        }
                        
                    }
        }else {
            JOptionPane.showMessageDialog(this, "usuário ou senha Incorretos\n Por favor Tente novamento", "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_btnLogarActionPerformed

    private void mniSessaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSessaoActionPerformed
        acesso.setVisible(true);
        acesso.toFront();
    }//GEN-LAST:event_mniSessaoActionPerformed

    private void mniRelClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniRelClientesActionPerformed
        if(rel == null){
            
           
            rel = new IFrmRelatorioAgendamento();
            
        }
        
        if(!rel.isVisible()){
            
            dskPrincipal.add(this.rel);
            rel.setPosicao();
            rel.show();
            
        }
        
        try {
            
            rel.toFront();
        
            rel.setSelected(true);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }//GEN-LAST:event_mniRelClientesActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tela_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tela_Principal().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JFrame acesso;
    public javax.swing.JButton btnAgenda;
    public javax.swing.JButton btnCadCli;
    public javax.swing.JButton btnCadProd;
    public javax.swing.JButton btnCadServ;
    public javax.swing.JButton btnLogar;
    public javax.swing.JButton btnOAgenda;
    public javax.swing.JButton btnViAgenda;
    public com.toedter.calendar.JCalendar dataAgenda;
    private javax.swing.JDesktopPane dskPrincipal;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JLabel lblControle;
    public javax.swing.JLabel lblTpAcesso;
    public javax.swing.JLabel lblUser;
    public javax.swing.JFrame login;
    public javax.swing.JMenu menuAcesso;
    public javax.swing.JMenuBar menuBar;
    public javax.swing.JMenu menuCadastro;
    public javax.swing.JMenu menuRelatorios;
    public javax.swing.JMenuItem mniAgenda;
    public javax.swing.JMenuItem mniClientes;
    public javax.swing.JMenuItem mniColaboradores;
    public javax.swing.JMenuItem mniFornecedores;
    public javax.swing.JMenuItem mniPedido;
    public javax.swing.JMenuItem mniProduto;
    public javax.swing.JMenuItem mniRelClientes;
    public javax.swing.JMenuItem mniServicos;
    public javax.swing.JMenuItem mniServicosTerc;
    public javax.swing.JMenuItem mniSessao;
    public javax.swing.JMenuItem mniTerceiros;
    public javax.swing.JScrollPane spTbl;
    public javax.swing.JTable tblAgendamento;
    public javax.swing.JPasswordField txtSenha;
    public javax.swing.JFormattedTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

    
}
