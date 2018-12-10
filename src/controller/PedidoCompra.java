package controller;
// Generated 17/11/2017 01:21:43 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PedidoCompra generated by hbm2java
 */
public class PedidoCompra  implements java.io.Serializable {


     private Integer idPedido;
     private Fornecedor fornecedor;
     private String nomeProduto;
     private double valorPedido;
     private Date dataPedido;
     private int transacao;
     private double quantidade;
     private double valorUnitario;
     private int status;
     private Set produtos = new HashSet(0);

    public PedidoCompra() {
    }
    
    
    public String[] getPedidoCompra(){
        
        return new String[]{
                String.valueOf(idPedido),
                nomeProduto,
                String.valueOf(quantidade),
                String.valueOf(valorUnitario),
                String.valueOf(valorPedido),
                fornecedor.getRazaoSocial(),
                String.valueOf(dataPedido),
        };
    }

	
    public PedidoCompra(Fornecedor fornecedor, String nomeProduto, double valorPedido, Date dataPedido, int transacao, double quantidade, double valorUnitario, int status) {
        this.fornecedor = fornecedor;
        this.nomeProduto = nomeProduto;
        this.valorPedido = valorPedido;
        this.dataPedido = dataPedido;
        this.transacao = transacao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.status = status;
    }
    public PedidoCompra(Fornecedor fornecedor, String nomeProduto, double valorPedido, Date dataPedido, int transacao, double quantidade, double valorUnitario, int status, Set produtos) {
       this.fornecedor = fornecedor;
       this.nomeProduto = nomeProduto;
       this.valorPedido = valorPedido;
       this.dataPedido = dataPedido;
       this.transacao = transacao;
       this.quantidade = quantidade;
       this.valorUnitario = valorUnitario;
       this.status = status;
       this.produtos = produtos;
    }
   
    public Integer getIdPedido() {
        return this.idPedido;
    }
    
    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }
    public Fornecedor getFornecedor() {
        return this.fornecedor;
    }
    
    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    public String getNomeProduto() {
        return this.nomeProduto;
    }
    
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public double getValorPedido() {
        return this.valorPedido;
    }
    
    public void setValorPedido(double valorPedido) {
        this.valorPedido = valorPedido;
    }
    public Date getDataPedido() {
        return this.dataPedido;
    }
    
    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }
    public int getTransacao() {
        return this.transacao;
    }
    
    public void setTransacao(int transacao) {
        this.transacao = transacao;
    }
    public double getQuantidade() {
        return this.quantidade;
    }
    
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
    public double getValorUnitario() {
        return this.valorUnitario;
    }
    
    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    public Set getProdutos() {
        return this.produtos;
    }
    
    public void setProdutos(Set produtos) {
        this.produtos = produtos;
    }




}


