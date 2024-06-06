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

public class ClienteRepositoryOracle {

    public static final String URL_CONNECTION = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        //ConexaoOracle.URL_CONNECTION;
    public static final String USER = "rm553791";
            //ConexaoOracle.USER;
    public static final String PASSWORD = "180298";
                //ConexaoOracle.PASSWORD;
    private static final String TB_NAME = "tb_cliente";
    private static final Map<String, String> TB_COLUMNS = Map.of(
        "id_clie", "id_clie",
        "nm_clie", "nm_clie",
        "CPF", "cpf",
        "EMAIL", "email",
        "SENHA", "senha",
        "dt_nasc", "dt_nasc",
        "dt_criacao", "dt_criacao"
    );

    private Connection getConnection() throws SQLException {
        LOGGER.info("Conectando ao banco de dados...");
        return DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
    }

    public ClienteRepositoryOracle() {
    }

    private void validateCPF(String cpf) throws InvalidStateException {
        CPFValidator validator = new CPFValidator();
        validator.assertValid(cpf);
    }

    public Cliente findById(int id) {
        Cliente cliente = null;
        try (
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "SELECT * FROM %s WHERE %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("ID")
                    )
            )
        ) {
            LOGGER.info("Executando query: SELECT * FROM " + TB_NAME + " WHERE " + TB_COLUMNS.get("ID") + " = " + id);
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                var _id = rs.getInt(TB_COLUMNS.get("id_clie"));
                var nome = rs.getString(TB_COLUMNS.get("nm_clie"));
                var cpf = rs.getInt(TB_COLUMNS.get("CPF"));
                var email = rs.getString(TB_COLUMNS.get("EMAIL"));
                var senha = rs.getString(TB_COLUMNS.get("SENHA"));
                var dt_nasc = rs.getDate(TB_COLUMNS.get("dt_nasc"));
                var dt_criacao = rs.getDate(TB_COLUMNS.get("dt_criacao"));
                cliente = new Cliente(_id, nome, cpf, email, senha, dt_nasc, dt_criacao);
                LOGGER.info("Cliente retornado com sucesso");
            } else {
                LOGGER.info("Cliente não encontrado com o ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar cliente: {0}", e.getMessage()));
        }

        return cliente;
    }

    public void create(Cliente cliente) {
        try {
            validateCPF(String.valueOf(cliente.getCpf())); // Valida CPF
            try (
                var conn = getConnection();
                var stmt = conn.prepareStatement(
                    "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)"
                        .formatted(
                            TB_NAME,
                            TB_COLUMNS.get("nm_clie"),
                            TB_COLUMNS.get("CPF"),
                            TB_COLUMNS.get("EMAIL"),
                            TB_COLUMNS.get("SENHA"),
                            TB_COLUMNS.get("dt_nasc")
                        )
                )
            ) {
                stmt.setString(1, cliente.getNm_clie());
                stmt.setInt(2, cliente.getCpf());
                stmt.setString(3, cliente.getEmail());
                stmt.setString(4, cliente.getSenha());
                stmt.setDate(5, cliente.getDt_nasc());
                var rs = stmt.executeUpdate();
                if (rs == 1) {
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

    public List<Cliente> readAll() {
        var lista = new ArrayList<Cliente>();
        try (
            var conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
                //getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME)
        ) {
            LOGGER.info("Executando query: SELECT * FROM " + TB_NAME);
            System.out.println("CHEGOU AQUI 1");
            var rs = stmt.executeQuery();
            System.out.println("CHEGOU AQUI 2");
            System.out.println(rs.next());
            while (rs.next()) {
                System.out.println("CHEGOU AQUI 3");
                LOGGER.info("Cliente encontrado: ID={}, Nome={}", rs.getInt(TB_COLUMNS.get("ID")), rs.getString(TB_COLUMNS.get("NOME")));
                lista.add(
                    new Cliente(
                        rs.getInt(TB_COLUMNS.get("id_clie")),
                        rs.getString(TB_COLUMNS.get("nm_clie")),
                        rs.getInt(TB_COLUMNS.get("CPF")),
                        rs.getString(TB_COLUMNS.get("EMAIL")),
                        rs.getString(TB_COLUMNS.get("SENHA")),
                        rs.getDate(TB_COLUMNS.get("dt_nasc")),
                        rs.getDate(TB_COLUMNS.get("dt_criacao"))
                    )
                );
            }
            LOGGER.info("Total de clientes retornados: {}", lista.size());
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar clientes: {0}", e.getMessage()));
        }

        return lista;
    }

    public int update(Cliente cliente) {
        try {
            validateCPF(String.valueOf(cliente.getCpf())); // Valida CPF
            try (
                var conn = getConnection();
                var stmt = conn.prepareStatement(
                    "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?"
                        .formatted(
                            TB_NAME,
                            TB_COLUMNS.get("nm_clie"),
                            TB_COLUMNS.get("CPF"),
                            TB_COLUMNS.get("EMAIL"),
                            TB_COLUMNS.get("SENHA"),
                            TB_COLUMNS.get("dt_nasc"),
                            TB_COLUMNS.get("id_clie")
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
                if (rs == 1) {
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

    public void delete(int id) {
        try (
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "DELETE FROM %s WHERE %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("id_clie")
                    )
            )
        ) {
            stmt.setInt(1, id);
            var rs = stmt.executeUpdate();
            if (rs == 1) {
                LOGGER.info("Cliente removido com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao deletar cliente: {0}", e.getMessage()));
        }
    }
}