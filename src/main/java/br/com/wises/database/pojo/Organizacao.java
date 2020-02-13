package br.com.wises.database.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "organizacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Organizacao.findAll", query = "SELECT o FROM Organizacao o"),
    @NamedQuery(name = "Organizacao.findById", query = "SELECT o FROM Organizacao o WHERE o.id = :id"),
    @NamedQuery(name = "Organizacao.findByNome", query = "SELECT o FROM Organizacao o WHERE o.nome = :nome"),
    @NamedQuery(name = "Organizacao.findByIdOrganizacaoPai", query = "SELECT o FROM Organizacao o WHERE o.idOrganizacaoPai = :idOrganizacaoPai"),
    @NamedQuery(name = "Organizacao.findByTipoOrganizacao", query = "SELECT o FROM Organizacao o WHERE o.tipoOrganizacao = :tipoOrganizacao"),
    @NamedQuery(name = "Organizacao.findByDominio", query = "SELECT o FROM Organizacao o WHERE o.dominio = :dominio"),
    @NamedQuery(name = "Organizacao.findByAtivo", query = "SELECT o FROM Organizacao o WHERE o.ativo = :ativo"),
    @NamedQuery(name = "Organizacao.findByDataCriacao", query = "SELECT o FROM Organizacao o WHERE o.dataCriacao = :dataCriacao"),
    @NamedQuery(name = "Organizacao.findByDataAlteracao", query = "SELECT o FROM Organizacao o WHERE o.dataAlteracao = :dataAlteracao"),
    @NamedQuery(name = "Organizacao.findDominioLike", query = "SELECT o FROM Organizacao o WHERE o.dominio LIKE :dominio "),})
public class Organizacao implements Serializable {

    @Column(name = "ativo")
    private Boolean ativo;
    @OneToMany(mappedBy = "idOrganizacao")
    private Collection<Sala> salaCollection;

    @OneToMany(mappedBy = "idOrganizacao")
    private Collection<Usuario> usuarioCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "nome")
    private String nome;
    @Column(name = "id_organizacao_pai")
    private Integer idOrganizacaoPai;
    @Column(name = "tipo_organizacao")
    private Character tipoOrganizacao;
    @Size(max = 64)
    @Column(name = "dominio")
    private String dominio;
    @Column(name = "dataCriacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    @Column(name = "dataAlteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;

    public Organizacao() {
    }

    public Organizacao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdOrganizacaoPai() {
        return idOrganizacaoPai;
    }

    public void setIdOrganizacaoPai(Integer idOrganizacaoPai) {
        this.idOrganizacaoPai = idOrganizacaoPai;
    }

    public Character getTipoOrganizacao() {
        return tipoOrganizacao;
    }

    public void setTipoOrganizacao(Character tipoOrganizacao) {
        this.tipoOrganizacao = tipoOrganizacao;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    @Override
    public String toString() {
        return "br.com.wises.database.pojo.Organizacao[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @XmlTransient
    public Collection<Sala> getSalaCollection() {
        return salaCollection;
    }

    public void setSalaCollection(Collection<Sala> salaCollection) {
        this.salaCollection = salaCollection;
    }

}
