package view;

import controller.Cidades;
import controller.Endereco;
import controller.Estado;
import java.awt.Dimension;

import org.hibernate.Session;
import org.hibernate.Query;

import dao.DAO_PROJETO;
import controller.PedidoCompra;
import controller.Fornecedor;
import java.awt.event.ActionEvent;
import static java.lang.Double.parseDouble;
import java.sql.Date;

import java.text.ParseException;

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

/**
 *
 * @author lgustavo
 */
public class IFrmPedido extends javax.swing.JInternalFrame {
    
    private DefaultListModel dlm ; //Usado p/Criação da lista com os dados vindo do BD
    private DefaultTableModel dtm; //Usado p/ Criação da Tabela com os dados vindo do BD
    private int operacao = 0;
    
    private void limparCampos(){//Limpa todos os campos após o cadastro
        txtNomeProd.setText("");
        spnQuantidade.setValue(0);
        txtVTotal.setText("");
        spnVUni.setValue(0);
        dataPedido.setDate(null);
        carregaCbxFornecedores();
        
        cbxFornecedor.setSelectedIndex(0);
        
        txtNomeProd.requestFocus();
    }
    
    private void preencherCampos(int linha, String valor){
        String sql = "from PedidoCompra order by idPedido";
        int aux = 0;
        
        if(valor != null){
            sql = " from PedidoCompra where nomeProduto like '%"+valor+"%' order by idProduto";
        }
        
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            PedidoCompra p;
            
            while(i.hasNext()){
                p = (PedidoCompra)i.next();
                if(aux == linha){
                    txtNomeProd.setText(p.getNomeProduto());
                    spnQuantidade.setValue(p.getQuantidade());
                    txtVTotal.setText(String.valueOf(p.getValorPedido()));
                    spnVUni.setValue(p.getValorUnitario());
                    dataPedido.setDate(p.getDataPedido());
                    
                    cbxFornecedor.removeAllItems();
                    cbxFornecedor.addItem(p.getFornecedor().getRazaoSocial());
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
}
    
    private int pegarIdPorLinha(int linha, String valor){
        String sql = "from PedidoCompra order by idPedido ";
        int aux = 0;
        int id = 0;
        
        if(valor != null){
            sql = " from PedidoCompra where nomeProduto like '%"+valor+"%' order by idPedido";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            PedidoCompra p;
            
            while(i.hasNext()){
                p = (PedidoCompra)i.next();
                if(aux == linha){
                    id = p.getIdPedido();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id; //Retorna um array com os ids de endereço, pessoa e PedidoCompra
    }
    
    private void habilitarCampos(){
        
        txtNomeProd.setEnabled(true);
        spnQuantidade.setEnabled(true);
        spnVUni.setEnabled(true);
        txtVTotal.setEnabled(true);
        dataPedido.setEnabled(true);
        cbxFornecedor.setEnabled(true);
        
    }
    
    private void desabilitarCampos(){
        txtNomeProd.setEnabled(false);
        spnQuantidade.setEnabled(false);
        txtVTotal.setEnabled(false);
        spnVUni.setEnabled(false);
        dataPedido.setEnabled(false);
        cbxFornecedor.setEnabled(false);
    }
    
    private void habilitarBotao(JButton btn){
        btn.setEnabled(true);
    }
    
    private void desabilitarBotao(JButton btn){
        btn.setEnabled(false);
    }
    
    private void carregaTabelaPedidoCompras(String valor){//carrega a tabela de PedidoCompras ordenando pelo nome
        String sql = "from PedidoCompra order by idPedido";
        
        if(valor != null){
            sql = " from PedidoCompra where nomeProduto like '%"+valor+"%' order by idPedido";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtm.setNumRows(0);
            
            PedidoCompra p;
            
            while(i.hasNext()){
                p = (PedidoCompra)i.next();
                dtm.addRow(p.getPedidoCompra());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
    }
    
    private void carregarListaPedido(String pedido){
       
        String sql = "from PedidoCompra order by idPedido";
        
        if(pedido != null){
            sql = " from PedidoCompra where nomeProduto like '%"+pedido+"%' order by idPedido";
        }
        
        dlm.removeAllElements();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            PedidoCompra p;
            
            while(i.hasNext()){
                p = (PedidoCompra)i.next();
                dlm.add(dlm.getSize(), p.getNomeProduto());
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
    }
    
    private void carregaCbxFornecedores(){
        String sql = "from Fornecedor order by razaoSocial";
        
        cbxFornecedor.removeAllItems();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Fornecedor f;
            
            while(i.hasNext()){
                f = (Fornecedor)i.next();
                cbxFornecedor.addItem(f.getRazaoSocial());
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
    }
    
    private int setarIdFornecedor(int index){
        String sql = "from Fornecedor order by razaoSocial";
        int aux = 0;
        int id = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().openSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Fornecedor f;
            
            while(i.hasNext()){
                f = (Fornecedor)i.next();
                if(aux == index){
                   id = f.getIdFornecedor();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id;
    }
    
    public IFrmPedido() {
        initComponents();
        
        listRazao.setModel(new DefaultListModel());
        dlm = (DefaultListModel) listRazao.getModel();
        dtm = (DefaultTableModel)tblPedido.getModel();
        carregarListaPedido(null);
        carregaTabelaPedidoCompras(null);
        carregaCbxFornecedores();
        btnSalvar.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnDesbloquear.setEnabled(false);
        
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

        tpnCadastrar = new javax.swing.JTabbedPane();
        pnCadastro = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomeProd = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        scrolLista = new javax.swing.JScrollPane();
        listRazao = new javax.swing.JList<>();
        txtBuscaProd = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        dataPedido = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        spnQuantidade = new javax.swing.JSpinner();
        jLabel18 = new javax.swing.JLabel();
        spnVUni = new javax.swing.JSpinner();
        jLabel21 = new javax.swing.JLabel();
        cbxFornecedor = new javax.swing.JComboBox<>();
        txtVTotal = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnDesbloquear = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        pnExibir = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPedido = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        btnAltera = new javax.swing.JButton();
        btnDeleta = new javax.swing.JButton();
        btnBusca = new javax.swing.JButton();
        txtBPedido = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setClosable(true);
        setIconifiable(true);
        setTitle("Pedido de Compra");
        setMaximumSize(new java.awt.Dimension(629, 510));
        setMinimumSize(new java.awt.Dimension(629, 510));

        pnCadastro.setBackground(new java.awt.Color(184, 168, 214));
        pnCadastro.setMaximumSize(new java.awt.Dimension(1039, 420));
        pnCadastro.setMinimumSize(new java.awt.Dimension(1039, 420));

        jLabel3.setText("Nome do Produto:");

        jLabel4.setText("Valor Total do Pedido:");

        listRazao.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listRazao.setMinimumSize(new java.awt.Dimension(45, 80));
        listRazao.setPreferredSize(new java.awt.Dimension(45, 83));
        listRazao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listRazaoMouseClicked(evt);
            }
        });
        listRazao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                listRazaoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                listRazaoKeyReleased(evt);
            }
        });
        scrolLista.setViewportView(listRazao);

        txtBuscaProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscaProdKeyReleased(evt);
            }
        });

        jLabel16.setText("Buscar:");

        jLabel1.setText("Data do Pedido:");

        jLabel15.setText("Quantidade do Pedido:");

        spnQuantidade.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        jLabel18.setText("Valor unitário:");

        spnVUni.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 0.1d));
        spnVUni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                spnVUniMouseExited(evt);
            }
        });

        jLabel21.setText("Fornecedor:");

        cbxFornecedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxFornecedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbxFornecedorFocusGained(evt);
            }
        });

        txtVTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtVTotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtVTotalFocusGained(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(166, 80, 189));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5750_Knob_Cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar Operação");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnDesbloquear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5051_lock_open_48.png"))); // NOI18N
        btnDesbloquear.setToolTipText("Desbloquear Campos");
        btnDesbloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesbloquearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnDesbloquear, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDesbloquear, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(166, 80, 189));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5113_add_48.png"))); // NOI18N
        btnNovo.setToolTipText("Adicionar novo pedido de compra");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addComponent(txtBuscaProd))
                    .addComponent(scrolLista, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel21))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbxFornecedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(spnVUni)
                                    .addComponent(spnQuantidade)
                                    .addComponent(txtNomeProd, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(dataPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtVTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(376, 376, 376))
        );
        pnCadastroLayout.setVerticalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNomeProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(spnQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(spnVUni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtVTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(dataPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21)
                    .addComponent(cbxFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscaProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addComponent(scrolLista, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        tpnCadastrar.addTab("Cadastrar", pnCadastro);

        pnExibir.setBackground(new java.awt.Color(0, 204, 204));
        pnExibir.setMaximumSize(new java.awt.Dimension(618, 455));
        pnExibir.setMinimumSize(new java.awt.Dimension(618, 455));

        tblPedido.setBackground(new java.awt.Color(0, 153, 153));
        tblPedido.setForeground(new java.awt.Color(255, 255, 255));
        tblPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Pedido", "Nome do Produto", "Quantidade", "Valor Unitário", "Valor Total", "Fornecedor", "Data do Pedido"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPedido.setMaximumSize(new java.awt.Dimension(366, 64));
        tblPedido.setMinimumSize(new java.awt.Dimension(366, 64));
        jScrollPane1.setViewportView(tblPedido);
        if (tblPedido.getColumnModel().getColumnCount() > 0) {
            tblPedido.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblPedido.getColumnModel().getColumn(1).setPreferredWidth(80);
            tblPedido.getColumnModel().getColumn(2).setPreferredWidth(43);
            tblPedido.getColumnModel().getColumn(3).setPreferredWidth(53);
            tblPedido.getColumnModel().getColumn(4).setPreferredWidth(35);
            tblPedido.getColumnModel().getColumn(5).setPreferredWidth(80);
            tblPedido.getColumnModel().getColumn(6).setPreferredWidth(60);
        }

        jLabel7.setText("Pedidos");

        btnAltera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_edit.png"))); // NOI18N
        btnAltera.setText("Alterar Pedido");
        btnAltera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlteraActionPerformed(evt);
            }
        });

        btnDeleta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_delete.png"))); // NOI18N
        btnDeleta.setText("Excluir Pedido");
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

        txtBPedido.setToolTipText("Digite o Nome do Produto");
        txtBPedido.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtBPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBPedidoActionPerformed(evt);
            }
        });

        jLabel17.setText("Buscar Pedido:");

        javax.swing.GroupLayout pnExibirLayout = new javax.swing.GroupLayout(pnExibir);
        pnExibir.setLayout(pnExibirLayout);
        pnExibirLayout.setHorizontalGroup(
            pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnExibirLayout.createSequentialGroup()
                .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnExibirLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnExibirLayout.createSequentialGroup()
                                .addComponent(btnAltera)
                                .addGap(8, 8, 8)
                                .addComponent(btnDeleta)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnExibirLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(btnBusca))))
                    .addGroup(pnExibirLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        pnExibirLayout.setVerticalGroup(
            pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnExibirLayout.createSequentialGroup()
                .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnExibirLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel7)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnExibirLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtBPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnBusca)))
                        .addGap(15, 15, 15)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAltera)
                    .addComponent(btnDeleta))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tpnCadastrar.addTab("Exibir", pnExibir);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 707, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setBounds(200, 100, 713, 510);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBPedidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBPedidoActionPerformed

    private void btnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaActionPerformed
            carregaTabelaPedidoCompras(txtBPedido.getText());
    }//GEN-LAST:event_btnBuscaActionPerformed

    private void btnDeletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletaActionPerformed
         int linha = tblPedido.getSelectedRow();
         int id = 0;

        if(!txtBPedido.getText().equals("")){
            id = pegarIdPorLinha(linha,txtBPedido.getText());
        }else{
            id = pegarIdPorLinha(linha,null);
        }
         
            
        Object[] op = { "Cancelar", "Confirmar" };
        if(linha >= 0){
            int opc = JOptionPane.showOptionDialog(IFrmPedido.this,"Deseja Confirmar a Exclusção do Pedido de Compra: "+dtm.getValueAt(linha, 0).toString(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, op, op[1]);
           
            if(opc == 1){
                
                Estado es = new Estado(0, "", "");
                Cidades c = new Cidades(0, es, "");
                Endereco e = new Endereco(c, "", 0, "");
                Fornecedor f = new Fornecedor(e, "", "", "");
                f.setIdFornecedor(1);
                Date dataAux = new Date(0, 0, 0);
                PedidoCompra p = new PedidoCompra(f, "", 0.0, dataAux , 0, 0.0, 0.0, 0);
                
                p.setIdPedido(id);
                    
                Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                try{
                    
                    s.beginTransaction();

                    s.delete(p);

                    s.getTransaction().commit();

                    txtBPedido.setText("");



                    carregaTabelaPedidoCompras(null);
                    carregarListaPedido(null);
                    
                    JOptionPane.showMessageDialog(IFrmPedido.this, "Pedido de Compra Excluido com sucesso");



                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Não se pode excluir o Pedido pois tem referencia deste cadastro em outro tela");
                    s.close();
                }
            }
            
        }else {
            JOptionPane.showMessageDialog(IFrmPedido.this, "Escolha um Pedido de Compra para Excluir", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
        }
         
    }//GEN-LAST:event_btnDeletaActionPerformed

    private void btnAlteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlteraActionPerformed
        limparCampos();
        
        listRazao.setEnabled(false);
        listRazao.enable(false);
        habilitarBotao(btnNovo);
        desabilitarBotao(btnSalvar);
        
        txtBuscaProd.setEnabled(false);
        
        if(!txtBPedido.getText().equals("") && tblPedido.getSelectedRow() >=0 ){    
            preencherCampos(tblPedido.getSelectedRow(),txtBPedido.getText());
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else if(tblPedido.getSelectedRow() >=0 ){
            preencherCampos(tblPedido.getSelectedRow(), null);
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else {
            JOptionPane.showMessageDialog(IFrmPedido.this, "Escolha um Pedido de Compra para Alterar", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
            operacao = 0;
        }
    }//GEN-LAST:event_btnAlteraActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        carregaTabelaPedidoCompras(null);
        carregarListaPedido(null);

        Object[] op = { "Não", "Sim" };
        int opc = 0;

        if(operacao == 1){
            opc = JOptionPane.showOptionDialog(IFrmPedido.this,"Deseja Cancelar a Operação:\nNovo Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){

                limparCampos();
                desabilitarCampos();
                listRazao.setEnabled(true);
                txtBuscaProd.setEnabled(true);
                desabilitarBotao(btnSalvar);
                habilitarBotao(btnNovo);
                operacao = 0;

            }

        }else if(operacao == 2 || operacao == 3){
            opc = JOptionPane.showOptionDialog(IFrmPedido.this,"Deseja Cancelar a Operação:\nAlterar Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){
                limparCampos();
                desabilitarCampos();
                listRazao.setEnabled(true);
                txtBuscaProd.setEnabled(true);
                desabilitarBotao(btnAlterar);
                desabilitarBotao(btnDesbloquear);
                habilitarBotao(btnNovo);

                operacao = 0;
            }
        }else if(operacao == 0){
            JOptionPane.showMessageDialog(IFrmPedido.this, "Nenhuma Operaçao foi iniciada");
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnDesbloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesbloquearActionPerformed
        habilitarCampos();
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);
        carregaCbxFornecedores();
        listRazao.setEnabled(false);
        txtBuscaProd.setEnabled(false);
        habilitarBotao(btnAlterar);
        operacao = 2;
    }//GEN-LAST:event_btnDesbloquearActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
            int linha = 0;
            Object[] op = { "Cancelar", "Confirmar" };
            
            int id = 0;
            int opc = JOptionPane.showOptionDialog(IFrmPedido.this,"Deseja Confirmar as alterações no Pedido de Compra: ", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){
                
                if(operacao == 2){
                    linha = listRazao.getSelectedIndex();

                    if(txtBuscaProd.getText().equals("")){
                        id = pegarIdPorLinha(linha,null);
                    }else{
                        id = pegarIdPorLinha(linha,txtBuscaProd.getText());
                    }

                }else if(operacao == 3){
                    linha = tblPedido.getSelectedRow();

                    if(!txtBPedido.getText().equals("")){
                        id = pegarIdPorLinha(linha,txtBPedido.getText());
                    }else{
                        id = pegarIdPorLinha(linha,null);
                    }
                }

                if(linha >= 0){
                    try{
                        Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                        s.beginTransaction();

                        Fornecedor f = new Fornecedor(null, "", "", "");
                        f.setIdFornecedor(setarIdFornecedor(cbxFornecedor.getSelectedIndex()));
                        
                        PedidoCompra p = new PedidoCompra(f, txtNomeProd.getText(), Double.valueOf(txtVTotal.getText()), new Date(dataPedido.getDate().getTime()),2, (double)spnQuantidade.getValue() ,(double)spnVUni.getValue(), 1);
                        
                        p.setIdPedido(id);
                        s.merge(p);

                        s.getTransaction().commit();

                    }catch(Exception erro){ erro.printStackTrace();}
                    carregarListaPedido(null);
                    carregaTabelaPedidoCompras(null);
                    limparCampos();
                    desabilitarCampos();
                    desabilitarBotao(btnAlterar);
                    desabilitarBotao(btnDesbloquear);
                    listRazao.setEnabled(true);
                    txtBuscaProd.setEnabled(true);
                    habilitarBotao(btnNovo);

                    operacao = 0;
                    
                    JOptionPane.showMessageDialog(IFrmPedido.this, "Pedido de Compra Alterado com Sucesso");

                }
            }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        if(txtNomeProd.getText().equals("") || (double)spnQuantidade.getValue() <= 0 || (double)spnVUni.getValue() <= 0){

            JOptionPane.showMessageDialog(IFrmPedido.this, "Alguns Campos Obrigatórios Não foram Preenchidos", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        }else {
            int opc = JOptionPane.showOptionDialog(IFrmPedido.this,"Deseja Salvar o Pedido de Compra: ", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
            
            if(opc == 1){
                
                try{
                    Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                    s.beginTransaction();

                    Fornecedor f = new Fornecedor(null, "", "", "");
                    f.setIdFornecedor(setarIdFornecedor(cbxFornecedor.getSelectedIndex()));

                    PedidoCompra p = new PedidoCompra(f, txtNomeProd.getText(), Double.valueOf(txtVTotal.getText()), new Date(dataPedido.getDate().getTime()),2, (double)spnQuantidade.getValue() ,(double)spnVUni.getValue(), 1);

                    s.save(p);

                    s.getTransaction().commit();

                }catch(Exception erro){ erro.printStackTrace();}
                carregarListaPedido(null);
                carregaTabelaPedidoCompras(null);
                limparCampos();
                desabilitarBotao(btnSalvar);
                desabilitarCampos();
                habilitarBotao(btnNovo);
                listRazao.setEnabled(true);
                txtBuscaProd.setEnabled(true);
                operacao = 0;
                
                JOptionPane.showMessageDialog(IFrmPedido.this, "Pedido de Compra Salvo com sucesso");

            }else if(JOptionPane.showOptionDialog(IFrmPedido.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
                btnCancelarActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        operacao = 1;
        habilitarCampos();
        limparCampos();
        listRazao.setEnabled(false);
        txtBuscaProd.setEnabled(false);
        carregaCbxFornecedores();
        habilitarBotao(btnSalvar);
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);
        
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtBuscaProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscaProdKeyReleased
        carregarListaPedido(txtBuscaProd.getText());
    }//GEN-LAST:event_txtBuscaProdKeyReleased

    private void listRazaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listRazaoKeyReleased
        if(operacao == 0){
            limparCampos();
            if(txtBuscaProd.getText().equals("")){
                preencherCampos(listRazao.getSelectedIndex(), null);
            }else {
                preencherCampos(listRazao.getSelectedIndex(), txtBuscaProd.getText());
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listRazaoKeyReleased

    private void listRazaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listRazaoKeyPressed
        if(operacao == 0){
            limparCampos();
            if(txtBuscaProd.getText().equals("")){
                preencherCampos(listRazao.getSelectedIndex(), null);
            }else {
                preencherCampos(listRazao.getSelectedIndex(), txtBuscaProd.getText());
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listRazaoKeyPressed

    private void listRazaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listRazaoMouseClicked
        if(operacao == 0){
            limparCampos();
            if(txtBuscaProd.getText().equals("")){
                preencherCampos(listRazao.getSelectedIndex(), null);
            }else {
                preencherCampos(listRazao.getSelectedIndex(), txtBuscaProd.getText());
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listRazaoMouseClicked

    private void cbxFornecedorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbxFornecedorFocusGained
        carregaCbxFornecedores();
    }//GEN-LAST:event_cbxFornecedorFocusGained

    private void spnVUniMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spnVUniMouseExited
        JOptionPane.showMessageDialog(null, "TEste");
        txtVTotal.setText(String.valueOf(10));
    }//GEN-LAST:event_spnVUniMouseExited

    private void txtVTotalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVTotalFocusGained
        Double qnt = (double)spnQuantidade.getValue(), unidade = (double)spnVUni.getValue();
        txtVTotal.setText(String.valueOf(qnt * unidade));
        txtVTotal.setEditable(false);
    }//GEN-LAST:event_txtVTotalFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAltera;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnBusca;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDeleta;
    private javax.swing.JButton btnDesbloquear;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbxFornecedor;
    private com.toedter.calendar.JDateChooser dataPedido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listRazao;
    private javax.swing.JPanel pnCadastro;
    private javax.swing.JPanel pnExibir;
    private javax.swing.JScrollPane scrolLista;
    private javax.swing.JSpinner spnQuantidade;
    private javax.swing.JSpinner spnVUni;
    private javax.swing.JTable tblPedido;
    private javax.swing.JTabbedPane tpnCadastrar;
    private javax.swing.JTextField txtBPedido;
    private javax.swing.JTextField txtBuscaProd;
    private javax.swing.JTextField txtNomeProd;
    private javax.swing.JTextField txtVTotal;
    // End of variables declaration//GEN-END:variables
}
