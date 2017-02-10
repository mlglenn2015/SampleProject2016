package prv.mark.project.batchclient.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import prv.mark.project.common.entity.StockSymbol;
import prv.mark.project.common.entity.converter.BooleanToStringConverter;
import prv.mark.project.common.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Spring Row mapper example.
 * <p>
 * The RowMapper implementation uses a JDBC {@link ResultSet} to map the results.
 * </p>
 * @author mlglenn
 */
@Component
public class StockSymbolRowMapper implements RowMapper<StockSymbol> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationParameterRowMapper.class);

    /**
     * Maps a row from the STOCK_SYMBOL table to @{link ApplicationParameter}.
     *
     * @param resultSet @{link ResultSet}
     * @param rowNumber integer
     * @return @{link BatteryOrderDto}
     * @throws SQLException
     */
    @Override
    public final StockSymbol mapRow(final ResultSet resultSet, final int rowNumber) throws SQLException {
        StockSymbol entity = new StockSymbol();
        entity.setId(resultSet.getLong("id"));
        entity.setTickerSymbol(StringUtils.safeString(resultSet.getString("ticker_symbol")));
        return entity;
    }
}
