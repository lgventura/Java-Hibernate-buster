package view;

import controller.CategoriaProduto;
import controller.Cidades;
import controller.Endereco;
import controller.Estado;
import controller.Estoque;
import controller.Fornecedor;
import controller.PedidoCompra;
import java.awt.Dimension;

import org.hibernate.Session;
import org.hibernate.Query;

import dao.DAO_PROJETO;
import controller.Produto;
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
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author lgustavo
 */
public class IFrmProdutos extends javax.swing.JInternalFrame {
    
    private DefaultListModel dlm ; //Usado p/Criação da lista com os dados vindo do BD
    private DefaultTableModel dtm; //Usado p/ Criação da Tabela com os dados vindo do BD
    private DefaultTableModel dtmE;
    private DefaultTableModel dtmP;
    private int operacao = 0;
    
    private void limparCampos(){//Limpa todos os campos após o cadastro

        txtBuscaProduto.setText("");
        txtNomeProd.setText("");
        txtObs.setText("");
        txtFabricante.setText("");
        txtIdPedido.setText("");
        txtObs.setText("");
        cbxCategoria.setSelectedIndex(0);
        cbxUnidadeM.setSelectedIndex(0);
        chbSPedido.setSelected(false);
        dataValidade.setDate(new java.util.Date());
        spnQuantidade.setValue(0);
        spnQuantidadeM.setValue(0);
        spnValorCompra.setValue(0);
        spnValorVenda.setValue(0);
        
        txtNomeProd.requestFocus();
    }
    
    
    private void habilitarCampos(){
        txtNomeProd.setEnabled(true);
        txtObs.setEnabled(true);
        txtFabricante.setEnabled(true);
        txtIdPedido.setEnabled(true);
        txtObs.setEnabled(true);
        dataValidade.setEnabled(true);
        cbxCategoria.setEnabled(true);
        cbxUnidadeM.setEnabled(true);
        chbSPedido.setEnabled(true);
        
        spnQuantidade.setEnabled(true);
        spnQuantidadeM.setEnabled(true);
        spnValorCompra.setEnabled(true);
        spnValorVenda.setEnabled(true);
        
    }
    
    private void desabilitarCampos(){
        txtNomeProd.setEnabled(false);
        txtObs.setEnabled(false);
        txtFabricante.setEnabled(false);
        txtIdPedido.setEnabled(false);
        txtObs.setEnabled(false);
        dataValidade.setEnabled(false);
        cbxCategoria.setEnabled(false);
        cbxUnidadeM.setEnabled(false);
        chbSPedido.setEnabled(false);
        
        spnQuantidade.setEnabled(false);
        spnQuantidadeM.setEnabled(false);
        spnValorCompra.setEnabled(false);
        spnValorVenda.setEnabled(false);
    }
    
    
    private void preencherCampos(int linha, String valor){
        String sql = "from Produto order by nome";
        int aux = 0;
        
        if(valor != null){
            sql = " from Produto where like '%"+valor+"%' order by nome";
        }
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Produto p;
            
            while(i.hasNext()){
                p = (Produto)i.next();
                if(aux == linha){
                    txtNomeProd.setText(p.getNome());
                    txtFabricante.setText(p.getFabricante());
                    txtIdPedido.setText(String.valueOf(p.getPedidoCompra().getIdPedido()));
                    txtObs.setText(p.getObservacoes());
                    dataValidade.setDate(p.getDataValidade());
                    cbxCategoria.removeAllItems();
                    cbxCategoria.addItem(p.getCategoriaProduto().getNomeCategoria());
                    
                    for(int auxCbx = 0; auxCbx < cbxUnidadeM.getItemCount(); auxCbx++){
                        if(cbxUnidadeM.getItemAt(auxCbx).equals(p.getUnidadeDeMedida())){
                            cbxUnidadeM.setSelectedIndex(auxCbx);
                        }
                    }
                    
                    if(p.getSPedido() == 1){
                        chbSPedido.setSelected(true);
                    }else{
                        chbSPedido.setSelected(false);
                    }
                    
                    spnQuantidade.setValue(p.getQuantidade());
                    spnQuantidadeM.setValue(p.getQuantidadeMedida());
                    spnValorCompra.setValue(p.getValorCompra());
                    spnValorVenda.setValue(p.getValorVenda());
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void carregaTabelaPedidoCompras(){//carrega a tabela de PedidoCompras ordenando pelo nome
        String sql = "from PedidoCompra order by idPedido";
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtmP.setNumRows(0);
            
            PedidoCompra p;
            
            while(i.hasNext()){
                p = (PedidoCompra)i.next();
                dtmP.addRow(p.getPedidoCompra());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
    }
    
    private int pegarIdPorLinha(int linha, String valor){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from Produto order by nome ";
        int aux = 0;
        int id = 0;
        
        if(valor != null ){
            sql = " from Produto where nome like '%"+valor+"%' order by nome";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Produto p;
            
            while(i.hasNext()){
                p = (Produto)i.next();
                if(aux == linha){
                    id = p.getIdProduto();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id;
    }
    
    private int pegarIdEstoque(String produto){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from Estoque ";
        
        int id = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Estoque e;
            
            while(i.hasNext()){
                e = (Estoque)i.next();
                
                    if(produto.equals(e.getProduto().getNome())){
                        id = e.getIdEstoque();
                    }
                    
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id;
    }
    
    private double pegarQuantidadeEstoque(String produto){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from Estoque ";
        
        double qnt = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Estoque e;
            
            while(i.hasNext()){
                e = (Estoque)i.next();
                
                    if(produto.equals(e.getProduto().getNome())){
                        qnt = e.getQuantidade();
                    }
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return qnt;
    }
    
    private int pegarIdCategoria(int index){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from CategoriaProduto order by idCategoria ";
        int aux = 0;
        int id = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            CategoriaProduto c;
            
            while(i.hasNext()){
                c = (CategoriaProduto)i.next();
                if(aux == index){
                    id = c.getIdCategoria();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id;
    }
    
    private void preencherCategoria(){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from CategoriaProduto order by idCategoria ";
        
        cbxCategoria.removeAllItems();
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            CategoriaProduto c;
            
            while(i.hasNext()){
                c = (CategoriaProduto)i.next();
                
                cbxCategoria.addItem(c.getNomeCategoria());
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
    
    private void carregaTabelaProdutos(String valor){//carrega a tabela de Produtos ordenando pelo nome
        String sql = "from Produto order by nome ";
        
        if(valor != null){
            sql = " from Produto where nome like '%"+valor+"%' order by nome";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtm.setNumRows(0);
            
            Produto p;
            
            while(i.hasNext()){
                p = (Produto)i.next();
                dtm.addRow(p.getProduto());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
    }
    
    private void carregaTabelaEstoque(String valor){//carrega a tabela de Produtos ordenando pelo nome
        String sql = "from Estoque as e order by e.produto.nome ";
        
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
                dtmE.addRow(e.getEstoque());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
    }
    
    private void carregarListaNomes(String nome){
       
        String sql = "from Produto order by nome";
        
        if(nome != null){
            sql = " from Produto where nome like '%"+nome+"%'";
        }
        
        dlm.removeAllElements();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Produto p;
            
            while(i.hasNext()){
                p = (Produto)i.next();
                dlm.add(dlm.getSize(), p.getNome());
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
    }
    
    public IFrmProdutos() {
        initComponents();
        
        listProdutos.setModel(new DefaultListModel());
        dlm = (DefaultListModel) listProdutos.getModel();
        dtm = (DefaultTableModel)tblProdutos.getModel();
        dtmE = (DefaultTableModel)tblEstoque.getModel();
        dtmP = (DefaultTableModel)tblPedido.getModel();
        carregarListaNomes(null);
        carregaTabelaProdutos(null);
        carregaTabelaEstoque(null);
        btnSalvar.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnDesbloquear.setEnabled(false);
        btnSelPedido.setEnabled(false);
        btnAddCategoria.setEnabled(false);
        
        preencherCategoria();
        
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

        frmCategoria = new javax.swing.JFrame();
        jLabel21 = new javax.swing.JLabel();
        btnCadCategoria = new javax.swing.JButton();
        txtCategoria = new javax.swing.JTextField();
        frmPedido = new javax.swing.JFrame();
        btnSeleciona = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPedido = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        tpnCadastrar = new javax.swing.JTabbedPane();
        pnEstoque = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnBusca1 = new javax.swing.JButton();
        txtBNome1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblEstoque = new javax.swing.JTable();
        pnCadastro = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomeProd = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFabricante = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        spnQuantidade = new javax.swing.JSpinner();
        scrolLista = new javax.swing.JScrollPane();
        listProdutos = new javax.swing.JList<>();
        scrolObs = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        txtBuscaProduto = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        spnValorCompra = new javax.swing.JSpinner();
        spnValorVenda = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        cbxUnidadeM = new javax.swing.JComboBox<>();
        spnQuantidadeM = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dataValidade = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        cbxCategoria = new javax.swing.JComboBox<>();
        btnAddCategoria = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtIdPedido = new javax.swing.JTextField();
        chbSPedido = new javax.swing.JCheckBox();
        btnSelPedido = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnDesbloquear = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        pnExibir = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        btnAltera = new javax.swing.JButton();
        btnDeleta = new javax.swing.JButton();
        btnBusca = new javax.swing.JButton();
        txtBNome = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();

        frmCategoria.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmCategoria.setTitle("Adicionar Nova Categoria de Produto");
        frmCategoria.setMinimumSize(new java.awt.Dimension(366, 153));

        jLabel21.setText("Cadastrar Categoria:");

        btnCadCategoria.setText("Adicionar");
        btnCadCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmCategoriaLayout = new javax.swing.GroupLayout(frmCategoria.getContentPane());
        frmCategoria.getContentPane().setLayout(frmCategoriaLayout);
        frmCategoriaLayout.setHorizontalGroup(
            frmCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmCategoriaLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(frmCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmCategoriaLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(frmCategoriaLayout.createSequentialGroup()
                        .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCadCategoria)))
                .addGap(46, 46, 46))
        );
        frmCategoriaLayout.setVerticalGroup(
            frmCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmCategoriaLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadCategoria))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        frmPedido.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmPedido.setTitle("Adicionar Nova Categoria de Produto");
        frmPedido.setBackground(new java.awt.Color(0, 204, 204));
        frmPedido.setMinimumSize(new java.awt.Dimension(850, 450));

        btnSeleciona.setText("Selecionar Pedido");
        btnSeleciona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaActionPerformed(evt);
            }
        });

        tblPedido.setBackground(new java.awt.Color(0, 204, 204));
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
        jScrollPane2.setViewportView(tblPedido);

        jLabel10.setText("Selecionar Pedido:");

        javax.swing.GroupLayout frmPedidoLayout = new javax.swing.GroupLayout(frmPedido.getContentPane());
        frmPedido.getContentPane().setLayout(frmPedidoLayout);
        frmPedidoLayout.setHorizontalGroup(
            frmPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmPedidoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmPedidoLayout.createSequentialGroup()
                        .addGroup(frmPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
                            .addGroup(frmPedidoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSeleciona)))
                        .addGap(21, 21, 21))
                    .addGroup(frmPedidoLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        frmPedidoLayout.setVerticalGroup(
            frmPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmPedidoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSeleciona)
                .addContainerGap())
        );

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setClosable(true);
        setIconifiable(true);
        setTitle("Produtos");

        pnEstoque.setBackground(new java.awt.Color(0, 204, 204));

        jLabel9.setText("Estoque de Produtos:");

        btnBusca1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5739_Knob_Search.png"))); // NOI18N
        btnBusca1.setText("Buscar");
        btnBusca1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusca1ActionPerformed(evt);
            }
        });

        txtBNome1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtBNome1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBNome1ActionPerformed(evt);
            }
        });

        jLabel18.setText("Buscar Produto:");

        tblEstoque.setBackground(new java.awt.Color(0, 153, 153));
        tblEstoque.setForeground(new java.awt.Color(255, 255, 255));
        tblEstoque.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nome do Produto", "Unidade de Medida", "Quantidade", "Quantidade por Medida"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblEstoque);
        if (tblEstoque.getColumnModel().getColumnCount() > 0) {
            tblEstoque.getColumnModel().getColumn(0).setPreferredWidth(110);
            tblEstoque.getColumnModel().getColumn(1).setPreferredWidth(60);
            tblEstoque.getColumnModel().getColumn(2).setPreferredWidth(50);
            tblEstoque.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        javax.swing.GroupLayout pnEstoqueLayout = new javax.swing.GroupLayout(pnEstoque);
        pnEstoque.setLayout(pnEstoqueLayout);
        pnEstoqueLayout.setHorizontalGroup(
            pnEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEstoqueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(pnEstoqueLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 401, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBNome1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBusca1)))
                .addContainerGap())
        );
        pnEstoqueLayout.setVerticalGroup(
            pnEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEstoqueLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBNome1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBusca1)
                    .addComponent(jLabel18)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tpnCadastrar.addTab("Estoque", pnEstoque);

        pnCadastro.setBackground(new java.awt.Color(184, 168, 214));
        pnCadastro.setMaximumSize(new java.awt.Dimension(1062, 597));
        pnCadastro.setMinimumSize(new java.awt.Dimension(1062, 597));

        jLabel3.setText("Nome:");

        jLabel4.setText("Fabricante:");

        jLabel1.setText("Quantidade:");
        jLabel1.setToolTipText("Quantidade em unidades");

        spnQuantidade.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        spnQuantidade.setToolTipText("Quantidade em unidades");

        listProdutos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listProdutos.setMinimumSize(new java.awt.Dimension(45, 80));
        listProdutos.setPreferredSize(new java.awt.Dimension(45, 83));
        listProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listProdutosMouseClicked(evt);
            }
        });
        listProdutos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                listProdutosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                listProdutosKeyReleased(evt);
            }
        });
        scrolLista.setViewportView(listProdutos);

        txtObs.setColumns(20);
        txtObs.setRows(5);
        scrolObs.setViewportView(txtObs);

        jLabel15.setText("Observações sobre o Produto:");

        txtBuscaProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscaProdutoKeyReleased(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5739_Knob_Search.png"))); // NOI18N
        jLabel16.setToolTipText("Buscar por produtos Cadastrados");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5750_Knob_Cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar Operação");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel19.setText("Valor Unitário de Compra:");

        spnValorCompra.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 0.1d));
        spnValorCompra.setToolTipText("");

        spnValorVenda.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 0.1d));
        spnValorVenda.setToolTipText("");

        jLabel20.setText("Valor de Venda:");

        jLabel22.setText("Unidade de Medida:");

        cbxUnidadeM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "kg", "ml", "un" }));
        cbxUnidadeM.setToolTipText("kg = Kilo, ml = Litros, un = Unidade");

        spnQuantidadeM.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        spnQuantidadeM.setToolTipText("Quantidade em unidades");

        jLabel5.setText("Quantidade por Produto:");
        jLabel5.setToolTipText("Quantidade de acordo com a medida");

        jLabel2.setText("Data de validade:");

        jLabel6.setText("Categoria do Produto:");

        cbxCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAddCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        btnAddCategoria.setToolTipText("Adicionar uma nova Cateoria de Produto");
        btnAddCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCategoriaActionPerformed(evt);
            }
        });

        jLabel8.setText("Pedido:");

        txtIdPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdPedidoActionPerformed(evt);
            }
        });

        chbSPedido.setText("Produto sem Pedido?");
        chbSPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbSPedidoActionPerformed(evt);
            }
        });

        btnSelPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bt_up.png"))); // NOI18N
        btnSelPedido.setText("Selecionar Pedido");
        btnSelPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelPedidoActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(166, 80, 189));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5113_add_48.png"))); // NOI18N
        btnNovo.setToolTipText("Adicionar Novo Produto");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDesbloquear, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDesbloquear, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
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
                        .addComponent(txtBuscaProduto))
                    .addComponent(scrolLista, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrolObs)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCadastroLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnCancelar))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCadastroLayout.createSequentialGroup()
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNomeProd, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4))
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(spnValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(spnValorVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addGap(43, 43, 43)
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxUnidadeM, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCadastroLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSelPedido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chbSPedido))
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAddCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(spnQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(jLabel5)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnQuantidadeM, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dataValidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(17, 17, 17))
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        pnCadastroLayout.setVerticalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(txtBuscaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrolLista))
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtIdPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chbSPedido)
                            .addComponent(btnSelPedido))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNomeProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(spnValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(spnValorVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(cbxUnidadeM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(spnQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(spnQuantidadeM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addComponent(dataValidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddCategoria))
                        .addGap(29, 29, 29)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrolObs, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar)
                .addGap(64, 64, 64))
        );

        tpnCadastrar.addTab("Cadastrar", pnCadastro);

        pnExibir.setBackground(new java.awt.Color(0, 204, 204));

        tblProdutos.setBackground(new java.awt.Color(0, 153, 153));
        tblProdutos.setForeground(new java.awt.Color(255, 255, 255));
        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome do Produto", "Fabricante", "Valor da Compra", "Valor da Venda", "Unidade de Medida", "D. Validade", "Quantidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProdutos);
        if (tblProdutos.getColumnModel().getColumnCount() > 0) {
            tblProdutos.getColumnModel().getColumn(0).setPreferredWidth(110);
            tblProdutos.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblProdutos.getColumnModel().getColumn(1).setHeaderValue("Fabricante");
            tblProdutos.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblProdutos.getColumnModel().getColumn(2).setHeaderValue("Valor da Compra");
            tblProdutos.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblProdutos.getColumnModel().getColumn(3).setHeaderValue("Valor da Venda");
            tblProdutos.getColumnModel().getColumn(4).setPreferredWidth(60);
            tblProdutos.getColumnModel().getColumn(5).setPreferredWidth(60);
            tblProdutos.getColumnModel().getColumn(5).setHeaderValue("D. Validade");
            tblProdutos.getColumnModel().getColumn(6).setPreferredWidth(50);
        }

        jLabel7.setText("Produtos Cadastrados:");

        btnAltera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_edit.png"))); // NOI18N
        btnAltera.setText("Alterar Produto");
        btnAltera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlteraActionPerformed(evt);
            }
        });

        btnDeleta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_delete.png"))); // NOI18N
        btnDeleta.setText("Excluir Produto");
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

        txtBNome.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtBNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBNomeActionPerformed(evt);
            }
        });

        jLabel17.setText("Buscar Produto:");

        javax.swing.GroupLayout pnExibirLayout = new javax.swing.GroupLayout(pnExibir);
        pnExibir.setLayout(pnExibirLayout);
        pnExibirLayout.setHorizontalGroup(
            pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnExibirLayout.createSequentialGroup()
                .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnExibirLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnAltera)
                        .addGap(17, 17, 17)
                        .addComponent(btnDeleta)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnExibirLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 398, Short.MAX_VALUE)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBNome, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBusca)))
                .addContainerGap())
        );
        pnExibirLayout.setVerticalGroup(
            pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnExibirLayout.createSequentialGroup()
                .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnExibirLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel7))
                    .addGroup(pnExibirLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBusca)
                            .addComponent(jLabel17))))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(pnExibirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAltera)
                    .addComponent(btnDeleta)))
        );

        tpnCadastrar.addTab("Exibir", pnExibir);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tpnCadastrar))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnCadastrar)
        );

        setBounds(200, 100, 1085, 621);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBNomeActionPerformed

    private void btnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaActionPerformed
       if(!txtBuscaProduto.getText().equals("")){
            carregaTabelaProdutos(txtBNome.getText());
        }else{
            carregaTabelaProdutos(null);
        }
    }//GEN-LAST:event_btnBuscaActionPerformed

    private void btnDeletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletaActionPerformed
         int linha = tblProdutos.getSelectedRow();
         int id = 0;

        if(!txtBNome.getText().equals("")){
            id = pegarIdPorLinha(linha,txtBNome.getText());
        }else{
            id = pegarIdPorLinha(linha,null);
        }
         
        Object[] op = { "Cancelar", "Confirmar" };
        if(linha >= 0){
            int opc = JOptionPane.showOptionDialog(IFrmProdutos.this,"Deseja Confirmar a Exclusção do Produto: "+dtm.getValueAt(linha, 0).toString(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, op, op[1]);
           
            if(opc == 1){
                Date dataAux = new Date(0, 0, 0);
                CategoriaProduto cat = new CategoriaProduto();
                cat.setIdCategoria(1);
                
                Estado es = new Estado(0, "", "");
                Cidades c = new Cidades(0, es, "");
                Endereco e = new Endereco(c, "", 0, "");
                Fornecedor f = new Fornecedor(e, "", "", "");
                
                PedidoCompra ped = new PedidoCompra(f, "", 0, dataAux, 0, 0, 0, 1);
                ped.setIdPedido(4);
                
                Produto p = new Produto(cat, ped, "", "", 0, 0, 0, 0, dataAux, Byte.parseByte("0"), 0, 0);
                p.setIdProduto(id);
                
                Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
               try{
                    
                    s.beginTransaction();

                    s.delete(p);

                    s.getTransaction().commit();

                    txtBNome.setText("");

                    txtBNome.setEnabled(false);

                    carregaTabelaProdutos(null);
                    carregarListaNomes(null);

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Não se pode excluir o Produto pois tem referencia deste cadastro em outro tela");
                    s.close();
                }
            }
            
        }else {
            JOptionPane.showMessageDialog(IFrmProdutos.this, "Escolha um Produto para Excluir", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeletaActionPerformed

    private void btnAlteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlteraActionPerformed
        limparCampos();
        
        listProdutos.setEnabled(false);
        listProdutos.enable(false);
        habilitarBotao(btnNovo);
        desabilitarBotao(btnSalvar);
        
        txtBuscaProduto.setEnabled(false);
        
        if(tblProdutos.getSelectedRow() >=0 && txtBNome.getText().equals("") ){    
            preencherCampos(tblProdutos.getSelectedRow(), null);
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else if(tblProdutos.getSelectedRow() >=0 && !txtBNome.getText().equals("") ){    
            preencherCampos(tblProdutos.getSelectedRow(), txtBNome.getText());
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else {
            JOptionPane.showMessageDialog(IFrmProdutos.this, "Escolha um Produto para Alterar", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
            operacao = 0;
        }
        
    }//GEN-LAST:event_btnAlteraActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        carregaTabelaProdutos(null);
        carregarListaNomes(null);

        Object[] op = { "Não", "Sim" };
        int opc = 0;

        if(operacao == 1){
            opc = JOptionPane.showOptionDialog(IFrmProdutos.this,"Deseja Cancelar a Operação:\nNovo Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){

                limparCampos();
                desabilitarCampos();
                listProdutos.setEnabled(true);
                txtBuscaProduto.setEnabled(true);
                desabilitarBotao(btnSalvar);
                desabilitarBotao(btnAddCategoria);
                habilitarBotao(btnNovo);
                operacao = 0;

            }

        }else if(operacao == 2 || operacao == 3){
            opc = JOptionPane.showOptionDialog(IFrmProdutos.this,"Deseja Cancelar a Operação:\nAlterar Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){
                limparCampos();
                desabilitarCampos();
                listProdutos.setEnabled(true);
                txtBuscaProduto.setEnabled(true);
                desabilitarBotao(btnAlterar);
                desabilitarBotao(btnAddCategoria);
                desabilitarBotao(btnDesbloquear);
                habilitarBotao(btnNovo);

                operacao = 0;
            }
        }else if(operacao == 0){
            JOptionPane.showMessageDialog(IFrmProdutos.this, "Nenhuma Operaçao foi iniciada");
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnDesbloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesbloquearActionPerformed
        habilitarCampos();
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);
        listProdutos.setEnabled(false);
        txtBuscaProduto.setEnabled(false);
        habilitarBotao(btnAlterar);
        habilitarBotao(btnAddCategoria);
        spnQuantidade.setEnabled(false);
        spnQuantidadeM.setEnabled(false);
        cbxUnidadeM.setEnabled(false);
        operacao = 2;
    }//GEN-LAST:event_btnDesbloquearActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
    
            int linha = 0;
            Object[] op = { "Cancelar", "Confirmar" };
            Object[] op2 = { "Não", "Sim" };
            
            int id = 0;
            int opc = JOptionPane.showOptionDialog(IFrmProdutos.this,"Deseja Confirmar as alterações no Pedido de Compra: ", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){
                
                if(operacao == 2){
                    linha = listProdutos.getSelectedIndex();

                    if(txtBuscaProduto.getText().equals("")){
                        id = pegarIdPorLinha(linha,null);
                    }else{
                        id = pegarIdPorLinha(linha,txtBuscaProduto.getText());
                    }

                }else if(operacao == 3){
                    linha = tblProdutos.getSelectedRow();

                    if(!txtBNome.getText().equals("")){
                        id = pegarIdPorLinha(linha,txtBNome.getText());
                    }else{
                        id = pegarIdPorLinha(linha,null);
                    }
                }

                if(linha >= 0){
                     int unidade = 0;
                Byte pedido = 0;
                
                Double qnt = (double)spnQuantidade.getValue(), valorCompra = (double)spnValorCompra.getValue();
                
                if(chbSPedido.isSelected()){
                    pedido = 1;
                }else {
                    pedido = 0;
                }
                
                if(cbxUnidadeM.getSelectedItem().toString().equals("kg")){
                    unidade = 1;
                }else if(cbxUnidadeM.getSelectedItem().toString().equals("ml")){
                    unidade = 2;
                }else if(cbxUnidadeM.getSelectedItem().toString().equals("un")){
                    unidade = 3;
                }
                
                    try{
                        CategoriaProduto cat = new CategoriaProduto();
                        cat.setIdCategoria(pegarIdCategoria(cbxCategoria.getSelectedIndex()));
                        
                        Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                        s.beginTransaction();

                        PedidoCompra ped = new PedidoCompra();
                        ped.setIdPedido(4);

                        Produto p = new Produto(cat, ped, txtNomeProd.getText(), txtFabricante.getText(), (double)spnValorCompra.getValue(), (double)spnValorVenda.getValue() , unidade , (double)spnQuantidade.getValue() , new Date(dataValidade.getDate().getTime()), pedido, qnt*valorCompra, (double)spnQuantidadeM.getValue() );
                        p.setIdProduto(id);
                        
                        s.merge(p);

                        s.getTransaction().commit();

                    }catch(Exception erro){ erro.printStackTrace();}
                    carregarListaNomes(null);
                    carregaTabelaProdutos(null);
                    limparCampos();
                    desabilitarBotao(btnSalvar);
                    desabilitarBotao(btnAddCategoria);
                    desabilitarCampos();
                    habilitarBotao(btnNovo);
                    listProdutos.setEnabled(true);
                    txtBuscaProduto.setEnabled(true);
                    operacao = 0;

                    JOptionPane.showMessageDialog(IFrmProdutos.this, "Produto alterado com sucesso");

                }else if(JOptionPane.showOptionDialog(IFrmProdutos.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
                    btnCancelarActionPerformed(evt);
                }
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        if(txtNomeProd.getText().equals("") || txtFabricante.getText().equals("") || dataValidade.getDate() == null || (double)spnQuantidade.getValue() <= 0.0 || (double)spnQuantidadeM.getValue() <= 0.0){
            
            JOptionPane.showMessageDialog(IFrmProdutos.this, "Alguns Campos Obrigatórios Não foram Preenchidos", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        }else {
            int opc = JOptionPane.showOptionDialog(IFrmProdutos.this,"Deseja Salvar o Produto de Compra: ", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
            
            if(opc == 1){
                int unidade = 0, idEstoque = 0;
                Byte pedido = 0;
                double qntEstoque = 0;
                
                Double qnt = (double)spnQuantidade.getValue(), valorCompra = (double)spnValorCompra.getValue();
                
                if(cbxUnidadeM.getSelectedItem().toString().equals("kg")){
                    unidade = 1;
                }else if(cbxUnidadeM.getSelectedItem().toString().equals("ml")){
                    unidade = 2;
                }else if(cbxUnidadeM.getSelectedItem().toString().equals("un")){
                    unidade = 3;
                }
                
                try{
                    
                    idEstoque = pegarIdEstoque(txtNomeProd.getText());
                    qntEstoque = pegarQuantidadeEstoque(txtNomeProd.getText());
                    
                    CategoriaProduto cat = new CategoriaProduto();
                    cat.setIdCategoria(pegarIdCategoria(cbxCategoria.getSelectedIndex()));
                    
                    Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                    s.beginTransaction();
                    
                    PedidoCompra ped = new PedidoCompra();
                    
                    if(!chbSPedido.isSelected()){
                        ped.setIdPedido(Integer.parseInt(txtIdPedido.getText()));   
                    }else {
                        ped.setIdPedido(1);   
                    }
                    

                    Produto p = new Produto(cat, ped, txtNomeProd.getText(), txtFabricante.getText(), (double)spnValorCompra.getValue(), (double)spnValorVenda.getValue() , unidade , (double)spnQuantidade.getValue() , new Date(dataValidade.getDate().getTime()), pedido, qnt*valorCompra, (double)spnQuantidadeM.getValue() );
                    
                    s.save(p);
                    
                    if(idEstoque != 0){
                        Estoque e = new Estoque();
                        e.setIdEstoque(idEstoque);
                        
                        e.setProduto(p);
                        e.setQuantidade(qntEstoque+(double)spnQuantidade.getValue());
                        e.setUnidadeDeMedida(unidade);
                        
                        s.merge(e);
                    }else {
                        Estoque e = new Estoque(p, (double)spnQuantidade.getValue(), unidade, (double)spnQuantidadeM.getValue());
                        
                        s.save(e);
                    }

                    s.getTransaction().commit();
                    
                    carregarListaNomes(null);
                    carregaTabelaProdutos(null);
                    carregaTabelaEstoque(null);
                    limparCampos();
                    desabilitarBotao(btnSalvar);
                    desabilitarBotao(btnAddCategoria);
                    desabilitarCampos();
                    habilitarBotao(btnNovo);
                    listProdutos.setEnabled(true);
                    txtBuscaProduto.setEnabled(true);
                    operacao = 0;

                    JOptionPane.showMessageDialog(IFrmProdutos.this, "Produto Salvo com sucesso");

                }catch(Exception erro){ erro.printStackTrace();}
                

            }else if(JOptionPane.showOptionDialog(IFrmProdutos.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
                btnCancelarActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        
        operacao = 1;
        habilitarCampos();
        limparCampos();
        listProdutos.setEnabled(false);
        txtBuscaProduto.setEnabled(false);
        habilitarBotao(btnSalvar);
        habilitarBotao(btnAddCategoria);
        habilitarBotao(btnSelPedido);
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);
        
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtBuscaProdutoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscaProdutoKeyReleased
//        carregarListaNomes(txtBuscaProduto.getText());
    }//GEN-LAST:event_txtBuscaProdutoKeyReleased

    private void listProdutosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listProdutosKeyReleased
        if(operacao == 0){
            limparCampos();
            if(txtBuscaProduto.getText().equals("")){
                preencherCampos(listProdutos.getSelectedIndex(), null);
            }else {
                preencherCampos(listProdutos.getSelectedIndex(), txtBuscaProduto.getText());
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listProdutosKeyReleased

    private void listProdutosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listProdutosKeyPressed
        if(operacao == 0){
            limparCampos();
            if(txtBuscaProduto.getText().equals("")){
                preencherCampos(listProdutos.getSelectedIndex(), null);
            }else {
                preencherCampos(listProdutos.getSelectedIndex(), txtBuscaProduto.getText());
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listProdutosKeyPressed

    private void listProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listProdutosMouseClicked
        if(operacao == 0){
            limparCampos();
            if(txtBuscaProduto.getText().equals("")){
                preencherCampos(listProdutos.getSelectedIndex(), null);
            }else {
                preencherCampos(listProdutos.getSelectedIndex(), txtBuscaProduto.getText());
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listProdutosMouseClicked

    private void btnCadCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadCategoriaActionPerformed
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        if(txtCategoria.getText().equals("")){
            
            JOptionPane.showMessageDialog(IFrmProdutos.this, "Preencha o nome da Categoria", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        }else {
            int opc = JOptionPane.showOptionDialog(IFrmProdutos.this,"Deseja Salvar Categoria?: "+txtCategoria.getText(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
            
            if(opc == 1){
                
                try{
                    Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                    s.beginTransaction();
                    
                    CategoriaProduto c = new CategoriaProduto();
                    c.setNomeCategoria(txtCategoria.getText());
                    
                    s.save(c);

                    s.getTransaction().commit();

                }catch(Exception erro){ erro.printStackTrace();}
                
                txtCategoria.setText("");
                frmCategoria.setVisible(false);
                frmCategoria.toBack();
                preencherCategoria();
                
                JOptionPane.showMessageDialog(IFrmProdutos.this, "Categoria Salva");

            }else if(JOptionPane.showOptionDialog(IFrmProdutos.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
                txtCategoria.setText("");
                frmCategoria.setVisible(false);
                frmCategoria.toBack();
            }
        }
    }//GEN-LAST:event_btnCadCategoriaActionPerformed

    private void btnBusca1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusca1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBusca1ActionPerformed

    private void txtBNome1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBNome1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBNome1ActionPerformed

    private void txtIdPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdPedidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdPedidoActionPerformed

    private void btnAddCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCategoriaActionPerformed
        frmCategoria.setLocationRelativeTo(null);
        frmCategoria.setVisible(true);
        frmCategoria.toFront();
    }//GEN-LAST:event_btnAddCategoriaActionPerformed

    private void btnSelPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelPedidoActionPerformed
        carregaTabelaPedidoCompras();
        frmPedido.setLocationRelativeTo(null);
        frmPedido.setVisible(true);
        frmPedido.toFront();
    }//GEN-LAST:event_btnSelPedidoActionPerformed

    private void btnSelecionaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaActionPerformed
        int linha = tblPedido.getSelectedRow();
        
        if(linha >=0){
            txtIdPedido.setText(dtmP.getValueAt(linha, 0).toString());
            txtIdPedido.setEnabled(false);
            
            txtNomeProd.setText(dtmP.getValueAt(linha, 1).toString());
            txtNomeProd.setEnabled(false);
            
            spnQuantidade.setValue(Double.parseDouble(dtmP.getValueAt(linha, 2).toString()));
            spnQuantidade.setEnabled(false);
            
            spnValorCompra.setValue(Double.parseDouble(dtmP.getValueAt(linha, 3).toString()));
            spnValorCompra.setEnabled(false);
            
            chbSPedido.setEnabled(false);
            
            frmPedido.setVisible(false);
            frmPedido.toBack();
            
        }else {
            JOptionPane.showMessageDialog(IFrmProdutos.this, "Selecione um Pedido na tabela.", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnSelecionaActionPerformed

    private void chbSPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbSPedidoActionPerformed
        if(chbSPedido.isSelected()){
            txtIdPedido.setEnabled(false);
            btnSelPedido.setEnabled(false);
        }else{
            txtIdPedido.setEnabled(true);
            btnSelPedido.setEnabled(true);
        }

    }//GEN-LAST:event_chbSPedidoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCategoria;
    private javax.swing.JButton btnAltera;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnBusca;
    private javax.swing.JButton btnBusca1;
    private javax.swing.JButton btnCadCategoria;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDeleta;
    private javax.swing.JButton btnDesbloquear;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSelPedido;
    private javax.swing.JButton btnSeleciona;
    private javax.swing.JComboBox<String> cbxCategoria;
    private javax.swing.JComboBox<String> cbxUnidadeM;
    private javax.swing.JCheckBox chbSPedido;
    private com.toedter.calendar.JDateChooser dataValidade;
    private javax.swing.JFrame frmCategoria;
    private javax.swing.JFrame frmPedido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> listProdutos;
    private javax.swing.JPanel pnCadastro;
    private javax.swing.JPanel pnEstoque;
    private javax.swing.JPanel pnExibir;
    private javax.swing.JScrollPane scrolLista;
    private javax.swing.JScrollPane scrolObs;
    private javax.swing.JSpinner spnQuantidade;
    private javax.swing.JSpinner spnQuantidadeM;
    private javax.swing.JSpinner spnValorCompra;
    private javax.swing.JSpinner spnValorVenda;
    private javax.swing.JTable tblEstoque;
    private javax.swing.JTable tblPedido;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTabbedPane tpnCadastrar;
    private javax.swing.JTextField txtBNome;
    private javax.swing.JTextField txtBNome1;
    private javax.swing.JTextField txtBuscaProduto;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtFabricante;
    private javax.swing.JTextField txtIdPedido;
    private javax.swing.JTextField txtNomeProd;
    private javax.swing.JTextArea txtObs;
    // End of variables declaration//GEN-END:variables
}
