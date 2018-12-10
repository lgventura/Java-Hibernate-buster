package view;

import java.awt.Dimension;

import org.hibernate.Session;
import org.hibernate.Query;

import dao.DAO_PROJETO;
import controller.Fornecedor;
import controller.Endereco;
import controller.Estado;
import controller.Cidades;

import java.text.ParseException;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lgustavo
 */
public class IFrmFornecedores extends javax.swing.JInternalFrame {
    
    private DefaultListModel dlm ; //Usado p/Criação da lista com os dados vindo do BD
    private DefaultTableModel dtm; //Usado p/ Criação da Tabela com os dados vindo do BD
    private int operacao = 0;
    
    private void limparCampos(){//Limpa todos os campos após o cadastro
        txtBairro.setText("");
        txtBuscaFornecedor.setText("");
        txtCep.setText("");
        txtCnpj.setText("");
        txtRazao.setText("");
        txtNomaFant.setText("");
        txtRua.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtResponsavel.setText("");
        spnN.setValue(0);
        cbxEstados.setSelectedIndex(0);
        cbxCidades.removeAllItems();
        txtRazao.requestFocus();
    }
    
    private void preencherCampos(int linha, String valor, int teste){
        String sql = "from Fornecedor order by razaoSocial";
        int aux = 0;
        
        if(valor != null && teste == 1){
            sql = " from Fornecedor where razaoSocial like '%"+valor+"%' order by razaoSocial";
        }else if(valor != null && teste == 2){
            sql = " from Fornecedor where cnpjCpf like '%"+valor+"%' order by razaoSocial";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Fornecedor f;
            
            while(i.hasNext()){
                f = (Fornecedor)i.next();
                if(aux == linha){
                    txtRazao.setText(f.getRazaoSocial());
                    txtNomaFant.setText(f.getNomeFantasia());
                    txtCnpj.setText(f.getCnpjCpf());
                    txtTelefone.setText(f.getTelefone());
                    txtEmail.setText(f.getEmail());
                    txtResponsavel.setText(f.getResponsavel());
                    
                    txtRua.setText(f.getEndereco().getRua());
                    spnN.setValue(f.getEndereco().getNumero());
                    txtBairro.setText(f.getEndereco().getBairro());
                    txtCep.setText(f.getEndereco().getCep());
                    cbxEstados.removeAllItems();
                    cbxEstados.addItem(f.getEndereco().getCidades().getEstado().getSigla());
                    cbxCidades.removeAllItems();
                    cbxCidades.addItem(f.getEndereco().getCidades().getNomeCidade());
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private int[] pegarIdPorLinha(int linha, String valor, int teste){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from Fornecedor order by razaoSocial ";
        int aux = 0;
        int[] ids = new int[2];
        
        if(valor != null && teste == 1){
            sql = " from Fornecedor where razaoSocial like '%"+valor+"%' order by razaoSocial";
        }else if(valor != null && teste == 2){
            sql = " from Fornecedor where cnpjCpf like '%"+valor+"%' order by razaoSocial";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Fornecedor f;
            
            while(i.hasNext()){
                f = (Fornecedor)i.next();
                if(aux == linha){
                    ids[0] = f.getIdFornecedor();
                    ids[1] = f.getEndereco().getIdEndereco();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return ids; //Retorna um array com os ids de endereço, pessoa e Fornecedor
    }
    
    private void habilitarCampos(){
        
        txtCnpj.setEnabled(true);
        txtRazao.setEnabled(true);
        txtNomaFant.setEnabled(true);
        txtTelefone.setEnabled(true);
        txtEmail.setEnabled(true);
        txtResponsavel.setEnabled(true);
        
        txtRua.setEnabled(true);
        txtBairro.setEnabled(true);
        txtCep.setEnabled(true);
        spnN.setEnabled(true);
        cbxEstados.setEnabled(true);
        cbxCidades.setEnabled(true);
    }
    
    private void desabilitarCampos(){
        txtCnpj.setEnabled(false);
        txtRazao.setEnabled(false);
        txtNomaFant.setEnabled(false);
        txtTelefone.setEnabled(false);
        txtEmail.setEnabled(false);
        txtResponsavel.setEnabled(false);
        
        txtRua.setEnabled(false);
        txtBairro.setEnabled(false);
        txtCep.setEnabled(false);
        spnN.setEnabled(false);
        cbxEstados.setEnabled(false);
        cbxCidades.setEnabled(false);
    }
    
    private void habilitarBotao(JButton btn){
        btn.setEnabled(true);
    }
    
    private void desabilitarBotao(JButton btn){
        btn.setEnabled(false);
    }
    
    private void carregaTabelaFornecedors(String valor, int teste){//carrega a tabela de Fornecedors ordenando pelo nome
        String sql = "from Fornecedor order by razaoSocial ";
        
        if(valor != null && teste == 1){
            sql = " from Fornecedor where razaoSocial like '%"+valor+"%' order by razaoSocial";
        }else if(valor != null && teste == 2){
            sql = " from Fornecedor where cnpjCpf like '%"+valor+"%' order by razaoSocial";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtm.setNumRows(0);
            
            Fornecedor c;
            
            while(i.hasNext()){
                c = (Fornecedor)i.next();
                dtm.addRow(c.getFornecedor());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
    }
    
    private void carregarListaRazao(String razao){
       
        String sql = "from Fornecedor order by razaoSocial";
        
        if(razao != null){
            sql = " from Fornecedor where razaoSocial like '%"+razao+"%' order by razaoSocial";
        }
        
        dlm.removeAllElements();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Fornecedor f;
            
            while(i.hasNext()){
                f = (Fornecedor)i.next();
                dlm.add(dlm.getSize(), f.getRazaoSocial());
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
    }
    
    private void carregaEstados(){
        String sql = "from Estado";
        
        cbxEstados.removeAllItems();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Estado estado;
            
            while(i.hasNext()){
                estado = (Estado)i.next();
                cbxEstados.addItem(estado.getSigla());
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
    }
    
    private void carregaCidades(int estado){
        String sql = "from Cidades c inner join fetch c.estado as e where e.idEstado = "+estado;
        
        cbxCidades.removeAllItems();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().openSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            
            
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Cidades cidade;
            
            while(i.hasNext()){
                cidade = (Cidades)i.next();
                cbxCidades.addItem(cidade.getNomeCidade());
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
    }
    
    private void buscaEstados(String sigla){
        String sql = "from Estado where sigla = '"+sigla+"'";
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Estado estado;
            
            if(i.hasNext()){
                estado = (Estado)i.next();
                carregaCidades(estado.getIdEstado());
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
    }
    
    private int buscaIdCidade(String nomeCidade){
        String sql = "from Cidades where nomeCidade = '"+nomeCidade+"'";
        int id = 0;
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Cidades cidade;
            
            if(i.hasNext()){
                cidade = (Cidades)i.next();
                id = cidade.getIdCidade();
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
        
        return id;
    }
   
    public IFrmFornecedores() {
        initComponents();
        
        listRazao.setModel(new DefaultListModel());
        dlm = (DefaultListModel) listRazao.getModel();
        dtm = (DefaultTableModel)tblFornecedor.getModel();
        carregarListaRazao(null);
        carregaEstados();
        buscaEstados(cbxEstados.getSelectedItem().toString());
        carregaTabelaFornecedors(null, 0);
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

        gpBusca = new javax.swing.ButtonGroup();
        tpnCadastrar = new javax.swing.JTabbedPane();
        pnCadastro = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtRazao = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNomaFant = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCnpj = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JFormattedTextField();
        scrolLista = new javax.swing.JScrollPane();
        listRazao = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtRua = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        spnN = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCep = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        cbxEstados = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cbxCidades = new javax.swing.JComboBox<>();
        txtBuscaFornecedor = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtResponsavel = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnDesbloquear = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        pnExibir = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFornecedor = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        btnAltera = new javax.swing.JButton();
        btnDeleta = new javax.swing.JButton();
        btnBusca = new javax.swing.JButton();
        rbRazao = new javax.swing.JRadioButton();
        txtBCnpj = new javax.swing.JFormattedTextField();
        rbCnpj = new javax.swing.JRadioButton();
        txtBRazao = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        btnCancelarBusca = new javax.swing.JButton();

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setClosable(true);
        setIconifiable(true);
        setTitle("Fornecedores");

        pnCadastro.setBackground(new java.awt.Color(204, 204, 255));
        pnCadastro.setMaximumSize(new java.awt.Dimension(1039, 440));
        pnCadastro.setMinimumSize(new java.awt.Dimension(1039, 440));

        jLabel3.setText("Razão Social:");

        jLabel4.setText("Nome Fantasia:");

        jLabel5.setText("CNPJ:");

        try {
            txtCnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCnpj.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCnpj.setToolTipText("Difite Apenas Numeros");
        txtCnpj.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtCnpj.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtCnpj.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCnpjFocusLost(evt);
            }
        });

        jLabel6.setText("Telefone:");

        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefone.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);

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

        jLabel8.setText("Endereço");

        jLabel9.setText("Rua:");

        jLabel10.setText("Nº");

        spnN.setModel(new javax.swing.SpinnerNumberModel());

        jLabel11.setText("Bairro:");

        jLabel12.setText("CEP:");

        try {
            txtCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCep.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);

        jLabel13.setText("Estado:");

        cbxEstados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbxEstadosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbxEstadosFocusLost(evt);
            }
        });
        cbxEstados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cbxEstadosMouseExited(evt);
            }
        });

        jLabel14.setText("Cidade:");

        cbxCidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbxCidadesMouseClicked(evt);
            }
        });

        txtBuscaFornecedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscaFornecedorKeyReleased(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5739_Knob_Search.png"))); // NOI18N
        jLabel16.setToolTipText("Buscar Fornecedor");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5750_Knob_Cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar Operação");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel19.setText("E-mail p/ Contato:");

        jLabel20.setText("Responsável pela empresa:");

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5113_add_48.png"))); // NOI18N
        btnNovo.setToolTipText("Adicionar Novo Fornecedor");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNovo)
                .addGap(18, 18, 18)
                .addComponent(btnDesbloquear)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDesbloquear, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
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
                        .addComponent(txtBuscaFornecedor))
                    .addComponent(scrolLista, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxCidades, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnCadastroLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtRazao, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNomaFant, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spnN, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCadastroLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(70, 70, 70))
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(44, 44, 44)))
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(18, 18, 18)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail)
                            .addComponent(txtResponsavel)))
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCancelar)
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(120, 120, 120))
        );
        pnCadastroLayout.setVerticalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscaFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrolLista))
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtRazao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtNomaFant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(txtResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10)
                                .addComponent(spnN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11)
                                .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(cbxEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(cbxCidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addComponent(btnCancelar)))
                .addGap(45, 45, 45))
        );

        tpnCadastrar.addTab("Cadastrar", pnCadastro);

        pnExibir.setBackground(new java.awt.Color(204, 255, 204));
        pnExibir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblFornecedor.setBackground(new java.awt.Color(153, 255, 153));
        tblFornecedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Razão Social", "CNPJ", "Nome Fantasia", "email", "Telefone", "Rua", "Nº", "Cidade", "UF"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblFornecedor);
        if (tblFornecedor.getColumnModel().getColumnCount() > 0) {
            tblFornecedor.getColumnModel().getColumn(0).setPreferredWidth(110);
            tblFornecedor.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblFornecedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblFornecedor.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblFornecedor.getColumnModel().getColumn(4).setPreferredWidth(90);
            tblFornecedor.getColumnModel().getColumn(5).setPreferredWidth(77);
            tblFornecedor.getColumnModel().getColumn(6).setPreferredWidth(10);
            tblFornecedor.getColumnModel().getColumn(7).setPreferredWidth(70);
            tblFornecedor.getColumnModel().getColumn(8).setPreferredWidth(5);
        }

        pnExibir.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 900, 290));

        jLabel7.setText("Fornecedores Cadastrados");
        pnExibir.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        btnAltera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_edit.png"))); // NOI18N
        btnAltera.setText("Alterar Fornecedor");
        btnAltera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlteraActionPerformed(evt);
            }
        });
        pnExibir.add(btnAltera, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        btnDeleta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_delete.png"))); // NOI18N
        btnDeleta.setText("Excluir Fornecedor");
        btnDeleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletaActionPerformed(evt);
            }
        });
        pnExibir.add(btnDeleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, -1, -1));

        btnBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5739_Knob_Search.png"))); // NOI18N
        btnBusca.setText("Buscar");
        btnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaActionPerformed(evt);
            }
        });
        pnExibir.add(btnBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 20, -1, 30));

        gpBusca.add(rbRazao);
        rbRazao.setText("Razão Social");
        rbRazao.setActionCommand("1");
        rbRazao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRazaoActionPerformed(evt);
            }
        });
        pnExibir.add(rbRazao, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, 40));

        try {
            txtBCnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtBCnpj.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBCnpj.setEnabled(false);
        txtBCnpj.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBCnpjFocusLost(evt);
            }
        });
        pnExibir.add(txtBCnpj, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 240, 30));

        gpBusca.add(rbCnpj);
        rbCnpj.setText("CNPJ");
        rbCnpj.setActionCommand("2");
        rbCnpj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCnpjActionPerformed(evt);
            }
        });
        pnExibir.add(rbCnpj, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, -1, -1));

        txtBRazao.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtBRazao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBRazaoActionPerformed(evt);
            }
        });
        pnExibir.add(txtBRazao, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 240, 30));

        jLabel17.setText("Buscar Clientes por:");
        pnExibir.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, 40));

        btnCancelarBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5744_Knob_Refresh.png"))); // NOI18N
        btnCancelarBusca.setText("Cancelar Busca");
        btnCancelarBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarBuscaActionPerformed(evt);
            }
        });
        pnExibir.add(btnCancelarBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 390, 220, -1));

        tpnCadastrar.addTab("Exibir", pnExibir);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tpnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 934, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnCadastrar)
        );

        setBounds(200, 100, 940, 532);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBRazaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBRazaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBRazaoActionPerformed

    private void rbRazaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRazaoActionPerformed
        txtBRazao.setText("");
        txtBCnpj.setText("");
        txtBRazao.setEnabled(true);
        txtBRazao.setVisible(true);
        
        txtBCnpj.setEnabled(false);
        txtBCnpj.setVisible(false);
        
        txtBRazao.grabFocus();
    }//GEN-LAST:event_rbRazaoActionPerformed

    private void rbCnpjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCnpjActionPerformed
        txtBRazao.setText("");
        txtBCnpj.setText("");
        
        txtBRazao.setEnabled(false);
        txtBRazao.setVisible(false);
        
        txtBCnpj.setEnabled(true);
        txtBCnpj.setVisible(true);
        
        txtBCnpj.grabFocus();
    }//GEN-LAST:event_rbCnpjActionPerformed

    private void btnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaActionPerformed
        if(gpBusca.getSelection().getActionCommand() == "1"){
            carregaTabelaFornecedors(txtBRazao.getText(), 1);
        }else if(gpBusca.getSelection().getActionCommand() == "2"){
            carregaTabelaFornecedors(txtBCnpj.getText(), 2);
        }
        
        
    }//GEN-LAST:event_btnBuscaActionPerformed

    private void txtBCnpjFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBCnpjFocusLost
         try {
            txtBCnpj.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(IFrmFornecedores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtBCnpjFocusLost

    private void btnDeletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletaActionPerformed
         int linha = tblFornecedor.getSelectedRow();
         int[] ids = new int[2];

        if(rbRazao.isSelected()){
            ids = pegarIdPorLinha(linha,txtBRazao.getText(), 1 );
        }else if(rbCnpj.isSelected()){
            ids = pegarIdPorLinha(linha,txtBCnpj.getText(), 2 );
        }else{
            ids = pegarIdPorLinha(linha,null, 0 );
        }
         
            
        Object[] op = { "Cancelar", "Confirmar" };
        if(linha >= 0){
            int opc = JOptionPane.showOptionDialog(IFrmFornecedores.this,"Deseja Confirmar a Exclusção do Fornecedor: "+dtm.getValueAt(linha, 0).toString(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, op, op[1]);
           
            if(opc == 1){
                Cidades ci = new Cidades();
                ci.setIdCidade(1);

                Endereco e = new Endereco(ci, "", 0, "");
                e.setIdEndereco(ids[1]);

                Fornecedor f = new Fornecedor(e, "", "", "");
                f.setIdFornecedor(ids[0]);


                    Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                try{
                    
                    s.beginTransaction();

                    s.delete(f);
                    s.delete(e);

                    s.getTransaction().commit();

                    txtBCnpj.setText("");
                    txtBRazao.setText("");

                    txtBCnpj.setEnabled(false);
                    txtBRazao.setEnabled(false);

                    gpBusca.clearSelection();

                    carregaTabelaFornecedors(null, 0);
                    carregarListaRazao(null);
                    
                    JOptionPane.showMessageDialog(IFrmFornecedores.this, "Fornecedor Excluido com sucesso");



                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Não pode excluir Fornecedor pois existe referenicas deste fornecedor em outros cadastros");
                    s.close();
                }
            }
            
        }else {
            JOptionPane.showMessageDialog(IFrmFornecedores.this, "Escolha um Fornecedor para Excluir", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
        }
         
    }//GEN-LAST:event_btnDeletaActionPerformed

    private void btnCancelarBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarBuscaActionPerformed
        txtBCnpj.setText("");
        txtBRazao.setText("");
                
        txtBCnpj.setEnabled(false);
        txtBRazao.setEnabled(false);
                
        gpBusca.clearSelection();
                
        carregaTabelaFornecedors(null, 0);
        carregarListaRazao(null);
    }//GEN-LAST:event_btnCancelarBuscaActionPerformed

    private void btnAlteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlteraActionPerformed
        limparCampos();
        
        listRazao.setEnabled(false);
        listRazao.enable(false);
        habilitarBotao(btnNovo);
        desabilitarBotao(btnSalvar);
        
        txtBuscaFornecedor.setEnabled(false);
        
        if(rbRazao.isSelected() && tblFornecedor.getSelectedRow() >=0 ){    
            preencherCampos(tblFornecedor.getSelectedRow(),txtBRazao.getText(), 1);
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else if(rbCnpj.isSelected() && tblFornecedor.getSelectedRow() >=0 ){
            preencherCampos(tblFornecedor.getSelectedRow(),txtBCnpj.getText(), 2 );
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else if(tblFornecedor.getSelectedRow() >=0 ){
            preencherCampos(tblFornecedor.getSelectedRow(), null, 0 );
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else {
            JOptionPane.showMessageDialog(IFrmFornecedores.this, "Escolha um Fornecedor para Alterar", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
            operacao = 0;
        }
        
        
        
    }//GEN-LAST:event_btnAlteraActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        carregaTabelaFornecedors(null, 0);
        carregarListaRazao(null);

        Object[] op = { "Não", "Sim" };
        int opc = 0;

        if(operacao == 1){
            opc = JOptionPane.showOptionDialog(IFrmFornecedores.this,"Deseja Cancelar a Operação:\nNovo Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){

                limparCampos();
                desabilitarCampos();
                listRazao.setEnabled(true);
                txtBuscaFornecedor.setEnabled(true);
                desabilitarBotao(btnSalvar);
                habilitarBotao(btnNovo);
                operacao = 0;

            }

        }else if(operacao == 2 || operacao == 3){
            opc = JOptionPane.showOptionDialog(IFrmFornecedores.this,"Deseja Cancelar a Operação:\nAlterar Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){
                limparCampos();
                desabilitarCampos();
                listRazao.setEnabled(true);
                txtBuscaFornecedor.setEnabled(true);
                desabilitarBotao(btnAlterar);
                desabilitarBotao(btnDesbloquear);
                habilitarBotao(btnNovo);

                operacao = 0;
            }
        }else if(operacao == 0){
            JOptionPane.showMessageDialog(IFrmFornecedores.this, "Nenhuma Operaçao foi iniciada");
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnDesbloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesbloquearActionPerformed
        habilitarCampos();
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);
        listRazao.setEnabled(false);
        txtBuscaFornecedor.setEnabled(false);
        habilitarBotao(btnAlterar);
        operacao = 2;
    }//GEN-LAST:event_btnDesbloquearActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
            int linha = 0;
            Object[] op = { "Cancelar", "Confirmar" };
            
            int[] ids = new int[2];
            int opc = JOptionPane.showOptionDialog(IFrmFornecedores.this,"Deseja Confirmar as alterações no Fornecedor: "+txtRazao.getText(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){
                Estado es = new Estado();
                es.setSigla(cbxEstados.getSelectedItem().toString());
                Cidades c = new Cidades(buscaIdCidade(cbxCidades.getSelectedItem().toString()), es, cbxCidades.getSelectedItem().toString());
                Endereco e = new Endereco(c, txtRua.getText(), Integer.parseInt(spnN.getValue().toString()), txtCep.getText());
                e.setBairro(txtBairro.getText());


                if(operacao == 2){
                    linha = listRazao.getSelectedIndex();

                    if(txtBuscaFornecedor.getText().equals("")){
                        ids = pegarIdPorLinha(linha,null, 0);
                    }else{
                        ids = pegarIdPorLinha(linha,txtBuscaFornecedor.getText(), 1 );
                    }

                }else if(operacao == 3){
                    linha = tblFornecedor.getSelectedRow();

                    if(rbRazao.isSelected()){
                        ids = pegarIdPorLinha(linha,txtBRazao.getText(), 1 );
                    }else if(rbCnpj.isSelected()){
                        ids = pegarIdPorLinha(linha,txtBCnpj.getText(), 2 );
                    }else{
                        ids = pegarIdPorLinha(linha,null, 0 );
                    }
                }

                if(linha >= 0){
                    try{
                        Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                        s.beginTransaction();

                        e.setIdEndereco(ids[1]);
                        s.merge(e);

                        Fornecedor f = new Fornecedor(e, txtRazao.getText(), txtCnpj.getText(), txtResponsavel.getText());
                        f.setEmail(txtEmail.getText());
                        f.setNomeFantasia(txtNomaFant.getText());
                        f.setTelefone(txtTelefone.getText());

                        f.setIdFornecedor(ids[0]);
                        s.merge(f);

                        s.getTransaction().commit();

                    }catch(Exception erro){ erro.printStackTrace();}
                    carregarListaRazao(null);
                    carregaTabelaFornecedors(null, 0);
                    limparCampos();
                    desabilitarCampos();
                    desabilitarBotao(btnAlterar);
                    desabilitarBotao(btnDesbloquear);
                    listRazao.setEnabled(true);
                    txtBuscaFornecedor.setEnabled(true);
                    habilitarBotao(btnNovo);

                    operacao = 0;
                    
                    JOptionPane.showMessageDialog(IFrmFornecedores.this, "Fornecedor Alterado com Sucesso");

                }
            }

        
        
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        if(txtRazao.getText().equals("") || txtCnpj.getText().equals("") || txtResponsavel.getText().equals("")  || txtCep.getText().equals("") || txtRua.getText().equals("")){

            JOptionPane.showMessageDialog(IFrmFornecedores.this, "Alguns Campos Obrigatórios Não foram Preenchidos", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        }else {
            int opc = JOptionPane.showOptionDialog(IFrmFornecedores.this,"Deseja Salvar o Fornecedor: "+txtRazao.getText(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
            
            if(opc == 1){
                
                Estado es = new Estado();
                es.setSigla(cbxEstados.getSelectedItem().toString());
                Cidades c = new Cidades(buscaIdCidade(cbxCidades.getSelectedItem().toString()), es, cbxCidades.getSelectedItem().toString());
                Endereco e = new Endereco(c, txtRua.getText(), Integer.parseInt(spnN.getValue().toString()), txtCep.getText());
                e.setBairro(txtBairro.getText());

                try{
                    Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                    s.beginTransaction();

                    s.save(e);

                    Fornecedor f = new Fornecedor(e, txtRazao.getText(), txtCnpj.getText(), txtResponsavel.getText());
                        f.setEmail(txtEmail.getText());
                        f.setNomeFantasia(txtNomaFant.getText());
                        f.setTelefone(txtTelefone.getText());

                    s.save(f);

                    s.getTransaction().commit();

                }catch(Exception erro){ erro.printStackTrace();}
                carregarListaRazao(null);
                carregaTabelaFornecedors(null, 0);
                limparCampos();
                desabilitarBotao(btnSalvar);
                desabilitarCampos();
                habilitarBotao(btnNovo);
                listRazao.setEnabled(true);
                txtBuscaFornecedor.setEnabled(true);
                operacao = 0;
                
                JOptionPane.showMessageDialog(IFrmFornecedores.this, "Fornecedor Salvo com sucesso");

            }else if(JOptionPane.showOptionDialog(IFrmFornecedores.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
                btnCancelarActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        operacao = 1;
        habilitarCampos();
        carregaEstados();
        limparCampos();
        listRazao.setEnabled(false);
        txtBuscaFornecedor.setEnabled(false);
        habilitarBotao(btnSalvar);
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);
        
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtBuscaFornecedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscaFornecedorKeyReleased
        carregarListaRazao(txtBuscaFornecedor.getText());
    }//GEN-LAST:event_txtBuscaFornecedorKeyReleased

    private void cbxCidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxCidadesMouseClicked
        buscaEstados(cbxEstados.getSelectedItem().toString());
    }//GEN-LAST:event_cbxCidadesMouseClicked

    private void cbxEstadosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxEstadosMouseExited
        buscaEstados(cbxEstados.getSelectedItem().toString());
    }//GEN-LAST:event_cbxEstadosMouseExited

    private void cbxEstadosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbxEstadosFocusLost
        buscaEstados(cbxEstados.getSelectedItem().toString());
    }//GEN-LAST:event_cbxEstadosFocusLost

    private void cbxEstadosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbxEstadosFocusGained
        if(operacao == 2 || operacao == 3){
            carregaEstados();
        }
        if(operacao == 0){
            buscaEstados(cbxEstados.getSelectedItem().toString());
        }
    }//GEN-LAST:event_cbxEstadosFocusGained

    private void listRazaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listRazaoKeyReleased
        if(operacao == 0){
            limparCampos();
            if(txtBuscaFornecedor.getText().equals("")){
                preencherCampos(listRazao.getSelectedIndex(), null, 0);
            }else {
                preencherCampos(listRazao.getSelectedIndex(), txtBuscaFornecedor.getText(), 1);
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listRazaoKeyReleased

    private void listRazaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listRazaoKeyPressed
        if(operacao == 0){
            limparCampos();
            if(txtBuscaFornecedor.getText().equals("")){
                preencherCampos(listRazao.getSelectedIndex(), null, 0);
            }else {
                preencherCampos(listRazao.getSelectedIndex(), txtBuscaFornecedor.getText(), 1);
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listRazaoKeyPressed

    private void listRazaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listRazaoMouseClicked
        if(operacao == 0){
            limparCampos();
            if(txtBuscaFornecedor.getText().equals("")){
                preencherCampos(listRazao.getSelectedIndex(), null, 0);
            }else {
                preencherCampos(listRazao.getSelectedIndex(), txtBuscaFornecedor.getText(), 1);
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listRazaoMouseClicked

    private void txtCnpjFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCnpjFocusLost
    /*        String cpf1 = "", cpf2 = "", cpf3 = "", cpf4 = "", cpfFinal = "";

        cpf1 = txtCnpj.getText().substring(0, 3);
        cpf2 = txtCnpj.getText().substring(4, 7);
        cpf3 = txtCnpj.getText().substring(8, 11);
        cpf4 = txtCnpj.getText().substring(12, 14);

        cpfFinal = cpf1+cpf2+cpf3+cpf4;
        
        if(validaCpfUnico(txtCnpj.getText())){
            
            if(!verifica.testaCPF(cpfFinal)){
                JOptionPane.showMessageDialog(IFrmFornecedores.this, "CPF Inválido \nPor favor Digite Novamente", "Atenção", JOptionPane.WARNING_MESSAGE);
                txtCnpj.grabFocus();
                
                
            }
            
        }else {
            JOptionPane.showMessageDialog(IFrmFornecedores.this, "CPF Já Cadastro na base de dados\n\tPor favor digite um outro CPF", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtCnpj.grabFocus();
        }
            */
    }//GEN-LAST:event_txtCnpjFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAltera;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnBusca;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarBusca;
    private javax.swing.JButton btnDeleta;
    private javax.swing.JButton btnDesbloquear;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbxCidades;
    private javax.swing.JComboBox<String> cbxEstados;
    private javax.swing.ButtonGroup gpBusca;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listRazao;
    private javax.swing.JPanel pnCadastro;
    private javax.swing.JPanel pnExibir;
    private javax.swing.JRadioButton rbCnpj;
    private javax.swing.JRadioButton rbRazao;
    private javax.swing.JScrollPane scrolLista;
    private javax.swing.JSpinner spnN;
    private javax.swing.JTable tblFornecedor;
    private javax.swing.JTabbedPane tpnCadastrar;
    private javax.swing.JFormattedTextField txtBCnpj;
    private javax.swing.JTextField txtBRazao;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtBuscaFornecedor;
    private javax.swing.JFormattedTextField txtCep;
    private javax.swing.JFormattedTextField txtCnpj;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNomaFant;
    private javax.swing.JTextField txtRazao;
    private javax.swing.JTextField txtResponsavel;
    private javax.swing.JTextField txtRua;
    private javax.swing.JFormattedTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
