package me.sasidhar.healthtracker.domain.mongo;

import com.mongodb.DBObject;
import me.sasidhar.healthtracker.domain.Unit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * Created by Sasidhar on 4/4/15.
 */
@ReadingConverter
public class UnitReadConverter implements Converter<DBObject, Unit> {

    @Override
    public Unit convert(DBObject dbObject) {
        String value = (String) dbObject.get("unit");
        return Unit.lookupBySymbol(value);
    }
}
