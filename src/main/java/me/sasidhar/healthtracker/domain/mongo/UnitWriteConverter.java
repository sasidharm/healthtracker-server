package me.sasidhar.healthtracker.domain.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import me.sasidhar.healthtracker.domain.Unit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * Created by Sasidhar on 4/4/15.
 */
@WritingConverter
public class UnitWriteConverter implements Converter<Unit, DBObject> {
    @Override
    public DBObject convert(Unit unit) {
        DBObject dbo = new BasicDBObject();
        dbo.put("unit", unit.getSymbol());
        return dbo;
    }
}
