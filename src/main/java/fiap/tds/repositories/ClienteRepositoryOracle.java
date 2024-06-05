package fiap.tds.repositories;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import fiap.tds.models.Cliente;
import fiap.tds.models.ConexaoOracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fiap.tds.Main.LOGGER;

public class ClienteRepositoryOracle extends ConexaoOracle {

    public static final String URL_CONNECTION = ConexaoOracle.URL_CONNECTION;
    public static final String USER = ConexaoOracle.USER;
    public static final String PASSWORD = ConexaoOracle.PASSWORD;
    private static final String TB_NAME = "tb_cliente";
    private static final Map<String, String> TB_COLUMNS = Map.of(
        "ID", "id_clie",
        "NOME", "nm_clie",
        "CPF", "cpf",
        "EMAIL", "email",
        "SENHA", "senha",
        "DATA_NASCIMENTO", "dt_nasc",
        "DATA_CRIACAO", "dt_criacao"
    );

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
    }

    public ClienteRepositoryOracle(){
    }

    private void validateCPF(String cpf) throws InvalidStateException {
        CPFValidator validator = new CPFValidator();
        validator.assertValid(cpf);
    }

    public Cliente findById(int id){
        var cliente = new Cliente();
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "SELECT * FROM %s WHERE %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("ID")
                    )
            )
        )
        {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if(rs.next()){
                var _id = rs.getInt(TB_COLUMNS.get("ID"));
                var nome = rs.getString(TB_COLUMNS.get("NOME"));
                var cpf = rs.getInt(TB_COLUMNS.get("CPF"));
                var email = rs.getString(TB_COLUMNS.get("EMAIL"));
                var senha = rs.getString(TB_COLUMNS.get("SENHA"));
                var dt_nasc = rs.getDate(TB_COLUMNS.get("DT_NASCIMENTO"));
                var dt_criacao = rs.getDate(TB_COLUMNS.get("DT_CRIACAO"));
                cliente = new Cliente(_id,nome,cpf,email,senha,dt_nasc,dt_criacao);
                LOGGER.info("Cliente retornado com sucesso");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar cliente: {0}", e.getMessage()));
        }

        return cliente;
    }

    public void create(Cliente cliente){
        try {
            validateCPF(String.valueOf(cliente.getCpf())); // Valida CPF
            try (
                var conn = getConnection();
                var stmt = conn.prepareStatement(
                    "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)"
                        .formatted(
                            TB_NAME,
                            TB_COLUMNS.get("NOME"),
                            TB_COLUMNS.get("CPF"),
                            TB_COLUMNS.get("EMAIL"),
                            TB_COLUMNS.get("SENHA"),
                            TB_COLUMNS.get("DATA_NASCIMENTO")
                        )
                )
            ) {
                stmt.setString(1, cliente.getNm_clie());
                stmt.setInt(2, cliente.getCpf());
                stmt.setString(3, cliente.getEmail());
                stmt.setString(4, cliente.getSenha());
                stmt.setDate(5, cliente.getDt_nasc());
                var rs = stmt.executeUpdate();
                if (rs == 1){
                    LOGGER.info("Cliente criado com sucesso!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.error(MessageFormat.format("Erro ao criar cliente: {0}", e.getMessage()));
            }
        } catch (InvalidStateException e) {
            LOGGER.error("CPF inválido: " + e.getInvalidMessages());
            throw new IllegalArgumentException("CPF inválido: " + e.getInvalidMessages());
        }
    }

    public List<Cliente> readAll(){
        var lista = new ArrayList<Cliente>();
        try (
            var conn = getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM "+TB_NAME)
        )
        {
            var rs = stmt.executeQuery();
            while (rs.next()){
                lista.add(
                    new Cliente(
                        rs.getInt(TB_COLUMNS.get("ID")),
                        rs.getString(TB_COLUMNS.get("NOME")),
                        rs.getInt(TB_COLUMNS.get("CPF")),
                        rs.getString(TB_COLUMNS.get("EMAIL")),
                        rs.getString(TB_COLUMNS.get("SENHA")),
                        rs.getDate(TB_COLUMNS.get("DT_NASCIMENTO")),
                        rs.getDate(TB_COLUMNS.get("DT_CRIACAO"))
                    )
                );
            }
            LOGGER.info("Clientes retornados com sucesso");
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar clientes: {0}", e.getMessage()));
        }

        return lista;
    }

    public int update(Cliente cliente){
        try {
            validateCPF(String.valueOf(cliente.getCpf())); // Valida CPF
            try (
                var conn = getConnection();
                var stmt = conn.prepareStatement(
                    "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?"
                        .formatted(
                            TB_NAME,
                            TB_COLUMNS.get("NOME"),
                            TB_COLUMNS.get("CPF"),
                            TB_COLUMNS.get("EMAIL"),
                            TB_COLUMNS.get("SENHA"),
                            TB_COLUMNS.get("DATA_NASCIMENTO"),
                            TB_COLUMNS.get("ID")
                        )
                )
            ) {
                stmt.setString(1, cliente.getNm_clie());
                stmt.setInt(2, cliente.getCpf());
                stmt.setString(3, cliente.getEmail());
                stmt.setString(4, cliente.getSenha());
                stmt.setDate(5, cliente.getDt_nasc());
                stmt.setInt(6, cliente.getId());
                var rs = stmt.executeUpdate();
                if (rs == 1){
                    LOGGER.info("Cliente atualizado com sucesso!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.error(MessageFormat.format("Erro ao atualizar cliente: {0}", e.getMessage()));
            }
        } catch (InvalidStateException e) {
            LOGGER.error("CPF inválido: " + e.getInvalidMessages());
            throw new IllegalArgumentException("CPF inválido: " + e.getInvalidMessages());
        }
        return 0;
    }

    public void delete(int id){
        try (
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "DELETE FROM %s WHERE %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("ID")
                    )
            )
        )
        {
            stmt.setInt(1, id);
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Cliente removido com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao deletar cliente: {0}", e.getMessage()));
        }
    }
}
