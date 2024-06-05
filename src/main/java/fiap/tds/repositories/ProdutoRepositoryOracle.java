package fiap.tds.repositories;

import fiap.tds.models.Produto;
import fiap.tds.models.ConexaoOracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fiap.tds.Main.LOGGER;

public class ProdutoRepositoryOracle extends ConexaoOracle {

    public static final String URL_CONNECTION = ConexaoOracle.URL_CONNECTION;
    public static final String USER = ConexaoOracle.USER;
    public static final String PASSWORD = ConexaoOracle.PASSWORD;
    private static final String TB_NAME = "tb_produto";
    private static final Map<String, String> TB_COLUMNS = Map.of(
        "ID", "id_prod",
        "NOME", "nm_prod",
        "DESCRICAO", "desc_prod",
        "PRECO", "preco",
        "ESTOQUE", "estoque",
        "CATEGORIA", "categoria",
        "DT_CRIACAO", "dt_criacao_prod",
        "FK_LOJA", "fk_loja"
    );

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
    }

    public ProdutoRepositoryOracle(){
    }

    public Produto findById(int id){
        var produto = new Produto();
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
                var desc = rs.getString(TB_COLUMNS.get("DESCRICAO"));
                var preco = rs.getDouble(TB_COLUMNS.get("PRECO"));
                var estoque = rs.getInt(TB_COLUMNS.get("ESTOQUE"));
                var categoria = rs.getString(TB_COLUMNS.get("CATEGORIA"));
                var dt_criacao = rs.getDate(TB_COLUMNS.get("DT_CRIACAO"));
                var fk_loja = rs.getInt(TB_COLUMNS.get("FK_LOJA"));
                produto = new Produto(_id,nome,desc,preco,estoque,categoria,dt_criacao,fk_loja);
                LOGGER.info("Produto retornado com sucesso");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar produto: {0}", e.getMessage()));
        }

        return produto;
    }

    public void create(Produto produto){
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?)"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("NOME"),
                        TB_COLUMNS.get("DESCRICAO"),
                        TB_COLUMNS.get("PRECO"),
                        TB_COLUMNS.get("ESTOQUE"),
                        TB_COLUMNS.get("CATEGORIA"),
                        TB_COLUMNS.get("FK_LOJA")
                    )
            )
        )
        {
            stmt.setString(1, produto.getNm_prod());
            stmt.setString(2, produto.getDesc_prod());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setString(5, produto.getCategoria());
            stmt.setInt(6, produto.getFk_loja());
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Produto criado com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao criar produto: {0}", e.getMessage()));
        }
    }

    public List<Produto> readAll(){
        var lista = new ArrayList<Produto>();
        try (
            var conn = getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM "+TB_NAME)
        )
        {
            var rs = stmt.executeQuery();
            while (rs.next()){
                lista.add(
                    new Produto(
                        rs.getInt(TB_COLUMNS.get("ID")),
                        rs.getString(TB_COLUMNS.get("NOME")),
                        rs.getString(TB_COLUMNS.get("DESCRICAO")),
                        rs.getDouble(TB_COLUMNS.get("PRECO")),
                        rs.getInt(TB_COLUMNS.get("ESTOQUE")),
                        rs.getString(TB_COLUMNS.get("CATEGORIA")),
                        rs.getDate(TB_COLUMNS.get("DT_CRIACAO")),
                        rs.getInt(TB_COLUMNS.get("FK_LOJA"))
                    )
                );
            }
            LOGGER.info("Produtos retornados com sucesso");
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar produtos: {0}", e.getMessage()));
        }

        return lista;
    }

    public int update(Produto produto){
        try(
            var conn = getConnection();
            var stmt = conn.prepareStatement(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?"
                    .formatted(
                        TB_NAME,
                        TB_COLUMNS.get("NOME"),
                        TB_COLUMNS.get("DESCRICAO"),
                        TB_COLUMNS.get("PRECO"),
                        TB_COLUMNS.get("ESTOQUE"),
                        TB_COLUMNS.get("CATEGORIA"),
                        TB_COLUMNS.get("FK_LOJA"),
                        TB_COLUMNS.get("ID")
                    )
            )
        )
        {
            stmt.setString(1, produto.getNm_prod());
            stmt.setString(2, produto.getDesc_prod());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setString(5, produto.getCategoria());
            stmt.setInt(6, produto.getFk_loja());
            stmt.setInt(7, produto.getId());
            var rs = stmt.executeUpdate();
            if (rs == 1){
                LOGGER.info("Produto atualizado com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao atualizar produto: {0}", e.getMessage()));
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
                LOGGER.info("Produto removido com sucesso!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao deletar produto: {0}", e.getMessage()));
        }
    }
}
