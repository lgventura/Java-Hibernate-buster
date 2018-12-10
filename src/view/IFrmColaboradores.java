package view;

import java.awt.Dimension;

import org.hibernate.Session;
import org.hibernate.Query;

import dao.DAO_PROJETO;
import controller.Colaborador;
import controller.Pessoa;
import controller.Usuarios;
import controller.Endereco;
import controller.Estado;
import controller.Cidades;
import controller.VerificaCPF;

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
public class IFrmColaboradores extends javax.swing.JInternalFrame {
    
    private DefaultListModel dlm ; //Usado p/Criação da lista com os dados vindo do BD
    private DefaultTableModel dtm; //Usado p/ Criação da Tabela com os dados vindo do BD
    private int operacao = 0;
    
    private void limparCampos(){//Limpa todos os campos após o cadastro
        txtBairro.setText("");
        txtBuscaColaborador.setText("");
        txtCep.setText("");
        txtCpf.setText("");
        txtNome.setText("");
        txtFormacao.setText("");
        txtRg.setText("");
        txtRua.setText("");
        txtTelefone.setText("");
        spnIdade.setValue(0);
        spnN.setValue(0);
        rbMasculino.setSelected(isSelected);
        cbxEstados.setSelectedIndex(0);
        cbxEspecialidade.setSelectedIndex(0);
        cbxCidades.removeAllItems();
        
        txtNome.requestFocus();
    }
    
    private void zerarFrmAcesso(){
        cbxAcesso.setSelectedIndex(0);
       
        chCadAg.setSelected(false);
        chCadCli.setSelected(false);
        chCadCol.setSelected(false);
        chCadProd.setSelected(false);
        chCadServ.setSelected(false);

        
        chCadAg.setEnabled(false);
        chCadCli.setEnabled(false);
        chCadCol.setEnabled(false);
        chCadProd.setEnabled(false);
        chCadServ.setEnabled(false);

        lblCad.setEnabled(false);
        
        frmAcesso.setVisible(false);
        
    }
    
    private void preemcherFrmAcesso(int linha, String valor, int teste){
        String sql = "from Colaborador as c order by c.pessoa.nome ";
        int aux = 0;
        
        if(valor != null && teste == 1){
            sql = " from Colaborador as c where c.pessoa.nome like '%"+valor+"%' order by nome";
        }else if(valor != null && teste == 2){
            sql = " from Colaborador as c where c.pessoa.cpfCnpj like '%"+valor+"%' order by nome";
        }
        
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
                    
                    int tamanho = c.getUsuarios().getTipoDeAcesso().length();
                    String acesso = c.getUsuarios().getTipoDeAcesso().substring(0,3);
                    String acessoAux = c.getUsuarios().getTipoDeAcesso();
                    
                    
                    txtSenha.setText(c.getUsuarios().getSenha());
                    
                    if(acesso.equals("NRM")){
                        cbxAcesso.setSelectedIndex(0);
                    }else if(acesso.equals("ADM")){
                        cbxAcesso.setSelectedIndex(1);
                    }else if(acesso.equals("PSL")){
                        
                        cbxAcesso.setSelectedIndex(2);
                        
                        cbxAcesso.actionPerformed(null);
                        
                        
                        for(int auxAc = 3; auxAc < tamanho; auxAc+=3 ){
                            if(acessoAux.substring(auxAc, auxAc+3).equals("CAG")){
                                chCadAg.setSelected(true);
                            }
                        }
                        
                        for(int auxAc = 3; auxAc < tamanho; auxAc+=3 ){
                            if(acessoAux.substring(auxAc, auxAc+3).equals("CCL")){
                                chCadCli.setSelected(true);
                            }
                        }
                        
                        for(int auxAc = 3; auxAc < tamanho; auxAc+=3 ){
                            if(acessoAux.substring(auxAc, auxAc+3).equals("CCO")){
                                chCadCol.setSelected(true);
                            }
                        }
                        
                        
                        for(int auxAc = 3; auxAc < tamanho; auxAc+=3 ){
                            if(acessoAux.substring(auxAc, auxAc+3).equals("CPD")){
                                chCadProd.setSelected(true);
                            }
                        }
                        
                        
                        for(int auxAc = 3; auxAc < tamanho; auxAc+=3 ){
                            if(acessoAux.substring(auxAc, auxAc+3).equals("CSV")){
                                chCadServ.setSelected(true);
                            }
                        }
                        
                        
                    }
                    
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private boolean validaCpfUnico(String cpfTeste){
        String sql = "from Pessoa";
        int teste = 0;
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Pessoa p;
            
            while(i.hasNext()){
                p = (Pessoa)i.next();
                if(p.getCpfCnpj().equals(cpfTeste)){
                    teste = 1;
                }
            }
            s.getTransaction().commit();
            
        }catch(Exception erro){ erro.printStackTrace();}
        
        if(teste == 1)
            return false;
        else
            return true;
        
    }
    
    private void preencherCampos(int linha, String valor, int teste){
        String sql = "from Colaborador as c order by c.pessoa.nome ";
        int aux = 0;
        
        if(valor != null && teste == 1){
            sql = " from Colaborador as c where c.pessoa.nome like '%"+valor+"%' order by nome";
        }else if(valor != null && teste == 2){
            sql = " from Colaborador as c where c.pessoa.cpfCnpj like '%"+valor+"%' order by nome";
        }
        
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
                    txtNome.setText(c.getPessoa().getNome());
                    txtRg.setText(c.getPessoa().getRg());
                    txtCpf.setText(c.getPessoa().getCpfCnpj());
                    spnIdade.setValue(c.getIdade());
                    if(c.getSexo().equals("Masculino")){
                        rbMasculino.setSelected(isSelected);
                    }else {
                        rbFeminino.setSelected(isSelected);
                    }
                    
                    txtTelefone.setText(c.getPessoa().getTelefone());
                    txtRua.setText(c.getPessoa().getEndereco().getRua());
                    spnN.setValue(c.getPessoa().getEndereco().getNumero());
                    txtBairro.setText(c.getPessoa().getEndereco().getBairro());
                    txtCep.setText(c.getPessoa().getEndereco().getCep());
                    cbxEstados.removeAllItems();
                    cbxEstados.addItem(c.getPessoa().getEndereco().getCidades().getEstado().getSigla());
                    cbxCidades.removeAllItems();
                    cbxCidades.addItem(c.getPessoa().getEndereco().getCidades().getNomeCidade());
                    
                    for(int auxCbx = 0; auxCbx < cbxEspecialidade.getItemCount(); auxCbx++){
                        if(cbxEspecialidade.getItemAt(auxCbx).equals(c.getEspecialidade())){
                            cbxEspecialidade.setSelectedIndex(auxCbx);
                        }
                    }
                    txtFormacao.setText(c.getPessoa().getObservacoes());
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private int[] pegarIdPorLinha(int linha, String valor, int teste){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from Colaborador as c order by c.pessoa.nome ";
        int aux = 0;
        int[] ids = new int[4];
        
        if(valor != null && teste == 1){
            sql = " from Colaborador as c where c.pessoa.nome like '%"+valor+"%' order by nome";
        }else if(valor != null && teste == 2){
            sql = " from Colaborador as c where c.pessoa.cpfCnpj like '%"+valor+"%' order by nome";
        }
        
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
                    ids[0] = c.getIdColaborador();
                    ids[1] = c.getPessoa().getIdPessoa();
                    ids[2] = c.getPessoa().getEndereco().getIdEndereco();
                    ids[3] = c.getUsuarios().getIdUser();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return ids; //Retorna um array com os ids de endereço, pessoa e Colaborador
    }
    
    private void habilitarCampos(){
        txtBairro.setEnabled(true);
        txtCep.setEnabled(true);
        txtCpf.setEnabled(true);
        txtNome.setEnabled(true);
        txtFormacao.setEnabled(true);
        txtRg.setEnabled(true);
        txtRua.setEnabled(true);
        txtTelefone.setEnabled(true);
        spnIdade.setEnabled(true);
        spnN.setEnabled(true);
        rbMasculino.setEnabled(true);
        rbFeminino.setEnabled(true);
        cbxEstados.setEnabled(true);
        cbxCidades.setEnabled(true);
        cbxEspecialidade.setEnabled(true);
    }
    
    private void desabilitarCampos(){
        txtBairro.setEnabled(false);
        txtCep.setEnabled(false);
        txtCpf.setEnabled(false);
        txtNome.setEnabled(false);
        txtFormacao.setEnabled(false);
        txtRg.setEnabled(false);
        txtRua.setEnabled(false);
        txtTelefone.setEnabled(false);
        spnIdade.setEnabled(false);
        spnN.setEnabled(false);
        rbMasculino.setEnabled(false);
        rbFeminino.setEnabled(false);
        cbxEstados.setEnabled(false);
        cbxCidades.setEnabled(false);
        cbxEspecialidade.setEnabled(false);
        zerarFrmAcesso();
    }
    
    private void habilitarBotao(JButton btn){
        btn.setEnabled(true);
    }
    
    private void desabilitarBotao(JButton btn){
        btn.setEnabled(false);
    }
    
    private void carregaTabelaColaboradors(String valor, int teste){//carrega a tabela de Colaboradors ordenando pelo nome
        String sql = "from Colaborador as c order by c.pessoa.nome ";
        
        if(valor != null && teste == 1){
            sql = " from Colaborador as c where c.pessoa.nome like '%"+valor+"%' order by nome";
        }else if(valor != null && teste == 2){
            sql = " from Colaborador as c where c.pessoa.cpfCnpj like '%"+valor+"%' order by nome";
        }
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            dtm.setNumRows(0);
            
            Colaborador c;
            
            while(i.hasNext()){
                c = (Colaborador)i.next();
                dtm.addRow(c.getColaborador());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
    }
    
    private void carregarListaNomes(String nome){
       
        String sql = "from Colaborador as c order by c.pessoa.nome";
        
        if(nome != null){
            sql = " from Colaborador as c where c.pessoa.nome like '%"+nome+"%'";
        }
        
        dlm.removeAllElements();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Colaborador c;
            
            while(i.hasNext()){
                c = (Colaborador)i.next();
                dlm.add(dlm.getSize(), c.getPessoa().getNome());
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
    
   
    VerificaCPF verifica = null;
    
    public IFrmColaboradores() {
        initComponents();
        
        listNomes.setModel(new DefaultListModel());
        dlm = (DefaultListModel) listNomes.getModel();
        dtm = (DefaultTableModel)tblColaborador.getModel();
        carregarListaNomes(null);
        carregaEstados();
        buscaEstados(cbxEstados.getSelectedItem().toString());
        carregaTabelaColaboradors(null, 0);
        btnSalvar.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnDesbloquear.setEnabled(false);
        verifica = new VerificaCPF();
        
        
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

        gpSexo = new javax.swing.ButtonGroup();
        gpBusca = new javax.swing.ButtonGroup();
        frmAcesso = new javax.swing.JFrame();
        chCadAg = new javax.swing.JCheckBox();
        lblCad = new javax.swing.JLabel();
        chCadCli = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        chCadCol = new javax.swing.JCheckBox();
        cbxAcesso = new javax.swing.JComboBox<>();
        chCadProd = new javax.swing.JCheckBox();
        btnFinalizar = new javax.swing.JButton();
        chCadServ = new javax.swing.JCheckBox();
        lblSenha = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        tpnCadastrar = new javax.swing.JTabbedPane();
        pnCadastro = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtRg = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCpf = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        spnIdade = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        rbMasculino = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JFormattedTextField();
        scrolLista = new javax.swing.JScrollPane();
        listNomes = new javax.swing.JList<>();
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
        scrolObs = new javax.swing.JScrollPane();
        txtFormacao = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        txtBuscaColaborador = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        rbFeminino = new javax.swing.JRadioButton();
        btnCancelar = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        cbxEspecialidade = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnDesbloquear = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        pnExibir = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblColaborador = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        btnAltera = new javax.swing.JButton();
        btnDeleta = new javax.swing.JButton();
        btnBusca = new javax.swing.JButton();
        rbNome = new javax.swing.JRadioButton();
        txtBCpf = new javax.swing.JFormattedTextField();
        rbCpf = new javax.swing.JRadioButton();
        txtBNome = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        btnCancelarBusca = new javax.swing.JButton();

        frmAcesso.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmAcesso.setTitle("Configurar Nivel de Acesso do Colaborador");
        frmAcesso.setMinimumSize(new java.awt.Dimension(550, 450));

        chCadAg.setText("Agendamento");
        chCadAg.setEnabled(false);

        lblCad.setText("Cadastros:");
        lblCad.setEnabled(false);

        chCadCli.setText("Colaboradores");
        chCadCli.setEnabled(false);

        jLabel21.setText("Nível de Acesso:");

        chCadCol.setText("Clientes");
        chCadCol.setEnabled(false);

        cbxAcesso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Administrativo", "Personalizado" }));
        cbxAcesso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxAcessoActionPerformed(evt);
            }
        });

        chCadProd.setText("Produtos");
        chCadProd.setEnabled(false);

        btnFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5122_accepted_48.png"))); // NOI18N
        btnFinalizar.setText("Finalizar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        chCadServ.setText("Serviços");
        chCadServ.setEnabled(false);

        lblSenha.setText("Senha:");

        javax.swing.GroupLayout frmAcessoLayout = new javax.swing.GroupLayout(frmAcesso.getContentPane());
        frmAcesso.getContentPane().setLayout(frmAcessoLayout);
        frmAcessoLayout.setHorizontalGroup(
            frmAcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmAcessoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmAcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmAcessoLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(chCadAg)
                    .addGroup(frmAcessoLayout.createSequentialGroup()
                        .addComponent(chCadCol)
                        .addGap(32, 32, 32)
                        .addComponent(chCadCli)
                        .addGap(18, 18, 18)
                        .addComponent(chCadProd)
                        .addGap(26, 26, 26)
                        .addComponent(chCadServ))
                    .addComponent(lblCad)
                    .addGroup(frmAcessoLayout.createSequentialGroup()
                        .addComponent(lblSenha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFinalizar)
                .addContainerGap())
        );
        frmAcessoLayout.setVerticalGroup(
            frmAcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmAcessoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmAcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cbxAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(frmAcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenha)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(lblCad)
                .addGap(18, 18, 18)
                .addGroup(frmAcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chCadCol)
                    .addComponent(chCadCli)
                    .addComponent(chCadProd)
                    .addComponent(chCadServ))
                .addGap(19, 19, 19)
                .addComponent(chCadAg)
                .addGap(245, 245, 245))
        );

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setClosable(true);
        setIconifiable(true);
        setTitle("Colaboradores");

        pnCadastro.setBackground(new java.awt.Color(204, 204, 255));
        pnCadastro.setMaximumSize(new java.awt.Dimension(929, 544));
        pnCadastro.setMinimumSize(new java.awt.Dimension(929, 544));

        jLabel3.setText("Nome:");

        jLabel4.setText("RG:");

        jLabel5.setText("CPF:");

        try {
            txtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCpf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCpf.setToolTipText("Difite Apenas Numeros");
        txtCpf.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtCpf.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtCpf.setNextFocusableComponent(spnIdade);
        txtCpf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCpfFocusLost(evt);
            }
        });

        jLabel1.setText("Idade:");

        spnIdade.setModel(new javax.swing.SpinnerNumberModel());
        spnIdade.setToolTipText("");

        jLabel2.setText("Sexo:");

        gpSexo.add(rbMasculino);
        rbMasculino.setSelected(true);
        rbMasculino.setText("Masculino");
        rbMasculino.setActionCommand("1");

        jLabel6.setText("Celular:");

        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)# ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefone.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);

        listNomes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listNomes.setMinimumSize(new java.awt.Dimension(45, 80));
        listNomes.setPreferredSize(new java.awt.Dimension(45, 83));
        listNomes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listNomesMouseClicked(evt);
            }
        });
        listNomes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                listNomesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                listNomesKeyReleased(evt);
            }
        });
        scrolLista.setViewportView(listNomes);

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

        txtFormacao.setColumns(20);
        txtFormacao.setRows(5);
        scrolObs.setViewportView(txtFormacao);

        jLabel15.setText("Descreva brevemente a formação do Colaborador:");

        txtBuscaColaborador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscaColaboradorKeyReleased(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5739_Knob_Search.png"))); // NOI18N

        gpSexo.add(rbFeminino);
        rbFeminino.setText("Feminino");
        rbFeminino.setActionCommand("2");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5750_Knob_Cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar Operação");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel18.setText("Especialidade:");

        cbxEspecialidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cabelo Feminino", "Cabelo Masculino", "Tintura", "Colorimetria", "Unhas", "Pele" }));

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5113_add_48.png"))); // NOI18N
        btnNovo.setToolTipText("Adicionar Novo Colaborador");
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
                .addGap(43, 43, 43)
                .addComponent(btnAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNovo)
                .addGap(37, 37, 37)
                .addComponent(btnDesbloquear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnDesbloquear, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnCadastroLayout = new javax.swing.GroupLayout(pnCadastro);
        pnCadastro.setLayout(pnCadastroLayout);
        pnCadastroLayout.setHorizontalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscaColaborador))
                    .addComponent(scrolLista, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addComponent(spnIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(3, 3, 3)
                                .addComponent(rbMasculino)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbFeminino)
                                .addGap(73, 73, 73)))
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spnN, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(scrolObs)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCadastroLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar))
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxEspecialidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel15))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnCadastroLayout.setVerticalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCadastroLayout.createSequentialGroup()
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnCadastroLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtBuscaColaborador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(scrolLista))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnCadastroLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(spnIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(rbMasculino)
                            .addComponent(jLabel6)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbFeminino))
                        .addGap(28, 28, 28)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(cbxEspecialidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrolObs, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)))
                .addGap(23, 23, 23))
        );

        tpnCadastrar.addTab("Cadastrar", pnCadastro);

        pnExibir.setBackground(new java.awt.Color(204, 255, 204));
        pnExibir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblColaborador.setBackground(new java.awt.Color(153, 255, 153));
        tblColaborador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "CPF", "Sexo", "Idade", "Telefone", "Especialidade", "Rua", "Nº", "Cidade", "UF"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblColaborador);
        if (tblColaborador.getColumnModel().getColumnCount() > 0) {
            tblColaborador.getColumnModel().getColumn(0).setPreferredWidth(110);
            tblColaborador.getColumnModel().getColumn(1).setPreferredWidth(80);
            tblColaborador.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblColaborador.getColumnModel().getColumn(3).setPreferredWidth(5);
            tblColaborador.getColumnModel().getColumn(4).setPreferredWidth(85);
            tblColaborador.getColumnModel().getColumn(5).setPreferredWidth(90);
            tblColaborador.getColumnModel().getColumn(6).setPreferredWidth(77);
            tblColaborador.getColumnModel().getColumn(7).setPreferredWidth(10);
            tblColaborador.getColumnModel().getColumn(8).setPreferredWidth(70);
            tblColaborador.getColumnModel().getColumn(9).setPreferredWidth(5);
        }

        pnExibir.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 912, 380));

        jLabel7.setText("Colaboradores Cadastrados");
        pnExibir.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        btnAltera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_edit.png"))); // NOI18N
        btnAltera.setText("Alterar Colaborador");
        btnAltera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlteraActionPerformed(evt);
            }
        });
        pnExibir.add(btnAltera, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));

        btnDeleta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_delete.png"))); // NOI18N
        btnDeleta.setText("Excluir Colaborador");
        btnDeleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletaActionPerformed(evt);
            }
        });
        pnExibir.add(btnDeleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 470, -1, -1));

        btnBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5739_Knob_Search.png"))); // NOI18N
        btnBusca.setText("Buscar");
        btnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaActionPerformed(evt);
            }
        });
        pnExibir.add(btnBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, -1, 30));

        gpBusca.add(rbNome);
        rbNome.setText("Nome");
        rbNome.setActionCommand("1");
        rbNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNomeActionPerformed(evt);
            }
        });
        pnExibir.add(rbNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, -1, -1));

        try {
            txtBCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtBCpf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBCpf.setEnabled(false);
        txtBCpf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBCpfFocusLost(evt);
            }
        });
        pnExibir.add(txtBCpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 240, 30));

        gpBusca.add(rbCpf);
        rbCpf.setText("CPF");
        rbCpf.setActionCommand("2");
        rbCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCpfActionPerformed(evt);
            }
        });
        pnExibir.add(rbCpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, -1, -1));

        txtBNome.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtBNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBNomeActionPerformed(evt);
            }
        });
        pnExibir.add(txtBNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 240, 30));

        jLabel17.setText("Buscar Clientes por:");
        pnExibir.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, -1));

        btnCancelarBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5744_Knob_Refresh.png"))); // NOI18N
        btnCancelarBusca.setText("Cancelar Busca");
        btnCancelarBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarBuscaActionPerformed(evt);
            }
        });
        pnExibir.add(btnCancelarBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 470, -1, -1));

        tpnCadastrar.addTab("Exibir", pnExibir);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnCadastrar)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnCadastrar)
        );

        setBounds(200, 100, 949, 599);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBNomeActionPerformed

    private void rbNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNomeActionPerformed
        txtBNome.setText("");
        txtBCpf.setText("");
        txtBNome.setEnabled(true);
        txtBNome.setVisible(true);
        
        txtBCpf.setEnabled(false);
        txtBCpf.setVisible(false);
        
        txtBNome.grabFocus();
    }//GEN-LAST:event_rbNomeActionPerformed

    private void rbCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCpfActionPerformed
        txtBNome.setText("");
        txtBCpf.setText("");
        
        txtBNome.setEnabled(false);
        txtBNome.setVisible(false);
        
        txtBCpf.setEnabled(true);
        txtBCpf.setVisible(true);
        
        txtBCpf.grabFocus();
    }//GEN-LAST:event_rbCpfActionPerformed

    private void btnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaActionPerformed
        if(gpBusca.getSelection().getActionCommand() == "1"){
            carregaTabelaColaboradors(txtBNome.getText(), 1);
        }else if(gpBusca.getSelection().getActionCommand() == "2"){
            carregaTabelaColaboradors(txtBCpf.getText(), 2);
        }
        
        
    }//GEN-LAST:event_btnBuscaActionPerformed

    private void txtBCpfFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBCpfFocusLost
         try {
            txtBCpf.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(IFrmColaboradores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtBCpfFocusLost

    private void btnDeletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletaActionPerformed
         int linha = tblColaborador.getSelectedRow();
         int[] ids = new int[4];

        if(rbNome.isSelected()){
            ids = pegarIdPorLinha(linha,txtBNome.getText(), 1 );
        }else if(rbCpf.isSelected()){
            ids = pegarIdPorLinha(linha,txtBCpf.getText(), 2 );
        }else{
            ids = pegarIdPorLinha(linha,null, 0 );
        }
         
            
        Object[] op = { "Cancelar", "Confirmar" };
        if(linha >= 0){
            int opc = JOptionPane.showOptionDialog(IFrmColaboradores.this,"Deseja Confirmar a Exclusção do Colaborador: "+dtm.getValueAt(linha, 0).toString(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, op, op[1]);
           
            if(opc == 1){
                Cidades ci = new Cidades();
                ci.setIdCidade(1);

                Endereco e = new Endereco(ci, "", 0, "");
                e.setIdEndereco(ids[2]);


                Pessoa p = new Pessoa(e, "", "", "");
                p.setIdPessoa(ids[1]);
                
                Usuarios u = new Usuarios("", "", "");
                u.setIdUser(ids[3]);


                Colaborador c = new Colaborador(p,u, 0, "");
                c.setIdColaborador(ids[0]);


                    Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                try{
                    
                    s.beginTransaction();

                    s.delete(c);
                    s.delete(p);
                    s.delete(e);
                    s.delete(u);

                    s.getTransaction().commit();

                    txtBCpf.setText("");
                    txtBNome.setText("");

                    txtBCpf.setEnabled(false);
                    txtBNome.setEnabled(false);

                    gpBusca.clearSelection();

                    carregaTabelaColaboradors(null, 0);
                    carregarListaNomes(null);



                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Não pode excluir este colaborador pos existe referencias deste colaborador em outro cadastros");
                    s.close();
                }
            }
            
        }else {
            JOptionPane.showMessageDialog(IFrmColaboradores.this, "Escolha um Colaborador para Excluir", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
        }
         
    }//GEN-LAST:event_btnDeletaActionPerformed

    private void btnCancelarBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarBuscaActionPerformed
        txtBCpf.setText("");
        txtBNome.setText("");
                
        txtBCpf.setEnabled(false);
        txtBNome.setEnabled(false);
                
        gpBusca.clearSelection();
                
        carregaTabelaColaboradors(null, 0);
        carregarListaNomes(null);
    }//GEN-LAST:event_btnCancelarBuscaActionPerformed

    private void btnAlteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlteraActionPerformed
        limparCampos();
        
        listNomes.setEnabled(false);
        listNomes.enable(false);
        habilitarBotao(btnNovo);
        desabilitarBotao(btnSalvar);
        
        txtBuscaColaborador.setEnabled(false);
        
        if(rbNome.isSelected() && tblColaborador.getSelectedRow() >=0 ){    
            preencherCampos(tblColaborador.getSelectedRow(),txtBNome.getText(), 1);
            preemcherFrmAcesso(tblColaborador.getSelectedRow(),txtBNome.getText(), 1);
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else if(rbCpf.isSelected() && tblColaborador.getSelectedRow() >=0 ){
            preencherCampos(tblColaborador.getSelectedRow(),txtBCpf.getText(), 2 );
            preemcherFrmAcesso(tblColaborador.getSelectedRow(),txtBCpf.getText(), 2 );
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else if(tblColaborador.getSelectedRow() >=0 ){
            preencherCampos(tblColaborador.getSelectedRow(), null, 0 );
            preemcherFrmAcesso(tblColaborador.getSelectedRow(), null, 0 );
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else {
            JOptionPane.showMessageDialog(IFrmColaboradores.this, "Escolha um Colaborador para Alterar", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
            operacao = 0;
        }
        
        
        
    }//GEN-LAST:event_btnAlteraActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        carregaTabelaColaboradors(null, 0);
        carregarListaNomes(null);

        Object[] op = { "Não", "Sim" };
        int opc = 0;

        if(operacao == 1){
            opc = JOptionPane.showOptionDialog(IFrmColaboradores.this,"Deseja Cancelar a Operação:\nNovo Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){

                limparCampos();
                desabilitarCampos();
                listNomes.setEnabled(true);
                txtBuscaColaborador.setEnabled(true);
                desabilitarBotao(btnSalvar);
                habilitarBotao(btnNovo);
                operacao = 0;

            }

        }else if(operacao == 2 || operacao == 3){
            opc = JOptionPane.showOptionDialog(IFrmColaboradores.this,"Deseja Cancelar a Operação:\nAlterar Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){
                limparCampos();
                desabilitarCampos();
                listNomes.setEnabled(true);
                txtBuscaColaborador.setEnabled(true);
                desabilitarBotao(btnAlterar);
                desabilitarBotao(btnDesbloquear);
                habilitarBotao(btnNovo);

                operacao = 0;
            }
        }else if(operacao == 0){
            JOptionPane.showMessageDialog(IFrmColaboradores.this, "Nenhuma Operaçao foi iniciada");
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnDesbloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesbloquearActionPerformed
        habilitarCampos();
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);
        listNomes.setEnabled(false);
        txtBuscaColaborador.setEnabled(false);
        habilitarBotao(btnAlterar);
        operacao = 2;
    }//GEN-LAST:event_btnDesbloquearActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        frmAcesso.setVisible(true);
        frmAcesso.setLocationRelativeTo(null);
        frmAcesso.toFront();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        if(txtNome.getText().equals("") || txtCpf.getText().equals("") || ((int)spnIdade.getValue() <= 0 || (int)spnIdade.getValue() == 100  ) || txtCep.getText().equals("")){

            JOptionPane.showMessageDialog(IFrmColaboradores.this, "Alguns Campos Obrigatórios Não foram Preenchidos", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        }else {
            int opc = JOptionPane.showOptionDialog(IFrmColaboradores.this,"Deseja Alterar o Colaborador: "+txtNome.getText(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
            if(opc == 1){
                    
                frmAcesso.setVisible(true);
                frmAcesso.setLocationRelativeTo(null);
                frmAcesso.toFront();
                
            }else if(JOptionPane.showOptionDialog(IFrmColaboradores.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
                btnCancelarActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        operacao = 1;
        habilitarCampos();
        carregaEstados();
        limparCampos();
        listNomes.setEnabled(false);
        txtBuscaColaborador.setEnabled(false);
        habilitarBotao(btnSalvar);
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);
        
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtBuscaColaboradorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscaColaboradorKeyReleased
        carregarListaNomes(txtBuscaColaborador.getText());
    }//GEN-LAST:event_txtBuscaColaboradorKeyReleased

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

    private void listNomesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listNomesKeyReleased
        if(operacao == 0){
            limparCampos();
            if(txtBuscaColaborador.getText().equals("")){
                preencherCampos(listNomes.getSelectedIndex(), null, 0);
                preemcherFrmAcesso(listNomes.getSelectedIndex(), null, 0);
            }else {
                preencherCampos(listNomes.getSelectedIndex(), txtBuscaColaborador.getText(), 1);
                preemcherFrmAcesso(listNomes.getSelectedIndex(), txtBuscaColaborador.getText(), 1);
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listNomesKeyReleased

    private void listNomesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listNomesKeyPressed
        if(operacao == 0){
            limparCampos();
            if(txtBuscaColaborador.getText().equals("")){
                preencherCampos(listNomes.getSelectedIndex(), null, 0);
                preemcherFrmAcesso(listNomes.getSelectedIndex(), null, 0);
            }else {
                preencherCampos(listNomes.getSelectedIndex(), txtBuscaColaborador.getText(), 1);
                preemcherFrmAcesso(listNomes.getSelectedIndex(), txtBuscaColaborador.getText(), 1);
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listNomesKeyPressed

    private void listNomesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listNomesMouseClicked
        if(operacao == 0){
            limparCampos();
            if(txtBuscaColaborador.getText().equals("")){
                preencherCampos(listNomes.getSelectedIndex(), null, 0);
                preemcherFrmAcesso(listNomes.getSelectedIndex(), null, 0);
            }else {
                preencherCampos(listNomes.getSelectedIndex(), txtBuscaColaborador.getText(), 1);
                preemcherFrmAcesso(listNomes.getSelectedIndex(), txtBuscaColaborador.getText(), 1);
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listNomesMouseClicked

    private void txtCpfFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCpfFocusLost
        String cpf1 = "", cpf2 = "", cpf3 = "", cpf4 = "", cpfFinal = "";

        cpf1 = txtCpf.getText().substring(0, 3);
        cpf2 = txtCpf.getText().substring(4, 7);
        cpf3 = txtCpf.getText().substring(8, 11);
        cpf4 = txtCpf.getText().substring(12, 14);

        cpfFinal = cpf1+cpf2+cpf3+cpf4;
        
        if(validaCpfUnico(txtCpf.getText())){
            
            if(!verifica.testaCPF(cpfFinal)){
                JOptionPane.showMessageDialog(IFrmColaboradores.this, "CPF Inválido \nPor favor Digite Novamente", "Atenção", JOptionPane.WARNING_MESSAGE);
                txtCpf.grabFocus();
                
                
            }
            
        }else {
            JOptionPane.showMessageDialog(IFrmColaboradores.this, "CPF Já Cadastro na base de dados\n\tPor favor digite um outro CPF", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtCpf.grabFocus();
        }

    }//GEN-LAST:event_txtCpfFocusLost

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed

        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };
        String acesso = "";
        
        if(operacao == 1){
            
            int opc = JOptionPane.showOptionDialog(IFrmColaboradores.this,"Deseja finalizar o cadastro do Colaborador: "+txtNome.getText(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
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

                    Pessoa p = new Pessoa(e, txtNome.getText(), txtCpf.getText(), txtTelefone.getText());
                    p.setRg(txtRg.getText());
                    p.setObservacoes(txtFormacao.getText());

                    s.save(p);

                    if(cbxAcesso.getSelectedItem().equals("Normal")){
                        acesso = "NRM";
                    }else if(cbxAcesso.getSelectedItem().equals("Administrativo")){
                        acesso = "ADM";
                    }else if(cbxAcesso.getSelectedItem().equals("Personalizado")){
                        chCadAg.setEnabled(true);
                        
                        chCadCli.setEnabled(true);
                        
                        chCadCol.setEnabled(true);
                        
                        chCadProd.setEnabled(true);
                        
                        chCadServ.setEnabled(true);
                        

                        acesso = "PSL";

                        if(chCadAg.isSelected()){
                            acesso += "CAG";
                        }
                        if(chCadCli.isSelected()){
                            acesso += "CCL";
                        }
                        if(chCadCol.isSelected()){
                            acesso += "CCO";
                        }
                        if(chCadProd.isSelected()){
                            acesso += "CPD";
                        }
                        if(chCadServ.isSelected()){
                            acesso += "CSV";
                        }
                    }

                    Usuarios u = new Usuarios(txtCpf.getText(), "beleza123", acesso);
                    s.save(u);

                    Colaborador col = new Colaborador(p, u, Integer.parseInt(spnIdade.getValue().toString()), cbxEspecialidade.getSelectedItem().toString());
                    col.setDescricaoFormacao(txtFormacao.getText());

                    if(gpSexo.getSelection().getActionCommand().equals("1")){
                        col.setSexo("Masculino");
                    }else{
                        col.setSexo("Feminino");
                    }

                    s.save(col);

                    s.getTransaction().commit();

                }catch(Exception erro){ erro.printStackTrace();}
                carregarListaNomes(null);
                carregaTabelaColaboradors(null, 0);
                limparCampos();
                desabilitarBotao(btnSalvar);
                desabilitarCampos();
                habilitarBotao(btnNovo);
                listNomes.setEnabled(true);
                txtBuscaColaborador.setEnabled(true);
                operacao = 0;

            }else if(JOptionPane.showOptionDialog(IFrmColaboradores.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
                btnCancelarActionPerformed(evt);
            }
        }else if(operacao == 2 || operacao == 3){
            int linha = 0;
            String alterarAcesso = "";
            int[] ids = new int[4];
            
            int opc = JOptionPane.showOptionDialog(IFrmColaboradores.this,"Deseja Confirmar as alterações no Colaborador: "+txtNome.getText(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){
                String alergia = "0";
                Estado es = new Estado();
                es.setSigla(cbxEstados.getSelectedItem().toString());
                Cidades c = new Cidades(buscaIdCidade(cbxCidades.getSelectedItem().toString()), es, cbxCidades.getSelectedItem().toString());
                Endereco e = new Endereco(c, txtRua.getText(), Integer.parseInt(spnN.getValue().toString()), txtCep.getText());
                e.setBairro(txtBairro.getText());


                if(operacao == 2){
                    linha = listNomes.getSelectedIndex();

                    if(txtBuscaColaborador.getText().equals("")){
                        ids = pegarIdPorLinha(linha,null, 0);
                    }else{
                        ids = pegarIdPorLinha(linha,txtBuscaColaborador.getText(), 1 );
                    }

                }else if(operacao == 3){
                    linha = tblColaborador.getSelectedRow();

                    if(rbNome.isSelected()){
                        ids = pegarIdPorLinha(linha,txtBNome.getText(), 1 );
                    }else if(rbCpf.isSelected()){
                        ids = pegarIdPorLinha(linha,txtBCpf.getText(), 2 );
                    }else{
                        ids = pegarIdPorLinha(linha,null, 0 );
                    }
                }

                if(linha >= 0){
                    try{
                        Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                        s.beginTransaction();

                        e.setIdEndereco(ids[2]);
                        s.merge(e);

                        Pessoa p = new Pessoa(e, txtNome.getText(), txtCpf.getText(), txtTelefone.getText());
                        p.setRg(txtRg.getText());
                        p.setObservacoes(txtFormacao.getText());

                        p.setIdPessoa(ids[1]);
                        s.merge(p);
                        
                        if(cbxAcesso.getSelectedItem().equals("Normal")){
                            alterarAcesso = "NRM";
                        }else if(cbxAcesso.getSelectedItem().equals("Administrativo")){
                            alterarAcesso = "ADM";
                        }else if(cbxAcesso.getSelectedItem().equals("Personalizado")){
                            chCadAg.setEnabled(true);

                            chCadCli.setEnabled(true);

                            chCadCol.setEnabled(true);

                            chCadProd.setEnabled(true);
                            
                            chCadServ.setEnabled(true);
                            

                            alterarAcesso = "PSL";

                            if(chCadAg.isSelected()){
                                alterarAcesso += "CAG";
                            }
                            if(chCadCli.isSelected()){
                                alterarAcesso += "CCL";
                            }
                            
                            if(chCadCol.isSelected()){
                                alterarAcesso += "CCO";
                            }
                            
                            if(chCadProd.isSelected()){
                                alterarAcesso += "CPD";
                            }
                            
                            if(chCadServ.isSelected()){
                                alterarAcesso += "CSV";
                            }
                            
                        }

                        Usuarios u = new Usuarios(txtCpf.getText(), txtSenha.getText(), alterarAcesso);
                        u.setIdUser(ids[3]);
                    
                        s.merge(u);

                        Colaborador col = new Colaborador(p,u, Integer.parseInt(spnIdade.getValue().toString()), cbxEspecialidade.getSelectedItem().toString());
                        col.setDescricaoFormacao(txtFormacao.getText());

                        if(gpSexo.getSelection().getActionCommand().equals("1")){
                            col.setSexo("Masculino");
                        }else{
                            col.setSexo("Feminino");
                        }

                        col.setIdColaborador(ids[0]);
                        s.merge(col);

                        s.getTransaction().commit();

                    }catch(Exception erro){ erro.printStackTrace();}
                    carregarListaNomes(null);
                    carregaTabelaColaboradors(null, 0);
                    limparCampos();
                    desabilitarCampos();
                    desabilitarBotao(btnAlterar);
                    desabilitarBotao(btnDesbloquear);
                    listNomes.setEnabled(true);
                    txtBuscaColaborador.setEnabled(true);
                    habilitarBotao(btnNovo);

                    operacao = 0;
                    
                    JOptionPane.showMessageDialog(frmAcesso, "Colaborador Cadastrado com Sucesso");

                }
            }
        }
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void cbxAcessoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxAcessoActionPerformed

        if(cbxAcesso.getSelectedIndex() == 2){
            chCadAg.setEnabled(true);

            chCadCli.setEnabled(true);

            chCadCol.setEnabled(true);

            chCadProd.setEnabled(true);
            
            chCadServ.setEnabled(true);
            

            lblCad.setEnabled(true);
            
        }else{
            chCadAg.setEnabled(false);
            
            chCadCli.setEnabled(false);
            
            chCadCol.setEnabled(false);
            
            chCadProd.setEnabled(false);
            
            chCadServ.setEnabled(false);
            

            lblCad.setEnabled(false);
            
        }

    }//GEN-LAST:event_cbxAcessoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAltera;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnBusca;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarBusca;
    private javax.swing.JButton btnDeleta;
    private javax.swing.JButton btnDesbloquear;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbxAcesso;
    private javax.swing.JComboBox<String> cbxCidades;
    private javax.swing.JComboBox<String> cbxEspecialidade;
    private javax.swing.JComboBox<String> cbxEstados;
    private javax.swing.JCheckBox chCadAg;
    private javax.swing.JCheckBox chCadCli;
    private javax.swing.JCheckBox chCadCol;
    private javax.swing.JCheckBox chCadProd;
    private javax.swing.JCheckBox chCadServ;
    private javax.swing.JFrame frmAcesso;
    private javax.swing.ButtonGroup gpBusca;
    private javax.swing.ButtonGroup gpSexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCad;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JList<String> listNomes;
    private javax.swing.JPanel pnCadastro;
    private javax.swing.JPanel pnExibir;
    private javax.swing.JRadioButton rbCpf;
    private javax.swing.JRadioButton rbFeminino;
    private javax.swing.JRadioButton rbMasculino;
    private javax.swing.JRadioButton rbNome;
    private javax.swing.JScrollPane scrolLista;
    private javax.swing.JScrollPane scrolObs;
    private javax.swing.JSpinner spnIdade;
    private javax.swing.JSpinner spnN;
    private javax.swing.JTable tblColaborador;
    private javax.swing.JTabbedPane tpnCadastrar;
    private javax.swing.JFormattedTextField txtBCpf;
    private javax.swing.JTextField txtBNome;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtBuscaColaborador;
    private javax.swing.JFormattedTextField txtCep;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JTextArea txtFormacao;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtRg;
    private javax.swing.JTextField txtRua;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JFormattedTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
