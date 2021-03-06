package vo;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import util.EncripitarSenha;
import vo.excecao.UsuarioVOException;

@Entity
@SequenceGenerator(initialValue = 1, name = "seq_usuario", sequenceName = "seq_usuario")
public class UsuarioVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
	private Long idUsuario;

	private String nome;
	private String login;
	private String senha;
	private String email;
	private Boolean estadoLogado;
	private Double credito;

	@ManyToMany
	@JoinTable(name = "usuario_jogo")
	private List<JogoVO> jogos;

	@ManyToOne
	private UsuarioVO parent;

	@OneToMany(mappedBy = "parent")
	private Collection<UsuarioVO> children;

	@ManyToOne
	@JoinColumn(name = "idPedido")
	private List<PedidoVO> pedidos;

	public UsuarioVO getParent() {
		return parent;
	}

	public void setParent(UsuarioVO parent) {
		this.parent = parent;
	}

	public Collection<UsuarioVO> getChildren() {
		return children;
	}

	public void setChildren(Collection<UsuarioVO> children) {
		this.children = children;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws UsuarioVOException {
		if(nome.isEmpty())
			throw new UsuarioVOException(UsuarioVOException.NOMEOBRIGATORIO);
		
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) throws UsuarioVOException {
		if(login.isEmpty())
			throw new UsuarioVOException(UsuarioVOException.LOGINOBRIGATORIO);
		
		this.login = login;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws UsuarioVOException {
		if(senha.isEmpty())
			throw new UsuarioVOException(UsuarioVOException.SENHAOBRIGATORIO);
		
		this.senha = EncripitarSenha.encriptar(senha);
	}

	public Boolean getEstadoLogado() {
		return estadoLogado;
	}

	public void setEstadoLogado(Boolean estadoLogado) {
		this.estadoLogado = estadoLogado;
	}

	public Double getCredito() {
		return credito;
	}

	public void setCredito(Double credito) {
		this.credito = credito;
	}

	public List<JogoVO> getJogos() {
		return jogos;
	}

	public void setJogos(List<JogoVO> jogos) {
		this.jogos = jogos;
	}
	
	public boolean validarEmail(String email) {
		Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
		Matcher m = p.matcher(email);

		return m.find();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idUsuario == null) ? 0 : idUsuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		UsuarioVO other = (UsuarioVO) obj;
		
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
			
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		
		return true;
	}

}
