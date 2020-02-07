package br.com.wises.database.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "reserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT a FROM Reserva a"),
    @NamedQuery(name = "Reserva.findById", query = "SELECT a FROM Reserva a WHERE a.id = :id"),
    @NamedQuery(name = "Reserva.findByIdSala", query = "SELECT a FROM Reserva a WHERE a.idSala = :idSala"),
    @NamedQuery(name = "Reserva.findByIdUsuario", query = "SELECT a FROM Reserva a WHERE a.idUsuario = :idUsuario"),
    @NamedQuery(name = "Reserva.findByDataHoraInicio", query = "SELECT a FROM Reserva a WHERE a.dataHoraInicio = :dataHoraInicio"),
    @NamedQuery(name = "Reserva.findByDataHoraFim", query = "SELECT a FROM Reserva a WHERE a.dataHoraFim = :dataHoraFim"),
    @NamedQuery(name = "Reserva.findByDescricao", query = "SELECT a FROM Reserva a WHERE a.descricao = :descricao"),
    @NamedQuery(name = "Reserva.findByDataCriacao", query = "SELECT a FROM Reserva a WHERE a.dataCriacao = :dataCriacao"),
    @NamedQuery(name = "Reserva.findByDataAlteracao", query = "SELECT a FROM Reserva a WHERE a.dataAlteracao = :dataAlteracao"),
    @NamedQuery(name = "Reserva.findByAtivo", query = "SELECT a FROM Reserva a WHERE a.ativo = :ativo")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_sala")
    private Integer idSala;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Size(max = 45)
    @Column(name = "nome_organizador")
    private String nomeOrganizador;
    @Column(name = "data_hora_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraInicio;
    @Column(name = "data_hora_fim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraFim;
    @Size(max = 45)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "dataCriacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    @Column(name = "dataAlteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;
    @Column(name = "ativo")
    private boolean ativo;

    public Reserva() {
    }

    public Reserva(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Date dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Date getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(Date dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getNomeOrganizador() {
        return nomeOrganizador;
    }

    public void setNomeOrganizador(String nomeOrganizador) {
        this.nomeOrganizador = nomeOrganizador;
    }
    
    @Override
    public String toString() {
        return "br.com.wises.database.pojo.Reserva[ id=" + id + " ]";
    }

}
