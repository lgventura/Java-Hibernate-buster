package view;

import java.awt.Dimension;

import org.hibernate.Session;
import org.hibernate.Query;

import dao.DAO_PROJETO;
import controller.Cliente;
import controller.Pessoa;
import controller.Usuarios;
import controller.Endereco;
import controller.Estado;
import controller.Cidades;
import controller.CorFio;
import controller.VerificaCPF;
import controller.Ficha;
import controller.ProblemasSaude;
import controller.ProdutosCasa;
import controller.TblAssocCor;
import controller.TblAssocCorId;
import controller.TblAssocProdCasa;
import controller.TblAssocProdCasaId;
import controller.TblAssocSaude;
import controller.TblAssocSaudeId;
import controller.TblAssocTpCabelo;
import controller.TblAssocTpCabeloId;
import controller.TipoCabelo;

import java.text.ParseException;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MSYSTEM
 */
public class IFrmClientes extends javax.swing.JInternalFrame {
    
    private DefaultListModel dlm ; //Usado p/Criação da lista com os dados vindo do BD
    private DefaultTableModel dtm; //Usado p/ Criação da Tabela com os dados vindo do BD
    private int operacao = 0; // Controle das operações que estão sendo executadas
    
    
    //Criação do array de checkBox para poder criar a Ficha da Anaminése
    javax.swing.JCheckBox chFichaTpCabelo[] = new JCheckBox[24];
    javax.swing.JCheckBox chFichaSaude[] = new JCheckBox[9];
    javax.swing.JCheckBox chFichaProdCasa[] = new JCheckBox[9];
    javax.swing.JCheckBox chFichaCor[] = new JCheckBox[11];
    
    
    private void limparCampos(){//Limpa todos os campos após o cadastro
        txtBairro.setText("");
        txtBuscaCliente.setText("");
        txtCep.setText("");
        txtCpf.setText("");
        txtNome.setText("");
        txtObs.setText("");
        txtRg.setText("");
        txtRua.setText("");
        txtTelefone.setText("");
        txtaDescAlergia.setText("");
        spnIdade.setValue(0);
        spnN.setValue(0);
        rbMasculino.setSelected(isSelected);
        chbAlergia.setSelected(false);
        txtaDescAlergia.setEnabled(false);
        lblAlergia.setEnabled(false);
        cbxEstados.setSelectedIndex(0);
        cbxCidades.removeAllItems();
        
        txtNome.requestFocus();
    }
    
    //Inicio dos metodos para criação de Ficha
    
    private void criarFichaTpCabelo(){//metodo para criar a ficha de tipo de cabelo
        String sql = "from TipoCabelo";//consulta a tabela tipo cabelo
        int aux = 1, x = 10, y = 60, linha = 1; // Variaveis para controle de tamanho e espaço no form
        
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            TipoCabelo c;
            
            while(i.hasNext()){
                c = (TipoCabelo)i.next();
                chFichaTpCabelo[aux].setText(c.getTipoCabelo()); //pega o texto do banco
                chFichaTpCabelo[aux].setBounds(x, y, 150, 15); // inidica os tamanhos e posição
                chFichaTpCabelo[aux].setSelected(false);
                
                if(linha == 6){
                    y += 30;
                    x = 10;
                    linha = 1;
                }else {
                    linha++;
                    x += 150;
                }
                
                frmFicha.add(chFichaTpCabelo[aux]); // adiciona ao formulario
                aux++;
                
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private void criarFichaSaude(){ // criação da ficha de saude, os passos são os mesmos da ficha anterior
        String sql = "from ProblemasSaude";
        int aux = 1, x = 10, y = 250, linha = 1;
        
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            ProblemasSaude p;
            
            while(i.hasNext()){
                p = (ProblemasSaude)i.next();
                chFichaSaude[aux].setText(p.getTipoProblema());
                chFichaSaude[aux].setBounds(x, y, 150, 15);
                chFichaSaude[aux].setSelected(false);
                
                if(linha == 6){
                    y += 30;
                    x = 10;
                    linha = 1;
                }else {
                    linha++;
                    x += 150;
                }
                
                frmFicha.add(chFichaSaude[aux]);
                aux++;
                
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private void criarFichaProdCasa(){ // criação da ficha de produtos que utiliza em casa
        String sql = "from ProdutosCasa";
        int aux = 1, x = 10, y = 370, linha = 1;
        
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            ProdutosCasa prod;
            
            while(i.hasNext()){
                prod = (ProdutosCasa)i.next();
                chFichaProdCasa[aux].setText(prod.getProdCasa());
                chFichaProdCasa[aux].setBounds(x, y, 150, 15);
                chFichaProdCasa[aux].setSelected(false);
                
                if(linha == 6){
                    y += 30;
                    x = 10;
                    linha = 1;
                }else {
                    linha++;
                    x += 150;
                }
                
                frmFicha.add(chFichaProdCasa[aux]);
                aux++;
                
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private void criarFichaCor(){ // criação da ficha sobre o tipo de cor do cabelo do cliente
        String sql = "from CorFio";
        int aux = 1, x = 10, y = 500, linha = 1;
        
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            CorFio c;
            
            while(i.hasNext()){
                c = (CorFio)i.next();
                chFichaCor[aux].setText(c.getCor());
                chFichaCor[aux].setBounds(x, y, 150, 15);
                chFichaCor[aux].setSelected(false);
                
                if(linha == 6){
                    y += 30;
                    x = 10;
                    linha = 1;
                }else {
                    linha++;
                    x += 150;
                }
                
                frmFicha.add(chFichaCor[aux]);
                aux++;
                
            }
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private void salvar(Object o){ // metodo para salvar os objetos
        Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        s.save(o);
        
        s.getTransaction().commit();
        
    }
    
    private void preencherCampos(int linha, String valor, int teste){// preencher os campos do formulario
        String sql = "from Cliente as c order by c.pessoa.nome ";
        int aux = 0;
        
        if(valor != null && teste == 1){// teste 1 indica q esta buscando por nome
            sql = " from Cliente as c where c.pessoa.nome like '%"+valor+"%' order by nome";
        }else if(valor != null && teste == 2){ // teste 2 indica q está buscando por cpf
            sql = " from Cliente as c where c.pessoa.cpfCnpj like '%"+valor+"%' order by nome";
        }
        
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
                    if(c.getAlergia() == Byte.parseByte("1")){
                        chbAlergia.setSelected(true);
                        txtaDescAlergia.setText(c.getDescricaoAlergia());
                    }else{
                        chbAlergia.setSelected(false);
                    }
                    txtRua.setText(c.getPessoa().getEndereco().getRua());
                    spnN.setValue(c.getPessoa().getEndereco().getNumero());
                    txtBairro.setText(c.getPessoa().getEndereco().getBairro());
                    txtCep.setText(c.getPessoa().getEndereco().getCep());
                    cbxEstados.removeAllItems();
                    cbxEstados.addItem(c.getPessoa().getEndereco().getCidades().getEstado().getSigla());
                    cbxCidades.removeAllItems();
                    cbxCidades.addItem(c.getPessoa().getEndereco().getCidades().getNomeCidade());
                    txtObs.setText(c.getPessoa().getObservacoes());
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private int[] pegarIdPorLinha(int linha, String valor, int teste){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from Cliente as c order by c.pessoa.nome ";
        int aux = 0;
        int[] ids = new int[3];
        
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
            
            Cliente c;
            
            while(i.hasNext()){
                c = (Cliente)i.next();
                if(aux == linha){
                    ids[0] = c.getIdCliente();
                    ids[1] = c.getPessoa().getIdPessoa();
                    ids[2] = c.getPessoa().getEndereco().getIdEndereco();
                }
                aux++;
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return ids; //Retorna um array com os ids de endereço, pessoa e cliente
    }
    
    private int pegarIdFicha(int cliente){//Busca o ID pelo numero da linha, já que os dados são ordenados pelo nome
        String sql = "from Ficha ";
        int id = 0;
        
        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Ficha f;
            
            while(i.hasNext()){
                f = (Ficha)i.next();
                   if(f.getCliente().getIdCliente() == cliente){
                       id = f.getIdFicha();
                   }
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return id; //Retorna um array com os ids de endereço, pessoa e cliente
    }
    
    private void habilitarCampos(){
        txtBairro.setEnabled(true);
        txtCep.setEnabled(true);
        txtCpf.setEnabled(true);
        txtNome.setEnabled(true);
        txtObs.setEnabled(true);
        txtRg.setEnabled(true);
        txtRua.setEnabled(true);
        txtTelefone.setEnabled(true);
        spnIdade.setEnabled(true);
        spnN.setEnabled(true);
        rbMasculino.setEnabled(true);
        rbFeminino.setEnabled(true);
        chbAlergia.setEnabled(true);
        if(chbAlergia.isSelected()){
            txtaDescAlergia.setEnabled(true);
        }
        cbxEstados.setEnabled(true);
        cbxCidades.setEnabled(true);
    }
    
    private void desabilitarCampos(){
        txtBairro.setEnabled(false);
        txtCep.setEnabled(false);
        txtCpf.setEnabled(false);
        txtNome.setEnabled(false);
        txtObs.setEnabled(false);
        txtRg.setEnabled(false);
        txtRua.setEnabled(false);
        txtTelefone.setEnabled(false);
        spnIdade.setEnabled(false);
        spnN.setEnabled(false);
        rbMasculino.setEnabled(false);
        rbFeminino.setEnabled(false);
        chbAlergia.setEnabled(false);
        if(!chbAlergia.isSelected()){
            txtaDescAlergia.setEnabled(false);
        }
        cbxEstados.setEnabled(false);
        cbxCidades.setEnabled(false);
    }
    
    private void habilitarBotao(JButton btn){
        btn.setEnabled(true);
    }
    
    private void desabilitarBotao(JButton btn){
        btn.setEnabled(false);
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
            
            dtm.setNumRows(0);
            
            Cliente c;
            
            while(i.hasNext()){
                c = (Cliente)i.next();
                dtm.addRow(c.getCliente());
            }
            
            s.getTransaction().commit();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
    }
    
    private boolean validaCpfUnico(String cpfTeste){ // validar se o CPF adicionado é Unico
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
    
    private void carregarListaNomes(String nome){// carrega a list ao canto esquerdo da tela com os nomes
       
        String sql = "from Cliente as c order by c.pessoa.nome";
        
        if(nome != null){
            sql = " from Cliente as c where c.pessoa.nome like '%"+nome+"%'";
        }
        
        dlm.removeAllElements();
        
        try{
           
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();
            
            Query q = s.createQuery(sql);
            List l = q.list();
            
            Iterator i = l.iterator();
            
            Cliente c;
            
            while(i.hasNext()){
                c = (Cliente)i.next();
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
    
    public IFrmClientes() {
        initComponents();
        
        listNomes.setModel(new DefaultListModel());
        dlm = (DefaultListModel) listNomes.getModel();
        dtm = (DefaultTableModel)tblCLiente.getModel();
        carregarListaNomes(null);
        carregaEstados();
        buscaEstados(cbxEstados.getSelectedItem().toString());
        carregaTabelaClientes(null, 0);
        lblAlergia.enable(false);
        txtaDescAlergia.enable(false);
        btnSalvar.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnDesbloquear.setEnabled(false);
        verifica = new VerificaCPF();
        
        //Inicializa os Arrays para a Ficha
        for(int aux=0; aux < 24; aux++){
            chFichaTpCabelo[aux] = new JCheckBox();
        }
        for(int aux=0; aux < 9; aux++){
            chFichaSaude[aux] = new JCheckBox();
        }
        for(int aux=0; aux < 9; aux++){
            chFichaProdCasa[aux] = new JCheckBox();
        }
        for(int aux=0; aux < 11; aux++){
            chFichaCor[aux] = new JCheckBox();
        }
        
        
        limparCampos();
        desabilitarCampos();
    }
    
    public void setPosicao() {// meteodo para o form iniciar ao meio da tela
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
        frmFicha = new javax.swing.JFrame();
        jLabel21 = new javax.swing.JLabel();
        btnFinalizar = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
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
        chbAlergia = new javax.swing.JCheckBox();
        scrolDesc = new javax.swing.JScrollPane();
        txtaDescAlergia = new javax.swing.JTextArea();
        lblAlergia = new javax.swing.JLabel();
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
        txtObs = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        txtBuscaCliente = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        rbFeminino = new javax.swing.JRadioButton();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnDesbloquear = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        pnExibir = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCLiente = new javax.swing.JTable();
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

        frmFicha.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frmFicha.setTitle("Ficha de Anaminése");
        frmFicha.setBackground(new java.awt.Color(255, 204, 255));
        frmFicha.setMinimumSize(new java.awt.Dimension(936, 653));

        jLabel21.setText("Detalhes sobre o Tipo de Cabelo:");

        btnFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5122_accepted_48.png"))); // NOI18N
        btnFinalizar.setText("Finalizar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        jLabel22.setText("Detalhes sobre a Saude do Cliente:");

        jLabel23.setText("Detalhes Sobre a Cor do Fio");

        jLabel24.setText("Detalhes sobre Tratamento que o Cliente Faz em Casa:");

        javax.swing.GroupLayout frmFichaLayout = new javax.swing.GroupLayout(frmFicha.getContentPane());
        frmFicha.getContentPane().setLayout(frmFichaLayout);
        frmFichaLayout.setHorizontalGroup(
            frmFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmFichaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmFichaLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 545, Short.MAX_VALUE)
                        .addComponent(btnFinalizar))
                    .addGroup(frmFichaLayout.createSequentialGroup()
                        .addGroup(frmFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addGap(113, 113, 113)
                .addComponent(jLabel23)
                .addGap(138, 138, 138))
        );

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setClosable(true);
        setIconifiable(true);
        setTitle("Clientes");
        setMaximumSize(new java.awt.Dimension(1121, 700));
        setMinimumSize(new java.awt.Dimension(1121, 700));
        setPreferredSize(new java.awt.Dimension(1121, 700));

        pnCadastro.setBackground(new java.awt.Color(204, 204, 255));
        pnCadastro.setMaximumSize(new java.awt.Dimension(1132, 600));
        pnCadastro.setMinimumSize(new java.awt.Dimension(1132, 600));
        pnCadastro.setName(""); // NOI18N
        pnCadastro.setPreferredSize(new java.awt.Dimension(1132, 600));

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
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCpfFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCpfFocusLost(evt);
            }
        });

        jLabel1.setText("Idade:");

        spnIdade.setModel(new javax.swing.SpinnerNumberModel());
        spnIdade.setToolTipText("");
        spnIdade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                spnIdadeFocusGained(evt);
            }
        });

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
        txtTelefone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTelefoneFocusGained(evt);
            }
        });

        chbAlergia.setText("Possui Alergia?");
        chbAlergia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbAlergiaActionPerformed(evt);
            }
        });

        txtaDescAlergia.setColumns(20);
        txtaDescAlergia.setRows(5);
        scrolDesc.setViewportView(txtaDescAlergia);

        lblAlergia.setText("Descrição da alergia:");

        listNomes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
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
        txtCep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCepFocusGained(evt);
            }
        });

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

        txtObs.setColumns(20);
        txtObs.setRows(5);
        scrolObs.setViewportView(txtObs);

        jLabel15.setText("Observações");

        txtBuscaCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscaClienteKeyReleased(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5739_Knob_Search.png"))); // NOI18N
        jLabel16.setToolTipText("Buscar Cliente pelo nome");

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

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_5113_add_48.png"))); // NOI18N
        btnNovo.setToolTipText("Cadastrar Novo");
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
        btnSalvar.setToolTipText("Salvar");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNovo)
                .addGap(18, 18, 18)
                .addComponent(btnDesbloquear, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDesbloquear, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnCadastroLayout = new javax.swing.GroupLayout(pnCadastro);
        pnCadastro.setLayout(pnCadastroLayout);
        pnCadastroLayout.setHorizontalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                    .addComponent(scrolLista))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrolDesc)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spnN, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel15)
                            .addComponent(chbAlergia)
                            .addComponent(lblAlergia)
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spnIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(3, 3, 3)
                                        .addComponent(rbMasculino)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rbFeminino)))
                                .addGap(73, 73, 73)
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnCadastroLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(103, 103, 103))
        );
        pnCadastroLayout.setVerticalGroup(
            pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCadastroLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(spnIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(rbMasculino)
                                    .addComponent(jLabel6)
                                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rbFeminino))))
                        .addGap(18, 18, 18)
                        .addComponent(chbAlergia)
                        .addGap(8, 8, 8)
                        .addComponent(lblAlergia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrolDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
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
                        .addGap(30, 30, 30)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrolObs, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCadastroLayout.createSequentialGroup()
                        .addGroup(pnCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addGroup(pnCadastroLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(txtBuscaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrolLista, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 60, Short.MAX_VALUE))
        );

        tpnCadastrar.addTab("Cadastrar", pnCadastro);

        pnExibir.setBackground(new java.awt.Color(204, 255, 204));
        pnExibir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblCLiente.setBackground(new java.awt.Color(153, 255, 153));
        tblCLiente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "CPF", "Sexo", "Idade", "Telefone", "Alergia?", "Rua", "Nº", "Cidade", "UF"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCLiente);
        if (tblCLiente.getColumnModel().getColumnCount() > 0) {
            tblCLiente.getColumnModel().getColumn(0).setPreferredWidth(120);
            tblCLiente.getColumnModel().getColumn(1).setPreferredWidth(80);
            tblCLiente.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblCLiente.getColumnModel().getColumn(3).setPreferredWidth(10);
            tblCLiente.getColumnModel().getColumn(4).setPreferredWidth(80);
            tblCLiente.getColumnModel().getColumn(5).setPreferredWidth(20);
            tblCLiente.getColumnModel().getColumn(6).setPreferredWidth(80);
            tblCLiente.getColumnModel().getColumn(7).setPreferredWidth(10);
            tblCLiente.getColumnModel().getColumn(8).setPreferredWidth(70);
            tblCLiente.getColumnModel().getColumn(9).setPreferredWidth(10);
        }

        pnExibir.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1020, 480));

        jLabel7.setText("Clientes Cadastrados");
        pnExibir.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 17, -1, -1));

        btnAltera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_edit.png"))); // NOI18N
        btnAltera.setText("Alterar Cliente");
        btnAltera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlteraActionPerformed(evt);
            }
        });
        pnExibir.add(btnAltera, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, -1, -1));

        btnDeleta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/app_delete.png"))); // NOI18N
        btnDeleta.setText("Excluir Cliente");
        btnDeleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletaActionPerformed(evt);
            }
        });
        pnExibir.add(btnDeleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 550, -1, -1));

        btnBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5739_Knob_Search.png"))); // NOI18N
        btnBusca.setText("Buscar");
        btnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaActionPerformed(evt);
            }
        });
        pnExibir.add(btnBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, -1, -1));

        gpBusca.add(rbNome);
        rbNome.setText("Nome");
        rbNome.setActionCommand("1");
        rbNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNomeActionPerformed(evt);
            }
        });
        pnExibir.add(rbNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 12, -1, -1));

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
        pnExibir.add(txtBCpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 240, 30));

        gpBusca.add(rbCpf);
        rbCpf.setText("CPF");
        rbCpf.setActionCommand("2");
        rbCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCpfActionPerformed(evt);
            }
        });
        pnExibir.add(rbCpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 12, -1, -1));

        txtBNome.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtBNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBNomeActionPerformed(evt);
            }
        });
        pnExibir.add(txtBNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 240, 30));

        jLabel17.setText("Buscar Clientes por:");
        pnExibir.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 15, -1, -1));

        btnCancelarBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Botoes_Site_5744_Knob_Refresh.png"))); // NOI18N
        btnCancelarBusca.setText("Cancelar Busca");
        btnCancelarBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarBuscaActionPerformed(evt);
            }
        });
        pnExibir.add(btnCancelarBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 550, -1, -1));

        tpnCadastrar.addTab("Exibir", pnExibir);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tpnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 1057, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnCadastrar, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        setBounds(200, 100, 1062, 709);
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
            carregaTabelaClientes(txtBNome.getText(), 1);
        }else if(gpBusca.getSelection().getActionCommand() == "2"){
            carregaTabelaClientes(txtBCpf.getText(), 2);
        }
        
        
    }//GEN-LAST:event_btnBuscaActionPerformed

    private void txtBCpfFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBCpfFocusLost
         
    }//GEN-LAST:event_txtBCpfFocusLost

    private void btnDeletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletaActionPerformed
        //botao para deletar os clientes 
        int linha = tblCLiente.getSelectedRow();
         int[] ids = new int[3];
         int idFIcha = 0;

        if(rbNome.isSelected()){
            ids = pegarIdPorLinha(linha,txtBNome.getText(), 1 );
        }else if(rbCpf.isSelected()){
            ids = pegarIdPorLinha(linha,txtBCpf.getText(), 2 );
        }else{
            ids = pegarIdPorLinha(linha,null, 0 );
        }
        
        idFIcha = pegarIdFicha(ids[0]);
         
            
        Object[] op = { "Cancelar", "Confirmar" };
        if(linha >= 0){
            int opc = JOptionPane.showOptionDialog(IFrmClientes.this,"Deseja Confirmar a Exclusção do Cliente: "+dtm.getValueAt(linha, 0).toString(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, op, op[1]);
           
            if(opc == 1){
                Cidades ci = new Cidades();
                ci.setIdCidade(1);

                Endereco e = new Endereco(ci, "", 0, "");
                e.setIdEndereco(ids[2]);


                Pessoa p = new Pessoa(e, "", "", "");
                p.setIdPessoa(ids[1]);
                
                Cliente c = new Cliente(p, 0, Byte.valueOf("0"));
                c.setIdCliente(ids[0]);
                
                
                Ficha f = new Ficha(c, Byte.valueOf("0"), Byte.valueOf("0"), Byte.valueOf("0"));
                f.setIdFicha(idFIcha);
                Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
                try{
                    
                    s.beginTransaction();
                    
                    c.excluir(idFIcha); // exclui os dados das tabelas associativas primeiro
                    c.excluirS(idFIcha);
                    c.excluirP(idFIcha);
                    c.excluirC(idFIcha);
                    s.delete(f);
                    s.delete(c);
                    s.delete(p);
                    s.delete(e);

                    s.getTransaction().commit();

                    txtBCpf.setText("");
                    txtBNome.setText("");

                    txtBCpf.setEnabled(false);
                    txtBNome.setEnabled(false);

                    gpBusca.clearSelection();

                    carregaTabelaClientes(null, 0);
                    carregarListaNomes(null);



                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Não pode Excluir o cliente pois existe referencia desre cliente em outros cadastros");
                    s.close();
                }
            }
            
        }else {
            JOptionPane.showMessageDialog(IFrmClientes.this, "Escolha um Cliente para Excluir", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
        }
         
    }//GEN-LAST:event_btnDeletaActionPerformed

    private void btnCancelarBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarBuscaActionPerformed
        txtBCpf.setText("");
        txtBNome.setText("");
                
        txtBCpf.setEnabled(false);
        txtBNome.setEnabled(false);
                
        gpBusca.clearSelection();
                
        carregaTabelaClientes(null, 0);
        carregarListaNomes(null);
    }//GEN-LAST:event_btnCancelarBuscaActionPerformed

    private void btnAlteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlteraActionPerformed
        limparCampos();
        
        listNomes.setEnabled(false);
        listNomes.enable(false);
        habilitarBotao(btnNovo);
        desabilitarBotao(btnSalvar);
        
        txtBuscaCliente.setEnabled(false);
        
        if(rbNome.isSelected() && tblCLiente.getSelectedRow() >=0 ){    
            preencherCampos(tblCLiente.getSelectedRow(),txtBNome.getText(), 1);
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else if(rbCpf.isSelected() && tblCLiente.getSelectedRow() >=0 ){
            preencherCampos(tblCLiente.getSelectedRow(),txtBCpf.getText(), 2 );
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else if(tblCLiente.getSelectedRow() >=0 ){
            preencherCampos(tblCLiente.getSelectedRow(), null, 0 );
            operacao = 3;
            btnDesbloquear.setEnabled(true);
            tpnCadastrar.setSelectedIndex(0);
        }else {
            JOptionPane.showMessageDialog(IFrmClientes.this, "Escolha um Cliente para Alterar", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
            operacao = 0;
        }
        
        
        
    }//GEN-LAST:event_btnAlteraActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        carregaTabelaClientes(null, 0);
        carregarListaNomes(null);

        Object[] op = { "Não", "Sim" };
        int opc = 0;

        if(operacao == 1){
            opc = JOptionPane.showOptionDialog(IFrmClientes.this,"Deseja Cancelar a Operação:\nNovo Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){

                limparCampos();
                desabilitarCampos();
                listNomes.setEnabled(true);
                txtBuscaCliente.setEnabled(true);
                desabilitarBotao(btnSalvar);
                habilitarBotao(btnNovo);
                operacao = 0;

            }

        }else if(operacao == 2 || operacao == 3){
            opc = JOptionPane.showOptionDialog(IFrmClientes.this,"Deseja Cancelar a Operação:\nAlterar Cadastro", "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

            if(opc == 1){
                limparCampos();
                desabilitarCampos();
                listNomes.setEnabled(true);
                txtBuscaCliente.setEnabled(true);
                desabilitarBotao(btnAlterar);
                desabilitarBotao(btnDesbloquear);
                habilitarBotao(btnNovo);

                operacao = 0;
            }
        }else if(operacao == 0){
            JOptionPane.showMessageDialog(IFrmClientes.this, "Nenhuma Operaçao foi iniciada");
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnDesbloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesbloquearActionPerformed
        habilitarCampos();
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);
        listNomes.setEnabled(false);
        txtBuscaCliente.setEnabled(false);
        habilitarBotao(btnAlterar);
        operacao = 2;
    }//GEN-LAST:event_btnDesbloquearActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        int linha = 0;
        int[] ids = new int[3];
        Object[] op = { "Cancelar", "Confirmar" };

        int opc = JOptionPane.showOptionDialog(IFrmClientes.this,"Deseja Confirmar as alterações no Cliente: "+txtNome.getText(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);

        if(opc == 1){
            String alergia = "0";
            Estado es = new Estado();
            es.setSigla(cbxEstados.getSelectedItem().toString());
            Cidades c = new Cidades(buscaIdCidade(cbxCidades.getSelectedItem().toString()), es, cbxCidades.getSelectedItem().toString());
            Endereco e = new Endereco(c, txtRua.getText(), Integer.parseInt(spnN.getValue().toString()), txtCep.getText());
            e.setBairro(txtBairro.getText());

            if(chbAlergia.isSelected()){
                alergia = "1";
            }else{
                alergia = "0";
            }

            if(operacao == 2){
                linha = listNomes.getSelectedIndex();

                if(txtBuscaCliente.getText().equals("")){
                    ids = pegarIdPorLinha(linha,null, 0);
                }else{
                    ids = pegarIdPorLinha(linha,txtBuscaCliente.getText(), 1 );
                }

            }else if(operacao == 3){
                linha = tblCLiente.getSelectedRow();

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
                    p.setObservacoes(txtObs.getText());

                    p.setIdPessoa(ids[1]);
                    s.merge(p);

                    Cliente cli = new Cliente(p, Integer.parseInt(spnIdade.getValue().toString()), Byte.parseByte(alergia));
                    if(chbAlergia.isSelected()){
                        cli.setDescricaoAlergia(txtaDescAlergia.getText());
                    }
                    if(gpSexo.getSelection().getActionCommand().equals("1")){
                        cli.setSexo("Masculino");
                    }else{
                        cli.setSexo("Feminino");
                    }

                    cli.setIdCliente(ids[0]);
                    s.merge(cli);

                    s.getTransaction().commit();

                }catch(Exception erro){ erro.printStackTrace();}
                carregarListaNomes(null);
                carregaTabelaClientes(null, 0);
                limparCampos();
                desabilitarCampos();
                desabilitarBotao(btnAlterar);
                desabilitarBotao(btnDesbloquear);
                listNomes.setEnabled(true);
                txtBuscaCliente.setEnabled(true);
                habilitarBotao(btnNovo);

                operacao = 0;

            }
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        Object[] op = { "Cancelar", "Confirmar" };
        Object[] op2 = { "Não", "Sim" };

        if(txtNome.getText().equals("") || txtCpf.getText().equals("") || ((int)spnIdade.getValue() <= 0 || (int)spnIdade.getValue() == 100  ) || txtCep.getText().equals("")){

            JOptionPane.showMessageDialog(IFrmClientes.this, "Alguns Campos Obrigatórios Não foram Preenchidos", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        }else {
            int opc = JOptionPane.showOptionDialog(IFrmClientes.this,"Deseja Salvar o cliente: "+txtNome.getText(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
            if(opc == 1){

                criarFichaTpCabelo();
                criarFichaSaude();
                criarFichaProdCasa();
                criarFichaCor();
                frmFicha.setLocationRelativeTo(null);
                frmFicha.setVisible(true);
                frmFicha.setEnabled(true);
                frmFicha.toFront();
                
                JOptionPane.showMessageDialog(frmFicha, "Cliente Cadastrado com sucesso\n\n Por favor Cadastre os dados da Ficha de Anaminése do cliente: "+txtNome.getText(),"Cadastro realizado", JOptionPane.INFORMATION_MESSAGE );

           }else if(JOptionPane.showOptionDialog(IFrmClientes.this,"Deseja Cancelar a Operação?", "Cancelar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op2, op2[1]) == 1) {
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
        txtBuscaCliente.setEnabled(false);
        habilitarBotao(btnSalvar);
        desabilitarBotao(btnNovo);
        desabilitarBotao(btnDesbloquear);

    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtBuscaClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscaClienteKeyReleased
        carregarListaNomes(txtBuscaCliente.getText());
    }//GEN-LAST:event_txtBuscaClienteKeyReleased

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
            if(txtBuscaCliente.getText().equals("")){
                preencherCampos(listNomes.getSelectedIndex(), null, 0);
            }else {
                preencherCampos(listNomes.getSelectedIndex(), txtBuscaCliente.getText(), 1);
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listNomesKeyReleased

    private void listNomesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listNomesKeyPressed
        limparCampos();
        if(txtBuscaCliente.getText().equals("")){
            preencherCampos(listNomes.getSelectedIndex(), null, 0);
        }else {
            preencherCampos(listNomes.getSelectedIndex(), txtBuscaCliente.getText(), 1);
        }

        habilitarBotao(btnDesbloquear);

    }//GEN-LAST:event_listNomesKeyPressed

    private void listNomesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listNomesMouseClicked
        if(operacao == 0){
            limparCampos();
            if(txtBuscaCliente.getText().equals("")){
                preencherCampos(listNomes.getSelectedIndex(), null, 0);
            }else {
                preencherCampos(listNomes.getSelectedIndex(), txtBuscaCliente.getText(), 1);
            }

            habilitarBotao(btnDesbloquear);
        }
    }//GEN-LAST:event_listNomesMouseClicked

    private void chbAlergiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbAlergiaActionPerformed
        if(chbAlergia.isSelected()){
            lblAlergia.setEnabled(true);
            txtaDescAlergia.setEnabled(true);
        }else{
            lblAlergia.setEnabled(false);
            txtaDescAlergia.setEnabled(false);
            txtaDescAlergia.setText("");
        }

    }//GEN-LAST:event_chbAlergiaActionPerformed

    private void txtCpfFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCpfFocusLost
        String cpf1 = "", cpf2 = "", cpf3 = "", cpf4 = "", cpfFinal = "";

        cpf1 = txtCpf.getText().substring(0, 3);
        cpf2 = txtCpf.getText().substring(4, 7);
        cpf3 = txtCpf.getText().substring(8, 11);
        cpf4 = txtCpf.getText().substring(12, 14);

        cpfFinal = cpf1+cpf2+cpf3+cpf4;
        
        if(validaCpfUnico(txtCpf.getText())){
            
            if(!verifica.testaCPF(cpfFinal)){
                JOptionPane.showMessageDialog(IFrmClientes.this, "CPF Inválido \nPor favor Digite Novamente", "Atenção", JOptionPane.WARNING_MESSAGE);
                txtCpf.grabFocus();
                
                
            }
            
        }else {
            JOptionPane.showMessageDialog(IFrmClientes.this, "CPF Já Cadastro na base de dados\n\tPor favor digite um outro CPF", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtCpf.grabFocus();
        }
    }//GEN-LAST:event_txtCpfFocusLost

    private void txtCpfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCpfFocusGained
        txtCpf.grabFocus();
    }//GEN-LAST:event_txtCpfFocusGained

    private void txtTelefoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefoneFocusGained
        txtTelefone.grabFocus();
    }//GEN-LAST:event_txtTelefoneFocusGained

    private void txtCepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCepFocusGained
        txtCep.grabFocus();
    }//GEN-LAST:event_txtCepFocusGained

    private void spnIdadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spnIdadeFocusGained
        
    }//GEN-LAST:event_spnIdadeFocusGained

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        
    Object[] op = { "Cancelar", "Confirmar" };

    int opc = JOptionPane.showOptionDialog(IFrmClientes.this,"Deseja confirmar os dados da Ficha?: "+txtNome.getText(), "Atenção!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[1]);
    if(opc == 1){

        
        String alergia = "0";
        Estado es = new Estado();
        es.setSigla(cbxEstados.getSelectedItem().toString());
        Cidades c = new Cidades(buscaIdCidade(cbxCidades.getSelectedItem().toString()), es, cbxCidades.getSelectedItem().toString());
        Endereco e = new Endereco(c, txtRua.getText(), Integer.parseInt(spnN.getValue().toString()), txtCep.getText());
        e.setBairro(txtBairro.getText());

        if(chbAlergia.isSelected()){
            alergia = "1";
        }else{
            alergia = "0";
        }

        try{
            Session s = DAO_PROJETO.getSessionFactory().getCurrentSession();
            s.beginTransaction();

            s.save(e);

            Pessoa p = new Pessoa(e, txtNome.getText(), txtCpf.getText(), txtTelefone.getText());
            p.setRg(txtRg.getText());
            p.setObservacoes(txtObs.getText());

            s.save(p);

            Cliente cli = new Cliente(p, Integer.parseInt(spnIdade.getValue().toString()), Byte.parseByte(alergia));
            if(chbAlergia.isSelected()){
                cli.setDescricaoAlergia(txtaDescAlergia.getText());
            }
            if(gpSexo.getSelection().getActionCommand().equals("1")){
                cli.setSexo("Masculino");
            }else{
                cli.setSexo("Feminino");
            }

            s.save(cli);


            Ficha f = new Ficha(cli, Byte.parseByte("1"), Byte.parseByte("1"), Byte.parseByte("1"));
            
            s.save(f);
            
            s.getTransaction().commit();

            TipoCabelo t = new TipoCabelo();
            TblAssocTpCabeloId i = new TblAssocTpCabeloId();
            TblAssocTpCabelo tpCabelo = new TblAssocTpCabelo();
            for(int aux = 1; aux < 24 ; aux++){

                if(chFichaTpCabelo[aux].isSelected()){
                    t = new TipoCabelo();
                    t.setIdTipo(aux);
                    t.setTipoCabelo("");
                    
                     i = new TblAssocTpCabeloId(aux, f.getIdFicha());
                     tpCabelo = new TblAssocTpCabelo(i, f, t);

                    salvar(tpCabelo);
                }
            }
            
            ProblemasSaude ps = new ProblemasSaude();
            TblAssocSaudeId is = new TblAssocSaudeId();
            TblAssocSaude tps = new TblAssocSaude();
            
            for(int aux = 1; aux < 9 ; aux++){

                if(chFichaSaude[aux].isSelected()){
                    ps = new ProblemasSaude();
                    ps.setIdProblemas(aux);
                    ps.setTipoProblema("");
                    
                     is = new TblAssocSaudeId(aux, f.getIdFicha());
                     tps = new TblAssocSaude(is, f, ps);

                    salvar(tps);
                }
            }
            
            ProdutosCasa pc = new ProdutosCasa();
            TblAssocProdCasaId ip = new TblAssocProdCasaId();
            TblAssocProdCasa tpc = new TblAssocProdCasa();
            for(int aux = 1; aux < 9 ; aux++){

                if(chFichaProdCasa[aux].isSelected()){
                    pc = new ProdutosCasa();
                    pc.setIdProdCasa(aux);
                    pc.setProdCasa("");
                    
                     ip = new TblAssocProdCasaId(aux, f.getIdFicha());
                     tpc = new TblAssocProdCasa(ip, f, pc);

                    salvar(tpc);
                }
            }
            
            CorFio cf = new CorFio();
            TblAssocCorId ic = new TblAssocCorId();
            TblAssocCor tpcf = new TblAssocCor();
            
            for(int aux = 1; aux < 11 ; aux++){

                if(chFichaCor[aux].isSelected()){
                    cf = new CorFio();
                    cf.setIdCor(aux);
                    cf.setCor("");
                    
                     ic = new TblAssocCorId(aux, f.getIdFicha());
                     tpcf = new TblAssocCor(ic, cf, f);

                    salvar(tpcf);
                }
            }
        

            }catch(Exception erro){ erro.printStackTrace();}
            carregarListaNomes(null);
            carregaTabelaClientes(null, 0);
            limparCampos();
            desabilitarBotao(btnSalvar);
            desabilitarCampos();
            habilitarBotao(btnNovo);
            listNomes.setEnabled(true);
            txtBuscaCliente.setEnabled(true);
            frmFicha.setVisible(false);
            frmFicha.setEnabled(false);
            frmFicha.toBack();
            operacao = 0;
            
            JOptionPane.showMessageDialog(IFrmClientes.this, "Cadastro de Cliente finalizado com sucesso");
            
        }else {
            JOptionPane.showMessageDialog(frmFicha, "Verifique as informações");
        } 
    }//GEN-LAST:event_btnFinalizarActionPerformed


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
    private javax.swing.JComboBox<String> cbxCidades;
    private javax.swing.JComboBox<String> cbxEstados;
    private javax.swing.JCheckBox chbAlergia;
    private javax.swing.JFrame frmFicha;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAlergia;
    private javax.swing.JList<String> listNomes;
    private javax.swing.JPanel pnCadastro;
    private javax.swing.JPanel pnExibir;
    private javax.swing.JRadioButton rbCpf;
    private javax.swing.JRadioButton rbFeminino;
    private javax.swing.JRadioButton rbMasculino;
    private javax.swing.JRadioButton rbNome;
    private javax.swing.JScrollPane scrolDesc;
    private javax.swing.JScrollPane scrolLista;
    private javax.swing.JScrollPane scrolObs;
    private javax.swing.JSpinner spnIdade;
    private javax.swing.JSpinner spnN;
    private javax.swing.JTable tblCLiente;
    private javax.swing.JTabbedPane tpnCadastrar;
    private javax.swing.JFormattedTextField txtBCpf;
    private javax.swing.JTextField txtBNome;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtBuscaCliente;
    private javax.swing.JFormattedTextField txtCep;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextArea txtObs;
    private javax.swing.JTextField txtRg;
    private javax.swing.JTextField txtRua;
    private javax.swing.JFormattedTextField txtTelefone;
    private javax.swing.JTextArea txtaDescAlergia;
    // End of variables declaration//GEN-END:variables
}
