package prv.mark.project.batchclient.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import prv.mark.project.common.entity.ApplicationParameterEntity;
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
public class ApplicationParameterRowMapper implements RowMapper<ApplicationParameterEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationParameterRowMapper.class);

    /**
     * Maps a row from the APPLICATION_PARAMETER table to @{link ApplicationParameterEntity}.
     *
     * @param resultSet @{link ResultSet}
     * @param rowNumber integer
     * @return @{link BatteryOrderDto}
     * @throws SQLException
     */
    @Override
    public final ApplicationParameterEntity mapRow(final ResultSet resultSet, final int rowNumber) throws SQLException {
        BooleanToStringConverter boolConverter = new BooleanToStringConverter();
        ApplicationParameterEntity entity = new ApplicationParameterEntity();
        entity.setId(resultSet.getLong("id"));
        entity.setParameterKey(StringUtils.safeString(resultSet.getString("parameter_key")));
        entity.setParameterValue(StringUtils.safeString(resultSet.getString("parameter_value")));
        entity.setEnabled(boolConverter.convertToEntityAttribute(StringUtils.safeString(resultSet.getString("enabled"))));
        entity.setCreateDate(resultSet.getDate("create_date"));
        return entity;
    }

}
