package com.blazebit.persistence.impl.dialect;

import java.util.Map;

import com.blazebit.persistence.spi.DbmsModificationState;
import com.blazebit.persistence.spi.DbmsStatementType;


public class HSQLDbmsDialect extends DefaultDbmsDialect {

    @Override
    public boolean supportsReturningColumns() {
        return true;
    }

    @Override
    public Map<String, String> appendExtendedSql(StringBuilder sqlSb, DbmsStatementType statementType, boolean isSubquery, StringBuilder withClause, String limit, String offset, String[] returningColumns, Map<DbmsModificationState, String> includedModificationStates) {
        if (isSubquery && returningColumns != null) {
            throw new IllegalArgumentException("Returning columns in a subquery is not possible for this dbms!");
        }
        
        return super.appendExtendedSql(sqlSb, statementType, isSubquery, withClause, limit, offset, returningColumns, includedModificationStates);
    }
	
}
